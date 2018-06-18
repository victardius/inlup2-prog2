public class NamedPlace extends Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected NamedPlace(Coordinates coordinates, String name, Category category) {
		super(coordinates, name, category);
	}

	@Override
	public String toString() {
		return "Named," + getCategory() + "," + getCoordinatesToString() + "," + getName();
	}

}
