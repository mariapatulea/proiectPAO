package entitati;

import java.util.List;

public class Membru extends Cititor {
    // constructor
    public Membru(String prenume, String nume, int idCititor, List<Carte> cartiImprumutate) {
        super(prenume, nume, idCititor, cartiImprumutate);
    }

    public void imprumuta (Carte carte) {
        if(getCartiImprumutate().size() >= 10) {
            System.out.println("A fost atinsă limita de cărți împrumutate pentru cititor membru. Returnează una din " +
                    "cărți pentru a împrumuta.");
        } else if(carte.getNumarExemplare() >= 1) {
            List<Carte> cartiActuale = getCartiImprumutate();
            cartiActuale.add(carte);
            setCartiImprumutate(cartiActuale);
            System.out.println("Ai împrumutat încă o carte. Mai poți împrumuta " + (10 - cartiActuale.size()) +
                    "cărți.");
            carte.setNumarExemplare(carte.getNumarExemplare() - 1);
        } else {
            System.out.println("Cartea " + carte.getTitluCarte() + " nu poate fi împrumutată pentru că nu mai este " +
                    "disponibilă");
        }
    }

    public void returneaza (Carte carte) {
        if(!getCartiImprumutate().contains(carte)) {
            System.out.println("Cartea " + carte.getTitluCarte() + " nu poate fi returnată pentru că nu a fost " +
                    "împrumutată de acest cititor.");
        } else {
            List<Carte> cartiActuale = getCartiImprumutate();
            cartiActuale.remove(carte);
            setCartiImprumutate(cartiActuale);
            System.out.println("A fost returnată o carte. Cititorul mai are " + cartiActuale.size() + " cărți " +
                    "împrumutate.");
            carte.setNumarExemplare(carte.getNumarExemplare() + 1);
        }
    }
}
