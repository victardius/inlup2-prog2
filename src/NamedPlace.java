public class NamedPlace extends Location {

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

}
