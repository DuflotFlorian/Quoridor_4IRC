package Class;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.misc.IOUtils;
import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class Scores
        //extends JSONException
{
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
    public static void createJson(List<Player> listPlayer, QuoridorColor winnerPlayer) throws IOException {
        checkOrCreateScoreFile();
        JSONObject obj = new JSONObject();
        JSONObject objList = new JSONObject();
        JSONArray list = new JSONArray();

        String scoreFileString = scoreFile.toString();
        RandomAccessFile f = new RandomAccessFile(scoreFileString, "rw");
        BufferedWriter bw = null;
        FileWriter fw = null;
        if(f.length() != 0) {
            deleteLastAccoladenJson(scoreFileString);
            try {
                fw = new FileWriter(scoreFileString, true);
                bw = new BufferedWriter(fw);
                bw.write(",\n");
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
                obj.put("game", objList);
                String objStr = obj.toJSONString();
                String replacedString = objStr.replace("{\"game\"", "\"game\"");
                bw.write(replacedString);
                bw.write("\n");

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
        } else {
            System.out.println("premier score !");
            try {
                fw = new FileWriter(scoreFileString, true);
                bw = new BufferedWriter(fw);
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
                obj.put("game", objList);
                bw.write(obj.toJSONString());
                bw.write("\n");
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
    }


    /**
     * Selon l'OS :
     * Vérifie si le dossier courant existe, sinon le crée
     * Vérifie si le fichier scores.json existe, sinon le crée
     */
    private static void checkOrCreateScoreFile(){
        scoreFolder = getFilePathByOsName();
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
            String scoreFileString = scoreFile.toString();
            BufferedWriter bw = null;
            FileWriter fw = null;
            try {
                fw = new FileWriter(scoreFileString,true);
                bw = new BufferedWriter(fw);
//                bw.write("{\n}\n");
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
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    }

    /**
     * Selon un des trois OS principaux :
     * @return File Path du dossier qui contiendra notre sauvegarde scores.json
     */
    private static File getFilePathByOsName() {
        String osname = System.getProperty("os.name");
        String firstStr[] = osname.split(" ", 2);
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



    private static boolean isScoreFileIsJsonValid(String scorePathFile){
        JSONParser parser = new JSONParser();
        boolean isValid = false;
        try {

            Object obj = parser.parse(new FileReader(scorePathFile));
            JSONObject jsonObject = (JSONObject) obj;
//            String name = (String) jsonObject.get("name");
//            System.out.println(name);
//
//            long age = (Long) jsonObject.get("age");
//            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("game");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isValid;
    }


    private static void deleteLastAccoladenJson(String fileName) throws IOException {
        try
        {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            while((line = reader.readLine()) != null)
            {
                oldtext += line + "\r\n";
            }
            reader.close();
            // replace a word in a file
            //String newtext = oldtext.replaceAll("drink", "Love");

            //To replace a line in a file
            String newtext = oldtext.replaceAll("}]}}", "}]}");

            FileWriter writer = new FileWriter(fileName);
            writer.write(newtext);writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
