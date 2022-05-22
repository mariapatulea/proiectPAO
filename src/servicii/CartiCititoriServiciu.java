package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class CartiCititoriServiciu {
    private static CartiCititoriServiciu instanta = null;
    private final Database bazaDeDate = Database.getInstance();
    private final CarteServiciu carteServiciu = CarteServiciu.getInstance();
    private final CititorServiciu cititorServiciu = CititorServiciu.getInstance();

    // constructor privat
    private CartiCititoriServiciu() { }

    public static CartiCititoriServiciu getInstance() {
        if (instanta == null)
            instanta = new CartiCititoriServiciu();
        return instanta;
    }

    // create
    public boolean create(int idCarte, int idCititor) {
        try {
            // verificam ca id-urile sunt valide
            // idCarte trebuie sa fie in idCarte din carti
            int[] idCarti = carteServiciu.getAllIds();
            int ok1 = 0;
            for (int id: idCarti) {
                if (id == idCarte) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok1 == 0) {
                System.out.println("idCarte introdus este invalid");
                return false;
            }

            // idCititor trebuie sa fie in idCititor din cititori
            int[] idCititori = cititorServiciu.getAllIds();
            int ok2 = 0;
            for (int id: idCititori) {
                if (id == idCititor) {
                    ok2 = 1;
                    break;
                }
            }
            if (ok2 == 0) {
                System.out.println("idCititor introdus este invalid");
                return false;
            }

            // cartea trebuie sa aiba suficiente exemplare disponibile pentru imprumutat
            int nrExemplareCarte = carteServiciu.getNrExemplare(idCarte);
            if (nrExemplareCarte == 0) {
                System.out.println("Nu mai sunt exemplare disponibile de imprumutat");
                return false;
            } else if (nrExemplareCarte == -1) {
                System.out.println("eroare la imprumutare");
                return false;
            }

            // cititorul trebuie sa aiba 0-1 carti imprumutate (daca este guest), sau 0-9 carti imprumutate (daca este
            // membru)
            String tip = cititorServiciu.getTipCititor(idCititor);
            int nrCartiImprumutate = cititorServiciu.getNrCartiImprumutate(idCititor);
            if (Objects.equals(tip, "") || nrCartiImprumutate == -1) {
                System.out.println("eroare la imprumutare 2");
                return false;
            } else if (Objects.equals(tip, "guest") && nrCartiImprumutate >= 2) {
                System.out.println("Cititorul nu mai poate imprumuta carti");
                return false;
            } else if (Objects.equals(tip, "membru") && nrCartiImprumutate >= 10) {
                System.out.println("Cititorul nu mai poate imprumuta carti");
                return false;
            }

            // actualizam numarul de exemplare disponibile si numarul de carti imprumutate
            String query = String.format("update carti set numarExemplare = (%d - 1) where idCarte = %d",
                nrExemplareCarte, idCarte);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            query = String.format("update cititori set nrCartiImprumutate = (%d + 1) where idCititor = %d",
                    nrCartiImprumutate, idCititor);
            connection = bazaDeDate.incarcaBazaDeDate();
            statement = connection.createStatement();
            statement.executeUpdate(query);

            // putem insera in tabela carti_cititori
            query = String.format("insert into carti_cititori (idCarte, idCititor) values (%d, %d)", idCarte, idCititor);
            connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Create' apelat cu succes!");
            statement.close();
            connection.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // read
    public void read() {
        try {
            String query = "select * from carti_cititori";
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int nrTotalCartiImprumutate = 0;
            while (resultSet.next()) {
                System.out.println("Cititorul cu id-ul " + resultSet.getInt("idCititor") + " a imprumutat " +
                    "cartea cu id-ul " + resultSet.getInt("idCarte"));
                nrTotalCartiImprumutate++;
            }
            System.out.println("Numar total de carti imprumutate: " + nrTotalCartiImprumutate + ".");
//            statement.close();
//            connection.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("a aparut o eroare la afisarea cartilor imprumutate");
        }



    }

    // delete
    public boolean delete(int idCarte, int idCititor) {
        try {
            // verificam ca id-urile sunt valide
            // idCarte trebuie sa fie in idCarte din carti
            int[] idCarti = carteServiciu.getAllIds();
            int ok1 = 0;
            for (int id: idCarti) {
                if (id == idCarte) {
                    ok1 = 1;
                    break;
                }
            }
            if (ok1 == 0) {
                System.out.println("idCarte introdus este invalid");
                return false;
            }

            // idCititor trebuie sa fie in idCititor din cititori
            int[] idCititori = cititorServiciu.getAllIds();
            int ok2 = 0;
            for (int id: idCititori) {
                if (id == idCititor) {
                    ok2 = 1;
                    break;
                }
            }
            if (ok2 == 0) {
                System.out.println("idCititor introdus este invalid");
                return false;
            }

            String query = String.format("delete from carti_cititori where idCarte = %d and idCititor = %d", idCarte,
                idCititor);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Delete' apelat cu succes!");

            // update nr exemplare disponibile si nr carti imprumutate
            int nrExemplareCarte = carteServiciu.getNrExemplare(idCarte);
            int nrCartiImprumutate = cititorServiciu.getNrCartiImprumutate(idCititor);
            query = String.format("update carti set numarExemplare = (%d + 1) where idCarte = %d",
                nrExemplareCarte, idCarte);
            connection = bazaDeDate.incarcaBazaDeDate();
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = String.format("update cititori set nrCartiImprumutate = (%d - 1) where idCititor = %d",
                nrCartiImprumutate, idCititor);
            connection = bazaDeDate.incarcaBazaDeDate();
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Cititorul nu a putut returna cartea deoarece nu a imprumutat-o!");
            return false;
        }
    }
}
