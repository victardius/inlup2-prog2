
public abstract class Location {
	
	private int[] coordinates = new int[2];
	private String name;
	private Category category;

	public Location(int coordinates[], String name, Category category){
		this.coordinates = coordinates;
		this.name = name;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public int[] getCoordinates() {
		return coordinates;
	}
	
	public Category getCategory(){
		return category;
	}
	
	public abstract String toString();
	
}
