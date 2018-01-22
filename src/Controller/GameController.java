package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.io.*;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.awt.OSInfo;


public class GameController extends AbstractGameController {
    private File scoreFolder;
    private File scoreFile;


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

    public void createJson(QuoridorColor winnerPlayer){
        checkAndCreateScoreFile();
        JSONObject obj = new JSONObject();
        JSONObject objList = new JSONObject();
        JSONArray list = new JSONArray();

        for (Player j : game.listPlayer()) {
            JSONObject p = new JSONObject();
            QuoridorColor c = j.getQuoridorColor();
            p.put("joueur",c.getJLabelPlayerName().getText());
            list.add(p);
            if (winnerPlayer.equals(j.getQuoridorColor())){
                p.put("win",1);
            } else {
                p.put("win",0);
            }
        }
        objList.put("players", list);
        obj.put("game", objList);





        String scoreFileString = scoreFile.toString();
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(scoreFileString,true);
            bw = new BufferedWriter(fw);
            bw.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void checkAndCreateScoreFile(){
        switch (OSInfo.OSType.LINUX) {
            case LINUX:
                scoreFolder=new File(System.getProperty("user.home")+"/.quoridor");
                break;
            case WINDOWS:
                scoreFolder=new File(System.getenv("APPDATA")+"/.quoridor");
                break;
            default:
                break;
        }

        if (!scoreFolder.exists() || scoreFolder.isDirectory()) {
            try{
                if(scoreFolder.mkdirs()) {
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        if (!scoreFolder.exists() || scoreFolder.isDirectory()) {
            try{
                if(scoreFolder.mkdirs()) {
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }

        scoreFile = new File (scoreFolder+"/scores.json");
        if (!scoreFile.exists() || !scoreFile.isFile()) {
            File f = scoreFile;
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}