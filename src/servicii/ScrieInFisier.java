package servicii;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ScrieInFisier {
    private static ScrieInFisier instanta = null;
    private ScrieInFisier() {}  // constructor privat

    public static ScrieInFisier getInstance() {
        if (instanta == null) {
            instanta = new ScrieInFisier();
        }
        return instanta;
    }

    // metoda care introduce valorile din lista in fisierul csv
    public void scrie(String caleFisier, List<String> lista) {
        File output = new File(caleFisier);

        try {
            if(!output.exists()) {
                output.createNewFile();
            }

            FileWriter writer = new FileWriter(output, true);
            StringBuilder continut = new StringBuilder();
            for (String str: lista) {
                continut.append(str);
                continut.append(',');
            }
            continut.deleteCharAt(continut.length() - 1);  // stergem ultima virgula adaugata
            continut.append('\n');  // new line

            writer.write(continut.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("eroare");
            e.printStackTrace();
        }
    }
}
