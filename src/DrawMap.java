import javax.swing.*;
import java.awt.*;

public class DrawMap extends JPanel {
private ImageIcon bg = new ImageIcon("");
	
	public DrawMap(){
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
