package entitati;

public class Agent {
    // date membre/atribute private
    private String prenume;
    private String nume;
    private Agentie agentie;

    // constructor cu toti parametrii
    public Agent(String prenume, String nume, Agentie agentie) {
        this.prenume = prenume;
        this.nume = nume;
        this.agentie = agentie;
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

    public Agentie getAgentie() {
        return agentie;
    }
    public void setAgentie(Agentie agentie) {
        this.agentie = agentie;
    }

    // metode specifice
    public void afisare() {
        System.out.println("Prenume agent: " + this.prenume);
        System.out.println("Nume agent: " + this.nume);
        this.agentie.afisare();
    }
}
