package Class;

public interface BoardGames {
    /**
     * Permet de deplacer une piece connaissant ses coordonnees initiales
     * vers ses coordonnees finales 	 *
     *
     * @param initCoord  coord initial
     * @param finalCoord coord final
     * @return OK si deplacement OK
     */
    public boolean move(Coordinates initCoord, Coordinates finalCoord, boolean isWall);

    /**
     * @return true si c'est la fin du jeu
     */
    public boolean isEnd();

    /**
     * @return un message sur l'état du jeu
     */
    public String getMessage();

    /**
     * @return la couleur du jouer courant
     */
    public QuoridorColor getColorCurrentPlayer();

    /**
     * @param c Coordinates
     * @return la couleur de la pièce sélectionnée
     */
    public QuoridorColor getPieceColor(Coordinates c);
}
