public abstract class Location {
	
	private Coordinates coordinates;
	private String name;
	private String category;

	public Location(Coordinates coordinates, String name, String category){
		this.coordinates = coordinates;
		this.name = name;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public String getCategory(){
		return category;
	}
	
	public abstract String toString();
	
}