package Class;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

public class Scores
{
    private static File scoreFolder;
    private static File scoreFile;
    private static JSONArray scoresArray;
    private static HashMap<String, Integer> scoresMap;

    static {
        try {
            getHomePathByOsName(); // initialise scoreFolder
            checkOrCreateScoreFile(); // initialise scoreFile
            if(isScores())getScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum OS {
        Linux, Windows, Mac
    }

    /**
     * Permet d'instancier un score au format json et le stocker dans le fichier scores.json du dossier home du user
     * @param listPlayer la liste des joueurs du jeu courant
     * @param winnerPlayer le joueur victorieux du jeu courant
     */
    public static void createJson(List<Player> listPlayer, QuoridorColor winnerPlayer) throws IOException, ParseException {

        String scoreFileString = scoreFile.toString();
        RandomAccessFile f = new RandomAccessFile(scoreFileString, "rw");
        BufferedWriter bw;
        JSONArray scoresObj;
        if(f.length()==0) {
            bw = new BufferedWriter(new FileWriter(scoreFileString, false));
            bw.write("[]");
            bw.close();
        }
        scoresObj = getScores();
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
        if(scoresObj.size()==0) { // la premiere partie sera à la fin du tableau json
            bw.write(objArray.toJSONString());
        } else {
            objArray.addAll(scoresObj);
            bw.write(objArray.toJSONString());
            bw.write("\n");
        }
        bw.close();
    }


    public static boolean isScores(){
        String scoreFileString = scoreFile.toString();
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(scoreFileString, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if(f.length()==0) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
     * @return les parties sauvegardées au format json tableau
     * @throws IOException lève l'exception IO
     * @throws ParseException lève l'exception de parse
     */
    private static JSONArray getScores() {
        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
             obj = parser.parse(new FileReader(scoreFile));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scoresArray = (JSONArray) obj;
        return (JSONArray) obj;
    }

    public static LinkedHashMap<String, Integer> getTopRank(){
        getScores();
        HashMap<String, Integer> playersVictories = new HashMap();
        for (Object anArray : scoresArray) {
            JSONObject jsonobject = (JSONObject) anArray;
            JSONArray a = (JSONArray) jsonobject.get("players");

            for (Object anA : a) {
                JSONObject ob = (JSONObject) anA;
                String joueur = ob.get("joueur").toString();
                if (!playersVictories.containsKey(joueur)) {
                    playersVictories.put(joueur, 0);
                }
                Object s = ob.get("win");
                int value = playersVictories.get(joueur);
                value = value + Integer.parseInt(s.toString());
                playersVictories.put(joueur, value);

            }
        }
        scoresMap = sortMap(playersVictories);
        return sortMap(playersVictories);
    }

    public static LinkedHashMap<String, List<Double>> getTopRankByParticipation() {
        HashMap<String, Integer> arrayVictories = scoresMap;
        HashMap<String, Double> scoreMapUnsorted = new HashMap();
        Iterator it = arrayVictories.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String player = pair.getKey().toString();
            int playerGames = getNbGames(player);
            double playerVictories = scoresMap.get(player);
            double ratio = (playerVictories * 100) / playerGames;
            ratio = Math.floor(ratio * 100) / 100;
            scoreMapUnsorted.put(player, ratio);
        }

        HashMap<String, Double> scoreMapSorted = sortMapDouble(scoreMapUnsorted);
        LinkedHashMap<String, List<Double>> scoreMapSortedList  = new LinkedHashMap<>();
        Iterator it2 = scoreMapSorted.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry) it2.next();
            String player = pair.getKey().toString();

            double playerVictories = scoreMapSorted.get(player);
            int playerGames = getNbGames(player);
            List<Double> l = new ArrayList<>();
            l.add(playerVictories);
            l.add((double) playerGames);
            scoreMapSortedList.put(player,l);
        }
        return scoreMapSortedList;
    }



    private static LinkedHashMap<String,Integer> sortMap(HashMap playersVictories){
        // tri du Hashmap via un TreeSet
        SortedSet<Map.Entry<String,Integer>> sortedEntries = new TreeSet<Map.Entry<String,Integer>>(
                new Comparator<Map.Entry<String,Integer>>() {
                    @Override public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? -res : -1; // conserve les valeurs de victoires identiques
                    }
                }
        );
        sortedEntries.addAll(playersVictories.entrySet());

        // conversion TreeSet en LinkedHashMap (qui conserve l'ordre d'insertion)
        LinkedHashMap<String, Integer> ret = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> score : sortedEntries) {
            ret.put(score.getKey(), score.getValue());
        }
        return ret;
    }


    private static LinkedHashMap<String,Double> sortMapDouble(HashMap playersVictories){
        // tri du Hashmap via un TreeSet
        SortedSet<Map.Entry<String,Double>> sortedEntries = new TreeSet<Map.Entry<String,Double>>(
                new Comparator<Map.Entry<String,Double>>() {
                    @Override public int compare(Map.Entry<String,Double> e1, Map.Entry<String,Double> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? -res : -1; // conserve les valeurs de victoires identiques
                    }
                }
        );
        sortedEntries.addAll(playersVictories.entrySet());

        // conversion TreeSet en LinkedHashMap (qui conserve l'ordre d'insertion)
        LinkedHashMap<String, Double> ret = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> score : sortedEntries) {
            ret.put(score.getKey(), score.getValue());
        }
        return ret;
    }

    private static int getNbGames(String player){
        int playerGames = 0;
        for (Object anArray : scoresArray) {
            JSONObject jsonobject = (JSONObject) anArray;
            JSONArray a = (JSONArray) jsonobject.get("players");
            for (Object anA : a) {
                JSONObject ob = (JSONObject) anA;
                String joueur = ob.get("joueur").toString();
                if (player.equals(joueur)) {
                    playerGames++;
                }
            }
        }
        return playerGames;
    }

}
