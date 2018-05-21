import java.awt.Color;

public class DescribedPlace extends Location {

	private String description;

	public DescribedPlace(Coordinates coordinates, String name, String category, String description, Color color) {
		super(coordinates, name, category, color);
		description = description.trim();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Described," + getCategory() + "," + getCoordinatesToString() + "," + getName() + "," + description;
	}

}
