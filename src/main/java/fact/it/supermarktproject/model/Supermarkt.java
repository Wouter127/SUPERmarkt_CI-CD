package fact.it.supermarktproject.model;
// Wouter De Smet
// r0785520

import java.util.ArrayList;

public class Supermarkt {
    private String naam;
    private int aantalKlanten;
    private ArrayList<Afdeling> afdelingen = new ArrayList<>();

    public Supermarkt(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ArrayList<Afdeling> getAfdelingen() {
        return afdelingen;
    }

    public int getAantalAfdelingen() {
        return afdelingen.size();
    }

    public void voegAfdelingToe(Afdeling afdeling) {
        afdelingen.add(afdeling);
    }

    public Afdeling zoekAfdelingOpNaam(String naam) {
        for (Afdeling afdeling : afdelingen) {
            if (afdeling.getNaam().equals(naam)) {
                return afdeling;
            }
        }
        return null;
    }

    public void registreerKlant(Klant klant) {
        aantalKlanten += 1;
        klant.setKlantenkaartnr(aantalKlanten);
    }
}
