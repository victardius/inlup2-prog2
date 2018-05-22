import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUIArchive extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser jfc = new JFileChooser(".");
	private FileFilter ff = new FileNameExtensionFilter("Places", "places");
	JMenuBar mbar = new JMenuBar();
	GUIMap gui;

	GUIArchive(GUIMap map) {
		gui = map;
		setJMenuBar(mbar);
		JMenu archiveMenu = new JMenu("Archive");
		mbar.add(archiveMenu);
		JMenuItem newMapItem = new JMenuItem("New Map");
		archiveMenu.add(newMapItem);
		newMapItem.addActionListener(new NewListener());
		JMenuItem loadItem = new JMenuItem("Load Places");
		archiveMenu.add(loadItem);
		loadItem.addActionListener(new LoadListener());
		JMenuItem saveItem = new JMenuItem("Save");
		archiveMenu.add(saveItem);
		saveItem.addActionListener(new SaveListener());
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ExitListener());
		archiveMenu.add(exitItem);

		jfc.setAcceptAllFileFilterUsed(false);
	}

	public JMenuBar getJMenuBar() {
		return mbar;
	}

	class NewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			FileFilter ff = new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes());

			jfc.setFileFilter(ff);
			if (checkIfSaved()) {
				gui.setMap(jfc.getSelectedFile().getAbsolutePath());
				gui.setSaved(true);
			}

		}

	}

	class LoadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {

			jfc.setFileFilter(ff);

			if (checkIfSaved()) {
				loadFile(jfc.getSelectedFile().getAbsolutePath());
			}

		}

	}

	private boolean checkIfSaved() {
		int answer = jfc.showOpenDialog(gui);

		if (answer != JFileChooser.APPROVE_OPTION)
			return false;
		else if (isSaved()) {
			gui.resetAll();
			return true;
		} else {
			return false;
		}
	}

	private void loadFile(String fileName) {
		try {
			FileReader inFile = new FileReader(fileName);
			BufferedReader in = new BufferedReader(inFile);
			String line = null;
			while ((line = in.readLine()) != null) {
				String[] strings = line.split(",");
				Coordinates coordinates = new Coordinates(Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));

				if (strings[0].equals("Named")) {
					gui.addNamedToLists(coordinates, strings[4], Category.valueOf(strings[1]));
				} else if (strings[0].equals("Described")) {
					gui.addDescribedToLists(coordinates, strings[4], Category.valueOf(strings[1]), strings[5]);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(gui, e, "File not found or missing: ", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gui, e);
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(gui, "Map must be loaded first", "No map available", JOptionPane.ERROR_MESSAGE);
			gui.setSaved(true);
		}
	}

	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			jfc.setFileFilter(ff);

			int answer = jfc.showSaveDialog(gui);

			if (answer != JFileChooser.APPROVE_OPTION)
				return;
			else {
				saveFile(jfc.getSelectedFile(), jfc.getSelectedFile().getAbsolutePath());

			}
		}

	}

	private void saveFile(File file, String fileName) {
		if (fileName.endsWith(".places") || !fileName.contains(".")) {
			int start, end;

			end = fileName.length();
			if (end > 7) {
				start = end - 6;

				if (start < 0 || !fileName.substring(start, end).equals(".places"))
					if (!fileName.endsWith(".places"))
						fileName += ".places";
			}
			try {
				FileWriter outFile = new FileWriter(fileName);
				PrintWriter out = new PrintWriter(outFile);
				Collection<Location> list = gui.getLocations();
				for (Location l : list) {
					out.println(l);
				}
				out.close();
				gui.setSaved(true);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(gui, e);
			}

		} else {
			JOptionPane.showMessageDialog(gui, "File must be a .places file!", "Wrong file type",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ave) {
			if (isSaved()) {
				System.exit(0);
			}
		}

	}

	private boolean isSaved() {
		if (!gui.getSaved()) {
			int answer = JOptionPane.showConfirmDialog(gui,
					"Are you sure you want to proceed? You have unsaved changes.", "Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (answer == JOptionPane.OK_OPTION) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

}
