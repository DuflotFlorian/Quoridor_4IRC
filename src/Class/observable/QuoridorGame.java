package Class.observable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Class.*;

public class QuoridorGame extends Observable implements BoardGames {
    private Game game;

    public QuoridorGame(int nb, List<String> name) {
        super();
        this.game = new Game(nb, name);
        this.notifyObservers(game.getPiecesIHM());
    }

    @Override
    public boolean move(Coordinates initCoord, Coordinates finalCoord, boolean isWall) {
        boolean ret = false;
        ret = game.isMoveOk(initCoord, finalCoord, isWall);
        if (ret) {
            if (isWall) {
                ret = game.putWall(game.getCurrentPlayer(), finalCoord);
            } else {
                for (Player j : game.listPlayer()) {
                    if (j.getActualCoord().equals(initCoord)) {
                        ret = game.move(j, finalCoord);
                    }
                }
            }
        }
        this.notifyObservers(game.getPiecesIHM());

        return ret;
    }

    @Override
    public boolean isEnd() {
        return game.isWin();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public QuoridorColor getColorCurrentPlayer() {
        return game.getCurrentPlayer().getQuoridorColor();
    }

    public QuoridorColor getPieceColor(Coordinates coord) {
        return game.getPieceColor(coord);
    }

    public List<Coordinates> getMovePossible(Coordinates c) {
        return null;
    }

    public List<Player> listPlayer() {
        return game.listPlayer();
    }

//    public String getNamePlayer() {return game.}
    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        this.notifyObservers(game.getPiecesIHM());
    }

    @Override
    public void deleteObservers() {
        super.deleteObservers();
    }

    public int getCurrentPlayer() {
        return game.getIdCurrentPlayer();
    }

    public List<PieceIHMs> getPiecesIHM() {
        return game.getPiecesIHM();
    }

    public QuoridorColor getPlayerColor(int numPlayer) {
        return game.getPlayerColor(numPlayer);
    }

    public String getPlayerName(int numPlayer) {
        return game.getPlayerName(numPlayer);
    }

    public void setPlayerName(int numPlayer, String name) {
        game.setPlayerName(numPlayer, name);
    }

    public int getPlayerWallRemaining(int numPlayer) {
        return game.getPlayerWallRemaining(numPlayer);
    }

    public List<Coordinates> possibleMove(Coordinates initCoord) { return game.possibleMove(initCoord); }
}
