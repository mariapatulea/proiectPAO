package servicii;

import entitati.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final EdituraServiciu edituraServiciu = EdituraServiciu.getInstance();
    private final CititorServiciu cititorServiciu = CititorServiciu.getInstance();
    private final AutorServiciu autorServiciu = AutorServiciu.getInstance();
    private final CarteServiciu carteServiciu = CarteServiciu.getInstance();
    private final CartiCititoriServiciu cartiCititoriServiciu = CartiCititoriServiciu.getInstance();

    // comanda 1
    public void adaugare_cititor_membru(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Prenume cititor: ");
        String prenume = console.next();
        System.out.println("Nume cititor: ");
        String nume = console.next();

        if(cititorServiciu.create(prenume, nume, "membru")) {
            System.out.println("am apelat cu succes metoda create din CititorServiciu");
            int idCititor = cititorServiciu.getIdByLastName(nume);
            continutFisier.add(prenume);
            continutFisier.add(nume);
            continutFisier.add(Integer.toString(idCititor));
            continutFisier.add("membru");
        } else {
            System.out.println("nu am putut apela metoda create din CititorServiciu");
        }

        incarcaDateDB("cititor");

        scrieInFisier.scrie("./date/Cititor.csv", continutFisier);
//        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Cititor.csv");
//        System.out.println(fisier);

        audit.log("adaugare_cititor_membru");
    }

    // comanda 7
    public void adaugare_cititor_guest(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Prenume cititor: ");
        String prenume = console.next();
        System.out.println("Nume cititor: ");
        String nume = console.next();

        if(cititorServiciu.create(prenume, nume, "guest")) {
            System.out.println("am apelat cu succes metoda create din CititorServiciu");
            int idCititor = cititorServiciu.getIdByLastName(nume);
            continutFisier.add(prenume);
            continutFisier.add(nume);
            continutFisier.add(Integer.toString(idCititor));
            continutFisier.add("guest");
        } else {
            System.out.println("nu am putut apela metoda create din CititorServiciu");
        }

        incarcaDateDB("cititor");

        scrieInFisier.scrie("./date/Cititor.csv", continutFisier);
//        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Cititor.csv");
//        System.out.println(fisier);

        audit.log("adaugare_cititor_guest");
    }

    // comanda 9
    public void imprumutare_carte(Scanner console) {
        // afisam cititorii si cartile (cu tot cu id)
        afisare_cititori();
        System.out.println("Introduceti ID-ul cititorului care doreste sa imprumute o carte: ");
        int idCititor = console.nextInt();

        afisare_carti();
        System.out.println("Introduceti ID-ul cartii: ");
        int idCarte = console.nextInt();

        if (cartiCititoriServiciu.create(idCarte, idCititor)) {
            System.out.println("Cititorul a imprumutat o carte si am actualizat baza de date!");
        } else {
            System.out.println("EROARE! Cititorul nu a putut imprumuta o carte!");
        }

        audit.log("imprumutare_carte");
    }

    // comanda 8
    public void returnare_carte(Scanner console) {
        // trb sa afisam ceva de genul cititorul cu id-ul x a imprumutat cartea cu id-ul y
        cartiCititoriServiciu.read();
        System.out.println("Introduceti ID-ul cititorului care doreste sa returneze o carte: ");
        int idCititor = console.nextInt();

        System.out.println("Introduceti ID-ul cartii: ");
        int idCarte = console.nextInt();

        if (cartiCititoriServiciu.delete(idCarte, idCititor)) {
            System.out.println("Cititorul a returnat o carte si am actualizat baza de date!");
        } else {
            System.out.println("EROARE! Cititorul nu a putut returna o carte!");
        }

        audit.log("returnare_carte");
    }

    // comanda 2
    public void adaugare_carte(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Titlu carte: ");
        String titluCarte = console.next();
        System.out.println("Prenume autor: ");
        String prenumeAutor = console.next();
        System.out.println("Nume autor: ");
        String numeAutor = console.next();

        // get id autor
        int idAutor = autorServiciu.getIdByLastName(numeAutor);
        if (idAutor == -1) {
            System.out.println("id autor invalid");
            return;
        }

        System.out.println("Numar volum: ");
        int numarVolum = console.nextInt();
        System.out.println("Editura: ");
        String editura = console.next();
        continutFisier.add(editura);

        // get id editura
        int idEditura = edituraServiciu.getIdByDenumire(editura);
        if (idEditura == -1) {
            System.out.println("id editura invalid");
            return;
        }

        System.out.println("Numar pagini: ");
        int numarPagini = console.nextInt();
        System.out.println("ISBN: ");
        String isbn = console.next();
        System.out.println("Exemplare disponibile: ");
        int numarExemplare = console.nextInt();
        System.out.println("Hardcover? ");
        boolean hardcover = console.nextBoolean();

        if (carteServiciu.create(titluCarte, numarVolum, idEditura, numarPagini, isbn, numarExemplare, hardcover,
            idAutor)) {
            System.out.println("am adaugat cartea in baza de date");
            continutFisier.add(titluCarte);
            continutFisier.add(numeAutor);
            continutFisier.add(prenumeAutor);
            continutFisier.add(Integer.toString(numarVolum));
            continutFisier.add(Integer.toString(numarPagini));
            continutFisier.add(isbn);
            continutFisier.add(Integer.toString(numarExemplare));
            if (hardcover) {
                continutFisier.add("Da");
            } else {
                continutFisier.add("Nu");
            }
            incarcaDateDB("carte");
        } else {
            System.out.println("nu am putut adauga cartea in baza de date");
        }

        scrieInFisier.scrie("./date/Carte.csv", continutFisier);
//        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Carte.csv");
//        System.out.println(fisier);

        audit.log("adaugare_carte");
    }

    // comanda 10
    public Autor adaugare_autor(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Prenume autor: ");
        String prenume = console.next();
        System.out.println("Nume autor: ");
        String nume = console.next();

        if(autorServiciu.create(prenume, nume)) {
            System.out.println("am apelat cu succes metoda create din AutorServiciu");
            continutFisier.add(prenume);
            continutFisier.add(nume);
            incarcaDateDB("autor");
        } else {
            System.out.println("nu am putut apela metoda create din AutorServiciu");
        }
        incarcaDateDB("autor");

        Autor autorNou = new Autor(prenume, nume);
        scrieInFisier.scrie("./date/Autor.csv", continutFisier);
//        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Autor.csv");
//        System.out.println(fisier);

        audit.log("adaugare_autor");

        return autorNou;
    }

    // comanda 11
    public Editura adaugare_editura(Scanner console) {
        ArrayList<String> continutFisier = new ArrayList<>();
        System.out.println("Editura: ");
        String denumire = console.next();

        if(edituraServiciu.create(denumire)) {
            System.out.println("am apelat cu succes metoda create din EdituraServiciu");
            continutFisier.add(denumire);
            incarcaDateDB("editura");
        } else {
            System.out.println("nu am putut apela metoda create din EdituraServiciu");
        }
        Editura edituraNoua = new Editura(denumire);
        scrieInFisier.scrie("./date/Editura.csv", continutFisier);
//        ArrayList<ArrayList<String>> fisier = citesteDinFisier.citeste("./date/Editura.csv");
//        System.out.println(fisier);

        audit.log("adaugare_editura");

        return edituraNoua;
    }

    // comanda 12
    public void afisare_edituri() {
        ResultSet resultSet = edituraServiciu.read();
        try {
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString("idEditura") + " Denumire: " +
                        resultSet.getString("denumire"));
            }
        } catch(SQLException ex) {
            System.out.println("nu am putut apela metoda read din EdituraServiciu");
        }

        audit.log("afisare_edituri");
    }

    // comanda 15
    public void afisare_cititori() {
        ResultSet resultSet = cititorServiciu.read();
        try {
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getString("idCititor") + " Prenume: " +
                    resultSet.getString("prenume") + " Nume: " + resultSet.getString("nume")
                    + " Tip: " + resultSet.getString("tip") + " Numar carti imprumutate: " +
                    resultSet.getInt("nrCartiImprumutate"));
            }
        } catch(SQLException ex) {
            System.out.println("nu am putut apela metoda read din CititorServiciu");
        }

        audit.log("afisare_cititori");
    }

    // comanda 16
    public void actualizare_cititor(Scanner console) {
        System.out.println("Id-ul cititorului de actualizat: ");
        int id = console.nextInt();
        System.out.println("Noul tip de cititor: ");
        String tip = console.next();

        if(cititorServiciu.update(tip, id)) {
            System.out.println("am actualizat baza de date");
            afisare_cititori();
            incarcaDateDB("cititor");
        } else {
            System.out.println("nu am putut actualiza tipul cititorului");
        }

        audit.log("actualizare_cititor");
    }

    // comanda 17
    public void stergere_cititor(Scanner console) {
        System.out.println("Id-ul cititorului de sters: ");
        int id = console.nextInt();
        int[] ids = cititorServiciu.getAllIds();
        int ok = 0;
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == id) {
                    ok = 1;
                    break;
                }
            }
        }
        if(cititorServiciu.delete(id) && ok == 1) {
            System.out.println("am sters cititorul cu id-ul " + id);
            afisare_cititori();
            incarcaDateDB("cititor");
        } else {
            System.out.println("nu am putut sterge cititorul");
        }

        audit.log("stergere_cititor");
    }

    // comanda 13
    public void actualizare_editura(Scanner console) {
        System.out.println("Numele editurii de actualizat: ");
        String denumire = console.next();
        System.out.println("Noul Id al editurii: ");
        int id = console.nextInt();

        if (edituraServiciu.update(denumire, id)) {
            System.out.println("am actualizat baza de date");
            afisare_edituri();
        } else {
            System.out.println("nu am putut actualiza id-ul editurii");
        }

        audit.log("actualizare_editura");
    }

    // comanda 14
    public void stergere_editura(Scanner console) {
        System.out.println("Id-ul editurii de sters: ");
        int id = console.nextInt();
        String denumire = edituraServiciu.getDenumireById(id);

        if(edituraServiciu.delete(id)) {
            System.out.println("am sters editura cu id-ul " + id);
            afisare_edituri();
            incarcaDateDB("editura");
        } else {
            System.out.println("nu am putut sterge editura");
        }

        audit.log("stergere_editura");
    }

    // comanda 3
    public void afisare_carti() {
        for(Carte carte: cartiBiblioteca) {
            System.out.println("Id-ul cartii: " + carteServiciu.getIdByTitle(carte.getTitluCarte()));
            carte.afisare();
            System.out.println();
        }

        audit.log("afisare_carti");
    }

    // comanda 4
    public void afisare_inventar() {
        int numarTotalCarti = 0;
        for(Carte carte: cartiBiblioteca) {
            System.out.println("Cartea cu titlul " + carte.getTitluCarte() + " se gaseste in " +
                carte.getNumarExemplare() + " exemplare.");
            numarTotalCarti += carte.getNumarExemplare();
        }
        System.out.println("Numar total de carti: " + numarTotalCarti + ".");

        audit.log("afisare_inventar");
    }

    // comanda 5
    public void afisare_carti_imprumutate() {
        cartiCititoriServiciu.read();

        audit.log("afisare_carti_imprumutate");
    }

    // comanda 6
    public void afisare_carti_autor(Scanner console) {
        System.out.println("Introduceti prenumele autorului: ");
        String prenume = console.next();
        System.out.println("Introduceti numele autorului: ");
        String nume = console.next();
        for (Carte carte: cartiBiblioteca) {
            if (carte.getAutor().getNume().equals(nume)) {
                carte.afisare();
                System.out.println();
            }
        }

        audit.log("afisare_carti_autor");
    }

    // comanda 18
    public void actualizare_autor(Scanner console) {
        System.out.println("Introduceti Id-ul autorului: ");
        int id = console.nextInt();
        System.out.println("Introduceti noul nume al autorului: ");
        String nume = console.next();

        if (autorServiciu.update(id, nume)) {
            System.out.println("am actualizat autorul si baza de date");
            incarcaDateDB("autor");
        } else {
            System.out.println("nu am putut actualiza autorul");
        }

        audit.log("actualizare_autor");
    }

    // comanda 19
    public void stergere_autor(Scanner console) {
        System.out.println("Introduceti Id-ul autorului pe care doriti sa il stergeti: ");
        int id = console.nextInt();
        int[] ids = autorServiciu.getAllIds();
        int ok = 0;
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] == id) {
                    ok = 1;
                    break;
                }
            }
        }

        if (autorServiciu.delete(id) && ok == 1) {
            System.out.println("am sters autorul cu id-ul " + id);
            incarcaDateDB("autor");
        } else {
            System.out.println("nu am putut sterge autorul");
        }

        audit.log("stergere_autor");
    }

    // metoda care incarca datele din fisierele csv
    public void incarcaDateCSV() {
        CitesteDinFisier citesteDinFisier = CitesteDinFisier.getInstance();

        // vrem sa adaugam in lista de cititori membri si lista de cititori guest ce se gasete in fisierul
        // Cititor.csv
        ArrayList<ArrayList<String>> continutFisier = citesteDinFisier.citeste("./date/Cititor.csv");
        for (ArrayList<String> line: continutFisier) {
            if (Objects.equals(line.get(3), "membru")) {
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
            boolean hardcover = false;
            if (Objects.equals(line.get(8), "Da")) {
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

    // metoda care incarca datele din baza de date
    public void incarcaDateDB(String entitate) {
        if (entitate.equals("all")) {
            // adaugam in lista de autori ce se gaseste in baza de date
            ResultSet resultSet = autorServiciu.read();
            try {
                while (resultSet.next()) {
                    Autor autor = new Autor(resultSet.getString("prenume"),
                        resultSet.getString("nume"));
                    autoriCarti.add(autor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca autorii din baza de date");
            }

            // adaugam aici cum incarcam datele din tabela carti_cititori!!

            // adaugam in lista de edituri ce se gaseste in baza de date
            resultSet = edituraServiciu.read();
            try {
                while (resultSet.next()) {
                    Editura editura = new Editura(resultSet.getString("denumire"));
                    edituriCarti.add(editura);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca editurile din baza de date");
            }

            // adaugam in listele de cititori ce se gaseste in baza de date
            resultSet = cititorServiciu.read();
            try {
                while (resultSet.next()) {
                    if (resultSet.getString("tip").equals("membru")) {
                        ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                        Membru membru = new Membru(resultSet.getString("prenume"),
                                resultSet.getString("nume"), resultSet.getInt("idCititor"),
                                cartiImprumutate);
                        cititoriMembri.add(membru);
                    } else {
                        ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                        Guest guest = new Guest(resultSet.getString("prenume"),
                                resultSet.getString("nume"), resultSet.getInt("idCititor"),
                                cartiImprumutate);
                        cititoriGuests.add(guest);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca cititorii din baza de date");
            }

            resultSet = carteServiciu.read();
            try {
                while (resultSet.next()) {
                    int idAutor = resultSet.getInt("idAutor");
                    int idEditura = resultSet.getInt("idEditura");
                    String denumireEditura = edituraServiciu.getDenumireById(idEditura);
                    String numeAutor = autorServiciu.getNumeById(idAutor);
                    String prenumeAutor = autorServiciu.getPrenumeById(idAutor);
                    boolean hardcover = false;
                    if (resultSet.getInt("hardCover") == 1) {
                        hardcover = true;
                    }

                    Autor autor = new Autor(prenumeAutor, numeAutor);
                    Editura editura = new Editura(denumireEditura);
                    Carte carte = new Carte(resultSet.getString("titluCarte"), autor,
                            resultSet.getInt("numarVolum"), editura, resultSet.getInt("numarPagini"),
                            resultSet.getString("ISBN"), resultSet.getInt("numarExemplare"),
                            hardcover);

                    cartiBiblioteca.add(carte);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca cartile din baza de date");
            }

        } else if (entitate.equals("cititor")) {
            // adaugam in listele de cititori ce se gaseste in baza de date
            ResultSet resultSet = cititorServiciu.read();
            try {
                while (resultSet.next()) {
                    if (resultSet.getString("tip").equals("membru")) {
                        ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                        Membru membru = new Membru(resultSet.getString("prenume"),
                                resultSet.getString("nume"), resultSet.getInt("idCititor"),
                                cartiImprumutate);
                        cititoriMembri.add(membru);
                    } else {
                        ArrayList<Carte> cartiImprumutate = new ArrayList<>();
                        Guest guest = new Guest(resultSet.getString("prenume"),
                                resultSet.getString("nume"), resultSet.getInt("idCititor"),
                                cartiImprumutate);
                        cititoriGuests.add(guest);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca cititorii din baza de date");
            }
        } else if (entitate.equals("editura")) {
            // adaugam in lista de edituri ce se gaseste in baza de date
            ResultSet resultSet = edituraServiciu.read();
            try {
                while (resultSet.next()) {
                    Editura editura = new Editura(resultSet.getString("denumire"));
                    edituriCarti.add(editura);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca editurile din baza de date");
            }
        } else if (entitate.equals("autor")) {
            // adaugam in lista de autori ce se gaseste in baza de date
            ResultSet resultSet = autorServiciu.read();
            try {
                while (resultSet.next()) {
                    Autor autor = new Autor(resultSet.getString("prenume"),
                            resultSet.getString("nume"));
                    autoriCarti.add(autor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca autorii din baza de date");
            }
        } else if (entitate.equals("carte")) {
            ResultSet resultSet = carteServiciu.read();
            try {
                while (resultSet.next()) {
                    int idAutor = resultSet.getInt("idAutor");
                    int idEditura = resultSet.getInt("idEditura");
                    String denumireEditura = edituraServiciu.getDenumireById(idEditura);
                    String numeAutor = autorServiciu.getNumeById(idAutor);
                    String prenumeAutor = autorServiciu.getPrenumeById(idAutor);
                    boolean hardcover = false;
                    if (resultSet.getInt("hardCover") == 1) {
                        hardcover = true;
                    }

                    Autor autor = new Autor(prenumeAutor, numeAutor);
                    Editura editura = new Editura(denumireEditura);
                    Carte carte = new Carte(resultSet.getString("titluCarte"), autor,
                        resultSet.getInt("numarVolum"), editura, resultSet.getInt("numarPagini"),
                        resultSet.getString("ISBN"), resultSet.getInt("numarExemplare"),
                        hardcover);

                    cartiBiblioteca.add(carte);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("nu am putut incarca cartile din baza de date");
            }
        }

        System.out.println("Am incarcat datele din baza de date!");
    }
}
