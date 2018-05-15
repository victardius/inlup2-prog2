import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

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
			 * Om man v�ljer New Map s� visas en fildialog d�r anv�ndaren kan v�lja en bild
			 * (f�rhoppningsvis f�rest�llande en karta): Bilden ska laddas in och visas i
			 * f�nstret. Om bilden �r st�rre �n f�nstret ska rulllistor visas s� att man ska
			 * kunna scrolla kartan. Man ska �ven kunna f�rstora f�nstret genom att dra ut
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
			else {
				File file = jfc.getSelectedFile();
				String fileName = file.getAbsolutePath();
				System.out.println("Loading: " + fileName);
				try {
					FileReader inFile = new FileReader(fileName);
					BufferedReader in = new BufferedReader(inFile);
					int lineNo = 0;
					String line;
					while ((line = in.readLine()) != null) {
						lineNo++;
						String[] strings = line.split(",");
						Coordinates coordinates = new Coordinates(Integer.parseInt(strings[2]),
								Integer.parseInt(strings[3]));
						
						Color color;
						
						if (strings[1].equals("Bus")) {
							color = (Color.GREEN);
						} else if (strings[1].equals("Underground")) {
							color = Color.BLUE;
						} else if (strings[1].equals("Train")) {
							color = Color.RED;
						} else
							color = Color.BLACK;
						
						if (strings[0].equals("Named")) {
							gui.addNamedToLists(coordinates, strings[4], strings[1], color);
						} else if (strings[0].equals("Described")) {
							gui.addDescribedToLists(coordinates, strings[4], strings[1], strings[5], color);
						}
					}
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(gui, e, "File not found or missing: ", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(gui, e);
				}
			}

			/*
			 * B�de Load Places och Save ska visa en fil�ppningsdialog och fr�ga anv�ndaren
			 * om filnamnet d�r platserna ska sparas/d�rifr�n platserna ska l�sas in.
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
			else {
				int start, end;
				File file = jfc.getSelectedFile();
				String fileName = file.getAbsolutePath();
				if (!fileName.contains(".")) {

					end = fileName.length();
					if (end > 7) {
						start = end - 6;

						/*
						 * fileName = fileName.substring(0, fileName.lastIndexOf(".", end)); end =
						 * fileName.length(); start = end - 6;
						 */

						if (start < 0 || !fileName.substring(start, end).equals(".places"))
							fileName += ".places";
					}
					try {
						System.out.println("Loading: " + fileName);
						FileWriter outFile = new FileWriter(fileName);
						PrintWriter out = new PrintWriter(outFile);
						Collection<Location> list = gui.getLocations();
						for (Location l : list) {
							out.println(l);
							System.out.println(l);
						}
						out.close();
						gui.setSaved(true);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(gui, e);
					}

				} else {
					JOptionPane.showMessageDialog(gui, "File must be a .places file!", "Wrong file type", JOptionPane.ERROR_MESSAGE);
				}
			}
			/*
			 * B�de Load Places och Save ska visa en fil�ppningsdialog och fr�ga anv�ndaren
			 * om filnamnet d�r platserna ska sparas/d�rifr�n platserna ska l�sas in.
			 * 
			 * Platserna ska sparas p� en textfil, med en plats per rad. P� varje rad ska
			 * platsens v�rden skrivas ut i en kommaseparerad lista, med platsens typ (Named
			 * eller Described), platsens kategori (Bus, Underground, Train eller None om
			 * platsen saknar kategori), x-koordinaten, y-koordinaten, platsens namn och (om
			 * platsen �r av typen Described) dess beskrivning.
			 */
		}

	}

	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			if (!gui.getSaved()) {
				int answer = JOptionPane.showConfirmDialog(gui,
						"Are you sure you want to exit? You have unsaved changes.", "Warning",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (answer == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
			/*
			 * Exit ska avsluta programexekveringen. Programmet ska �ven kunna avslutas via
			 * st�ngningsrutan. Om det finns osparade f�r�ndringar ska det visas en
			 * dialogruta som varnar om att det finns osparade �ndringar och fr�gar om man
			 * �nd� vill avsluta � anv�ndaren har d� m�jligheten att avbryta operationen.
			 */
		}

	}

}
