package Class;

public interface Piece {

    public Coordonnees getCoordonnees();

    public boolean isMoveOk(Coordonnees finalCoord);

    public boolean move(Coordonnees coord);
}
