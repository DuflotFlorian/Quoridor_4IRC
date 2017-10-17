package Class;

import java.util.ArrayList;

public class Jeu {
	private Joueur[] joueurs;
	private Plateau plateau;
	private int nbJoueurs;
	private int currentPlayer;
	
	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.plateau = new Plateau();
		this.currentPlayer = 0;
		this.joueurs = new Joueur[nbJoueurs];
		switch (this.nbJoueurs) {
		case 2:
			this.joueurs[0] = new Joueur(10, Couleur.BLANC);
			this.joueurs[0].setCoordonnees(new Coordonnees(8,0));
			this.joueurs[1] = new Joueur(10, Couleur.NOIR);
			this.joueurs[1].setCoordonnees(new Coordonnees(8,16));
			break;

		default:
			break;
		}
	}

	public Joueur getCurrentPlayer(){
		return this.joueurs[currentPlayer];
	}

	public ArrayList<Coordonnees> getAvailableMove(Joueur j) {
		return j.getAvailableMove();
	}

	public boolean moveIfAllow(Joueur j, Coordonnees coord) {
		if(getCurrentPlayer().equals(j) && getAvailableMove(j).contains(coord)){
			j.move(coord);
			this.currentPlayer = (this.currentPlayer + 1) % this.nbJoueurs;
			return true;
		}
		return false;
	}

	public boolean isPlayerHere(Coordonnees coord){
		boolean result = false;
		for(int i = 0; i < this.nbJoueurs; i++){
			if(this.joueurs[i].getCoordonnees().equals(coord)){
				result = true;
				break;
			}
		}
		return result;
	}
}
