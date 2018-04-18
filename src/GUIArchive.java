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
			
			//skapar ny karta
		}
		
	}
	
	class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//laddar in en .places fil och sätter koordinater
		}
		
	}
	
	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//sparar ner en .places fil så man kan ladda in platserna igen
		}
		
	}
	
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			//Stänger av programmet
		}
		
	}

}
