package Class;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import vue.QuoridorGUI;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Scores {
    private static File scoreFolder;
    private static File scoreFile;


    public enum OS {
        Linux, Windows, Mac
    }


    /**
     * Permet de créer l'objet partie au format json et le stock dans le fichier scores.json
     *
     * @param listPlayer la liste des joueurs du jeu courant
     * @param winnerPlayer le joueur victorieux du jeu courant
     */
    public static void createJson(List<Player> listPlayer, QuoridorColor winnerPlayer){
        checkOrCreateScoreFile();
        JSONObject obj = new JSONObject();
        JSONObject objList = new JSONObject();
        JSONArray list = new JSONArray();

        for (Player j : listPlayer) {
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


    /**
     * Selon l'OS :
     * Vérifie si le dossier courant existe, sinon le crée
     * Vérifie si le fichier scores.json existe, sinon le crée
     */
    private static void checkOrCreateScoreFile(){
        String osname = System.getProperty("os.name");
        String firstStr[] = osname.split(" ", 2);
        String osStr = firstStr[0];
        OS os = OS.valueOf(osStr);
        switch (os) {
            case Linux:
                scoreFolder=new File(System.getProperty("user.home")+"/.quoridor");
                break;
            case Windows:
                scoreFolder=new File(System.getenv("APPDATA")+"\\.quoridor");
                break;
            case Mac:
                scoreFolder=new File(System.getProperty("user.home")+"/Library/Application/.quoridor");
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
