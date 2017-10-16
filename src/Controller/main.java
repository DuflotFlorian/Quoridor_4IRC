package Controller;
import Class.*;

public class main {

    public static void main(String[] args){
        Jeu monJeu = new Jeu(2);

        monJeu.deplacerPion(new Coordonnees(0,0));
        monJeu.deplacerPion(new Coordonnees(10,10));
        System.out.println(monJeu.toString());
    }

}
