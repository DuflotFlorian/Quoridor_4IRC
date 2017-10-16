package Class;

import java.util.ArrayList;


public class Joueur {
	private Pion pion;
	private ArrayList<Mur> listMurs;
	private int nbMaxMurs;
	
	public Joueur(int nbMurs, Couleur c) {
		this.pion = new Pion(c);
		this.listMurs = new ArrayList<Mur>();
		this.nbMaxMurs = nbMurs;
	}

	public Couleur getCouleurs() {
		return pion.getCouleur();
	}
	
	public int nbMursPosee(){
		return this.listMurs.size();
	}

	public int getNbMaxMurs() {
		return nbMaxMurs;
	}

}

