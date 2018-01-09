package Class.observable;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Class.*;

public class QuoridorGame extends Observable implements BoardGames {
    private Jeu jeu;

    public QuoridorGame(int nb) {
        super();
        this.jeu = new Jeu(nb);
        this.notifyObservers(jeu.getPiecesIHM());
    }

    @Override
    public boolean move(Coordonnees initCoord, Coordonnees finalCoord, boolean isWall) {
        boolean ret = false;
        ret = jeu.isMoveOk(initCoord, finalCoord, isWall);
        if(ret){
            if(isWall){
                ret = jeu.putWall(jeu.getIdCurrentPlayer(),finalCoord);
            } else {
                for(Joueur j : jeu.listPlayer()){
                    if(j.getActualCoord().equals(initCoord)){
                        ret = jeu.move(j, finalCoord);
                    }
                }
            }
        }
        this.notifyObservers(jeu.getPiecesIHM());
        return ret;
    }

    @Override
    public boolean isEnd() {
        return jeu.isWin();
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        return jeu.getIdCurrentPlayer().getCouleurs();
    }

    public Couleur getPieceColor(Coordonnees coord) {
        return jeu.getPieceColor(coord);
    }

    public List<Coordonnees> getMovePossible(Coordonnees c){
        return null;
    }

    public List<Joueur> listPlayer(){
        return jeu.listPlayer();
    }

    @Override
    public void	notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer o){
        super.addObserver(o);
        this.notifyObservers(jeu.getPiecesIHM());
    }

    public int getIdCurrentPlayer() {
        return jeu.getIntIdCurrentPlayer();
    }

    public List<PieceIHMs> getPiecesIHM() {
        return jeu.getPiecesIHM();
    }

    public Couleur getPlayerColor(int numPlayer){
        return jeu.getPlayerColor(numPlayer);
    }

    public int getPlayerWallRemaining(int numPlayer) {
        return jeu.getPlayerWallRemaining(numPlayer);
    }
}
