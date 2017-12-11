package Controller;

import Class.Jeu;
import Class.TypeMove;
import Class.Couleur;
import Class.Coordonnees;
import java.util.List;

public class QuoridorController {

    private Jeu jeu;

    public QuoridorController (Jeu jeu, int nbJoueurs) {
        this.jeu = new Jeu(nbJoueurs);
    }

    public boolean isPlayerMoveOK(TypeMove typeMove) {
        return jeu.isMoveOK(typeMove);
    }

    public boolean isWin() {
        return jeu.isWin();
    }

    public Couleur getCurrentPlayer() {
        return jeu.getCurrentPlayer();
    }

    public int getMurDisponnibles(Couleur c) {
        return jeu.getMurDisponnibles(c);
    }

    public List<Coordonnees> getPionsIhm() {
        return jeu.getPionsIhm();
    }

    public List<Coordonnees> getMursIhm() {
        return jeu.getMursIhm();
    }

    public void movePion(Couleur coul,Coordonnees coord) {

        //TODO kevin
    }

    public void setMur(Couleur coul,Coordonnees coord) {

        //TODO kevin
    }



}
