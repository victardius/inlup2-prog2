import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIArchive extends JFrame {
	
	GUIArchive(){
		//TO-DO: Everything (layout) menyn l�ngst upp till v�nster.
	}
	
	class newListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			/*
			 * Om man v�ljer New Map s� visas en fildialog d�r anv�ndaren kan v�lja en bild (f�rhoppningsvis
			 * f�rest�llande en karta):
			 * Bilden ska laddas in och visas i f�nstret. Om bilden �r st�rre �n f�nstret ska rulllistor visas s� att
			 * man ska kunna scrolla kartan. Man ska �ven kunna f�rstora f�nstret genom att dra ut det. 
			 */
		}
		
	}
	
	class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * B�de Load Places och Save ska visa en fil�ppningsdialog och fr�ga anv�ndaren om filnamnet d�r
			 * platserna ska sparas/d�rifr�n platserna ska l�sas in. 
			 */
		}
		
	}
	
	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * B�de Load Places och Save ska visa en fil�ppningsdialog och fr�ga anv�ndaren om filnamnet d�r
			 * platserna ska sparas/d�rifr�n platserna ska l�sas in. 
			 * 
			 * Platserna ska sparas p� en textfil, med en plats per rad. P� varje rad ska platsens v�rden skrivas ut i
			 * en kommaseparerad lista, med platsens typ (Named eller Described), platsens kategori (Bus,
			 * Underground, Train eller None om platsen saknar kategori), x-koordinaten,
			 * y-koordinaten, platsens namn och (om platsen �r av typen Described) dess beskrivning. 
			 */
		}
		
	}
	
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			/*
			 * Exit ska avsluta programexekveringen. Programmet ska �ven kunna avslutas via st�ngningsrutan. Om
			 * det finns osparade f�r�ndringar ska det visas en dialogruta som varnar om att det finns osparade
			 * �ndringar och fr�gar om man �nd� vill avsluta � anv�ndaren har d� m�jligheten att avbryta operationen. 
			 */
		}
		
	}

}