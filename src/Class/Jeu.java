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

		switch (this.nbJoueurs) {
		case 2:
			this.joueurs = new Joueur[2];
			this.joueurs[0] = new Joueur(10, Couleur.BLANC);
			this.joueurs[0].setPionCoordonnees(new Coordonnees(0,8));
			this.joueurs[0].setWinCoord(new Coordonnees(16,8));
			this.joueurs[1] = new Joueur(10, Couleur.NOIR);
			this.joueurs[1].setPionCoordonnees(new Coordonnees(16,8));
			this.joueurs[1].setWinCoord(new Coordonnees(0,8));
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
		if(getCurrentPlayer().equals(j) && getAvailableMove(j).contains(coord) && !isPlayerHere(coord)){
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

	public boolean isWin(){
		boolean result = false;
		for(int i = 0; i < joueurs.length; i++){
			Joueur j = joueurs[i];
			if(j.getCoordonnees().equals(j.getWinCoord())){
				result = true;
				break;
			}
		}
		return result;
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
