package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.List;


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

    public String getPlayerName(int numPlayer) {
        return game.getPlayerName(numPlayer);
    }

    public void setPlayerName(int numPlayer, String name) {
        game.setPlayerName(numPlayer,name);
    }

    public int getPlayerWallRemaining(int numPlayer) {
        return game.getPlayerWallRemaining(numPlayer);
    }

    @Override
    protected void endMove(Coordinates initCoord, Coordinates finalCoord) { }

    public static boolean isScores() {
        return Scores.isScores();
    }

    public static LinkedHashMap<String, Integer> getTopRank() {
        if(!Scores.isScores()) return null;
        return Scores.getTopRank();
    }

    public static LinkedHashMap<String, List<Integer>> getTopRankByParticipation() {
        if(!Scores.isScores()) return null;
        return Scores.getTopRankByParticipation();
    }

}