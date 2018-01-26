package Class;

import java.util.ArrayList;

public abstract class AbstractGrid {
    private ArrayList<AbstractPiece> elements;
    private String name;

    AbstractGrid(ArrayList elem) {
        super();
        this.elements = elem;
        this.name = this.getClass().getSimpleName();
    }

    public ArrayList<AbstractPiece> getElements() {
        return elements;
    }

    public abstract boolean isAddOk(Coordinates coord);

    public boolean addElement(AbstractPiece elem) {
        if (isAddOk(elem.getCoordinates())) {
            this.elements.add(elem);

            return true;
        }

        return false;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberElement() {
        return this.elements.size();
    }

    public void removeElement(AbstractPiece p) {
        this.elements.remove(p);
    }

}
