package Class;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return this.x + "&" + this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Coordinates)) {
            return false;
        }

        Coordinates coord = (Coordinates) obj;

        return ((this.x == coord.getX()) && (this.y == coord.getY()));
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;

        return result;
    }


}
