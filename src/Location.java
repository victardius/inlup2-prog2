import javax.swing.*;
import java.awt.*;

public abstract class Location extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coordinates coordinates;
	private String name;
	private Category category;

	public Location(Coordinates coordinates, String name, Category category) {

		name = name.trim();
		this.coordinates = coordinates;
		this.name = name;
		if (category != null)
			this.category = category;
		else
			this.category = Category.None;

		setBounds(coordinates.getX() - 10, coordinates.getY() - 20, 20, 20);
		setPreferredSize(new Dimension(20, 20));

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int[] xes = { 0, 10, 20 };
		int[] yes = { 0, 20, 0 };

		g.setColor(category.getColor());
		g.fillPolygon(xes, yes, 3);
	}

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

	public Category getCategory() {
		return category;
	}

	public abstract String toString();

}