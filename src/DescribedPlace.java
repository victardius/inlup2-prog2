
public class DescribedPlace extends Location {
	
	private String description;
	
	public DescribedPlace(int[] coordinates, String name, Category category, String description) {
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
