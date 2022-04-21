package entitati;

public class Editura {
    // date membre/atribute private
    private String denumire;

    // constructor cu toti parametrii
    public Editura(String denumire) {
        this.denumire = denumire;
    }

    // setteri si getteri pentru fiecare atribut
    public String getDenumire() {
        return denumire;
    }
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    // metode
    public void afisare () {
        System.out.println("Denumire editura: " + this.denumire);
    }
}
