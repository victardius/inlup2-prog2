public class NamedPlace extends Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NamedPlace(Coordinates coordinates, String name, Category category) {
		super(coordinates, name, category);
	}

	@Override
	public String toString() {
		return "Named," + getCategory() + "," + getCoordinatesToString() + "," + getName();
	}

	public String printInfo() {
		String[] outprint = this.toString().split(",");
		return "Name: " + outprint[4] + "\nCoordinates: " + outprint[2] + ", " + outprint[3];
	}

}
