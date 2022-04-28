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
    private final ScrieInFisier scrieInFisier = ScrieInFisier.getInstance();
    private final CitesteDinFisier citesteDinFisier = CitesteDinFisier.getInstance();
    private final AuditServiciu audit = AuditServiciu.getInstance();

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
        continutFisier.add("membru");

        Membru membruNou = new Membru(prenume, nume, id, cartiImprumutate);
        scrieInFisier.scrie("./date/Cititor.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Cititor.csv");
        System.out.println(fisier);

        audit.log("adaugare_cititor_membru");
    }

    // comanda 7
    public void adaugare_cititor_guest(Scanner console) {
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
        continutFisier.add("guest");

        Guest guestNou = new Guest(prenume, nume, id, cartiImprumutate);
        scrieInFisier.scrie("./date/Cititor.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Cititor.csv");
        System.out.println(fisier);

        audit.log("adaugare_cititor_guest");
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

        audit.log("imprumutare_carte");
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

        audit.log("returnare_carte");
    }

    // comanda 2
    public void adaugare_carte(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Titlu carte: ");
        String titluCarte = console.next();
        continutFisier.add(titluCarte);
        Autor autor = adaugare_autor(console);
        String numeAutor = autor.getNume();
        continutFisier.add(numeAutor);
        String prenumeAutor = autor.getPrenume();
        continutFisier.add(prenumeAutor);
        System.out.println("Numar volum: ");
        int numarVolum = console.nextInt();
        continutFisier.add(Integer.toString(numarVolum));
        Editura editura = adaugare_editura(console);
        continutFisier.add(editura.getDenumire());
        System.out.println("Numar pagini: ");
        int numarPagini = console.nextInt();
        continutFisier.add(Integer.toString(numarPagini));
        System.out.println("ISBN: ");
        String isbn = console.next();
        continutFisier.add(isbn);
        System.out.println("Exemplare disponibile: ");
        int numarExemplare = console.nextInt();
        continutFisier.add(Integer.toString(numarExemplare));
        System.out.println("Hardcover? ");
        Boolean hardcover = console.nextBoolean();
        if (hardcover) {
            continutFisier.add("Da");
        } else {
            continutFisier.add("Nu");
        }

        Carte carteNoua = new Carte(titluCarte, autor, numarVolum, editura, numarPagini, isbn, numarExemplare, hardcover);
        scrieInFisier.scrie("./date/Carte.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Carte.csv");
        System.out.println(fisier);

        audit.log("adaugare_carte");
    }

    // comanda 10
    public Autor adaugare_autor(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Prenume autor: ");
        String prenume = console.next();
        continutFisier.add(prenume);
        System.out.println("Nume autor: ");
        String nume = console.next();
        continutFisier.add(nume);
        Autor autorNou = new Autor(prenume, nume);
        scrieInFisier.scrie("./date/Autor.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Autor.csv");
        System.out.println(fisier);

        audit.log("adaugare_autor");

        return autorNou;
    }

    // comanda 11
    public Editura adaugare_editura(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Editura: ");
        String denumire = console.next();
        continutFisier.add(denumire);
        Editura edituraNoua = new Editura(denumire);
        scrieInFisier.scrie("./date/Editura.csv", continutFisier);
        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Editura.csv");
        System.out.println(fisier);

        audit.log("adaugare_editura");

        return edituraNoua;
    }

    // comanda 3
    public void afisare_carti() {
        for(Carte carte: cartiBiblioteca) {
            carte.afisare();
            System.out.println();
        }

        audit.log("afisare_carti");
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

        audit.log("afisare_inventar");
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

        audit.log("afisare_carti_imprumutate");
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

        audit.log("afisare_carti_autor");
    }

    // metoda care incarca datele din fisierele csv
    public void incarcaDate() {
        CitesteDinFisier citesteDinFisier = CitesteDinFisier.getInstance();

        // vrem sa adaugam in lista de cititori membri si lista de cititori guest ce se gasete in fisierul
        // Cititor.csv
        ArrayList<ArrayList<String>> continutFisier = citesteDinFisier.citeste("./date/Cititor.csv");
        for (ArrayList<String> line: continutFisier) {
            if (line.get(3) == "membru") {
                ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                Membru membru = new Membru(line.get(0), line.get(1), Integer.parseInt(line.get(2)), cartiImprumutate);
                cititoriMembri.add(membru);
            } else {
                ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                Guest guest = new Guest(line.get(0), line.get(1), Integer.parseInt(line.get(2)), cartiImprumutate);
                cititoriGuests.add(guest);
            }
        }

        // adaugam in lista de autori ce se gaseste in fisierul Autor.csv
        continutFisier = citesteDinFisier.citeste("./date/Autor.csv");
        for (ArrayList<String> line: continutFisier) {
            Autor autor = new Autor(line.get(0), line.get(1));
            autoriCarti.add(autor);
        }

        // adaugam in lista de carti ce se gaseste in fisierul Carte.csv
        continutFisier = citesteDinFisier.citeste("./date/Carte.csv");
        for (ArrayList<String> line: continutFisier) {
            String numeAutor = line.get(1);
            String prenumeAutor = line.get(2);
            Autor autor = new Autor(prenumeAutor, numeAutor);
            String denumireEditura = line.get(4);
            Editura editura = new Editura(denumireEditura);
            Boolean hardcover = false;
            if (line.get(8) == "Da") {
                hardcover = true;
            }
            Carte carte = new Carte(line.get(0), autor, Integer.parseInt(line.get(3)), editura,
                    Integer.parseInt(line.get(5)), line.get(6), Integer.parseInt(line.get(7)), hardcover);
            cartiBiblioteca.add(carte);
        }

        // adaugam in lista de edituri ce se gaseste in fisierul Editura.csv
        continutFisier = citesteDinFisier.citeste("./date/Editura.csv");
        for (ArrayList<String> line: continutFisier) {
            Editura editura = new Editura(line.get(0));
            edituriCarti.add(editura);
        }

        System.out.println("Am incarcat datele din fisierele csv!");
    }
}
