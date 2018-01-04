package Class;

public class MurGrille {
    Coordonnees coordonnees;

    public MurGrille(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Coordonnees getCoordonnees() {
        return this.coordonnees;
    }

    @Override
    public String toString() {
        return "MurGrille{" +
                "coordonnees=" + coordonnees +
                '}';
    }
}
