package Controller;
import Class.*;

public class main {

    public static void main(String[] args){
        Jeu monJeu = new Jeu(2);
        System.out.println(monJeu.toString());

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(2,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(16,6));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(4,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(16,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(6,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(14,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(8,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(12,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(10,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(10,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(12,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(8,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(14,8));
        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(6,4));

        monJeu.moveIfAllow(monJeu.getCurrentPlayer(),new Coordonnees(16,8));
        System.out.println(monJeu.toString());
        System.out.println(monJeu.isWin());
    }

}
