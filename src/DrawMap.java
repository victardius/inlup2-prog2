import javax.swing.*;
import java.awt.*;

public class DrawMap extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon bg;
	private int w, h;

	public DrawMap(String fileName) {
		super(null);
		bg = new ImageIcon(fileName);
		w = bg.getIconWidth();
		h = bg.getIconHeight();
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
		setMinimumSize(new Dimension(w, h));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, this);
	}

	public int getImageWidth() {
		return w;
	}

	public int getImageHeight() {
		return h;
	}

}
