package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CarteServiciu {
    private static CarteServiciu instanta = null;
    private final Database bazaDeDate = Database.getInstance();

    // constructor privat
    private CarteServiciu() { }

    public static CarteServiciu getInstance() {
        if (instanta == null)
            instanta = new CarteServiciu();
        return instanta;
    }

    // create
    public boolean create(String titluCarte, int numarVolum, int idEditura, int numarPagini, String ISBN, int
        numarExemplare, boolean hardCover, int idCititor, int idAutor) {
        try {
            String query;
            if (idCititor == -1) {
                query = String.format("insert into carti (titluCarte, numarVolum, idEditura, numarPagini, ISBN, " +
                    "numarExemplare, hardCover, idCititor, idAutor) values ('%s', %d, %d, %d, '%s', %d, %b, null, %d)",
                     titluCarte, numarVolum, idEditura, numarPagini, ISBN, numarExemplare, hardCover, idAutor);
            } else {
                query = String.format("insert into carti (titluCarte, numarVolum, idEditura, numarPagini, ISBN, " +
                    "numarExemplare, hardCover, idCititor, idAutor) values ('%s', %d, %d, %d, '%s', %d, %b, %d, %d)",
                    titluCarte, numarVolum, idEditura, numarPagini, ISBN, numarExemplare, hardCover, idCititor, idAutor);
            }
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
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

    public ResultSet read() {
        try {
            String query = "select * from carti order by idCarte";
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("'Select' apelat cu succes!");
//            statement.close();
//            connection.close();
            return resultSet;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // update
    public boolean update(int id, int numarExemplare) {
        try {
            String query = String.format("update carti set numarExemplare = %d where idCarte = %d", numarExemplare, id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Update' apelat cu succes!");
            statement.close();
            connection.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // delete
    public boolean delete(int id) {
        try {
            String query = String.format("delete from carti where idCarte = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Delete' apelat cu succes!");
            statement.close();
            connection.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
