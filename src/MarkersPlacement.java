import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MarkersPlacement extends JComponent {

	public MarkersPlacement(int x, int y) {
		setBounds(x, y, 25, 25);
		setPreferredSize(new Dimension(25, 25));
		
		
		}
		
	public void display(Graphics g) {
		int [] xes = {0,25,50};
		int [] yes = {50, 0, 50};
		g.setColor(Color.GREEN);
		g.fillPolygon(xes, yes, 3);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
