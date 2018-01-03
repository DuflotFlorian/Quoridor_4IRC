package Controller;

import Class.*;

import java.util.List;

public interface GameControllers {

    /**
     * @param initCoord
     * @param finalCoord
     * @return true si le déplacement s'est bien passé
     */
    public boolean move(Coordonnees initCoord, Coordonnees finalCoord);

    /**
     * @return message relatif aux déplacement, capture, etc.
     */
    public String getMessage();

    /**
     * @return true si fin de partie OK (echec et mat, pat, etc.)
     */
    public boolean isEnd();

    /**
     * @param initCoord
     * @return une info dont la vue se servira
     * pour empêcher tout déplacement sur le damier
     */
    public boolean isPlayerOK(Coordonnees initCoord);

    public List<Coordonnees> getMovePossible(Coordonnees c);
}
