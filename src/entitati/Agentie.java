package entitati;

public class Agentie {
    // date membre/atribute private
    private String denumire;

    // constructor cu toti parametrii
    public Agentie(String denumire) {
        this.denumire = denumire;
    }

    // setteri si getteri pentru fiecare atribut
    public String getDenumire() {
        return this.denumire;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    // metode
    public void afisare() {
        System.out.println("Denumire agentie: " + this.denumire);
    }
}
