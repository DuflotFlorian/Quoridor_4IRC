package Class;

public class PieceIHM implements PieceIHMs {
    Piece piece;

    public PieceIHM(Piece piece) {
        this.piece = piece;
    }

    @Override
    public Coordinates getCoordinates() {
        return piece.getCoordinates();
    }

    @Override
    public QuoridorColor getQuoridorColor() {
        return piece.getQuoridorColor();
    }

    @Override
    public String getNamePiece() {
        return piece.getName();
    }
}
