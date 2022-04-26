import entitati.*;
import servicii.Serviciu;

import java.util.*;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        // 8 tipuri de obiecte diferite
        Agentie aPWatt = new Agentie("A P Watt");
        Agent emilySmith = new Agent("Emily", "Smith", aPWatt);
        Set<Carte> cartiAlexandraBracken = new HashSet<>();
        Editura hyperion = new Editura("Hyperion");
        Editor robLowe = new Editor("Rob", "Lowe", hyperion);
        Autor alexandraBracken = new Autor("Alexandra", "Bracken", emilySmith, robLowe, cartiAlexandraBracken);
        Carte lore = new Carte("Lore", alexandraBracken, 1, hyperion, 480, "1484778200",
                13, true);
        cartiAlexandraBracken.add(lore);

        Set<Carte> cartiBrigidKemmerer = new HashSet<>();
        Editura bloomsburyYA = new Editura("Bloomsbury YA");
        Editor clareMoss = new Editor("Clare", "Moss", bloomsburyYA);
        Autor brigidKemmerer = new Autor("Brigid", "Kemmerer", emilySmith, clareMoss,
                cartiBrigidKemmerer);
        Carte defyTheNight = new Carte("Defy the Night", brigidKemmerer, 1, bloomsburyYA,
                448, "1547604670", 5, true);
        cartiBrigidKemmerer.add(defyTheNight);

        Carte defendTheDawn = new Carte("Defend the Dawn", brigidKemmerer, 2, bloomsburyYA,
                496, "9781547610082", 8, false);
        cartiBrigidKemmerer.add(defendTheDawn);

//        brigidKemmerer.afisare();

//        lore.afisare();

//        lore.getAutor().afisare();

        List<Carte> cartiImprMariaPatulea = new ArrayList<Carte>();
        cartiImprMariaPatulea.add(lore);
        cartiImprMariaPatulea.add(defendTheDawn);
        lore.setNumarExemplare(lore.getNumarExemplare() - 1);
        defendTheDawn.setNumarExemplare(defendTheDawn.getNumarExemplare() - 1);
        Cititor mariaPatulea = new Cititor("Maria", "Pătulea", 1, cartiImprMariaPatulea);
//        mariaPatulea.afisare();

        List<Carte> cartiFictiune = new ArrayList<Carte>();
        cartiFictiune.add(lore);
        cartiFictiune.add(defyTheNight);
        cartiFictiune.add(defendTheDawn);
        Sectiune fiction = new Sectiune("Fiction (Fictiune)", cartiFictiune);

        List<Carte> cartiFantasy = new ArrayList<Carte>();
        cartiFantasy.add(lore);
        Subsectiune fantasy = new Subsectiune("Fantasy (Fantezie)", cartiFantasy);
        

//        fiction.afisare();
//        fantasy.afisare();

        List<Carte> cartiImprAlexIonescu = new ArrayList<Carte>();
        cartiImprAlexIonescu.add(lore);
        lore.setNumarExemplare(lore.getNumarExemplare() - 1);
        Guest alexIonescu = new Guest("Alex", "Ionescu", 2, cartiImprAlexIonescu);
        alexIonescu.imprumuta(defendTheDawn);  // ok
        alexIonescu.imprumuta(defyTheNight);  // nu se mai poate imprumta
        alexIonescu.returneaza(lore);  // ok
        alexIonescu.imprumuta(defyTheNight);  // ok
        System.out.println();

        // afisare comenzi pentru actiuni
        Scanner console = new Scanner(System.in);
        Serviciu serviciu = new Serviciu();
        List<String> comenziDisponibile = Arrays.asList("adaugare_cititor_membru", "adaugare_carte", "afisare_carti",
                "afisare_inventar", "afisare_carti_imprumutate", "afisare_carti_autor", "adaugare_cititor_guest",
                "returnare_carte", "imprumutare_carte", "adauagare_autor", "adaugare_editura");

        System.out.println("Acestea sunt comenzile disponibile:");
        for(int i = 0; i < comenziDisponibile.size(); i++) {
            System.out.println((i + 1) + "). " + comenziDisponibile.get(i));
        }
        System.out.println("\nIntroduce un numar intre 1 - 11 pentru a alege una din aceste comenzi: ");
        String comanda = console.next();
        System.out.println("\nAi ales comanda " + comenziDisponibile.get((parseInt(comanda)) - 1) + ".");

        // realizare actiuni
        try {
            switch (comanda) {
                case "1": {
                    serviciu.adaugare_cititor_membru(console);
                    break;
                }
                case "2": {
                    serviciu.adaugare_carte(console);
                    break;
                }
                case "3": {
                    serviciu.afisare_carti();
                    break;
                }
                case "4": {
                    serviciu.afisare_inventar();
                    break;
                }
                case "5": {
                    serviciu.afisare_carti_imprumutate();
                    break;
                }
                case "6": {
                    serviciu.afisare_carti_autor(console);
                    break;
                }
                case "7": {
                    serviciu.adaugare_cititor_guest(console);
                    break;
                }
                case "8": {
                    serviciu.returnare_carte(console);
                    break;
                }
                case "9": {
                    serviciu.imprumutare_carte(console);
                    break;
                }
                case "10": {
                    serviciu.adaugare_autor(console);
                    break;
                }
                case "11": {
                    serviciu.adaugare_editura(console);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
