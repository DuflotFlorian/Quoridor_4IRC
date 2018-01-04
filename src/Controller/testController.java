package Controller;
import Class.*;

public class testController {

    public static void main(String[] args){
        Jeu monJeu = new Jeu(2);
        monJeu.isThereAPath();

    }
    public static void test_movement(){

        Jeu monJeu = new Jeu(2);
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(2,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(16,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(4,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(14,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(6,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(12,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(8,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(10,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(10,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(8,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(12,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(6,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(14,8));
        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(4,6));

        monJeu.move(monJeu.getIdCurrentPlayer(), new Coordonnees(16,8));
        System.out.println(monJeu.toString());
    }

}
