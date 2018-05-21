import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUIMap extends JFrame {

	private Map<String, ArrayList<Location>> locationNames = new HashMap<>();
	private Map<Coordinates, Location> locationCoordinates = new HashMap<>();
	private Map<String, ArrayList<Location>> locationCategory = new HashMap<>();
	private ArrayList<Location> buses = new ArrayList<>();
	private ArrayList<Location> trains = new ArrayList<>();
	private ArrayList<Location> underground = new ArrayList<>();
	private ArrayList<Location> selectionList = new ArrayList<>();
	private JScrollPane scroll = null;
	private DrawMap mapArea = null;
	private DrawMap map;
	private JButton newButton;
	private getMousePosition ml = new getMousePosition();
	private markerMouseActions m2 = new markerMouseActions();
	private String[] cat = { "Bus", "Underground", "Train" };
	private JList<String> categoryList = new JList<String>(cat);
	private JRadioButton namedRadio, describedRadio;
	private Color color;
	private boolean saved = true;
	private boolean placingLocation = false;
	private JTextField searchField;
	private String searchName;

	GUIMap() {
		Box divideNorth = new Box(BoxLayout.PAGE_AXIS);
		add(divideNorth, BorderLayout.NORTH);

		setJMenuBar(new GUIArchive(this).getJMenuBar());

		JPanel north = new JPanel();
		divideNorth.add(north);
		newButton = new JButton("New");
		newButton.addActionListener(new newPositionListener());
		north.add(newButton);
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
		searchField.addFocusListener(new focusListener());
		JButton searchButton = new JButton("Search");
		north.add(searchButton);
		searchButton.addActionListener(new searchListener());
		JButton hideButton = new JButton("Hide");
		north.add(hideButton);
		hideButton.addActionListener(new hideListener());
		JButton removeButton = new JButton("Remove");
		north.add(removeButton);
		removeButton.addActionListener(new removeListener());
		JButton coordinatesButton = new JButton("Coordinates");
		north.add(coordinatesButton);
		coordinatesButton.addActionListener(new coordinatesListener());

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
		categoryList.addListSelectionListener(new listListener());
		JButton hideCategoriesButton = new JButton("Hide categories");
		hideCategoriesButton.setAlignmentX(CENTER_ALIGNMENT);
		eastLayout.add(hideCategoriesButton);
		hideCategoriesButton.addActionListener(new hideCategoryListener());

		addWindowListener(new closeWindowListener());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1000, 380);
		setLocationRelativeTo(null);
		setVisible(true);

		locationCategory.put("Bus", buses);
		locationCategory.put("Train", trains);
		locationCategory.put("Underground", underground);

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
	}

	public void addDescribedToLists(Coordinates coordinates, String name, String category, String description,
			Color color) {

		DescribedPlace place = new DescribedPlace(coordinates, name, category, description, color);

		addToLists(place);

	}

	public void addNamedToLists(Coordinates coordinates, String name, String category, Color color) {

		NamedPlace place = new NamedPlace(coordinates, name, category, color);

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

		if (place.getCategory().equals("Bus")) {
			buses.add(place);
		} else if (place.getCategory().equals("Train")) {
			trains.add(place);
		} else if (place.getCategory().equals("Underground")) {
			underground.add(place);
		}

		paintLocation(place);
		placingLocation = false;
	}

	public void removeLocation(Location l) {
		locationNames.remove(l.getName());
		locationCoordinates.remove(l.getCoordinates());
		if (l.getCategory().equals("Bus")) {
			buses.remove(l);
		} else if (l.getCategory().equals("Train")) {
			trains.remove(l);
		} else if (l.getCategory().equals("Underground")) {
			underground.remove(l);
		}
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

	public void clearSelection() {
		for (Location l : selectionList) {
			setMarked(l, true);
		}
		selectionList.clear();
	}

	class newPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			placingLocation = true;
			mapArea.addMouseListener(ml);
			mapArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

			/*
			 * En plats skapas genom att anv�ndaren v�ljer kategori i listan till h�ger,
			 * v�ljer platstypen med hj�lp av radioknapparna vid New-knappen och trycker p�
			 * knappen New. D� ska mark�ren �ver kartan �ndras till ett kors (f�r att
			 * markera att n�sta klick p� kartan skapar en plats) och en klick p� kartan
			 * skapar en plats p� den klickade positionen. Obs att det �r t�nkt att den
			 * nedre triangelspetsen visar var platsen finns, s� det beh�vs en viss
			 * justering av koordinater f�r platsen. Om ingen kategori �r markerad n�r en
			 * plats skapas blir platsen kategoril�s och dess f�rg blir svart. Om det redan
			 * finns en plats p� den klickade punkten ska ett felmeddelande ges � det �r
			 * endast till�tet med en plats per position. Se beskrivningen av operationen
			 * Coordinates nedan f�r lite mer information om detta. Efter att platsen �r
			 * skapad ska inte kartan vara mottaglig f�r klickning f�rr�n anv�ndaren trycker
			 * p� knappen New igen.
			 */
		}

	}

	class getMousePosition extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			int x = mev.getX();
			int y = mev.getY();

			categoryList.getSelectedIndex();
			if (categoryList.getSelectedIndex() == 0) {
				color = (Color.GREEN);
			} else if (categoryList.getSelectedIndex() == 1) {
				color = Color.BLUE;
			} else if (categoryList.getSelectedIndex() == 2) {
				color = Color.RED;
			} else
				color = Color.BLACK;

			if (x < mapArea.getImageWidth() && y < mapArea.getImageHeight()) {

				Coordinates coordinates = new Coordinates(x, y);
				String category = categoryList.getSelectedValue();
				String name;
				
				if (locationCoordinates.containsKey(coordinates)) {
					JOptionPane.showMessageDialog(mapArea, "There is already a location with these coordinates!",
							"Invalid location", JOptionPane.ERROR_MESSAGE);
				} else {

					if (namedRadio.isSelected()) {
						name = JOptionPane.showInputDialog("Name");
						if (!name.trim().isEmpty())
							addNamedToLists(coordinates, name, category, color);
						else
							JOptionPane.showMessageDialog(mapArea, "Name can't be empty", "Invalid input",
									JOptionPane.ERROR_MESSAGE);
					} else if (describedRadio.isSelected()) {
						DescribedButton described = new DescribedButton();
						int responce = JOptionPane.showConfirmDialog(GUIMap.this, described, "Described location",
								JOptionPane.OK_CANCEL_OPTION);
						if (responce != JOptionPane.OK_OPTION)
							return;
						String description = described.getDescription();
						name = described.getName();
						if (!name.trim().isEmpty())
							if (!description.trim().isEmpty())
								addDescribedToLists(coordinates, name, category, description, color);
							else
								JOptionPane.showMessageDialog(mapArea, "Description can't be empty", "Invalid input",
										JOptionPane.ERROR_MESSAGE);
						else
							JOptionPane.showMessageDialog(mapArea, "Name can't be empty", "Invalid input",
									JOptionPane.ERROR_MESSAGE);
					}
				}

			} else {
				JOptionPane.showMessageDialog(mapArea, "Location must be within the map!", "Invalid location",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}
	
	private void checkIfNull(String str) {
		if(str == null) {
			JOptionPane.showMessageDialog(mapArea, "Fields cant be empty!", "Invalid input",
					JOptionPane.ERROR_MESSAGE);
		}else {
			return;
		}
	}

	class markerMouseActions extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			if (!placingLocation) {
				Location l = (Location) mev.getComponent();

				if (mev.getButton() == MouseEvent.BUTTON1) {
					setMarked(l, selectionList.contains(l));

				}

				else if (mev.getButton() == MouseEvent.BUTTON3) {
					if (l instanceof NamedPlace) {
						JOptionPane.showMessageDialog(mapArea,
								"Name: " + l.getName() + "\n Coordinates: " + l.getCoordinatesToString());
					} else {
						l = (DescribedPlace) l;
						String[] outprint = l.toString().split(",");
						JOptionPane.showMessageDialog(mapArea, "Name: " + outprint[4] + "\n Coordinates: " + outprint[2]
								+ ", " + outprint[3] + "\n Description: " + outprint[5]);
					}

				}

			} else {
				JOptionPane.showMessageDialog(mapArea, "There is already a location with these coordinates!",
						"Invalid location", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void paintLocation(Location marker) {

		mapArea.add(marker);
		marker.addMouseListener(m2);
		mapArea.removeMouseListener(ml);
		mapArea.setCursor(Cursor.getDefaultCursor());
		categoryList.clearSelection();
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

	class searchListener implements ActionListener {

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
			/*
			 * Operationen Search letar upp platser med det namn som matats in i s�kf�ltet.
			 * Den b�rjar med att avmarkera ev. platser som �r markerade f�re s�kningen,
			 * d�refter h�mtar den s�kstr�ngen fr�n s�kf�ltet, och g�r alla platser som har
			 * detta namn synliga och markerade. Resultatet av s�kningen presenteras allts�
			 * genom att platser med detta namn blir markerade (och synliga om de var g�mda
			 * f�rut). Search-operationen f�ruts�tter att man snabbt kan f� fram alla
			 * platser som har angivet namn. Det beh�vs en l�mplig datastruktur som g�r det
			 * m�jligt. Obs att vi l�tsas som om antalet platser kan vara mycket stort,
			 * sekvensiell genomg�ng av ol�mpliga datastrukturer kan d�rf�r inte accepteras.
			 */
		}

	}

	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			for (Location l : selectionList) {
				l.setDisplayed(false);
			}
			selectionList.clear();

			/*
			 * Operationen Hide g�mmer alla markerade platser och g�r dem avmarkerade. �ven
			 * denna operation borde st�djas av n�gon l�mplig datastruktur s� att man inte
			 * beh�ver g� igenom alla platser utan bara de som �r markerade.
			 */
		}

	}

	class coordinatesListener implements ActionListener {

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
					// for(locationCoordinates.Entry<Coordinates,Location> entry :
					// locationCoordinates.keySet()){
					// if(c.containsKey(h)){
					// System.out.println("location found");
					// }
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

	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			for (Location l : selectionList) {
				removeLocation(l);
				mapArea.remove(l);
			}
			selectionList.clear();
			repaint();

			/*
			 * Operationen Remove tar bort alla markerade platser � inte bara s� att de inte
			 * syns p� kartan utan objekten ska tas bort fr�n alla datastrukturer d�r de kan
			 * finnas.
			 */
		}

	}

	class hideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			categoryList.getSelectedIndex();
			if (categoryList.getSelectedIndex() == 0) {
				for (Location l : buses) {
					l.setDisplayed(false);
				}
			} else if (categoryList.getSelectedIndex() == 1) {
				for (Location l : underground) {
					l.setDisplayed(false);
				}
			} else if (categoryList.getSelectedIndex() == 2) {
				for (Location l : trains) {
					l.setDisplayed(false);
				}
			}

			categoryList.clearSelection();

			/*
			 * Om man vill g�mma alla platser som h�r till en viss kategori s� v�ljer man
			 * kategorin i kategorilistan och klickar p� knappen Hide category � platser som
			 * h�r till denna kategori ska g�ras osynliga. Om man vill g�ra alla platser som
			 * h�r till en viss kategori synliga s� r�cker det att markera kategorin i
			 * listan
			 */
		}

	}

	class listListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent lev) {

			if (!lev.getValueIsAdjusting()) {
				categoryList.getSelectedIndex();
				if (categoryList.getSelectedIndex() == 0) {
					for (Location l : buses) {
						l.setDisplayed(true);
					}
				} else if (categoryList.getSelectedIndex() == 1) {
					for (Location l : underground) {
						l.setDisplayed(true);
					}
				} else if (categoryList.getSelectedIndex() == 2) {
					for (Location l : trains) {
						l.setDisplayed(true);
					}
				}
			}

		}

	}

	class focusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent fev) {
			searchField.setText("");
		}

		@Override
		public void focusLost(FocusEvent fev) {
			searchName = searchField.getText().toLowerCase();

		}

	}

	class closeWindowListener extends WindowAdapter {

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
