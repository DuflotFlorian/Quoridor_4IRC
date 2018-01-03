package Class;

import Class.*;

public class PieceIHM implements PieceIHMs {
    Piece piece;

    public PieceIHM(Piece piece){
        this.piece = piece;
    }

    @Override
    public Coordonnees getCoordonnees() {
        return piece.getCoordonnees();
    }

    @Override
    public Couleur getCouleur() {
        return piece.getCouleur();
    }

    @Override
    public String getNamePiece() {
        return piece.getName();
    }
}
