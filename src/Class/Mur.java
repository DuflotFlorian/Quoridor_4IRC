package Class;

public class Mur extends AbstractPiece{
	private Coordonnees coordonnees;
	private boolean isHorizontal;
	private Couleur color;
	
	public Mur(Coordonnees coord, Couleur color, boolean isHorizontal) {
		super(coord, color, isHorizontal);
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public boolean getSens() {
		return isHorizontal;
	}

	public void setSens(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public boolean isMoveOk(Coordonnees finalCoord){
		return true;
	}
	
	
}
