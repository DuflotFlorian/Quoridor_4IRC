package Class;
import java.util.ArrayList;


public class GrillePion extends AbstractGrille{
	
	public GrillePion() {
		super(new ArrayList<Pion>());
	}

	public boolean isAddOk(Coordonnees coord){
		boolean validX, validY;
		validX = (coord.getX() >= 0 && coord.getX() <= 16);
		validY = (coord.getY() >= 0 && coord.getY() <= 16);

		return validX && validY;
	}

	public boolean ajouterPion(Pion p){
		if(isAddOk(p.getCoordonnees())){
			this.addElement(p);
			return true;
		}
		return false;
	}

	public boolean move(Coordonnees oldCoord, Coordonnees newCoord){
		for(AbstractPiece piece: this.getElements()){
			Pion p = (Pion) piece;
			if(p.getCoordonnees().equals(oldCoord)){
				p.setCoordonnees(newCoord);
				return true;
			}
		}

		return false;
	}

	public String toString(){
		String res = "Grille pion :\n";
		for (AbstractPiece piece: this.getElements()) {
			Pion p = (Pion) piece;
			res += "\t" + p.toString() + "\n";
		}
		return res;
	}
}
