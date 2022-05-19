package servicii;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instanta = null;
    private Database() {}  // constructor private

    public static Database getInstance() {
        if (instanta == null) {
            instanta = new Database();
        }
        return instanta;
    }

    public Connection incarcaBazaDeDate() {
        try {
            // luam connection string-ul la baza de date
            Connection myConnectionString = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectPAO",
                    "root", "12345678");
            return myConnectionString;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
