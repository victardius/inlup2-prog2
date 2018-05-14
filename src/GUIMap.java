import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIMap extends JFrame {

	private Map<Location, Point> locations = new HashMap<>();
	private JScrollPane scroll = null;
	private JPanel mapArea = null;
	private DrawMap map;
	private JButton newButton;
	private getMousePosition ml = new getMousePosition();
	private String[] cat = { "Bus", "Underground", "Train" };
	private JList<String> categoryList = new JList<String>(cat);

	GUIMap() {
		Box divideNorth = new Box(BoxLayout.PAGE_AXIS);
		add(divideNorth, BorderLayout.NORTH);

		setJMenuBar(new GUIArchive(this).getJMenuBar());

		JPanel north = new JPanel();
		divideNorth.add(north);
		newButton = new JButton("New");
		newButton.addActionListener(new newPositionListener());
		north.add(newButton);
		JRadioButton namedRadio = new JRadioButton("Named");
		JRadioButton describedRadio = new JRadioButton("Described");
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
		setSize(1000, 1000);
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

		validate();
		mapArea.revalidate();
		mapArea.repaint();
	}

	public boolean getChangesDone() {
		if (locations.isEmpty())
			return false;
		else
			return true;
	}

	public void setPlaces() {

	}

	class newPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			mapArea.addMouseListener(ml);
			mapArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

			/*
			 * En plats skapas genom att användaren väljer kategori i listan till höger,
			 * väljer platstypen med hjälp av radioknapparna vid New-knappen och trycker på
			 * knappen New. Då ska markören över kartan ändras till ett kors (för att
			 * markera att nästa klick på kartan skapar en plats) och en klick på kartan
			 * skapar en plats på den klickade positionen. Obs att det är tänkt att den
			 * nedre triangelspetsen visar var platsen finns, så det behövs en viss
			 * justering av koordinater för platsen. Om ingen kategori är markerad när en
			 * plats skapas blir platsen kategorilös och dess färg blir svart. Om det redan
			 * finns en plats på den klickade punkten ska ett felmeddelande ges – det är
			 * endast tillåtet med en plats per position. Se beskrivningen av operationen
			 * Coordinates nedan för lite mer information om detta. Efter att platsen är
			 * skapad ska inte kartan vara mottaglig för klickning förrän användaren trycker
			 * på knappen New igen.
			 */
		}

	}
	
	class getMousePosition extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {

			int x = mev.getX();
			int y = mev.getY();
			System.out.println(x + "," + y);
			MarkersPlacement marker = new MarkersPlacement(x,y);
			mapArea.add(marker);
			mapArea.validate();
			mapArea.removeAll();
			mapArea.removeMouseListener(ml);
			mapArea.setCursor(Cursor.getDefaultCursor());
			categoryList.clearSelection();

		}
	}

	class ArchiveListener implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

		}
	}

	class searchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Search letar upp platser med det namn som matats in i sökfältet.
			 * Den börjar med att avmarkera ev. platser som är markerade före sökningen,
			 * därefter hämtar den söksträngen från sökfältet, och gör alla platser som har
			 * detta namn synliga och markerade. Resultatet av sökningen presenteras alltså
			 * genom att platser med detta namn blir markerade (och synliga om de var gömda
			 * förut). Search-operationen förutsätter att man snabbt kan få fram alla
			 * platser som har angivet namn. Det behövs en lämplig datastruktur som gör det
			 * möjligt. Obs att vi låtsas som om antalet platser kan vara mycket stort,
			 * sekvensiell genomgång av olämpliga datastrukturer kan därför inte accepteras.
			 */
		}

	}

	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Hide gömmer alla markerade platser och gör dem avmarkerade. Även
			 * denna operation borde stödjas av någon lämplig datastruktur så att man inte
			 * behöver gå igenom alla platser utan bara de som är markerade.
			 */
		}

	}

	class coordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Användaren ska kunna fråga om vad som finns på en viss position på kartan
			 * genom att klicka på knappen Coordinates. Detta öppnar en lite dialogruta: där
			 * användaren kan mata in koordinater. Om det finns en plats på dessa
			 * koordinater så ska platsen göras synlig (om den var osynlig) och markerad.
			 * Eventuella platser som var markerade innan ska avmarkeras. Om det inte finns
			 * någon plats på dessa koordinater ska en dialogruta med meddelande om detta
			 * visas. I den här dialogen bör det kontrolleras att de inmatade värdena är
			 * numeriska.
			 */
		}

	}

	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Remove tar bort alla markerade platser – inte bara så att de inte
			 * syns på kartan utan objekten ska tas bort från alla datastrukturer där de kan
			 * finnas.
			 */
		}

	}

	class hideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Om man vill gömma alla platser som hör till en viss kategori så väljer man
			 * kategorin i kategorilistan och klickar på knappen Hide category – platser som
			 * hör till denna kategori ska göras osynliga. Om man vill göra alla platser som
			 * hör till en viss kategori synliga så räcker det att markera kategorin i
			 * listan
			 */
		}

	}

	class closeWindowListener extends WindowAdapter {

		public void windowClosing(WindowEvent wev) {
			if (getChangesDone()) {
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
