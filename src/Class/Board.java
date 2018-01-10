package Class;

public class Board {
    private PawnGrid pawnGrid;
    private WallGrid wallGrid;

    public Board() {
        this.pawnGrid = new PawnGrid();
        this.wallGrid = new WallGrid();
    }

    public boolean addPawn(Pawn p) {
        return this.pawnGrid.addPawn(p);
    }

    public boolean movePawn(Coordinates oldCoord, Coordinates newCoord) {
        return this.pawnGrid.move(oldCoord, newCoord);
    }

    public String toString() {
        return this.pawnGrid.toString() + "\n" + this.wallGrid.toString();
    }

    public boolean isThereAPath(Coordinates init, Coordinates dest) {
        return AStar.isThereAPath(this.wallGrid, init, dest);
    }

    public void addWall(Wall m) {
        wallGrid.addWall(m);
    }

    public void removeWall(Wall m) {
        wallGrid.removeWall(m);
    }

}
