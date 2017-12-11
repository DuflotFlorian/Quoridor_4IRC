package Class;

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
			this.joueurs[0] = new Joueur(10, Couleur.BLANC, new Coordonnees(0,8));
			this.joueurs[0].setWinCoord(new Coordonnees(16,8));
			this.plateau.addPion(this.joueurs[0].getPion());
			this.joueurs[1] = new Joueur(10, Couleur.NOIR, new Coordonnees(16,8));
			this.joueurs[1].setWinCoord(new Coordonnees(0,8));
			this.plateau.addPion(this.joueurs[1].getPion());
			break;

		default:
			break;
		}
	}

	public Joueur getCurrentPlayer(){ //TODO return numéro ou couleur. On return pas un objet en entier avec tous sont contenus accseeible direct en dessous
		return this.joueurs[currentPlayer];
	}

	public void move(Joueur j, Coordonnees c){ //TODO Utiliser couleur au lieu du joueur //Renommer en movePion
		if(j.equals(getCurrentPlayer())){
			if(!isPlayerHere(c)){
				if(j.isMoveOk(c)){
					this.plateau.movePion(j.getCoordonnees(), c);
					j.move(c);
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
			if(this.joueurs[i].getCoordonnees().equals(coord)){
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isWin(){
		boolean result = false;
		for(Joueur j : joueurs){
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

		res += "Plateau : \n" + this.plateau.toString();

		return res;
	}

	//TODO isPlayerMoveOK()

	//TODO isMurMoveOK()

	//TODO int getMurDisponnibles(Couleur c)

	//TODO List<Coordonnees> getPionsIhm() + la classe Piece IHM

	//TODO List<Coordonnees> getMursIhM()

	//TODO moveMur ou setMur , .....


}
