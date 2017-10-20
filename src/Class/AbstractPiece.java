package Class;

public abstract class AbstractPiece implements Piece{

    private Coordonnees coordonnees;
    private Couleur couleur;
    private boolean isHorizontal;
    private String name;

    public static void main(String[] args) {

    }

    public AbstractPiece(Coordonnees coord, Couleur color, boolean isHorizontal){
        super();
        this.coordonnees = coord;
        this.couleur = color;
        this.isHorizontal = isHorizontal;
        this.name = this.getClass().getSimpleName();
    }

    public Coordonnees getCoordonnees(){
        return this.coordonnees;
    }

    public String getName(){
        return this.name;
    }

    public abstract boolean isMoveOk(Coordonnees finalCoord);

    public boolean move(Coordonnees coord){
        this.coordonnees = coord;
        return true;
    }

}
