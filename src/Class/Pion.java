package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pion extends AbstractPiece{
	
	Pion(Coordonnees coord, Couleur color) {
		super(coord, color);
	}

	public boolean isMoveOk(Coordonnees finalCoord){
		return getAvailableMove().contains(finalCoord);
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
