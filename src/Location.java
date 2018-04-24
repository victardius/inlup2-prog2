
public abstract class Location {
	
	private int[] coordinates = new int[2];
	private String name;
	private String category;

	public Location(int coordinates[], String name, String category){
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
	
	public String getCategory(){
		return category;
	}
	
	public abstract String toString();
	
}