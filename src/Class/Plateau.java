package Class;

public class Plateau implements Cloneable {
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

	//TODO
	public boolean isThereAPath(){
		return AStar.findPath(this.grilleMur, new Coordonnees(1,1), new Coordonnees(16,1));
	}

	public void addMur(Mur m) {
		grilleMur.ajouterMur(m);
	}

	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}

}
