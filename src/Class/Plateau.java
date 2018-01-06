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

	//TODO
	public boolean isThereAPath(){
		return AStar.findPath(this.grilleMur, new Coordonnees(0,0), new Coordonnees(16,0));
	}

	public void addMur(Mur m) {
		grilleMur.ajouterMur(m);
	}

	public void removeMur(Mur m){
		grilleMur.removeMur(m);
		System.out.println("Toto");
	}

}
