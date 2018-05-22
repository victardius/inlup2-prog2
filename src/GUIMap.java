import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIMap extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, ArrayList<Location>> locationNames = new HashMap<>();
	private Map<Coordinates, Location> locationCoordinates = new HashMap<>();
	private Map<Category, List<Location>> locationCategory = new HashMap<>();
	private List<Location> buses = new ArrayList<>();
	private List<Location> trains = new ArrayList<>();
	private List<Location> underground = new ArrayList<>();
	private List<Location> none = new ArrayList<>();
	private List<Location> selectionList = new ArrayList<>();
	private List<JButton> buttons = new ArrayList<>();
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

	GUIMap() {
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

		locationCategory.put(Category.Bus, buses);
		locationCategory.put(Category.Train, trains);
		locationCategory.put(Category.Underground, underground);
		locationCategory.put(Category.None, none);

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

	public void addToLists(Location place) {
		saved = false;
		if (locationNames.containsKey(place.getName().toLowerCase())) {
			locationNames.get(place.getName().toLowerCase()).add(place);
		} else {
			ArrayList<Location> list = new ArrayList<>();
			list.add(place);
			locationNames.put(place.getName().toLowerCase(), list);
		}

		locationCoordinates.put(place.getCoordinates(), place);

		locationCategory.get(place.getCategory()).add(place);

		paintLocation(place);
		placingLocation = false;
	}

	public void removeLocation(Location l) {
		locationNames.remove(l.getName());
		locationCoordinates.remove(l.getCoordinates());
		locationCategory.get(l.getCategory()).remove(l);
	}

	public Collection<Location> getLocations() {
		Collection<Location> list = locationCoordinates.values();
		return list;
	}

	public Map<String, ArrayList<Location>> getNameList() {
		return locationNames;
	}

	public Map<Coordinates, Location> getPositionList() {
		return locationCoordinates;
	}

	public boolean getSaved() {
		return saved;
	}

	public void setSaved(boolean b) {
		saved = b;
	}

	public void setMarked(Location l, boolean b) {
		if (b) {
			selectionList.remove(l);
			l.setBorder(null);
		} else {
			selectionList.add(l);
			l.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		}
	}

	public void clearMarked() {
		for (Location l : selectionList) {
			setMarked(l, true);
		}
		selectionList.clear();
	}

	public void resetAll() {
		locationNames.clear();
		locationCoordinates.clear();
		buses.clear();
		trains.clear();
		underground.clear();
		none.clear();
		selectionList.clear();
		if (!(mapArea == null))
			mapArea.removeAll();
		repaint();
	}

	class NewPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			placingLocation = true;
			newButton.setEnabled(false);
			mapArea.addMouseListener(ml);
			mapArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

	}

	class GetMousePosition extends MouseAdapter {

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
		if (!name.trim().isEmpty())
			addNamedToLists(coordinates, name, category);
		else
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
			if (!description.trim().isEmpty())
				addDescribedToLists(coordinates, name, category, description);
			else
				JOptionPane.showMessageDialog(mapArea, "Description can't be empty", "Invalid input",
						JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(mapArea, "Name can't be empty", "Invalid input", JOptionPane.ERROR_MESSAGE);
	}

	class MarkerMouseActions extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			if (!placingLocation) {
				Location l = (Location) mev.getComponent();

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

	private void displayLocationInfo(Location l) {
		if (l instanceof NamedPlace) {
			JOptionPane.showMessageDialog(mapArea,
					"Name: " + l.getName() + "\n Coordinates: " + l.getCoordinatesToString());
		} else {
			l = (DescribedPlace) l;
			String[] outprint = l.toString().split(",");
			JOptionPane.showMessageDialog(mapArea, "Name: " + outprint[4] + "\n Coordinates: " + outprint[2] + ", "
					+ outprint[3] + "\n Description: " + outprint[5]);
		}
	}

	private void paintLocation(Location marker) {

		mapArea.add(marker);
		marker.addMouseListener(m2);
		mapArea.removeMouseListener(ml);
		mapArea.setCursor(Cursor.getDefaultCursor());
		categoryList.setSelectedValue(null, true);
		repaint();

	}

	private void removeSelections() {
		ArrayList<Location> removeList = new ArrayList<>();
		removeList.addAll(selectionList);

		if (!selectionList.isEmpty()) {
			for (Location l : removeList)
				setMarked(l, true);
			selectionList.clear();
		}
	}

	class SearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			removeSelections();

			if (locationNames.containsKey(searchName))
				for (Location l : locationNames.get(searchName)) {
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

	class HideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			for (Location l : selectionList) {
				l.setDisplayed(false);
			}
			selectionList.clear();
		}

	}

	class CoordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			removeSelections();

			CoordinatesButton f = new CoordinatesButton();
			int responce = JOptionPane.showConfirmDialog(GUIMap.this, f, "Enter coordinates",
					JOptionPane.OK_CANCEL_OPTION);

			if (responce != JOptionPane.OK_OPTION)
				return;
			try {
				int x = f.getXCoordinate();
				int y = f.getYCoordinate();
				System.out.println(x + " " + y);
				Coordinates h = new Coordinates(x, y);
				if (locationCoordinates.containsKey(h)) {
					System.out.println("found location");
					Location l = locationCoordinates.get(h);
					setMarked(l, false);
					if (l instanceof NamedPlace) {
						JOptionPane.showMessageDialog(mapArea,
								"Name: " + l.getName() + "\n Coordinates: " + l.getCoordinatesToString());
					} else {
						l = (DescribedPlace) l;
						String[] outprint = l.toString().split(",");
						JOptionPane.showMessageDialog(mapArea, "Name: " + outprint[4] + "\n Coordinates: " + outprint[2]
								+ ", " + outprint[3] + "\n Description: " + outprint[5]);
					}
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

	class RemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			for (Location l : selectionList) {
				removeLocation(l);
				mapArea.remove(l);
			}
			selectionList.clear();
			repaint();
		}

	}

	class HideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			for (Location l : locationCategory.get(categoryList.getSelectedValue())) {
				l.setDisplayed(false);
			}

			categoryList.setSelectedValue(null, true);
		}

	}

	class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent lev) {

			if (!lev.getValueIsAdjusting()) {

				for (Location l : locationCategory.get(categoryList.getSelectedValue())) {
					l.setDisplayed(true);
				}

			}

		}

	}

	class FocusingListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent fev) {
			searchField.setText("");
		}

		@Override
		public void focusLost(FocusEvent fev) {
			searchName = searchField.getText().toLowerCase();

		}

	}

	class CloseWindowListener extends WindowAdapter {

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
