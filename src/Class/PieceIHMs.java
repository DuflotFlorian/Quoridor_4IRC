package Class;

public interface PieceIHMs {
    /**
     * @return coordonnées de la position de la piece
     */
    public Coordonnees getCoordonnees();

    /**
     * @return couleur de la piece
     */
    public Couleur getCouleur();


    /**
     * @return le nom de la pièce (Tour, Cavalier, etc.)
     *
     */
    public String getNamePiece();
}
