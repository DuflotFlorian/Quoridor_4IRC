package Class;

public interface Piece {

    public Couleur getCouleur();

    public String getName();

    public Coordonnees getCoordonnees();

    public boolean move(Coordonnees coord);


}
