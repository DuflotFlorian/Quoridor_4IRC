package Class;

public class Coordonnees {
	private int x;
	private int y;
	
	public Coordonnees(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "x = " + this.x + " y = " + this.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Coordonnees)) {
			return false;
		}

		Coordonnees coord = (Coordonnees) obj;
		return ((this.x == coord.getX()) && (this.y == coord.getY()));

	}

}
