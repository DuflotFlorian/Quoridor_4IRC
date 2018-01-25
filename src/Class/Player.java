package Class;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private List<Piece> pieces;
    private QuoridorColor quoridorColor;
    private String name;
    private Coordinates actualCoord;
    private Coordinates winCoord;
    private int nbWall;

    public Player(int nbWall, QuoridorColor c, Coordinates coord, Coordinates winCoord, String name) {
        this.quoridorColor = c;
        this.actualCoord = coord;
        this.name = name;
        Pawn pawn = new Pawn(coord, c);
        pieces = new ArrayList<Piece>();
        pieces.add(pawn);
        for (int i = 0; i < nbWall; i++) {
            Wall m = new Wall(new Coordinates(-1, -1), c, true);
            pieces.add(m);
        }
        this.nbWall = nbWall;
        this.winCoord = winCoord;
    }

    public QuoridorColor getQuoridorColor() {
        return this.quoridorColor;
    }

    public Piece findPiece(Coordinates coord) {
        for (Piece p : pieces) {
            if (p.getCoordinates().equals(coord)) {
                return p;
            }
        }
        return null;
    }

    public Coordinates getWinCoord() {
        return winCoord;
    }

    /**
     * Toujours tester le isMoveOK avant pour éviter toute erreur
     *
     * @param initCoord
     * @param finalCoord
     * @return
     */
    public boolean move(Coordinates initCoord, Coordinates finalCoord) {
        Piece p = findPiece(initCoord);
        p.move(finalCoord);
        this.actualCoord = finalCoord;
        pieces.set(pieces.indexOf(p), p);
        return true;
    }

    public boolean isMoveOk(Coordinates initCoord, Coordinates finalCoord, boolean isJumping) {
        Piece p = null;
        p = findPiece(initCoord);
        Pawn pawn = (Pawn) p;
        return pawn.isMoveOk(finalCoord, isJumping);
    }

    public boolean isWallOk(Coordinates wallCoord) {
        Piece p = getWallUnused();
        if (p != null) { //Quand le joueur n'as plus de mur dispo
            Wall wall = (Wall) p;
            return wall.isMoveOk(wallCoord);
        } else {
            return false;
        }
    }

    public boolean putWall(Coordinates wallCoord) {
        Piece p = getWallUnused();
        if (p != null) { //Quand le joueur n'as plus de mur dispo
            return p.move(wallCoord);
        } else {
            return false;
        }
    }

    public List<PieceIHMs> getPiecesIHM() {
        List<PieceIHMs> result = new LinkedList<PieceIHMs>();
        for (Piece p : pieces) {
            if (!p.getCoordinates().equals(new Coordinates(-1, -1))) {
                PieceIHM pIhm = new PieceIHM(p);
                result.add(pIhm);
            }
        }
        return result;
    }

    public Coordinates getActualCoord() {
        return this.actualCoord;
    }

    public String toString() {
        String res = "";
        res += "\tQuoridorColor : " + this.name + "\n";
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) {
            return false;
        }

        Player j = (Player) obj;

        return j.getQuoridorColor().equals(this.getQuoridorColor());
    }

    /**
     * Renvoi un mur non utilisé. Si il ne reste plus de mur non utilisés la fonction renvoi null.
     *
     * @return
     */
    private Piece getWallUnused() {
        for (Piece p : pieces) {
            if (p.getCoordinates().equals(new Coordinates(-1, -1)) && p.getName().equals("Wall")) {
                return p;
            }
        }
        return null;
    }

    public int getWallRemaining() {
        int i = nbWall;
        for (Piece p : pieces) {
            if (!(p.getCoordinates().equals(new Coordinates(-1, -1)) || p.getName().equals("Pawn"))) {
                i--;
            }
        }
        return i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

