package Class.observable;

import java.util.Observable;
import Class.*;

public class QuoridorGame extends Observable implements BoardGames {
    private Jeu jeu;

    public QuoridorGame() {
        super();
        this.jeu = new Jeu(2);
        this.notifyObservers(jeu.getPiecesIHM());
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

    @Override
    public Couleur getPieceColor(int x, int y) {
        return null;
    }
}
