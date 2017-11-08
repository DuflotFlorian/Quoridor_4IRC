package Class;

import java.util.ArrayList;

public abstract class AbstractGrille {
    private ArrayList<AbstractPiece> elements;
    private String name;

    AbstractGrille(ArrayList elem){
        super();
        this.elements = elem;
        this.name = this.getClass().getSimpleName();
    }

    public ArrayList<AbstractPiece> getElements() {
        return elements;
    }

    public boolean addElement(AbstractPiece elem){
        this.elements.add(elem);
        return true;
    }

    public String getName(){
        return this.name;
    }
}
