package servicii;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BazaDeDate {
    private static BazaDeDate instanta = null;
    private BazaDeDate() {}  // constructor private

    public static BazaDeDate getInstance() {
        if (instanta == null) {
            instanta = new BazaDeDate();
        }
        return instanta;
    }

    public Connection incarcaBazaDeDate() {
        try {
            // luam connection string-ul la baza de date
            Connection myConnectionString = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectPAO",
                    "root", "Cat&Bones2019");
            return myConnectionString;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
