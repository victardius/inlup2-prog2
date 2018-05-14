import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUIArchive extends JFrame {

	
	JMenuBar mbar = new JMenuBar();
	GUIMap gui;
	// JScrollPane scroll = null;
	// JPanel picturePanel = null;

	GUIArchive(GUIMap map) {
		gui = map;
		setJMenuBar(mbar);
		JMenu archiveMenu = new JMenu("Archive");
		mbar.add(archiveMenu);
		JMenuItem newMapItem = new JMenuItem("New Map");
		archiveMenu.add(newMapItem);
		newMapItem.addActionListener(new newListener());
		JMenuItem loadItem = new JMenuItem("Load Places");
		archiveMenu.add(loadItem);
		loadItem.addActionListener(new loadListener());
		JMenuItem saveItem = new JMenuItem("Save");
		archiveMenu.add(saveItem);
		saveItem.addActionListener(new saveListener());
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new exitListener());
		archiveMenu.add(exitItem);
	}

	public JMenuBar getJMenuBar() {
		return mbar;
	}

	class newListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			JFileChooser jfc = new JFileChooser(".");
			FileFilter ff = new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes());
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(ff);

			int answer = jfc.showOpenDialog(GUIArchive.this);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;

			File file = jfc.getSelectedFile();
			String fileName = file.getAbsolutePath();
			gui.setMap(fileName);

			/*
			 * Om man väljer New Map så visas en fildialog där användaren kan välja en bild
			 * (förhoppningsvis föreställande en karta): Bilden ska laddas in och visas i
			 * fönstret. Om bilden är större än fönstret ska rulllistor visas så att man ska
			 * kunna scrolla kartan. Man ska även kunna förstora fönstret genom att dra ut
			 * det.
			 */
		}

	}

	class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			JFileChooser jfc = new JFileChooser(".");
			FileFilter ff = new FileNameExtensionFilter("Places", "places");
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(ff);
			
			int answer = jfc.showOpenDialog(gui);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			
			
			/*
			 * Både Load Places och Save ska visa en filöppningsdialog och fråga användaren
			 * om filnamnet där platserna ska sparas/därifrån platserna ska läsas in.
			 */
		}

	}

	class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			
			JFileChooser jfc = new JFileChooser(".");
			FileFilter ff = new FileNameExtensionFilter("Places", "places");
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(ff);
			
			int answer = jfc.showSaveDialog(gui);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			/*
			 * Både Load Places och Save ska visa en filöppningsdialog och fråga användaren
			 * om filnamnet där platserna ska sparas/därifrån platserna ska läsas in.
			 * 
			 * Platserna ska sparas på en textfil, med en plats per rad. På varje rad ska
			 * platsens värden skrivas ut i en kommaseparerad lista, med platsens typ (Named
			 * eller Described), platsens kategori (Bus, Underground, Train eller None om
			 * platsen saknar kategori), x-koordinaten, y-koordinaten, platsens namn och (om
			 * platsen är av typen Described) dess beskrivning.
			 */
		}

	}

	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			if (gui.getChangesDone()) {
				int answer = JOptionPane.showConfirmDialog(gui, "Are you sure you want to exit? You have unsaved changes.", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
			/*
			 * Exit ska avsluta programexekveringen. Programmet ska även kunna avslutas via
			 * stängningsrutan. Om det finns osparade förändringar ska det visas en
			 * dialogruta som varnar om att det finns osparade ändringar och frågar om man
			 * ändå vill avsluta – användaren har då möjligheten att avbryta operationen.
			 */
		}

	}

}
