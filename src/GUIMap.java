import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GUIMap extends JFrame {
	
	private Map<String, int[]> locations = new HashMap<>();
	
	GUIMap(){
		//TO-DO: Everything (layout) kartan ska finnas här
	}
	
	class newListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			//skapar ny Location instans av typen named eller described
		}
		
	}
	
	class searchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//Söker på namn från ett textfält och synliggör samt markerar alla objekt med det namnet
		}
		
	}
	
	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//avmarkerar och gömmer alla platser
		}
		
	}
	
	class coordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//Öppnar en dialogruta som frågar efter en x och y koordinat, avmarkerar alla objekt och markerar objektet som eventuellt finns på den platsen. Om det inte finns någon där ska det stå i en dialogruta.
		}
		
	}
	
	class removeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//tar bort markerade platser.
		}
		
	}
	
	
	class hideCategoryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//Gömmer alla objekt som tillhör vald kategori som väljs utifrån en lista ovan knappen.
			}
		
	}
	
}
