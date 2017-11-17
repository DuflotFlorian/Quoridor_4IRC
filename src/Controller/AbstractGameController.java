package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.util.List;

public abstract class AbstractGameController implements GameControllers{
    protected QuoridorGame game;

    public AbstractGameController(QuoridorGame game){
        super();
        this.game = game;
    }

    final public boolean move(Coordonnees initCoord, Coordonnees finalCoord) {
        boolean ret = false;
        String promotionType = null;

        // si c'est bien au tour du joueur courant de jouer
        if (this.isPlayerOK(initCoord)) {

            // Déplacement métier
            ret = this.moveModel(initCoord, finalCoord);

            // Actions différentes selon les types de controleur
            if (ret) {
                this.endMove(initCoord, finalCoord, promotionType);
            }

        }else {
            this.moveModel(initCoord, initCoord);
        }
        return ret;
    }

    public abstract boolean isPlayerOK(Coordonnees initCoord) ;

    // Déplacement métier
    protected  boolean moveModel(Coordonnees initCoord, Coordonnees finalCoord)  {
        return game.move(initCoord, finalCoord);
    }

    protected abstract void endMove(Coordonnees initCoord, Coordonnees finalCoord, String promotionType) ;


    public boolean isEnd(){
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


    protected Couleur getColorCurrentPlayer(){
        return this.game.getColorCurrentPlayer();
    }

    protected Couleur getPieceColor(Coordonnees initCoord){
        return this.game.getPieceColor(initCoord);
    }

    public List<Coordonnees> getMovePossible(Coordonnees c){
        return this.game.getMovePossible(c);
    }
}
