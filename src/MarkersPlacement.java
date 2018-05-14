import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MarkersPlacement extends JComponent {
	
	int x, y;
	Color color;

	public MarkersPlacement(int x, int y, Color color) {
		setBounds(x-10, y-20, 20, 20);
		setPreferredSize(new Dimension(20, 20));
		
		this.color = color;
		
		
		}
		
	private void display(Graphics g) {
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int [] xes = {0, 10, 20};
		int [] yes = {0, 20, 0};
		g.setColor(color);
		g.fillPolygon(xes, yes, 3);
	}

}
