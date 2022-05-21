package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CititorServiciu {
    private static CititorServiciu instanta = null;
    private final Database bazaDeDate = Database.getInstance();

    // constructor privat
    private CititorServiciu() { }

    public static CititorServiciu getInstance() {
        if (instanta == null)
            instanta = new CititorServiciu();
        return instanta;
    }

    public int[] getAllIds() {
        int[] ids = new int[100];
        int i = 0;
        try {
            String query = "select idCititor from cititori";
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("idCititor");
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
    public boolean create(String prenume, String nume, String tip) {
        try {
            String query = String.format("insert into cititori (prenume, nume, tip) values ('%s', '%s', '%s')", prenume,
                    nume, tip);
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
            String query = "select * from cititori order by idCititor";
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
    public boolean update(String tip, int id) {
        try {
            String query = String.format("update cititori set tip = '%s' where idCititor = %d", tip, id);
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
            String query = String.format("delete from cititori where idCititor = %d", id);
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
