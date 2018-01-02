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

		if (finalCoord.getX() % 2 == 1 && finalCoord.getX()> 0 && finalCoord.getX()< 16 && finalCoord.getY() % 2 == 0 && finalCoord.getY()>= 0 && finalCoord.getX()< 15) { //Verif que la case est une case horizontale
			return true;
		} else if (finalCoord.getX() % 2 == 0 && finalCoord.getX()>= 0 && finalCoord.getX()< 15 && finalCoord.getY() % 2 == 1 && finalCoord.getY()> 0 && finalCoord.getX()< 16){
			return true;
		}

		return false;
	}

	public String toString(){
		String res = "Mur : " + this.getCoordonnees().toString() + "  " + this.getCouleur().toString();
		res += "   " + ((this.isHorizontal) ?  "horizontal" : "vertical");
		return res;
	}

	@Override
	/**
	 * Le isMoveOK doit être testé avant
	 */
	public boolean move(Coordonnees finalCoord){

		//Set wall vertical ou horizontal
		if(isWallBeHorizontal(finalCoord)) {
			this.isHorizontal = true;
		} else {
			this.isHorizontal = false;
		}
		//Position du mur OK
		this.setCoordonnees(finalCoord);
		return true;
	}

	/**
	 * Renvoi true si le mur posé à cette case est Horizontal
	 * Renvoi false si le mur est vertical ou impossible
	 * @param wallCoord
	 * @return
	 */
	public static boolean isWallBeHorizontal(Coordonnees wallCoord){
		if (wallCoord.getX() % 2 == 1 && wallCoord.getX()> 0 && wallCoord.getX()< 16 && wallCoord.getY() % 2 == 0 && wallCoord.getY()>= 0 && wallCoord.getX()< 15) { //Verif que la case est une case horizontale
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Renvoi true si le mur posé à cette case est Vertical
	 * Renvoi false si le mur est horizontal ou impossible
	 * @param wallCoord
	 * @return
	 */
	public static boolean isWallBeVertical(Coordonnees wallCoord){
		if (wallCoord.getX() % 2 == 0 && wallCoord.getX()>= 0 && wallCoord.getX()< 15 && wallCoord.getY() % 2 == 1 && wallCoord.getY()> 0 && wallCoord.getX()< 16) { //Verif que la case est une case verticale
			return true;
		} else {
			return false;
		}

	}

}
