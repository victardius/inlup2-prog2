import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUIMap extends JFrame {
	
	private Map<String, int[]> locations = new HashMap<>();
	private JPanel mapPanel = new JPanel();
	
	GUIMap(){
		//TO-DO: Everything (layout) kartan ska finnas här
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
			 * En plats skapas genom att användaren väljer kategori i listan till höger, väljer platstypen med hjälp
			 * av radioknapparna vid New-knappen och trycker på knappen New. Då ska markören över kartan
			 * ändras till ett kors (för att markera att nästa klick på kartan skapar en plats) och en klick på kartan
			 * skapar en plats på den klickade positionen. Obs att det är tänkt att den nedre triangelspetsen visar
			 * var platsen finns, så det behövs en viss justering av koordinater för platsen. Om ingen kategori är
			 * markerad när en plats skapas blir platsen kategorilös och dess färg blir svart.
			 * Om det redan finns en plats på den klickade punkten ska ett felmeddelande ges – det är endast
			 * tillåtet med en plats per position. Se beskrivningen av operationen Coordinates nedan för lite mer
			 * information om detta.
			 * Efter att platsen är skapad ska inte kartan vara mottaglig för klickning förrän användaren trycker på
			 * knappen New igen. 
			 */
		}
		
	}
	
	class searchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Search letar upp platser med det namn som matats in i sökfältet. Den börjar med att
			 * avmarkera ev. platser som är markerade före sökningen, därefter hämtar den söksträngen från
			 * sökfältet, och gör alla platser som har detta namn synliga och markerade. Resultatet av sökningen
			 * presenteras alltså genom att platser med detta namn blir markerade (och synliga om de var gömda
			 * förut).
			 * Search-operationen förutsätter att man snabbt kan få fram alla platser som har angivet namn. Det
			 * behövs en lämplig datastruktur som gör det möjligt. Obs att vi låtsas som om antalet platser kan
			 * vara mycket stort, sekvensiell genomgång av olämpliga datastrukturer kan därför inte accepteras. 
			 */
		}
		
	}
	
	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Hide gömmer alla markerade platser och gör dem avmarkerade. Även denna operation
			 * borde stödjas av någon lämplig datastruktur så att man inte behöver gå igenom alla platser utan bara
			 * de som är markerade. 
			 */
		}
		
	}
	
	class coordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Användaren ska kunna fråga om vad som finns på en viss position på kartan genom att klicka på
			 * knappen Coordinates. Detta öppnar en lite dialogruta:
			 * där användaren kan mata in koordinater. Om det finns en plats på dessa koordinater så ska platsen
			 * göras synlig (om den var osynlig) och markerad. Eventuella platser som var markerade innan ska
			 * avmarkeras. Om det inte finns någon plats på dessa koordinater ska en dialogruta med meddelande
			 * om detta visas. I den här dialogen bör det kontrolleras att de inmatade värdena är numeriska.
			 */
		}
		
	}
	
	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Operationen Remove tar bort alla markerade platser – inte bara så att de inte syns på kartan utan
			 * objekten ska tas bort från alla datastrukturer där de kan finnas. 
			 */
		}
		
	}
	
	
	class hideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Om man vill gömma alla platser som hör till en viss kategori så väljer man kategorin i kategorilistan
			 * och klickar på knappen Hide category – platser som hör till denna kategori ska göras osynliga. Om
			 * man vill göra alla platser som hör till en viss kategori synliga så räcker det att markera kategorin i
			 * listan
			 */
			}
		
	}
	
}
