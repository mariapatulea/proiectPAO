package servicii;

import entitati.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Serviciu {
    private final List<Membru> cititoriMembri = new ArrayList<>();
    private final List<Guest> cititoriGuests = new ArrayList<>();
    private final List<Carte> cartiBiblioteca = new ArrayList<>();
    private final List<Autor> autoriCarti = new ArrayList<>();
    private final List<Editura> edituriCarti = new ArrayList<>();
    private ScrieInFisier scrieInFisier = ScrieInFisier.getInstance();
    private CitesteDinFisier citesteDinFisier = CitesteDinFisier.getInstance();

    // comanda 1
    public void adaugare_cititor_membru(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Prenume cititor: ");
        String prenume = console.next();
        continutFisier.add(prenume);
        System.out.println("Nume cititor: ");
        String nume = console.next();
        continutFisier.add(nume);
        System.out.println("ID cititor: ");
        int id = console.nextInt();
        continutFisier.add(Integer.toString(id));
        List<Carte> cartiImprumutate = new ArrayList<>();
        continutFisier.add("None");

        Membru membruNou = new Membru(prenume, nume, id, cartiImprumutate);
        scrieInFisier.scrie("./date/Cititor.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Cititor.csv");
        System.out.println(fisier);
        cititoriMembri.add(membruNou);
        System.out.println(cititoriMembri.size());
    }

    // comanda 7
    public void adaugare_cititor_guest(Scanner console) {
        System.out.println("Prenume cititor: ");
        String prenume = console.next();
        System.out.println("Nume cititor: ");
        String nume = console.next();
        System.out.println("ID cititor: ");
        int id = console.nextInt();
        List<Carte> cartiImprumutate = new ArrayList<>();

        Guest guestNou = new Guest(prenume, nume, id, cartiImprumutate);
        cititoriGuests.add(guestNou);
        System.out.println(cititoriGuests.size());
    }

    // comanda 9
    public void imprumutare_carte(Scanner console) {
        System.out.println("Introduceti ID-ul cititorului care doreste sa imprumute o carte: ");
        int idCititor = console.nextInt();
        System.out.println("Introduceti ISBN-ul cartii: ");
        String isbn = console.next();
        for(Cititor cititor: cititoriMembri) {
            if(cititor.getIdCititor() == idCititor) {
                for(Carte carte: cartiBiblioteca) {
                    if(carte.getISBN() == isbn && carte.getNumarExemplare() >= 1) {
                        List<Carte> cartiImpr = cititor.getCartiImprumutate();
                        cartiImpr.add(carte);
                        carte.setNumarExemplare(carte.getNumarExemplare() - 1);
                        cititor.setCartiImprumutate(cartiImpr);
                        System.out.println("Cititorul cu ID-ul " + idCititor + " a imprumutat cartea cu isbn-ul " +
                                isbn);
                        break;
                    }
                }
                break;
            }
        }
    }

    // comanda 8
    public void returnare_carte(Scanner console) {
        // ne trb id-ul cititorului si isbn-ul cartii
        System.out.println("Introduceti ID-ul cititorului care doreste sa imprumute o carte: ");
        int idCititor = console.nextInt();
        System.out.println("Introduceti ISBN-ul cartii: ");
        String isbn = console.next();
        for(Cititor cititor: cititoriMembri) {
            if(cititor.getIdCititor() == idCititor) {
                for(Carte carte: cartiBiblioteca) {
                    if(carte.getISBN() == isbn && cititor.getCartiImprumutate().contains(carte)) {
                        List<Carte> cartiImpr = cititor.getCartiImprumutate();
                        cartiImpr.remove(carte);
                        carte.setNumarExemplare(carte.getNumarExemplare() + 1);
                        cititor.setCartiImprumutate(cartiImpr);
                        System.out.println("Cititorul cu ID-ul " + idCititor + " a returnat cartea cu isbn-ul " +
                                isbn);
                        break;
                    }
                }
                break;
            }
        }
    }

    // comanda 2
    public void adaugare_carte(Scanner console) {
        System.out.println("Titlu carte: ");
        String titluCarte = console.next();
        Autor autor = adaugare_autor(console);
        System.out.println("Numar volum: ");
        int numarVolum = console.nextInt();
        Editura editura = adaugare_editura(console);
        System.out.println("Numar pagini: ");
        int numarPagini = console.nextInt();
        System.out.println("ISBN: ");
        String isbn = console.next();
        System.out.println("Exemplare disponibile: ");
        int numarExemplare = console.nextInt();
        System.out.println("Hardcover? ");
        Boolean hardcover = console.nextBoolean();
        Carte carteNoua = new Carte(titluCarte, autor, numarVolum, editura, numarPagini, isbn, numarExemplare, hardcover);

        cartiBiblioteca.add(carteNoua);
    }

    // comanda 10
    public Autor adaugare_autor(Scanner console) {
        System.out.println("Prenume autor: ");
        String prenume = console.next();
        System.out.println("Nume autor: ");
        String nume = console.next();
        Autor autorNou = new Autor(prenume, nume);
        autoriCarti.add(autorNou);



        return autorNou;
    }

    // comanda 11
    public Editura adaugare_editura(Scanner console) {
        System.out.println("entitati.Editura: ");
        String denumire = console.next();
        Editura edituraNoua = new Editura(denumire);
        edituriCarti.add(edituraNoua);
        return edituraNoua;
    }

    // comanda 3
    public void afisare_carti() {
        for(Carte carte: cartiBiblioteca) {
            carte.afisare();
            System.out.println();
        }
    }

    // comanda 4
    public void afisare_inventar() {
        int numarTotalCarti = 0;
        for(Carte carte: cartiBiblioteca) {
            System.out.println("Cartea cu isbn-ul " + carte.getISBN() + " se gaseste in " + carte.getNumarExemplare()
                    + " exemplare.");
            numarTotalCarti += carte.getNumarExemplare();
        }
        System.out.println("Numar total de carti: " + numarTotalCarti + ".");
    }

    // comanda 5
    public void afisare_carti_imprumutate() {
        int numarTotalCartiImprumutate = 0;
        for(Cititor cititor: cititoriMembri) {
            for(Carte carte: cititor.getCartiImprumutate()) {
                carte.afisare();
                numarTotalCartiImprumutate++;
            }
        }
        for(Cititor cititor: cititoriGuests) {
            for(Carte carte: cititor.getCartiImprumutate()) {
                carte.afisare();
                numarTotalCartiImprumutate++;
            }
        }
        System.out.println("Numar total de carti imprumutate: " + numarTotalCartiImprumutate + ".");
    }

    // comanda 6
    public void afisare_carti_autor(Scanner console) {
        System.out.println("Introduceti prenumele autorului: ");
        String prenume = console.next();
        System.out.println("Introduceti numele autorului: ");
        String nume = console.next();
        for(Autor autor: autoriCarti) {
            if(autor.getPrenume() == prenume && autor.getNume() == nume) {
                for(Carte cartePublicata: autor.getCartiPublicate()) {
                    cartePublicata.afisare();
                    System.out.println();
                }
            }
        }
    }

    // metoda care incarca datele din fisierele csv
    public void incarcaDate() {
        CitesteDinFisier citesteDinFisier = CitesteDinFisier.getInstance();

        // vrem sa adaugam in lista de cititori membri ce se gasete in fisierul Cititor.csv
        ArrayList<ArrayList<String>> continutFisier = citesteDinFisier.citeste("./date/Cititor.csv");
        for (ArrayList<String> line: continutFisier) {
            ArrayList<Carte> cartiImprumutate = new ArrayList<>();
            Membru membru = new Membru(line.get(0), line.get(1), Integer.parseInt(line.get(2)), cartiImprumutate);
            cititoriMembri.add(membru);
        }
    }
}
