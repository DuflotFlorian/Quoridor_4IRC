package Class;

public interface Piece {

    QuoridorColor getQuoridorColor();

    String getName();

    Coordinates getCoordinates();

    boolean move(Coordinates coord);
}
