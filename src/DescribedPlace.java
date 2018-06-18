public class DescribedPlace extends Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;

	protected DescribedPlace(Coordinates coordinates, String name, Category category, String description) {
		super(coordinates, name, category);
		description = description.trim();
		this.description = description;
	}

	protected String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Described," + getCategory() + "," + getCoordinatesToString() + "," + getName() + "," + description;
	}

}
