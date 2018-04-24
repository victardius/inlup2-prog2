import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIMap extends JFrame {
	
	private Map<String, int[]> locations = new HashMap<>();
	private JPanel mapPanel = new JPanel();
	
	GUIMap(){
		//TO-DO: Everything (layout) kartan ska finnas h�r
		JPanel north = new JPanel();
		add(north, BorderLayout.NORTH);
		JButton newButton = new JButton("New");
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
		JButton coordinatesButton = new JButton("Search");
		north.add(coordinatesButton);
		north.setPreferredSize(new Dimension(100, 100));
		
		JPanel mapArea = new JPanel();
		add(mapArea, BorderLayout.CENTER);
		
		JPanel east = new JPanel();
		add(east, BorderLayout.EAST);
		east.setPreferredSize(new Dimension(200, 200));
		Box eastLayout = new Box(BoxLayout.PAGE_AXIS);
		east.add(eastLayout);
		JLabel categoriesLabel = new JLabel("categories");
		east.add(categoriesLabel);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	class newPositionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			/*
			 * En plats skapas genom att anv�ndaren v�ljer kategori i listan till h�ger, v�ljer platstypen med hj�lp
			 * av radioknapparna vid New-knappen och trycker p� knappen New. D� ska mark�ren �ver kartan
			 * �ndras till ett kors (f�r att markera att n�sta klick p� kartan skapar en plats) och en klick p� kartan
			 * skapar en plats p� den klickade positionen. Obs att det �r t�nkt att den nedre triangelspetsen visar
			 * var platsen finns, s� det beh�vs en viss justering av koordinater f�r platsen. Om ingen kategori �r
			 * markerad n�r en plats skapas blir platsen kategoril�s och dess f�rg blir svart.
			 * Om det redan finns en plats p� den klickade punkten ska ett felmeddelande ges � det �r endast
			 * till�tet med en plats per position. Se beskrivningen av operationen Coordinates nedan f�r lite mer
			 * information om detta.
			 * Efter att platsen �r skapad ska inte kartan vara mottaglig f�r klickning f�rr�n anv�ndaren trycker p�
			 * knappen New igen. 
			 */
		}
		
	}
	
	class searchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Search letar upp platser med det namn som matats in i s�kf�ltet. Den b�rjar med att
			 * avmarkera ev. platser som �r markerade f�re s�kningen, d�refter h�mtar den s�kstr�ngen fr�n
			 * s�kf�ltet, och g�r alla platser som har detta namn synliga och markerade. Resultatet av s�kningen
			 * presenteras allts� genom att platser med detta namn blir markerade (och synliga om de var g�mda
			 * f�rut).
			 * Search-operationen f�ruts�tter att man snabbt kan f� fram alla platser som har angivet namn. Det
			 * beh�vs en l�mplig datastruktur som g�r det m�jligt. Obs att vi l�tsas som om antalet platser kan
			 * vara mycket stort, sekvensiell genomg�ng av ol�mpliga datastrukturer kan d�rf�r inte accepteras. 
			 */
		}
		
	}
	
	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Hide g�mmer alla markerade platser och g�r dem avmarkerade. �ven denna operation
			 * borde st�djas av n�gon l�mplig datastruktur s� att man inte beh�ver g� igenom alla platser utan bara
			 * de som �r markerade. 
			 */
		}
		
	}
	
	class coordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Anv�ndaren ska kunna fr�ga om vad som finns p� en viss position p� kartan genom att klicka p�
			 * knappen Coordinates. Detta �ppnar en lite dialogruta:
			 * d�r anv�ndaren kan mata in koordinater. Om det finns en plats p� dessa koordinater s� ska platsen
			 * g�ras synlig (om den var osynlig) och markerad. Eventuella platser som var markerade innan ska
			 * avmarkeras. Om det inte finns n�gon plats p� dessa koordinater ska en dialogruta med meddelande
			 * om detta visas. I den h�r dialogen b�r det kontrolleras att de inmatade v�rdena �r numeriska.
			 */
		}
		
	}
	
	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Remove tar bort alla markerade platser � inte bara s� att de inte syns p� kartan utan
			 * objekten ska tas bort fr�n alla datastrukturer d�r de kan finnas. 
			 */
		}
		
	}
	
	
	class hideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Om man vill g�mma alla platser som h�r till en viss kategori s� v�ljer man kategorin i kategorilistan
			 * och klickar p� knappen Hide category � platser som h�r till denna kategori ska g�ras osynliga. Om
			 * man vill g�ra alla platser som h�r till en viss kategori synliga s� r�cker det att markera kategorin i
			 * listan
			 */
			}
		
	}
	
}
