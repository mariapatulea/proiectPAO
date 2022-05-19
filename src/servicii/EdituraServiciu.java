package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EdituraServiciu {
    private static EdituraServiciu instanta = null;
    private final BazaDeDate bazaDeDate = BazaDeDate.getInstance();

    // constructor privat
    private EdituraServiciu() { }

    public static EdituraServiciu getInstance() {
        if (instanta == null)
            instanta = new EdituraServiciu();
        return instanta;
    }

    public String getDenumireById(int id) {
        try {
            String query = String.format("select * from edituri where idEditura = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            String denumire = "";
            while (resultSet.next()) {
                denumire = resultSet.getString("denumire");
            }
            return denumire;

        } catch (Exception e) {
            return "";
        }
    }

    // create
    public boolean create(String denumire) {
        try {
            String query = String.format("insert into edituri (denumire) values ('%s')", denumire);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Create' realizat cu succes!");
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
            String query = "select * from edituri order by idEditura";
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("'Select' realizat cu succes!");
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
    public boolean update(String denumire, int id) {
        try {
            String query = String.format("update edituri set idEditura = %d where denumire = '%s'", id, denumire);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Update' realizat cu succes!");
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
            String query = String.format("delete from edituri where idEditura = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("'Delete' realizat cu succes!");
            statement.close();
            connection.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
