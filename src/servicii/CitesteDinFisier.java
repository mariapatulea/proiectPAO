package servicii;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CitesteDinFisier {
    private static CitesteDinFisier instanta = null;
    private CitesteDinFisier() {}  // constructor privat

    public static CitesteDinFisier getInstance() {
        if (instanta == null) {
            instanta = new CitesteDinFisier();
        }
        return instanta;
    }

    // metoda care citeste dintr-un fisier csv si returneaza datele intr-o lista de liste
    public ArrayList<ArrayList<String>> citeste(String caleFisier) {
        ArrayList<ArrayList<String>> continutFisier = new ArrayList<ArrayList<String>>();

        try {
            File input = new File(caleFisier);
            Scanner scanner = new Scanner(input);
            int counter = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().strip().split(",");
                ArrayList<String> splittedLine = new ArrayList<String>(Arrays.asList(line));
                if (counter != 0) {
                    continutFisier.add(splittedLine);
                }
                counter++;
            }
            return continutFisier;
        } catch (FileNotFoundException e) {
            System.out.println("Calea: " + caleFisier + " este inexistenta.");
        }

        return null;
    }
}
