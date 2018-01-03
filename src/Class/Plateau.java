package Class;

public class Plateau {
	private GrillePion grillePion;
	private GrilleMur grilleMur;
	
	public Plateau() {
		this.grillePion = new GrillePion();
		this.grilleMur = new GrilleMur();

		this.grilleMur.addElement(new Mur( new Coordonnees(1,1), Couleur.BLANC, true ));
		this.grilleMur.addElement(new Mur( new Coordonnees(5,7), Couleur.BLANC, true ));
		this.grilleMur.addElement(new Mur( new Coordonnees(7,3), Couleur.BLANC, false ));
	}

	public boolean addPion(Pion p){
		return this.grillePion.ajouterPion(p);
	}

	public boolean movePion(Coordonnees oldCoord, Coordonnees newCoord){
		return this.grillePion.move(oldCoord, newCoord);
	}

	public boolean isThereAPath(){
		AStar.findPath(this.grilleMur, new Coordonnees(1,1), new Coordonnees(16,1));
		return true;
	}

	public String toString(){
		return this.grillePion.toString() + "\n" + this.grilleMur.toString();
	}
}
