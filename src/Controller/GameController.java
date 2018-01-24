package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameController extends AbstractGameController {

    public GameController(QuoridorGame game) {
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
    public List<Coordinates> getMovePossible(Coordinates c) {
        return game.getMovePossible(c);
    }


    @Override
    public boolean isPlayerOK(Coordinates initCoord) {
        return game.getColorCurrentPlayer().equals(game.getPieceColor(initCoord));
    }

    public List<Player> listPlayer() {
        return game.listPlayer();
    }

    public QuoridorColor getPlayerColor(int numPlayer) {
        return game.getPlayerColor(numPlayer);
    }

    public int getPlayerWallRemaining(int numPlayer) {
        return game.getPlayerWallRemaining(numPlayer);
    }

    @Override
    protected void endMove(Coordinates initCoord, Coordinates finalCoord) { }

    public static LinkedHashMap<String, Integer> getTopRank() {
        return Scores.getTopRank();
    }

}