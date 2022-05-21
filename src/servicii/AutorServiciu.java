package servicii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AutorServiciu {
    private static AutorServiciu instanta = null;
    private final Database bazaDeDate = Database.getInstance();

    // constructor privat
    private AutorServiciu() { }

    public static AutorServiciu getInstance() {
        if (instanta == null)
            instanta = new AutorServiciu();
        return instanta;
    }

    public String getPrenumeById(int id) {
        try {
            String query = String.format("select prenume from autori where idAutor = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            String prenume = "";
            while (resultSet.next()) {
                prenume = resultSet.getString("prenume");
            }
            return prenume;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getNumeById(int id) {
        try {
            String query = String.format("select nume from autori where idAutor = %d", id);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            String nume = "";
            while (resultSet.next()) {
                nume = resultSet.getString("nume");
            }
            return nume;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getIdByLastName (String nume) {
        try {
            String query = String.format("select idAutor from autori where nume = '%s'", nume);
            Connection connection = bazaDeDate.incarcaBazaDeDate();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = -1;
            while (resultSet.next()) {
                id = resultSet.getInt("idAutor");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
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
            return null;
        }
    }

    // create
    public boolean create(String prenume, String nume) {
        try {
            String query = String.format("insert into autori (prenume, nume) values ('%s', '%s')", prenume,
                    nume);
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
            String query = "select * from autori order by idAutor";
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
    public boolean update(int id, String nume) {
        try {
            String query = String.format("update autori set nume = '%s' where idAutor = %d", nume, id);
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
            String query = String.format("delete from autori where idAutor = %d", id);
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
