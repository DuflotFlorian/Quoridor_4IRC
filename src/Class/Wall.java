package Class;

public class Wall extends AbstractPiece {
    private boolean isHorizontal;

    public Wall(Coordinates coord, QuoridorColor color, boolean isHorizontal) {
        super(coord, color);
        this.isHorizontal = isHorizontal;
    }

    public Coordinates getCoordinates() {
        return super.getCoordinates();
    }

    public void setCoordinates(Coordinates coordinates) {
        super.setCoordinates(coordinates);
    }

    public boolean getDirection() {
        return isHorizontal;
    }

    public void setDirection(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }

    public boolean isMoveOk(Coordinates finalCoord) {
        if (finalCoord.getX() % 2 == 1 && finalCoord.getY() % 2 == 0) { //Verif que la case est une case mur horizontale
            return true;
        } else if (finalCoord.getX() % 2 == 0 && finalCoord.getY() % 2 == 1) { //Vérif que la case est une case mur verticale
            return true;
        }
        return false;
    }

    public String toString() {
        String res = "Wall : " + this.getCoordinates().toString() + "  " + this.getQuoridorColor().toString();
        res += "   " + ((this.isHorizontal) ? "horizontal" : "vertical");
        return res;
    }

    @Override
    /**
     * Le isMoveOK doit être testé avant
     */
    public boolean move(Coordinates finalCoord) {
        //Set wall vertical ou horizontal
        this.isHorizontal = false;
        if (isWallHorizontal(finalCoord)) {
            this.isHorizontal = true;
        }
        //Position du mur OK
        this.setCoordinates(finalCoord);

        return true;
    }

    /**
     * Renvoi true si le mur posé à cette case est Horizontal
     * Renvoi false si le mur est vertical ou impossible
     *
     * @param wallCoord
     * @return bool
     */
    public static boolean isWallHorizontal(Coordinates wallCoord) {
        //Verif que la case est une case horizontale
        return wallCoord.getX() % 2 == 1 && wallCoord.getY() % 2 == 0;
    }

}
