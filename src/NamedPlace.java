
public class NamedPlace extends Location {

	public NamedPlace(int[] coordinates, String name, Category category) {
		super(coordinates, name, category);
	}
	
	@Override
	public String toString() {
		return "Named," + getCoordinates() + ","  + getName();
	}
	
}
