package Class;

public class Mur extends AbstractPiece{
	private boolean isHorizontal;
	
	public Mur(Coordonnees coord, Couleur color, boolean isHorizontal) {
		super(coord, color);
		this.isHorizontal = isHorizontal;
	}

	public Coordonnees getCoordonnees() {
		return super.getCoordonnees();
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		super.setCoordonnees(coordonnees);
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
