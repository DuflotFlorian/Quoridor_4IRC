package Class;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
	private Joueur[] joueurs;
	private int nbJoueurs;
	private int currentPlayer;

	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.currentPlayer = 0;

		switch (this.nbJoueurs) {
		case 2:
			this.joueurs = new Joueur[2];
			this.joueurs[0] = new Joueur(10, Couleur.BLANC, new Coordonnees(0,8),new Coordonnees(16,8));
			this.joueurs[1] = new Joueur(10, Couleur.NOIR, new Coordonnees(16,8),new Coordonnees(0,8));
			break;

		default:
			break;
		}
	}

	public Joueur getCurrentPlayer(){
		return this.joueurs[currentPlayer];
	}

	public void move(Joueur j, Coordonnees finalCoord){
		if(j.equals(getCurrentPlayer())){
			if(!isPlayerHere(finalCoord)){
				if(j.isMoveOk(j.findPion().getCoordonnees(), finalCoord)){
					j.move(j.findPion().getCoordonnees(),finalCoord);
					changeJoueur();
					if(isWin()){
						System.out.println("Fin du jeu");
					}
				} else {
					System.out.println("Ce deplacement n'est pas permis\n");
				}
			} else {
				System.out.println("Il y a deja un joueur ici\n");
			}
		} else {
			System.out.println("Ce n'est pas le joueur courant\n");
		}
	}

	public void changeJoueur(){
		this.currentPlayer += 1;
		this.currentPlayer = this.currentPlayer % this.nbJoueurs;
	}

	public boolean isPlayerHere(Coordonnees coord){
		boolean result = false;
		for(int i = 0; i < this.nbJoueurs; i++){
			if(this.joueurs[i].findPion().getCoordonnees().equals(coord)){
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isWin(){
		boolean result = false;
		for(Joueur j : joueurs){
			if(j.findPion().getCoordonnees().equals(j.getWinCoord())){
				result = true;
				break;
			}
		}
		return result;
	}

    public List<PieceIHMs> getPiecesIHM() {
	    List<PieceIHMs> result = new ArrayList<PieceIHMs>();
	    for(Joueur j: joueurs){
	        result.addAll(j.getPiecesIHM());
        }
        return result;
    }

}
