package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;


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

	public void setCoordonnees(Coordonnees coord) {
		this.pion.setCoordonnees(coord);
	}

	public Coordonnees getCoordonnees() {
		return this.pion.getCoordonnees();
	}

	public ArrayList<Coordonnees> getAvailableMove() {
		ArrayList<Coordonnees> result = new ArrayList<Coordonnees>();
		boolean validX, validY;
		for(int x = 0; x < 17; x++){
			for(int y = 0; y < 17; y++){
				int difX, difY;
				difX = abs(this.getCoordonnees().getX() - x);
				difY = abs(this.getCoordonnees().getY() - y);
				validX = (difX == 2) || (difX == 0);
				validY = (difY == 2) || (difY == 0);
				if((validX && validY) && (difX + difY == 2)){
					result.add(new Coordonnees(x,y));
				}
			}
		}
		return result;
	}

	public void move(Coordonnees coord) {
		this.setCoordonnees(coord);
	}

	public boolean equals(Joueur obj) {
		return obj.getCouleurs().equals(this.getCouleurs());
	}
}

