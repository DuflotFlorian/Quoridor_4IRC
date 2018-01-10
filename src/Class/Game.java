package Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private Player[] players;
    private int nbPlayers;
    private int idCurrentPlayer;
    private Board board;

    public Game(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        this.idCurrentPlayer = 0;
        this.board = new Board();

        switch (this.nbPlayers) {
            case 2:
                this.players = new Player[2];
                this.players[0] = new Player(10, QuoridorColor.BLUE, new Coordinates(0, 8), new Coordinates(16, 8));
                this.players[1] = new Player(10, QuoridorColor.RED, new Coordinates(16, 8), new Coordinates(0, 8));
                break;
            case 4:
                this.players = new Player[4];
                this.players[0] = new Player(5, QuoridorColor.BLUE, new Coordinates(0,8), new Coordinates(16,8));
                this.players[1] = new Player(5, QuoridorColor.RED, new Coordinates(16,8), new Coordinates(0,8));
                this.players[2] = new Player(5, QuoridorColor.YELLOW, new Coordinates(8,0), new Coordinates(8,16));
                this.players[3] = new Player(5, QuoridorColor.GREEN, new Coordinates(8,16), new Coordinates(8,0));
                break;

            default:
                break;
        }
    }

    public Player getCurrentPlayer() {
        return this.players[idCurrentPlayer];
    }

    public boolean isMoveOk(Coordinates initCoord, Coordinates finalCoord, boolean isWall) {
        if(initCoord.equals(finalCoord)){
            return false;
        }

        return true;
    }

    public boolean move(Player j, Coordinates finalCoord) {
        boolean ret = false;
        if (j.equals(getCurrentPlayer())) {
            if (!isPlayerHere(finalCoord)) {
                boolean isJumping = false;
                int diff;
                Coordinates currentCoord = j.getActualCoord();
                if (Math.abs(currentCoord.getX() - finalCoord.getX()) == 4 && currentCoord.getY() - finalCoord.getY() == 0) {
                    diff = currentCoord.getX() - finalCoord.getX();
                    isJumping = isPlayerHere(new Coordinates(currentCoord.getX() - (diff / 2), currentCoord.getY()));
                } else if (Math.abs(currentCoord.getY() - finalCoord.getY()) == 4 && currentCoord.getX() - finalCoord.getX() == 0) {
                    diff = currentCoord.getY() - finalCoord.getY();
                    isJumping = isPlayerHere(new Coordinates(currentCoord.getX(), currentCoord.getY() - (diff / 2)));
                }

                //Vérification présence mur pendant le deplacement
                if (canPionPass(j.getActualCoord(), finalCoord)) {
                    if (j.isMoveOk(j.getActualCoord(), finalCoord, isJumping)) {
                        j.move(j.getActualCoord(), finalCoord);
                        switchPlayer();
                        ret = true;
                        if (isWin()) {
                            System.out.println("Fin du jeu");
                        }
                    } else {
                        System.out.println("Ce deplacement n'est pas permis\n");
                    }
                } else {
                    System.out.println("Pawn ne passe pas");
                }
            } else {
                System.out.println("Il y a deja un joueur ici\n");
            }
        } else {
            System.out.println("Ce n'est pas le joueur courant\n");
        }

        return ret;
    }

    public boolean putWall(Player j, Coordinates wallCoord) {
        if (j.isWallOk(wallCoord)) {
            //Le placement du mur de base est valide
            //Vérification de non croisement et tentative poser un mur deja existant
            if (isWallBlockedByAnotherWall(wallCoord)) {
                return false;
            }
            // verif de l'intégralité du mur à l'intérieur du board
            if (!isWallInside(wallCoord)) {
                return false;
            }

            if (!addNewWallIfPaths(wallCoord)) {
                //Enfermement si on pose un mur à cette coordonnée
                return false;
            }
            j.putWall(wallCoord);
            switchPlayer();
            return true;
        }

        return false;
    }

    public void switchPlayer() {
        this.idCurrentPlayer += 1;
        this.idCurrentPlayer = this.idCurrentPlayer % this.nbPlayers;
    }

    public boolean isPlayerHere(Coordinates coord) {
        boolean result = false;
        for (int i = 0; i < this.nbPlayers; i++) {
            if (this.players[i].getActualCoord().equals(coord)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isWin() {
        boolean result = false;
        for (Player j : players) {
            if (j.getQuoridorColor().equals(QuoridorColor.BLUE) || j.getQuoridorColor().equals(QuoridorColor.RED)) {
                if (j.getActualCoord().getX() == j.getWinCoord().getX()) {
                    result = true;
                    break;
                }
            } else if (j.getActualCoord().getY() == j.getWinCoord().getY()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public List<PieceIHMs> getPiecesIHM() {
        List<PieceIHMs> result = new ArrayList<PieceIHMs>();
        for (Player j : players) {
            result.addAll(j.getPiecesIHM());
        }
        return result;
    }

    public QuoridorColor getPieceColor(Coordinates coord) {
        return players[idCurrentPlayer].getQuoridorColor();
    }

    public List<Player> listPlayer() {
        List<Player> result = new ArrayList<Player>();
        result.addAll(Arrays.asList(players));
        return result;
    }

    public int getIdCurrentPlayer() {
        return idCurrentPlayer;
    }

    /**
     * Permet de savoir si une piece de type mur à cette coord
     *
     * @param coord
     * @return
     */
    public boolean isWallHere(Coordinates coord) {
        Piece p;
        for (Player player : players) {
            p = player.findPiece(coord);
            if (p != null && !(p instanceof Pawn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renvoi true si il n'y a pas de mur entre l'ancienne et la nouvelle position du pion. Renvoi false dans le cas contraire
     *
     * @param initCoord
     * @param finalCoord
     * @return
     */
    private boolean canPionPass(Coordinates initCoord, Coordinates finalCoord) {

        //Si diffX != 0 diffY ==0 et inverssement
        //L'ecart est de 2 normalement et 4 en cas de saut de pion
        int diffX = finalCoord.getX() - initCoord.getX();
        int diffY = finalCoord.getY() - initCoord.getY();

        if (isPawnBlockedByWall(new Coordinates(initCoord.getX() + diffX / 2, initCoord.getY() + diffY / 2))) { //Deplacement normal, vérification de la non présence d'un mur entre ancienne et nouvelle position
            return false;
        }

        if (diffX == 4 || diffY == 4) { //Saut sur l'axe X ou Y
            if (isPawnBlockedByWall(new Coordinates(initCoord.getX() + diffX - (diffX / 4), initCoord.getY() + diffY - (diffY / 4)))) { //Test de la case juste avant la case destination
                return false;
            }
        }
        return true;
    }

    /**
     * Permet de savoir si la case est couverte par un mur.
     * Soit la case possède une pièce avec un mur.
     * Soit on test qu'il n'y ait pas un mur horizontal ou vertical sur la case à gauche ou en haut qui s'etend sur la case actuelle
     *
     * @return
     */
    private boolean isPawnBlockedByWall(Coordinates coord) {
        if (isWallHere(coord)) {
            return true;
        } else if (Wall.isWallHorizontal(coord)) {
            if (isWallHere(new Coordinates(coord.getX(), coord.getY() - 2))) {
                return true;
            }
        } else {
            if (isWallHere(new Coordinates(coord.getX() - 2, coord.getY()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de savoir si la case est couverte par un mur.
     * Soit la case posède un pièce avec un mur.
     * Soit on test qu'il n'y ait pas un mur horizontal ou vertical sur la case à gauche ou en haut qui s'etend sur la case actuelle
     *
     * @return
     */
    private boolean isWallBlockedByAnotherWall(Coordinates coord) {
        if (Wall.isWallHorizontal(coord)) {
            if (isWallHere(coord) || isWallHere(new Coordinates(coord.getX(), coord.getY() + 2)) || isWallHere(new Coordinates(coord.getX(), coord.getY() - 2)) || isWallHere(new Coordinates(coord.getX() - 1, coord.getY() + 1))) {
                return true;
            }
        } else {
            if (isWallHere(coord) || isWallHere(new Coordinates(coord.getX() + 2, coord.getY())) || isWallHere(new Coordinates(coord.getX() - 2, coord.getY())) || isWallHere(new Coordinates(coord.getX() + 1, coord.getY() - 1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de s'assurer qu'on essaye de positionner un mur avec ses 3 cases dans le board sans en sortir
     *
     * @param coord la coordonnées de placement du mur
     * @return boolean
     */
    private boolean isWallInside(Coordinates coord) {
        if (coord.getX() % 2 != 0 && coord.getY() < 15) { // clic horizontal
            return true;
        }
        if (coord.getX() % 2 == 0 && coord.getX() < 15) {
            return true;
        }
        return false;
    }

    public QuoridorColor getPlayerColor(int numPlayer) {
        return players[numPlayer].getQuoridorColor();
    }

    private boolean isThereAPath(Coordinates init, Coordinates dest) {
        return this.board.isThereAPath(init, dest);
    }

    public int getPlayerWallRemaining(int numPlayer) {
        return players[numPlayer].getWallRemaining();
    }

    /**
     * Ajoute le nouveau mur à la grille Wall et renvoi true si un chemin est possible après pose de ce mur
     *
     * @param wallCoord
     * @return
     */
    private boolean addNewWallIfPaths(Coordinates wallCoord) {
        Wall tmpWall = new Wall(wallCoord, getCurrentPlayer().getQuoridorColor(), Wall.isWallHorizontal(wallCoord));
        board.addWall(tmpWall);
        //Test de la présence d'un chemin pour chaque Player
        boolean retJoueur = false;
        int lineToCheck = -1;
        for (Player player : players) {
            if (player.getQuoridorColor().equals(QuoridorColor.BLUE)) {
                lineToCheck = 16;
            } else if (player.getQuoridorColor().equals(QuoridorColor.RED)) {
                lineToCheck = 0;
            }
            for (int i = 0; i < 17; i = i + 2) {
                if (isThereAPath(player.getActualCoord(), new Coordinates(lineToCheck, i))) {
                    retJoueur = true;
                    break;
                }
            }
            if (!retJoueur) { //Si un des players n'a pas de chemin on stoppe
                board.removeWall(tmpWall);
                return false;
            } else {
                retJoueur = false;
            }

        }
        return true;
    }
}
