package Class;

public abstract class AbstractPiece implements Piece{

    private Coordonnees coordonnees;
    private Couleur couleur;
    private String name;

    public static void main(String[] args) {

    }

    AbstractPiece(Coordonnees coord, Couleur color){
        super();
        this.coordonnees = coord;
        this.couleur = color;
        this.name = this.getClass().getSimpleName();
    }

    public void setCoordonnees(Coordonnees coord){
        this.coordonnees = coord;
    }

    public Coordonnees getCoordonnees(){
        return this.coordonnees;
    }

    Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public String getName(){
        return this.name;
    }

    public abstract boolean isMoveOk(Coordonnees finalCoord);

    public boolean move(Coordonnees coord){
        if(isMoveOk(coord)){
            this.coordonnees = coord;
            return true;
        }
        return false;
    }

}
