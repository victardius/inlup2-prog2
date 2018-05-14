import java.awt.Point;

public class DescribedPlace extends Location {
	
	private String description;
	
	public DescribedPlace(Point coordinates, String name, String category, String description) {
		super(coordinates, name, category);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "Described," + getCoordinates() + ","  + getName() + "," + description;
	}
	
}
