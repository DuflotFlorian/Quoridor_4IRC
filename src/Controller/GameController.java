package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.util.List;

public class GameController extends AbstractGameController {

    public GameController(QuoridorGame game){
        super(game);
    }

    @Override
    public String getMessage() {
        return game.getMessage();
    }

    @Override
    public boolean isEnd() {
        return game.isEnd();
    }

    @Override
    public List<Coordonnees> getMovePossible(Coordonnees c) {
        return game.getMovePossible(c);
    }

    @Override
    public boolean isPlayerOK(Coordonnees initCoord) {
        return game.getColorCurrentPlayer().equals(game.getPieceColor(initCoord));
    }

    public List<Joueur> listPlayer(){
        return game.listPlayer();
    }

    public Couleur getPlayerColor(int numPlayer){
        return game.getPlayerColor(numPlayer);
    }

    public int getPlayerWallRemaining(int numPlayer){
        return game.getPlayerWallRemaining(numPlayer);
    }

    @Override
    protected void endMove(Coordonnees initCoord, Coordonnees finalCoord) {

    }
}
