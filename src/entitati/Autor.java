package entitati;

import java.util.HashSet;
import java.util.Set;

public class Autor {
    // date membre/atribute private
    private String prenume;
    private String nume;
    private Agent agent;
    private Editor editor;
    private Set<Carte> cartiPublicate;  // colectie neordonata

    // constructori
    public Autor(String prenume, String nume) {
        this.prenume = prenume;
        this.nume = nume;
    }

    public Autor(String prenume, String nume, Agent agent, Editor editor, Set<Carte> cartiPublicate) {
        this.prenume = prenume;
        this.nume = nume;
        this.agent = agent;
        this.editor = editor;
        if(cartiPublicate == null) {
            this.cartiPublicate = new HashSet<Carte>();
        } else {
            this.cartiPublicate = cartiPublicate;
        }
    }

    // setteri si getteri pentru fiecare atribut
    public String getPrenume() {
        return this.prenume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return this.nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public Agent getAgent() {
        return agent;
    }
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Editor getEditor() {
        return editor;
    }
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Set<Carte> getCartiPublicate() {
        return cartiPublicate;
    }
    public void setCartiPublicate(Set<Carte> cartiPublicate) {
        this.cartiPublicate = cartiPublicate;
    }

    // metode
    public void afisare() {
        System.out.println("Prenume autor: " + this.prenume);
        System.out.println("Nume autor: " + this.nume);
        this.agent.afisare();
        this.editor.afisare();
        // iteram prin set-ul de carti publicate
        System.out.println("Carti publicate: \n");
        for(Carte carte: this.cartiPublicate) {
            carte.afisare();
            System.out.println("\n");
        }
    }

    public void afisareAutor() {
        System.out.println("Autor: " + this.prenume + " " + this.nume);
    }
}
