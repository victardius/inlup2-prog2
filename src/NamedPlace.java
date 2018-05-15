import java.awt.Color;

public class NamedPlace extends Location {

	public NamedPlace(Coordinates coordinates, String name, String category, Color color) {
		super(coordinates, name, category, color);
	}
	
	@Override
	public String toString() {
		return "Named," + getCoordinates() + ","  + getName();
	}
	
}
