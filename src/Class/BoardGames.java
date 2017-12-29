package Class;

public interface BoardGames {
    /**
     * Permet de deplacer une piece connaissant ses coordonnees initiales
     * vers ses coordonnees finales 	 *
     * @param initCoord coord initial
     * @param finalCoord coord final
     * @return OK si deplacement OK
     */
    public boolean move (Coordonnees initCoord, Coordonnees finalCoord, boolean isWall);

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
    public Couleur getColorCurrentPlayer();

    /**
     * @param c Coordonnees
     * @return la couleur de la pièce sélectionnée
     */
    public Couleur getPieceColor(Coordonnees c);
}
