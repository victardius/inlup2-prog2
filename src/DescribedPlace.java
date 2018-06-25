public class DescribedPlace extends Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	public DescribedPlace(Coordinates coordinates, String name, Category category, String description) {
		super(coordinates, name, category);
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

	public String printInfo() {
		String[] outprint = this.toString().split(",");
		return "Name: " + outprint[4] + "\nCoordinates: " + outprint[2] + ", " + outprint[3] + "\nDescription: "
				+ outprint[5];
	}

}
