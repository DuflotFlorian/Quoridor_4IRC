package Class;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
	private Joueur[] joueurs;
	private int nbJoueurs;
	private int idCurrentPlayer;
	private Plateau plateau;

	public Jeu(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
		this.idCurrentPlayer = 0;
		this.plateau = new Plateau();

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
				boolean isJumping = false;
				int diff = 0;
				Coordonnees currentCoord = j.getActualCoord();
				if(Math.abs(currentCoord.getX()-finalCoord.getX()) == 4 && currentCoord.getY()-finalCoord.getY() == 0) {
					diff = currentCoord.getX()-finalCoord.getX();
					isJumping = isPlayerHere(new Coordonnees(currentCoord.getX()-(diff/2) ,currentCoord.getY()));
				} else if (Math.abs(currentCoord.getY()-finalCoord.getY()) == 4 && currentCoord.getX()-finalCoord.getX() == 0) {
					diff = currentCoord.getY()-finalCoord.getY();
					isJumping = isPlayerHere(new Coordonnees(currentCoord.getX() ,currentCoord.getY()-(diff/2)));
				}

				//Vérification présence mur pendant le deplacement
				if(canPionPass(j.getActualCoord() , finalCoord)) {
					if (j.isMoveOk(j.getActualCoord(), finalCoord, isJumping)) {
						j.move(j.getActualCoord(), finalCoord);
						changeJoueur();
						ret = true;
						if (isWin()) {
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
			//Vérification de non croisement et tentative poser un mur deja existant
			if(isCoordCoverByWallForPutWall(wallCoord)) {
				return false;
			}
			if(!addNewWallIfPaths(wallCoord)){
				//Enfermement si on pose un mur à cette coordonnée
				return false;
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

	public int getIntIdCurrentPlayer() {
		return idCurrentPlayer;
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
			if(p != null && !(p instanceof Pion)) {
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

		if(isCoordCoverByWallForMovePion(new Coordonnees(initCoord.getX() + diffX/2, initCoord.getY() + diffY/2))){ //Deplacement normal, vérification de la non présence d'un mur entre ancienne et nouvelle position
			return false;
		}

		if(diffX == 4 || diffY == 4) { //Saut sur l'axe X ou Y
			if (isCoordCoverByWallForMovePion(new Coordonnees(initCoord.getX() + diffX - (diffX / 4), initCoord.getY() + diffY - (diffY / 4)))) { //Test de la case juste avant la case destination
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
	private  boolean isCoordCoverByWallForMovePion(Coordonnees coord){
		if(isWallHere(coord)){
			return true;
		}else if (Mur.isWallBeHorizontal(coord)) {
			if (isWallHere(new Coordonnees(coord.getX(), coord.getY() - 2))) {
				return true;
			}
		}else {
			if (isWallHere(new Coordonnees(coord.getX() - 2, coord.getY()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet de savoir si la case est couverte par un mur.
	 * Soit la case posède un pièce avec un mur.
	 * Soit on test qu'il n'y ait pas un mur horizontal ou vertical sur la case à gauche ou en haut qui s'etend sur la case actuelle
	 * @return
	 */
	private  boolean isCoordCoverByWallForPutWall(Coordonnees coord){
		if(Mur.isWallBeHorizontal(coord)){
			if(isWallHere(coord) || isWallHere(new Coordonnees(coord.getX(),coord.getY()+2)) || isWallHere(new Coordonnees(coord.getX(),coord.getY()-2)) || isWallHere(new Coordonnees(coord.getX()-1,coord.getY()+1))){
				return true;
			}
		} else {
			if(isWallHere(coord) || isWallHere(new Coordonnees(coord.getX()+2,coord.getY())) || isWallHere(new Coordonnees(coord.getX()-2,coord.getY())) || isWallHere(new Coordonnees(coord.getX()+1,coord.getY()-1))){
				return true;
			}
		}
		return false;
	}

	public Couleur getPlayerColor(int numPlayer){
		return joueurs[numPlayer].getCouleurs();
	}


	private boolean isThereAPath(Coordonnees init,Coordonnees dest){
		return this.plateau.isThereAPath(init,dest);
	}

	public int getPlayerWallRemaining(int numPlayer){
		return joueurs[numPlayer].getWallRemaining();
	}

	/** Ajoute le nouveau mur à la grille Mur et renvoi true si un chemin est possible après pose de ce mur
	 *
	 * @param wallCoord
	 * @return
	 */
	public boolean addNewWallIfPaths(Coordonnees wallCoord){
		Mur tmpMur = new Mur(wallCoord,getIdCurrentPlayer().getCouleurs(),Mur.isWallBeHorizontal(wallCoord));
		plateau.addMur(tmpMur);
		//Test de la présence d'un chemin pour chaque Joueur
		boolean retJoueur = false;
		int lineToCheck = -1;
		for (Joueur jou : joueurs){
			if(jou.getCouleurs().equals(Couleur.BLEU)){
				lineToCheck = 16;
			}else if (jou.getCouleurs().equals(Couleur.ROUGE)){
				lineToCheck = 0;
			}
			for(int i=0; i<17 ;i=i+2){
				if(isThereAPath(jou.getActualCoord(),new Coordonnees(lineToCheck,i))) {
					retJoueur = true;
					break;
				}
			}
			if( retJoueur == false) { //Si un des joueurs n'a pas de chemin on stoppe
				plateau.removeMur(tmpMur);
				return false;
			} else {
				retJoueur = false;
			}

		}
		return true;
	}
}
