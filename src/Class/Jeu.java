package Class;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
	private Joueur[] joueurs;
	private int nbJoueurs;
	private int idCurrentPlayer;

	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.idCurrentPlayer = 0;

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

	public Joueur getIdCurrentPlayer(){
		return this.joueurs[idCurrentPlayer];
	}

	public boolean isMoveOk(Coordonnees initCoord, Coordonnees finalCoord){
		return true;
	}

	public boolean move(Joueur j, Coordonnees finalCoord){
		boolean ret = false;
		if(j.equals(getIdCurrentPlayer())){
			if(!isPlayerHere(finalCoord)){
				if(j.isMoveOk(j.getActualCoord(), finalCoord)){
					j.move(j.getActualCoord(),finalCoord);
					changeJoueur();
					ret = true;
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

		return ret;
	}

	public boolean putWall(Joueur j,Coordonnees wallCoord){
		if (j.isWallOk(wallCoord)) {
			System.out.println("isWallOk true");
			//Le placement du mur de base est valide
			//TODO add toutes les verifs de non colision
			Coordonnees[] tabCoordWall = new Coordonnees[3];

			//Obtention de la coordonnées complète du futur mur
			if (Mur.isWallBeHorizontal(wallCoord)) {
				tabCoordWall[0]= wallCoord;
				tabCoordWall[1]= new Coordonnees(wallCoord.getX()+1 , wallCoord.getY());
				tabCoordWall[2]= new Coordonnees(wallCoord.getX()+2 , wallCoord.getY());
			} else if (Mur.isWallBeVertical(wallCoord)) {
				tabCoordWall[0]= wallCoord;
				tabCoordWall[1]= new Coordonnees(wallCoord.getX() , wallCoord.getY()+2);
				tabCoordWall[2]= new Coordonnees(wallCoord.getX(), wallCoord.getY()+2);
			}

			//Vérification de non croisement
			for (Coordonnees coord : tabCoordWall) {
				if(isWallHere(coord)) {
					return false;
				}
			}
			System.out.println("Jeu test de croisement OK"); //TODO debug todo here

			//TODO add tests de non enfermement
			j.putWall(wallCoord);
			changeJoueur();
			return true;
		} else {
			System.out.println("isWallOk false");
			return false;
		}
	}

	public void changeJoueur(){
		this.idCurrentPlayer += 1;
		this.idCurrentPlayer = this.idCurrentPlayer % this.nbJoueurs;
	}

	public boolean isPlayerHere(Coordonnees coord){
		boolean result = false;
		for(int i = 0; i < this.nbJoueurs; i++){
			if(this.joueurs[i].getActualCoord().equals(coord)){
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isWin(){
		boolean result = false;
		for(Joueur j : joueurs){
			if(j.getActualCoord().equals(j.getWinCoord())){
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

    public Couleur getPieceColor(Coordonnees coord){
		return joueurs[idCurrentPlayer].getCouleurs();
	}

	public List<Joueur> listPlayer(){
		List<Joueur> result = new ArrayList<Joueur>();
		for (Joueur j : joueurs) {
			result.add(j);
		}
		return result;
	}

	public boolean isWallHere(Coordonnees coord) {
		Piece p;
		for(Joueur jou : joueurs) {
			p = jou.findPiece(coord);
			if(p != null) {
				return false;
			}
		}
		return true;
	}

}
