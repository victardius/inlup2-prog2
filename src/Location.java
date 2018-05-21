import javax.swing.*;
import java.awt.*;

public abstract class Location extends JComponent {

	private Coordinates coordinates;
	private String name;
	private String category;
	private Color color;

	public Location(Coordinates coordinates, String name, String category, Color color) {

		name = name.trim();
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

		g.setColor(color);
		g.fillPolygon(xes, yes, 3);
	}

	// protected void paintBorder(Graphics g) {
	// int [] xes = {-2, 10, 22};
	// int [] yes = {0, 22, 0};
	//
	// g.setColor(Color.PINK);
	// }

	public void setDisplayed(boolean b) {
		this.setVisible(b);
		this.setEnabled(b);
	}

	public String getName() {
		return name;
	}

	public String getCoordinatesToString() {
		return coordinates.getX() + "," + coordinates.getY();
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public String getCategory() {
		return category;
	}

	public abstract String toString();

}