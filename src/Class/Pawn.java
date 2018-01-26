package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pawn extends AbstractPiece {

    public Pawn(Coordinates coord, QuoridorColor color) {
        super(coord, color);
    }

    public boolean isMoveOk(Coordinates finalCoord, boolean isJumping) {
        return getAvailableMove(isJumping).contains(finalCoord);
    }

    private ArrayList<Coordinates> getAvailableMove(boolean isJumping) {
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        boolean validX, validY;
        int diffOk = 2;
        if (isJumping) {
            diffOk = 4;
        }
        for (int x = 0; x < 17; x++) {
            for (int y = 0; y < 17; y++) {
                int difX, difY;
                difX = abs(this.getCoordinates().getX() - x);
                difY = abs(this.getCoordinates().getY() - y);
                validX = (difX == diffOk) || (difX == 0);
                validY = (difY == diffOk) || (difY == 0);
                if ((validX && validY) && (difX + difY == diffOk)) {
                    result.add(new Coordinates(x, y));
                }
            }
        }
        return result;
    }

    public String toString() {
        return "Pawn : " + this.getCoordinates().toString() + "  " + this.getQuoridorColor().toString();
    }
}
