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
			
			//skapar ny karta
		}
		
	}
	
	class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//laddar in en .places fil och s�tter koordinater
		}
		
	}
	
	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//sparar ner en .places fil s� man kan ladda in platserna igen
		}
		
	}
	
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//St�nger av programmet
		}
		
	}

}
