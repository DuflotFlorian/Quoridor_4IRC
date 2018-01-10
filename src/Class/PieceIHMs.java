package Class;

public interface PieceIHMs {
    /**
     * @return coordonnées de la position de la piece
     */
    Coordinates getCoordinates();

    /**
     * @return couleur de la piece
     */
    QuoridorColor getQuoridorColor();

    /**
     * @return le nom de la pièce (Tour, Cavalier, etc.)
     */
    String getNamePiece();
}
