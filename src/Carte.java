public class Carte {
    // date membre/atribute private
    private String titluCarte;
    private Autor autor;
    private int numarVolum;
    private Editura editura;
    private int numarPagini;
    private String ISBN;
    private int numarExemplare;
    private Boolean hardCover;

    // constructor cu toti parametrii
    public Carte(String titluCarte, Autor autor, int numarVolum, Editura editura, int numarPagini, String ISBN,
                 int numarExemplare, Boolean hardCover) {
        this.titluCarte = titluCarte;
        this.autor = autor;
        this.numarVolum = numarVolum;
        this.editura = editura;
        this.numarPagini = numarPagini;
        this.ISBN = ISBN;
        this.numarExemplare = numarExemplare;
        this.hardCover = hardCover;
    }

    // setteri si getteri pentru fiecare atribut
    public void setTitluCarte(String titluCarte) {
        this.titluCarte = titluCarte;
    }
    public String getTitluCarte() {
        return titluCarte;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public Autor getAutor() {
        return autor;
    }

    public void setNumarVolum(int numarVolum) {
        this.numarVolum = numarVolum;
    }
    public int getNumarVolum() {
        return numarVolum;
    }

    public void setEditura(Editura editura) {
        this.editura = editura;
    }
    public Editura getEditura() {
        return editura;
    }

    public void setNumarPagini(int numarPagini) {
        this.numarPagini = numarPagini;
    }
    public int getNumarPagini() {
        return numarPagini;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getISBN() {
        return ISBN;
    }

    public void setNumarExemplare(int numarExemplare) {
        this.numarExemplare = numarExemplare;
    }
    public int getNumarExemplare() {
        return numarExemplare;
    }

    public Boolean getHardCover() {
        return hardCover;
    }
    public void setHardCover(Boolean hardCover) {
        this.hardCover = hardCover;
    }

    // metode
    public void afisare() {
        System.out.println("Titlu carte: " + this.titluCarte);
        this.autor.afisareAutor();
        System.out.println("Numar volum: " + this.numarVolum);
        this.editura.afisare();
        System.out.println("Numar pagini: " + this.numarPagini);
        System.out.println("ISBN: " + this.ISBN);
        System.out.println("Exemplare disponibile: " + this.numarExemplare);
    }
}
