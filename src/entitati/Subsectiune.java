package entitati;

import java.util.List;

public class Subsectiune extends Sectiune {
    // date membre/atribute private
    private String subgen;
    private List<Carte> cartiSubgen;

    // constructori
    public Subsectiune(String subgen, List<Carte> cartiSubgen) {
        this.subgen = subgen;
        this.cartiSubgen = cartiSubgen;
    }
    public Subsectiune(String gen, List<Carte> carti, String subgen, List<Carte> cartiSubgen) {
        super(gen, carti);
        this.subgen = subgen;
        this.cartiSubgen = cartiSubgen;
    }

    // setteri si getteri pentru fiecare atribut
    public String getSubgen() {
        return subgen;
    }
    public void setSubgen(String subgen) {
        this.subgen = subgen;
    }

    // metode
    public void afisare() {
        super.afisare();
        System.out.println("Subsectiune: " + this.subgen);
        // iteram prin lista de carti
        System.out.println("Carti din aceasta subsectiune: ");
        for(Carte carte: this.cartiSubgen) {
            carte.afisare();
            System.out.println("\n");
        }
    }
}
