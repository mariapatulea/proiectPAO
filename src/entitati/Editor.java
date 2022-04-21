package entitati;

public class Editor {
    // date membre/atribute private
    private String prenume;
    private String nume;
    private Editura editura;

    // constructor cu toti parametrii
    public Editor(String prenume, String nume, Editura editura) {
        this.prenume = prenume;
        this.nume = nume;
        this.editura = editura;
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

    public Editura getEditura() {
        return editura;
    }
    public void setEditura(Editura editura) {
        this.editura = editura;
    }

    // metode
    public void afisare() {
        System.out.println("Prenume editor: " + this.prenume);
        System.out.println("Nume editor: " + this.nume);
        this.editura.afisare();
    }
}
