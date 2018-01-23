package Controller;

import Class.observable.QuoridorGame;
import Class.*;
import vue.QuoridorGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class GameController extends AbstractGameController {

    boolean isClient;
    static ServerSocket ss;
    static Socket s;
    static PrintWriter out;
    static BufferedReader in;
    String messageToSend = "";

    public GameController(QuoridorGame game, boolean isClient) {
        super(game);
        this.isClient = isClient;
        if(!isClient){
            Serveur();
        }else{
            Client();
        }
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
    protected void endMove(Coordinates initCoord, Coordinates finalCoord) {
        try{

            if(initCoord.equals(null)){
                messageToSend = "wall/" + finalCoord.toString();
            } else {
                messageToSend = "move/" + initCoord.toString() + "/" + finalCoord.toString();
            }
        } catch (Exception e){
            System.out.println("Send : " + e.getMessage());
        }
    }

    private void traitementMessageReception(String message){
        String[] splitMessage = message.split("/");
        if(splitMessage[0].equals("wall")){
            String[] coordSplit = splitMessage[1].split("&");
            Coordinates c = new Coordinates(Integer.parseInt(coordSplit[0]), Integer.parseInt(coordSplit[1]));
            this.putWall(c);
            this.notifyObserver();
        }
        if(splitMessage[0].equals("move")){
            String[] coordInitSplit = splitMessage[1].split("&");
            String[] coordFinalSplit = splitMessage[2].split("&");
            Coordinates cInit = new Coordinates(Integer.parseInt(coordInitSplit[0]), Integer.parseInt(coordInitSplit[1]));
            Coordinates cFinal = new Coordinates(Integer.parseInt(coordFinalSplit[0]), Integer.parseInt(coordFinalSplit[1]));
            this.move(cInit, cFinal);
            this.notifyObserver();
        }
    }

    private void sendMesage(){
        if(!messageToSend.equals("")){
            System.out.println(messageToSend);
            out.println(messageToSend);
        }
    }

    private void receptionMessage() {
        String msg;
        try {
            msg = in.readLine();
            //tant que le client est connecté
            if (!isEnd()) {
                traitementMessageReception(msg);
                msg = in.readLine();
            }
            //sortir de la boucle si le client a déconecté
            System.out.println("Client déconecté");
            //fermer le flux et la session socket
            out.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void Serveur(){
        try
        {
            ss = new ServerSocket(1201);
            s = ss.accept();
            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader (new InputStreamReader (s.getInputStream()));

            Thread envoi= new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!isEnd()){
                        sendMesage();
                    }
                }
            });
            envoi.start();

            Thread recevoir= new Thread(new Runnable() {
                @Override
                public void run() {
                    receptionMessage();
                }
            });
            recevoir.start();
        } catch(Exception e)
        {
            System.out.println("Serveur : " + e.getMessage());
        }
    }

    private void Client(){

        try {
         /*
         * les informations du serveur ( port et adresse IP ou nom d'hote
         * 127.0.0.1 est l'adresse local de la machine
         */
            s = new Socket("127.0.0.1",1201);

            //flux pour envoyer
            out = new PrintWriter(s.getOutputStream());
            //flux pour recevoir
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            Thread envoyer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!isEnd()){
                        sendMesage();
                    }
                }
            });
            envoyer.start();

            Thread recevoir = new Thread(new Runnable() {
                @Override
                public void run() {
                    receptionMessage();
                }
            });
            recevoir.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
