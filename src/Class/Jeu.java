package Class;

public class Jeu {
	private Joueur[] joueurs;
	private Plateau plateau;
	private int nbJoueurs;
	private int currentJoueur;
	
	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.plateau = new Plateau();
		this.currentJoueur = 0;

		switch (this.nbJoueurs) {
		case 2:
			this.joueurs = new Joueur[2];
			this.joueurs[0] = new Joueur(10, Couleur.BLANC);
			this.joueurs[0].setPionCoordonnees(new Coordonnees(9,4));
			this.joueurs[1] = new Joueur(10, Couleur.NOIR);
			this.joueurs[1].setPionCoordonnees(new Coordonnees(0,4));
			break;

		default:
			break;
		}
	}

	public void deplacerPion(Coordonnees coord) {
		this.joueurs[currentJoueur].deplacer(coord);
		this.currentJoueur = (this.currentJoueur + 1) % nbJoueurs;
	}

	private boolean isMoveOk(Coordonnees coord){
		return (this.joueurs[currentJoueur].isMoveOk(coord)) && (this.plateau.isMoveOk(coord));
	}

	@Override
	public String toString() {
		String res = "";
		res += "Nombre de joueurs : " + this.nbJoueurs + "\n";
		for(int i = 0; i < this.joueurs.length; i++){
			res += "Joueur " + i + ": " + this.joueurs[i].toString() + "\n";
		}

		return res;
	}
}
