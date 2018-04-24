import javax.swing.*;
import java.awt.*;

public class DrawMap extends JPanel {
private ImageIcon bg;
	
	public DrawMap(String fileName){
		bg = new ImageIcon(fileName);
		int w = bg.getIconWidth();
		int h = bg.getIconHeight();
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w,h));
		setMinimumSize(new Dimension(w,h));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, this);
	}

}
