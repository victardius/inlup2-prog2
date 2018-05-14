import java.awt.Point;

public abstract class Location {
	
	private Point coordinates;
	private String name;
	private String category;

	public Location(Point coordinates, String name, String category){
		this.coordinates = coordinates;
		this.name = name;
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public Point getCoordinates() {
		return coordinates;
	}
	
	public String getCategory(){
		return category;
	}
	
	public abstract String toString();
	
}