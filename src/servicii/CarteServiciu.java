package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public int getIdByTitle(String titluCarte) {
        try {
            String query = String.format("select idCarte from carti where titluCarte = '%s'", titluCarte);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int idCarte = resultSet.getInt("idCarte");
                return idCarte;
            }
            System.out.println("error");
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("nu am putut prelua id-ul cartii");
            return -1;
        }
    }

    public int getNrExemplare(int id) {
        try {
            String query = String.format("select numarExemplare from carti where idCarte = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int nrExemplare = resultSet.getInt("numarExemplare");
                return nrExemplare;
            }
            System.out.println("error");
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("nu am putut prelua numarul de exemplare al cartii");
            return -1;
        }
    }

    public int[] getAllIds() {
        int[] ids = new int[100];
        int i = 0;
        try {
            String query = "select idCarte from carti";
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("idCarte");
                ids[i] = id;
                i++;
            }
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error here");
            return null;
        }
    }

    // create
    public boolean create(String titluCarte, int numarVolum, int idEditura, int numarPagini, String ISBN, int
        numarExemplare, boolean hardCover, int idAutor) {
        try {
            String query = String.format("insert into carti (titluCarte, numarVolum, idEditura, numarPagini, ISBN, " +
                "numarExemplare, hardCover, idAutor) values ('%s', %d, %d, %d, '%s', %d, %b, %d)",
                titluCarte, numarVolum, idEditura, numarPagini, ISBN, numarExemplare, hardCover, idAutor);
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

    // read
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
