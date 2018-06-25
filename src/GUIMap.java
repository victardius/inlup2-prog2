import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIMap extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Collection<Position>> locationNames = new HashMap<>();
	private Map<Coordinates, Position> locationCoordinates = new HashMap<>();
	private Map<Category, Collection<Position>> locationCategory = new HashMap<>();
	private Collection<Position> selectionList = new ArrayList<>();
	private Collection<JButton> buttons = new ArrayList<>();
	private JScrollPane scroll = null;
	private DrawMap mapArea = null;
	private DrawMap map;
	private JButton newButton;
	private GetMousePosition ml = new GetMousePosition();
	private MarkerMouseActions m2 = new MarkerMouseActions();
	private Category[] cat = { Category.Bus, Category.Underground, Category.Train };
	private JList<Category> categoryList = new JList<>();
	private JRadioButton namedRadio, describedRadio;
	private boolean saved = true;
	private boolean placingLocation = false;
	private JTextField searchField;
	private String searchName;

	public GUIMap() {
		Box divideNorth = new Box(BoxLayout.PAGE_AXIS);
		add(divideNorth, BorderLayout.NORTH);

		categoryList.setListData(cat);

		setJMenuBar(new GUIArchive(this).getJMenuBar());

		JPanel north = new JPanel();
		divideNorth.add(north);
		newButton = new JButton("New");
		newButton.addActionListener(new NewPositionListener());
		north.add(newButton);
		buttons.add(newButton);
		namedRadio = new JRadioButton("Named", true);
		describedRadio = new JRadioButton("Described");
		ButtonGroup bg = new ButtonGroup();
		bg.add(namedRadio);
		bg.add(describedRadio);
		Box verticalBox = new Box(BoxLayout.PAGE_AXIS);
		verticalBox.add(namedRadio);
		verticalBox.add(describedRadio);
		north.add(verticalBox);
		searchField = new JTextField("Search", 10);
		north.add(searchField);
		searchField.addFocusListener(new FocusingListener());
		JButton searchButton = new JButton("Search");
		north.add(searchButton);
		buttons.add(searchButton);
		searchButton.addActionListener(new SearchListener());
		JButton hideButton = new JButton("Hide");
		north.add(hideButton);
		buttons.add(hideButton);
		hideButton.addActionListener(new HideListener());
		JButton removeButton = new JButton("Remove");
		north.add(removeButton);
		buttons.add(removeButton);
		removeButton.addActionListener(new RemoveListener());
		JButton coordinatesButton = new JButton("Coordinates");
		north.add(coordinatesButton);
		buttons.add(coordinatesButton);
		coordinatesButton.addActionListener(new CoordinatesListener());

		JPanel east = new JPanel();
		add(east, BorderLayout.EAST);
		Box eastLayout = new Box(BoxLayout.PAGE_AXIS);
		east.add(eastLayout);
		JPanel labelBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel categoriesLabel = new JLabel("Categories");
		labelBox.add(categoriesLabel);
		eastLayout.add(labelBox, BorderLayout.WEST);
		categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		categoryList.setFixedCellWidth(150);
		categoryList.setVisibleRowCount(10);
		eastLayout.add(new JScrollPane(categoryList));
		categoryList.addListSelectionListener(new ListListener());
		JButton hideCategoriesButton = new JButton("Hide categories");
		hideCategoriesButton.setAlignmentX(CENTER_ALIGNMENT);
		eastLayout.add(hideCategoriesButton);
		hideCategoriesButton.addActionListener(new HideCategoryListener());
		buttons.add(hideCategoriesButton);

		addWindowListener(new CloseWindowListener());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1000, 380);
		setLocationRelativeTo(null);
		setVisible(true);

		buttonSetActive(false);

	}

	private void buttonSetActive(boolean bool) {
		for (JButton b : buttons) {
			b.setEnabled(bool);
		}
	}

	public void setMap(String fileName) {
		if (map != null) {
			remove(scroll);
		}
		map = new DrawMap(fileName);
		mapArea = map;
		scroll = new JScrollPane(mapArea);
		add(scroll, BorderLayout.CENTER);
		pack();
		validate();
		mapArea.revalidate();
		mapArea.repaint();
		buttonSetActive(true);
	}

	public void addDescribedToLists(Coordinates coordinates, String name, Category category, String description) {

		DescribedPlace place = new DescribedPlace(coordinates, name, category, description);

		addToLists(place);

	}

	public void addNamedToLists(Coordinates coordinates, String name, Category category) {

		NamedPlace place = new NamedPlace(coordinates, name, category);

		addToLists(place);

	}

	private void addToLists(Position place) {
		saved = false;
		addToNamedList(place);

		locationCoordinates.put(place.getCoordinates(), place);

		addToCategoryList(place);

		paintLocation(place);
		placingLocation = false;
	}

	private void addToCategoryList(Position place) {
		Category c = place.getCategory();

		if (locationCategory.containsKey(c))
			locationCategory.get(c).add(place);
		else {
			Collection<Position> list = new ArrayList<>();
			list.add(place);
			locationCategory.put(c, list);
		}
	}

	private void addToNamedList(Position place) {
		if (locationNames.containsKey(place.getName().toLowerCase())) {
			locationNames.get(place.getName().toLowerCase()).add(place);
		} else {
			Collection<Position> list = new ArrayList<>();
			list.add(place);
			locationNames.put(place.getName().toLowerCase(), list);
		}
	}

	private void removeLocation(Position l) {
		locationNames.remove(l.getName());
		locationCoordinates.remove(l.getCoordinates());
		locationCategory.get(l.getCategory()).remove(l);
	}

	public Collection<Position> getLocations() {
		Collection<Position> list = locationCoordinates.values();
		return list;
	}

	public Map<String, Collection<Position>> getNameList() {
		return locationNames;
	}

	public Map<Coordinates, Position> getPositionList() {
		return locationCoordinates;
	}

	public boolean getSaved() {
		return saved;
	}

	public void setSaved(boolean b) {
		saved = b;
	}

	private void setMarked(Position l, boolean b) {
		if (b) {
			selectionList.remove(l);
			l.setBorder(null);
		} else {
			selectionList.add(l);
			l.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		}
	}

	public void hideMarked() {
		Collection<Position> list = new ArrayList<>();
		list.addAll(selectionList);
		for (Position l : list) {
			setMarked(l, true);
			l.setDisplayed(false);
		}
		selectionList.clear();
	}

	public void resetAll() {
		locationNames.clear();
		locationCoordinates.clear();
		selectionList.clear();
		if (!(mapArea == null))
			mapArea.removeAll();
		repaint();
	}

	private class NewPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			placingLocation = true;
			newButton.setEnabled(false);
			mapArea.addMouseListener(ml);
			mapArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

	}

	private class GetMousePosition extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			int x = mev.getX();
			int y = mev.getY();

			if (x < mapArea.getImageWidth() && y < mapArea.getImageHeight()) {

				Coordinates coordinates = new Coordinates(x, y);
				Category category = categoryList.getSelectedValue();

				if (locationCoordinates.containsKey(coordinates)) {
					JOptionPane.showMessageDialog(mapArea, "There is already a location with these coordinates!",
							"Invalid location", JOptionPane.ERROR_MESSAGE);
				} else {

					if (namedRadio.isSelected()) {
						namedDialog(category, coordinates);
					} else if (describedRadio.isSelected()) {
						describedDialog(category, coordinates);
					}
				}

			} else {
				JOptionPane.showMessageDialog(mapArea, "Location must be within the map!", "Invalid location",
						JOptionPane.ERROR_MESSAGE);
			}

			newButton.setEnabled(true);

		}
	}

	private void namedDialog(Category category, Coordinates coordinates) {

		String name = JOptionPane.showInputDialog(GUIMap.this, "Name", "Named location", JOptionPane.QUESTION_MESSAGE);
		if (name == null)
			return;
		if (!name.trim().isEmpty()) {
			addNamedToLists(coordinates, name, category);
			repaint();
		} else
			JOptionPane.showMessageDialog(mapArea, "Name can't be empty", "Invalid input", JOptionPane.ERROR_MESSAGE);
	}

	private void describedDialog(Category category, Coordinates coordinates) {
		DescribedButton described = new DescribedButton();
		int responce = JOptionPane.showConfirmDialog(GUIMap.this, described, "Described location",
				JOptionPane.OK_CANCEL_OPTION);
		if (responce != JOptionPane.OK_OPTION)
			return;
		String description = described.getDescription();
		String name = described.getName();
		if (!name.trim().isEmpty())
			if (!description.trim().isEmpty()) {
				addDescribedToLists(coordinates, name, category, description);
				repaint();
			} else
				JOptionPane.showMessageDialog(mapArea, "Description can't be empty", "Invalid input",
						JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(mapArea, "Name can't be empty", "Invalid input", JOptionPane.ERROR_MESSAGE);
	}

	private class MarkerMouseActions extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			if (!placingLocation) {
				Position l = (Position) mev.getComponent();

				if (mev.getButton() == MouseEvent.BUTTON1) {
					setMarked(l, selectionList.contains(l));
				}

				else if (mev.getButton() == MouseEvent.BUTTON3) {
					displayLocationInfo(l);
				}

			} else {
				JOptionPane.showMessageDialog(mapArea, "There is already a location with these coordinates!",
						"Invalid location", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void displayLocationInfo(Position l) {
		JOptionPane.showMessageDialog(mapArea, l.printInfo());
	}

	private void paintLocation(Position marker) {

		mapArea.add(marker);
		marker.addMouseListener(m2);
		mapArea.removeMouseListener(ml);
		mapArea.setCursor(Cursor.getDefaultCursor());
		categoryList.setSelectedValue(null, true);

	}

	private void removeSelections() {
		Collection<Position> removeList = new ArrayList<>();
		removeList.addAll(selectionList);

		if (!selectionList.isEmpty()) {
			for (Position l : removeList)
				setMarked(l, true);
			selectionList.clear();
		}
	}

	private class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			removeSelections();

			if (locationNames.containsKey(searchName))
				for (Position l : locationNames.get(searchName)) {
					l.setDisplayed(true);
					setMarked(l, false);
				}
			else {
				JOptionPane.showMessageDialog(mapArea, "There is no location with name " + searchName + ".", "Missing",
						JOptionPane.ERROR_MESSAGE);
			}

			searchField.setText("Search");
		}

	}

	private class HideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			hideMarked();
		}

	}

	private class CoordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			removeSelections();

			CoordinatesButton f = new CoordinatesButton();
			int responce = JOptionPane.showConfirmDialog(GUIMap.this, f, "Enter coordinates",
					JOptionPane.OK_CANCEL_OPTION);

			if (responce != JOptionPane.OK_OPTION)
				return;

			findLocation(f.getXCoordinate(), f.getYCoordinate());

		}

		private void findLocation(int x, int y) {
			try {
				System.out.println(x + " " + y);
				Coordinates h = new Coordinates(x, y);
				if (locationCoordinates.containsKey(h)) {
					System.out.println("found location");
					Position l = locationCoordinates.get(h);
					setMarked(l, false);
					displayLocationInfo(l);

				} else {
					JOptionPane.showMessageDialog(mapArea, "Location does not exist!", "Invalid location",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mapArea, "Coordinates must be a number!", "Invalid location",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class RemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			for (Position l : selectionList) {
				removeLocation(l);
				mapArea.remove(l);
			}
			selectionList.clear();
			repaint();
		}

	}

	private class HideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			try {
				for (Position l : locationCategory.get(categoryList.getSelectedValue())) {
					l.setDisplayed(false);
				}

				categoryList.setSelectedValue(null, true);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(mapArea, "You have to select a category first!", "Invalid selection",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent lev) {

			try {
				if (!lev.getValueIsAdjusting()) {

					for (Position l : locationCategory.get(categoryList.getSelectedValue())) {
						l.setDisplayed(true);
					}

				}
			} catch (NullPointerException e) {
				// för att undvika att den gör något när ingen karta är laddad
			}

		}

	}

	private class FocusingListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent fev) {
			searchField.setText("");
		}

		@Override
		public void focusLost(FocusEvent fev) {
			searchName = searchField.getText().toLowerCase();

		}

	}

	private class CloseWindowListener extends WindowAdapter {

		public void windowClosing(WindowEvent wev) {
			if (!saved) {
				int answer = JOptionPane.showConfirmDialog(GUIMap.this,
						"Are you sure you want to exit? You have unsaved changes.", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
		}
	}

}
