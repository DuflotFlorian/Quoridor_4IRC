package Class.observable;

import java.util.List;
import java.util.Observable;
import Class.*;

public class QuoridorGame extends Observable implements BoardGames {
    private Jeu jeu;

    public QuoridorGame() {
        super();
        this.jeu = new Jeu(2);
        this.notifyObservers(jeu.getPiecesIHM());
    }

    public boolean move(Coordonnees initCoord, Coordonnees finalCoord) {
        return false;
    }

    @Override
    public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
        return false;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Couleur getColorCurrentPlayer() {
        return null;
    }

    public Couleur getPieceColor(Coordonnees coord) {
        return jeu.getPieceColor(coord);
    }

    public List<Coordonnees> getMovePossible(Coordonnees c){
        return null;
    }
}
