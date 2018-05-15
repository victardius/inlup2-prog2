import java.awt.Color;

public class DescribedPlace extends Location {

	private String description;
<<<<<<< HEAD

=======
	
>>>>>>> 1f2c8fa0d89ddde013c8a9f32740ac0fd276b5da
	public DescribedPlace(Coordinates coordinates, String name, String category, String description, Color color) {
		super(coordinates, name, category, color);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Described," + getCategory() + "," + getCoordinates() + "," + getName() + "," + description;
	}

}
