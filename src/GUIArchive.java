import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIArchive extends JFrame {
	
	GUIArchive(){
		//TO-DO: Everything (layout) menyn längst upp till vänster.
	}
	
	class newListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			/*
			 * Om man väljer New Map så visas en fildialog där användaren kan välja en bild (förhoppningsvis
			 * föreställande en karta):
			 * Bilden ska laddas in och visas i fönstret. Om bilden är större än fönstret ska rulllistor visas så att
			 * man ska kunna scrolla kartan. Man ska även kunna förstora fönstret genom att dra ut det. 
			 */
		}
		
	}
	
	class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Både Load Places och Save ska visa en filöppningsdialog och fråga användaren om filnamnet där
			 * platserna ska sparas/därifrån platserna ska läsas in. 
			 */
		}
		
	}
	
	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Både Load Places och Save ska visa en filöppningsdialog och fråga användaren om filnamnet där
			 * platserna ska sparas/därifrån platserna ska läsas in. 
			 * 
			 * Platserna ska sparas på en textfil, med en plats per rad. På varje rad ska platsens värden skrivas ut i
			 * en kommaseparerad lista, med platsens typ (Named eller Described), platsens kategori (Bus,
			 * Underground, Train eller None om platsen saknar kategori), x-koordinaten,
			 * y-koordinaten, platsens namn och (om platsen är av typen Described) dess beskrivning. 
			 */
		}
		
	}
	
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Exit ska avsluta programexekveringen. Programmet ska även kunna avslutas via stängningsrutan. Om
			 * det finns osparade förändringar ska det visas en dialogruta som varnar om att det finns osparade
			 * ändringar och frågar om man ändå vill avsluta – användaren har då möjligheten att avbryta operationen. 
			 */
		}
		
	}

}
