import javax.swing.*;
import java.awt.*;

public abstract class Location extends JComponent {
<<<<<<< HEAD

=======
	
>>>>>>> 1f2c8fa0d89ddde013c8a9f32740ac0fd276b5da
	private Coordinates coordinates;
	private String name;
	private String category;
	private Color color;

<<<<<<< HEAD
	public Location(Coordinates coordinates, String name, String category, Color color) {
		this.coordinates = coordinates;
		this.name = name;
		if (category != null)
			this.category = category;
		else
			this.category = "None";
		this.color = color;

		setBounds(coordinates.getX() - 10, coordinates.getY() - 20, 20, 20);
		setPreferredSize(new Dimension(20, 20));

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int[] xes = { 0, 10, 20 };
		int[] yes = { 0, 20, 0 };
=======
	public Location(Coordinates coordinates, String name, String category, Color color){
		this.coordinates = coordinates;
		this.name = name;
		this.category = category;
		this.color = color;
		
		setBounds(coordinates.getX()-10, coordinates.getY()-20, 20, 20);
		setPreferredSize(new Dimension(20, 20));
		
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int [] xes = {0, 10, 20};
		int [] yes = {0, 20, 0};
>>>>>>> 1f2c8fa0d89ddde013c8a9f32740ac0fd276b5da
		g.setColor(color);
		g.fillPolygon(xes, yes, 3);
	}

	public String getName() {
		return name;
	}

	public String getCoordinates() {
		return coordinates.getX() + "," + coordinates.getY();
	}

	public String getCategory() {
		return category;
	}

	public abstract String toString();

}