package Class;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Joueur {
	private List<Piece> pieces;
	private Couleur couleur;
	private Coordonnees actualCoord;
	private Coordonnees winCoord;

	public Joueur(int nbMurs, Couleur c, Coordonnees coord, Coordonnees winCoord) {
		this.couleur = c;
		this.actualCoord = coord;
		Pion pion = new Pion(coord, c);
		pieces = new ArrayList<Piece>();
		pieces.add(pion);
		for(int i = 0; i < nbMurs; i++){
			Mur m = new Mur(new Coordonnees(-1, -1), c, true);
			pieces.add(m);
		}

		this.winCoord = winCoord;
	}

	public Couleur getCouleurs() {
		return this.couleur;
	}

	public Piece findPiece(Coordonnees coord){
		for(Piece p : pieces){
			if(p.getCoordonnees().equals(coord)){
				return p;
			}
		}
		return null;
	}

	public Coordonnees getWinCoord() {
		return winCoord;
	}

	public boolean move(Coordonnees initCoord, Coordonnees finalCoord){
		Piece p = findPiece(initCoord);
		p.move(finalCoord);
		this.actualCoord = finalCoord;
		pieces.set(pieces.indexOf(p),p);
		return true;
	}

	public boolean isMoveOk(Coordonnees initCoord, Coordonnees finalCoord){
		Piece p = null;
		p = findPiece(initCoord);
		return p.isMoveOk(finalCoord);

	}


	public boolean isWallOk(Coordonnees wallCoord) {
		Piece p = getFirstWallUnsued();
		if (p != null){ //Quand le joueur n'as plus de mur dispo
			return p.isMoveOk(wallCoord);
		} else {
			return false;
		}
	}

	public boolean putWall(Coordonnees wallCoord) {
		Piece p = getFirstWallUnsued();
		if (p != null){ //Quand le joueur n'as plus de mur dispo
			return p.move(wallCoord);
		} else {
			return false;
		}
	}

	public List<PieceIHMs> getPiecesIHM(){
		List<PieceIHMs> result = new LinkedList<PieceIHMs>();
		for (Piece p : pieces) {
			if(!p.getCoordonnees().equals(new Coordonnees(-1,-1))){
				PieceIHM pIhm = new PieceIHM(p);
				result.add(pIhm);
			}
		}
		return result;
	}

	public Coordonnees getActualCoord() {
		return this.actualCoord;
	}

	public String toString() {
		String res = "";
		res += "\tCouleur : " + this.getCouleurs() + "\n";
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Joueur)) {
			return false;
		}

		Joueur j = (Joueur) obj;

		return j.getCouleurs().equals(this.getCouleurs());
	}

	private Piece getFirstWallUnsued(){
		for (Piece p : pieces) {
			if(p.getCoordonnees().equals(new Coordonnees(-1,-1)) && p.getName().equals("Mur")) {
				return p;
			}
		}
		return null;
	}
}

