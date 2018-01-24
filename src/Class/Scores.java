package Class;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

import java.util.List;

public class Scores
{
    private static File scoreFolder;
    private static File scoreFile;

    public enum OS {
        Linux, Windows, Mac
    }

    /**
     * Permet d'instancier un score au format json et le stocker dans le fichier scores.json du dossier home du user
     * @param listPlayer la liste des joueurs du jeu courant
     * @param winnerPlayer le joueur victorieux du jeu courant
     */
    public static void createJson(List<Player> listPlayer, QuoridorColor winnerPlayer) throws IOException, ParseException {
        checkOrCreateScoreFile();
        String scoreFileString = scoreFile.toString();
        RandomAccessFile f = new RandomAccessFile(scoreFileString, "rw");
        BufferedWriter bw;
        JSONArray scoresObj;
        if(f.length()==0) {
            bw = new BufferedWriter(new FileWriter(scoreFileString, false));
            bw.write("[]");
            bw.close();
        }
        scoresObj = getNumberOfScores(scoreFileString);
        JSONArray objArray = new JSONArray();
        JSONObject objList = new JSONObject();
        JSONArray list = new JSONArray();
        for (Player j : listPlayer) {
            JSONObject p = new JSONObject();
            QuoridorColor c = j.getQuoridorColor();
            p.put("joueur", c.getJLabelPlayerName().getText());
            list.add(p);
            if (winnerPlayer.equals(j.getQuoridorColor())) {
                p.put("win", 1);
            } else {
                p.put("win", 0);
            }
        }
        objList.put("players", list);
        objArray.add(objList);
        bw = new BufferedWriter(new FileWriter(scoreFileString, false));
        if(scoresObj.size()==0) {
            bw.write(objArray.toJSONString());
        } else {
            scoresObj.add(objArray);
            bw.write(scoresObj.toJSONString());
            bw.write("\n");
        }
        bw.close();
    }


    /**
     * Selon l'OS :
     * Vérifie si le dossier courant existe, sinon le crée
     * Vérifie si le fichier scores.json existe, sinon le crée
     */
    private static void checkOrCreateScoreFile() throws IOException{
        scoreFolder = getHomePathByOsName();
        if (!scoreFolder.exists() || scoreFolder.isDirectory()) scoreFolder.mkdirs();
        scoreFile = new File (scoreFolder+"/scores.json");
        if (!scoreFile.exists() || !scoreFile.isFile()) {
            File f = scoreFile;
            f.createNewFile();
        }
    }


    /**
     * Selon un des trois OS principaux :
     * @return Home Path du dossier qui contiendra notre sauvegarde scores.json
     */
    private static File getHomePathByOsName() {
        String osName = System.getProperty("os.name");
        String firstStr[] = osName.split(" ", 2);
        String osStr = firstStr[0];
        OS os = OS.valueOf(osStr);
            switch (os) {
                case Linux:
                    scoreFolder = new File(System.getProperty("user.home") + "/.quoridor");
                    break;
                case Windows:
                    scoreFolder = new File(System.getenv("APPDATA") + "\\quoridor");
                    break;
                case Mac:
                    scoreFolder = new File(System.getProperty("user.home") + "/Library/Application/.quoridor");
                    break;
                default:
                    break;
            }
            return scoreFolder;
        }


    /**
     *
     * @param fileName  chemin et nom du fichier de scores
     * @return le nombre de parties sauvegardées
     * @throws IOException lève l'exception IO
     * @throws ParseException lève l'exception de parse
     */
    private static JSONArray getNumberOfScores(String fileName) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj= parser.parse(new FileReader(fileName));
        return (JSONArray) obj;
    }
}
