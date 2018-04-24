import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MarkersPlacement extends JComponent {

	public MarkersPlacement(int x, int y) {
		setBounds(x, y, 25, 25);
		setPreferredSize(new Dimension(25, 25));
		
		
		}
		
	private void display(Graphics g) {
		int [] xes = {50,25,0};
		int [] yes = {0, 50, 0};
		g.setColor(Color.GREEN);
		g.fillPolygon(xes, yes, 3);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
