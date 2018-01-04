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
			this.joueurs[0] = new Joueur(10, Couleur.BLEU, new Coordonnees(0,8),new Coordonnees(16,8));
			this.joueurs[1] = new Joueur(10, Couleur.ROUGE, new Coordonnees(16,8),new Coordonnees(0,8));
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

				//Vérification présence mur pendant le deplacement
				if(canPionPass(j.getActualCoord() , finalCoord)){
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
					System.out.println("Pion ne passe pas");
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
			//Le placement du mur de base est valide
			Coordonnees[] tabCoordWall = new Coordonnees[3];

			//Obtention de la coordonnées complète du futur mur
			if (Mur.isWallBeHorizontal(wallCoord)) {
				tabCoordWall[0]= wallCoord;
				tabCoordWall[1]= new Coordonnees(wallCoord.getX() , wallCoord.getY()+ 1);
				tabCoordWall[2]= new Coordonnees(wallCoord.getX() , wallCoord.getY()+ 2);
			} else if (Mur.isWallBeVertical(wallCoord)) {
				tabCoordWall[0]= wallCoord;
				tabCoordWall[1]= new Coordonnees(wallCoord.getX()+1 , wallCoord.getY());
				tabCoordWall[2]= new Coordonnees(wallCoord.getX()+2 , wallCoord.getY());
			}

			//Vérification de non croisement et tentative poser un mur deja existant
			for (Coordonnees coord : tabCoordWall) {
				if(isWallHere(coord)) {
					return false;
				}
			}

			j.putWall(wallCoord);
			changeJoueur();
			return true;
		} else {
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


	public void isThereAPath(){
		this.plateau.isThereAPath();
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

	/**
	 * Permet de savoir si une piece de type mur à cette coord
	 * @param coord
	 * @return
	 */
	public boolean isWallHere(Coordonnees coord) {
		Piece p;
		for(Joueur jou : joueurs) {
			p = jou.findPiece(coord);
			if(p != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Renvoi true si il n'y a pas de mur entre l'ancienne et la nouvelle position du pion. Renvoi false dans le cas contraire
	 * @param initCoord
	 * @param finalCoord
	 * @return
	 */
	private boolean canPionPass(Coordonnees initCoord , Coordonnees finalCoord){

		//Si diffX != 0 diffY ==0 et inverssement
		//L'ecart est de 2 normalement et 4 en cas de saut de pion
		int diffX = finalCoord.getX() - initCoord.getX();
		int diffY = finalCoord.getY() - initCoord.getY();

		if(isCoordCoverByWall(new Coordonnees(initCoord.getX() + diffX/2, initCoord.getY() + diffY/2))){ //Deplacement normal, vérification de la non présence d'un mur entre ancienne et nouvelle position
			return false;
		}

		if(diffX == 4 || diffY == 4) { //Saut sur l'axe X ou Y
			if (isCoordCoverByWall(new Coordonnees(initCoord.getX() + diffX - (diffX / 4), initCoord.getY() + diffY - (diffY / 4)))) { //Test de la case juste avant la case destination
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de savoir si la case est couverte par un mur.
	 * Soit la case posède un pièce avec un mur.
	 * Soit on test qu'il n'y ait pas un mur horizontal ou vertical sur la case à gauche ou en haut qui s'etend sur la case actuelle
	 * @return
	 */
	private  boolean isCoordCoverByWall(Coordonnees coord){
		if(isWallHere(coord)){ //Partie gauche d'un mur horizontal ou partie haute d'un mur vertical
			return true;
		} else if(isWallHere(new Coordonnees(coord.getX(),coord.getY()-2))){// Partie droite d'un mur horizontal
			return true;
		} else if(isWallHere(new Coordonnees(coord.getX()-2,coord.getY()))){// Partie basse d'un mur horizontal
			return true;
		}
		return false;
	}

}
