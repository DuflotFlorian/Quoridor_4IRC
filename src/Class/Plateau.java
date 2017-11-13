package Class;

public class Plateau {
	private GrillePion grillePion;
	private GrilleMur grilleMur;
	
	public Plateau() {
		this.grillePion = new GrillePion();
		this.grilleMur = new GrilleMur();
	}

	public boolean addPion(Pion p){
		return this.grillePion.ajouterPion(p);
	}

	public boolean movePion(Coordonnees oldCoord, Coordonnees newCoord){
		return this.grillePion.move(oldCoord, newCoord);
	}

	public String toString(){
		return this.grillePion.toString() + "\n" + this.grilleMur.toString();
	}
}
