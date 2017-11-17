package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.util.List;

public class GameController extends AbstractGameController {

    public GameController(QuoridorGame game){
        super(game);
    }

    @Override
    public boolean move(Coordonnees initCoord, Coordonnees finalCoord) {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public List<Coordonnees> getMovePossible(Coordonnees c) {
        return null;
    }

    @Override
    public boolean isPlayerOK(Coordonnees initCoord) {
        return false;
    }
}
