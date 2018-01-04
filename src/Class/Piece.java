package Class;

public interface Piece {

    public Couleur getCouleur();

    public String getName();

    public Coordonnees getCoordonnees();

    public boolean isMoveOk(Coordonnees finalCoord, boolean isJumping);

    public boolean move(Coordonnees coord);
}
