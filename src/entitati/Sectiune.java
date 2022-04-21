package entitati;

import java.util.ArrayList;
import java.util.List;

public class Sectiune {
    // date membre/atribute protected
    protected String gen;
    protected List<Carte> carti;

    // constructor default
    public Sectiune() {
        this.gen = "neclasificat";
        this.carti = new ArrayList<Carte>();
    }

    // constructor cu toti parametrii
    public Sectiune(String gen, List<Carte> carti) {
        this.gen = gen;
        this.carti = carti;
    }

    // setteri si getteri pentru fiecare atribut
    public String getGen() {
        return gen;
    }
    public void setGen(String gen) {
        this.gen = gen;
    }

    public List<Carte> getCarti() {
        return carti;
    }
    public void setCarti(List<Carte> carti) {
        this.carti = carti;
    }

    // metode
    public void afisare() {
        System.out.println("entitati.Sectiune: " + this.gen);
        // iteram prin lista de carti
        System.out.println("Carti din aceasta sectiune: ");
        for(Carte carte: this.carti) {
            carte.afisare();
            System.out.println("\n");
        }
    }
}
