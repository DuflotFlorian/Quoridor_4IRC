package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pion extends AbstractPiece{
	private Coordonnees coordonnees;
	private Couleur couleur;
	
	public Pion(Coordonnees coord, Couleur color) {
		super(coord, color, true);
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public boolean isMoveOk(Coordonnees finalCoord){
		return true;
	}

	private ArrayList<Coordonnees> getAvailableMove() {
		ArrayList<Coordonnees> result = new ArrayList<Coordonnees>();
		boolean validX, validY;
		for(int x = 0; x < 17; x++){
			for(int y = 0; y < 17; y++){
				int difX, difY;
				difX = abs(this.getCoordonnees().getX() - x);
				difY = abs(this.getCoordonnees().getY() - y);
				validX = (difX == 2) || (difX == 0);
				validY = (difY == 2) || (difY == 0);
				if((validX && validY) && (difX + difY == 2)){
					result.add(new Coordonnees(x,y));
				}
			}
		}
		return result;
	}
	
}
