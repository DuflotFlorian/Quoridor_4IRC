package Class;

import java.util.ArrayList;


public class PawnGrid extends AbstractGrid {

    public PawnGrid() {
        super(new ArrayList<Pawn>());
    }

    public boolean isAddOk(Coordinates coord) {
        boolean validX, validY;
        validX = (coord.getX() >= 0 && coord.getX() <= 16);
        validY = (coord.getY() >= 0 && coord.getY() <= 16);

        return validX && validY;
    }

    public boolean addPawn(Pawn p) {
        if (isAddOk(p.getCoordinates())) {
            this.addElement(p);
            return true;
        }
        return false;
    }

    public boolean move(Coordinates oldCoord, Coordinates newCoord) {
        for (AbstractPiece piece : this.getElements()) {
            Pawn p = (Pawn) piece;
            if (p.getCoordinates().equals(oldCoord)) {
                p.setCoordinates(newCoord);
                return true;
            }
        }

        return false;
    }

    public String toString() {
        String res = "Grille pion :\n";
        for (AbstractPiece piece : this.getElements()) {
            Pawn p = (Pawn) piece;
            res += "\t" + p.toString() + "\n";
        }
        return res;
    }
}
