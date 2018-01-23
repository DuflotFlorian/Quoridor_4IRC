package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.util.List;

public abstract class AbstractGameController implements GameControllers {
    protected QuoridorGame game;

    public AbstractGameController(QuoridorGame game) {
        super();
        this.game = game;
    }

    final public boolean move(Coordinates initCoord, Coordinates finalCoord) {
        boolean ret = false;
        // si c'est bien au tour du joueur courant de jouer
        if (this.isPlayerOK(initCoord)) {
            // Déplacement métier
            ret = this.moveModel(initCoord, finalCoord, false);
            // Actions différentes selon les types de controleur
            if (ret) {
                this.endMove(initCoord, finalCoord);
            }
        }
        return ret;
    }

    public boolean putWall(Coordinates wallCoord) {
        boolean ret = false;
        ret = this.moveModel(null, wallCoord, true);
        if (ret) {
            this.endMove(null, wallCoord);
        }
        return ret;
    }

    public abstract boolean isPlayerOK(Coordinates initCoord);

    // Déplacement métier
    protected boolean moveModel(Coordinates initCoord, Coordinates finalCoord, boolean isWall) {
        return game.move(initCoord, finalCoord, isWall);
    }

    protected abstract void endMove(Coordinates initCoord, Coordinates finalCoord);

    public boolean isEnd() {
        return this.game.isEnd();
    }

    public String getMessage() {
        String ret = null;
        ret = this.game.getMessage();
        return ret;
    }

    public String toString() {
        return this.game.toString();
    }

    protected QuoridorColor getColorCurrentPlayer() {
        return this.game.getColorCurrentPlayer();
    }

    protected QuoridorColor getPieceColor(Coordinates initCoord) {
        return this.game.getPieceColor(initCoord);
    }

    public List<Coordinates> getMovePossible(Coordinates c) {
        return this.game.getMovePossible(c);
    }

    public int getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public void notifyObserver() {
        game.notifyObservers(game.getPiecesIHM());
    }

    public void deleteObservers() {
        game.deleteObservers();
    }
}
