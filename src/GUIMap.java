import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIMap extends JFrame {

	private Map<String, Location> locationNames = new HashMap<>();
	private Map<Coordinates, Location> locationCoordinatess = new HashMap<>();
	private Map<MarkersPlacement, Location> locations = new HashMap<>();
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
		JTextField searchField = new JTextField("Search", 10);
		north.add(searchField);
		JButton searchButton = new JButton("Search");
		north.add(searchButton);
		JButton hideButton = new JButton("Hide");
		north.add(hideButton);
		JButton removeButton = new JButton("Remove");
		north.add(removeButton);
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
		JButton hideCategoriesButton = new JButton("Hide categories");
		hideCategoriesButton.setAlignmentX(CENTER_ALIGNMENT);
		eastLayout.add(hideCategoriesButton);

		addWindowListener(new closeWindowListener());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1000, 380);
		setLocationRelativeTo(null);
		setVisible(true);

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
		saved = false;
		DescribedPlace place = new DescribedPlace(coordinates, name, category, description, color);
		locationNames.put(name, place);
		locationCoordinatess.put(coordinates, place);
		paintLocation(place);

	}

	public void addNamedToLists(Coordinates coordinates, String name, String category, Color color) {
		saved = false;
		NamedPlace place = new NamedPlace(coordinates, name, category, color);
		locationNames.put(name, place);
		locationCoordinatess.put(coordinates, place);
		paintLocation(place);
	}

	public Collection<Location> getLocations() {
		Collection<Location> list = locationCoordinatess.values();
		return list;
	}

	public Map<String, Location> getNameList() {
		return locationNames;
	}

	public Map<Coordinates, Location> getPositionList() {
		return locationCoordinatess;
	}

	public boolean getSaved() {
		return saved;
	}

	public void setSaved(boolean b) {
		saved = b;
	}

	class newPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

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

				if (namedRadio.isSelected()) {
					name = JOptionPane.showInputDialog("Name");
					addNamedToLists(coordinates, name, category, color);
				} else if (describedRadio.isSelected()) {
					DescribedButton described = new DescribedButton();
					int responce = JOptionPane.showConfirmDialog(GUIMap.this, described, "Enter coordinates",
							JOptionPane.OK_CANCEL_OPTION);
					if (responce != JOptionPane.OK_OPTION)
						return;
					String description = described.getDescription();
					name = described.getName();
					addDescribedToLists(coordinates, name, category, description, color);
				}

			} else {
				JOptionPane.showMessageDialog(mapArea, "Invalid location!");
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

	class markerMouseActions extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			System.out.println("i was clicked");
			Location l = (Location)mev.getComponent();

		}
	}

	class searchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
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

			CoordinatesButton f = new CoordinatesButton();
			int responce = JOptionPane.showConfirmDialog(GUIMap.this, f, "Enter coordinates",
					JOptionPane.OK_CANCEL_OPTION);

			if (responce != JOptionPane.OK_OPTION)
				return;
			int x = f.getXCoordinate();
			int y = f.getYCoordinate();

		}
	}

	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
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
			/*
			 * Om man vill g�mma alla platser som h�r till en viss kategori s� v�ljer man
			 * kategorin i kategorilistan och klickar p� knappen Hide category � platser som
			 * h�r till denna kategori ska g�ras osynliga. Om man vill g�ra alla platser som
			 * h�r till en viss kategori synliga s� r�cker det att markera kategorin i
			 * listan
			 */
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
