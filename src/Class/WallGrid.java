package Class;

import java.util.ArrayList;

public class WallGrid extends AbstractGrid {

    public WallGrid() {
        super(new ArrayList<Wall>());
    }

    public String toString() {
        String res = "Grille mur :\n";
        for (AbstractPiece piece : this.getElements()) {
            Wall m = (Wall) piece;
            res += "\t" + m.toString() + "\n";
        }
        return res;
    }

    //TODO
    public boolean isAddOk(Coordinates coord) {
        return true;
    }

    public void addWall(Wall m) {
        this.addElement(m);
    }

    public int getNumberWall() {
        return this.getNumberElement();
    }

    public Wall getWall(int i) {
        return (Wall) this.getElements().get(i);
    }

    public void removeWall(Wall m) {
        this.removeElement(m);
    }
}
