package fact.it.supermarktproject.model;
// Wouter De Smet
// r0785520

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personeelslid extends Persoon {
    private LocalDate inDienstSinds = LocalDate.now();
    private double wedde;

    public Personeelslid(String voornaam, String familienaam) {
        super(voornaam, familienaam);
    }

    public LocalDate getInDienstSinds() {
        return inDienstSinds;
    }

    public void setInDienstSinds(LocalDate inDienstSinds) {
        this.inDienstSinds = inDienstSinds;
    }

    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return "Personeelslid " + getFamilienaam().toUpperCase() + " " + getVoornaam() + " is in dienst sinds " + getInDienstSinds().format(dtf);
    }

    public double getWedde() {
        return wedde;
    }

    public void setWedde(double wedde) {
        this.wedde = wedde;
    }
}
