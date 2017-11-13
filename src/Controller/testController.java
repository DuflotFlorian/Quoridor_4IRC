package Controller;
import Class.*;

public class testController {

    public static void main(String[] args){
        GrillePion test = new GrillePion();
        Pion p = new Pion(new Coordonnees(0,0), Couleur.BLANC);
        Pion p1 = new Pion(new Coordonnees(8,0), Couleur.NOIR);
        test.addElement(p);
        test.addElement(p1);
        System.out.println(test.toString());

        GrilleMur mur = new GrilleMur();
        Mur m = new Mur(new Coordonnees(0,0), Couleur.BLANC, true);
        Mur m1 = new Mur(new Coordonnees(8,0), Couleur.NOIR, false);
        mur.addElement(m);
        mur.addElement(m1);
        System.out.println(mur);
    }

}
