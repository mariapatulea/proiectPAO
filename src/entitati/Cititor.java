package entitati;

import java.util.ArrayList;
import java.util.List;

public class Cititor {
    // date membre/atribute private
    private String prenume;
    private String nume;
    private int idCititor;
    private List<Carte> cartiImprumutate;  // colectie ordonata

    // constructor cu toti parametrii
    public Cititor(String prenume, String nume, int idCititor, List<Carte> cartiImprumutate) {
        this.prenume = prenume;
        this.nume = nume;
        this.idCititor = idCititor;
        if(cartiImprumutate == null) {
            this.cartiImprumutate = new ArrayList<>();
        } else {
            this.cartiImprumutate = cartiImprumutate;
        }
    }

    // setteri si getteri pentru fiecare atribut
    public String getPrenume() {
        return prenume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getIdCititor() {
        return idCititor;
    }
    public void setIdCititor(int idCititor) {
        this.idCititor = idCititor;
    }

    public List<Carte> getCartiImprumutate() {
        return cartiImprumutate;
    }
    public void setCartiImprumutate(List<Carte> cartiImprumutate) {
        this.cartiImprumutate = cartiImprumutate;
    }

    // metode
    public void afisare() {
        System.out.println("Prenume si nume cititor: " + this.prenume + " " + this.nume);
        System.out.println("ID cititor: " + this.idCititor);
        // iteram lista de carti imprumutate
        if(!(cartiImprumutate == null)) {
            System.out.println("Carti imprumutate: ");
            for (Carte carte : this.cartiImprumutate) {
                carte.afisare();
                System.out.println("\n");
            }
        } else {
            System.out.println("Nicio carte imprumutata.");
        }
    }
}
