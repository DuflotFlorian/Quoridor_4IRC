package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pion extends AbstractPiece{
	
	public Pion(Coordonnees coord, Couleur color) {
		super(coord, color);
	}

	public boolean isMoveOk(Coordonnees finalCoord, Boolean isJumping){
			return getAvailableMove(isJumping).contains(finalCoord);
	}

	private ArrayList<Coordonnees> getAvailableMove(Boolean isJumping) {
		ArrayList<Coordonnees> result = new ArrayList<Coordonnees>();
		boolean validX, validY;
		int diffOk = 2;
		if(isJumping){
			diffOk = 4;
		}
		for(int x = 0; x < 17; x++){
			for(int y = 0; y < 17; y++){
				int difX, difY;
				difX = abs(this.getCoordonnees().getX() - x);
				difY = abs(this.getCoordonnees().getY() - y);
				validX = (difX == diffOk) || (difX == 0);
				validY = (difY == diffOk) || (difY == 0);
				if((validX && validY) && (difX + difY == diffOk)){
					result.add(new Coordonnees(x,y));
				}
			}
		}
		return result;
	}

	public String toString(){
		String res = "Pion : " + this.getCoordonnees().toString() + "  " + this.getCouleur().toString();
		return res;
	}
	
}
