package Controller;

import Class.observable.QuoridorGame;
import Class.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class GameController extends AbstractGameController {

    boolean isClient;
    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    public GameController(QuoridorGame game, boolean isClient) {
        super(game);
        this.isClient = isClient;
        if(!isClient){
            try
            {
                ss = new ServerSocket(1201);
                s = ss.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            } catch(Exception e)
            {
                System.out.println("Serveur : " + e.getMessage());
            }
        }else{
            try{
                s = new Socket("127.0.0.1", 1201);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
            }catch (Exception e){
                System.out.println("Client : " + e.getMessage());
            }
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
            String messToSend = "";
            if(initCoord.equals(null)){
                messToSend = "wall/" + finalCoord.toString();
            } else {
                messToSend = "move/" + initCoord.toString() + "/" + finalCoord.toString();
            }
            byte[] data=messToSend.getBytes("UTF-8");
            dout.writeInt(data.length);
            dout.write(data);
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
        }
        if(splitMessage[0].equals("move")){
            String[] coordInitSplit = splitMessage[1].split("&");
            String[] coordFinalSplit = splitMessage[2].split("&");
            Coordinates cInit = new Coordinates(Integer.parseInt(coordInitSplit[0]), Integer.parseInt(coordInitSplit[1]));
            Coordinates cFinal = new Coordinates(Integer.parseInt(coordFinalSplit[0]), Integer.parseInt(coordFinalSplit[1]));
            this.move(cInit, cFinal);
        }
    }

    public void listen() {
        new Runnable(){
            @Override
            public void run() {
                try{
                    String message = "";
                    while(!isEnd()){
                        int length=din.readInt();
                        byte[] data=new byte[length];
                        din.readFully(data);
                        message=new String(data,"UTF-8");
                        traitementMessageReception(message);
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        };
    }
}
