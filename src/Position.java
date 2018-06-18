import javax.swing.*;
import java.awt.*;

public abstract class Position extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coordinates coordinates;
	private String name;
	private Category category;

	protected Position(Coordinates coordinates, String name, Category category) {

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

	protected void setDisplayed(boolean b) {
		this.setVisible(b);
		this.setEnabled(b);
	}

	public String getName() {
		return name;
	}

	protected String getCoordinatesToString() {
		return coordinates.getX() + "," + coordinates.getY();
	}

	protected Coordinates getCoordinates() {
		return coordinates;
	}

	protected Category getCategory() {
		return category;
	}

	public abstract String toString();

}