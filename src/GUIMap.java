import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class GUIMap extends JFrame {
	
	private Map<String, int[]> locations = new HashMap<>();
	
	GUIMap(){
		//TO-DO: Everything (layout) kartan ska finnas h�r
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
			//S�ker p� namn fr�n ett textf�lt och synligg�r samt markerar alla objekt med det namnet
		}
		
	}
	
	class hideListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//avmarkerar och g�mmer alla platser
		}
		
	}
	
	class coordinatesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//�ppnar en dialogruta som fr�gar efter en x och y koordinat, avmarkerar alla objekt och markerar objektet som eventuellt finns p� den platsen. Om det inte finns n�gon d�r ska det st� i en dialogruta.
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
			//G�mmer alla objekt som tillh�r vald kategori som v�ljs utifr�n en lista ovan knappen.
			}
		
	}
	
}
