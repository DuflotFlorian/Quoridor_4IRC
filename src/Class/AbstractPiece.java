package Class;

public abstract class AbstractPiece implements Piece {

    private Coordinates coordinates;
    private QuoridorColor quoridorColor;
    private String name;

    public static void main(String[] args) { }

    AbstractPiece(Coordinates coord, QuoridorColor color) {
        super();
        this.coordinates = coord;
        this.quoridorColor = color;
        this.name = this.getClass().getSimpleName();
    }

    public void setCoordinates(Coordinates coord) {
        this.coordinates = coord;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public QuoridorColor getQuoridorColor() {
        return quoridorColor;
    }

    public void setQuoridorColor(QuoridorColor quoridorColor) {
        this.quoridorColor = quoridorColor;
    }

    public String getName() {
        return this.name;
    }

    public boolean move(Coordinates coord) {
        this.coordinates = coord;

        return true;
    }
}
