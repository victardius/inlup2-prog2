
public class Coordinates {

	private int x, y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int hashCode() {
		return x * 1000 + y;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Coordinates) {
			Coordinates c = (Coordinates) obj;
			if (c.getX() == x && c.getY() == y)
				return true;
			else
				return false;
		} else
			return false;
	}

}
