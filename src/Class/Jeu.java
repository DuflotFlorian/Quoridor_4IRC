package Class;

public class Jeu {
	private Joueur[] joueurs;
	private Plateau plateau;
	private int nbJoueurs;
	
	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.plateau = new Plateau();
		switch (this.nbJoueurs) {
		case 2:
			this.joueurs[0] = new Joueur(10, Couleur.BLANC);
			this.joueurs[1] = new Joueur(10, Couleur.NOIR);
			break;

		default:
			break;
		}
	}
}
