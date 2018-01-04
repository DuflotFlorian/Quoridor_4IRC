package Class;
import java.util.ArrayList;

public class GrilleMur extends AbstractGrille{
	private ArrayList<Mur> listMurs;
	
	public GrilleMur() {
		super(new ArrayList<Mur>());
	}

	public String toString(){
		String res = "Grille mur :\n";
		for (AbstractPiece piece: this.getElements()) {
			Mur m = (Mur) piece;
			res += "\t" + m.toString() + "\n";
		}
		return res;
	}

	//TODO
	public boolean isAddOk(Coordonnees coord){
		return true;
	}

	public void ajouterMur(Mur m){
		this.addElement(m);
	}

	public int getNumberWall(){
		return this.getNumberElement();
	}

	public Mur getWall(int i){
		return (Mur)this.getElements().get(i);
	}
}
