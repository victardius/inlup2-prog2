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
			/*
			 * Anv�ndaren ska kunna fr�ga om vad som finns p� en viss position p� kartan
			 * genom att klicka p� knappen Coordinates. Detta �ppnar en lite dialogruta: d�r
			 * anv�ndaren kan mata in koordinater. Om det finns en plats p� dessa
			 * koordinater s� ska platsen g�ras synlig (om den var osynlig) och markerad.
			 * Eventuella platser som var markerade innan ska avmarkeras. Om det inte finns
			 * n�gon plats p� dessa koordinater ska en dialogruta med meddelande om detta
			 * visas. I den h�r dialogen b�r det kontrolleras att de inmatade v�rdena �r
			 * numeriska.
			 */
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
