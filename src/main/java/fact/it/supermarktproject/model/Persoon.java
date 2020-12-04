package fact.it.supermarktproject.model;
//Wouter De Smet
// r0785520

public class Persoon {
    private String voornaam;
    private String familienaam;
    private int geboortejaar;

    public Persoon() {
    }

    public Persoon(String voornaam, String familienaam) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public void setFamilienaam(String familienaam) {
        this.familienaam = familienaam;
    }

    public int getGeboortejaar() {
        return geboortejaar;
    }

    public void setGeboortejaar(int geboortejaar) {
        this.geboortejaar = geboortejaar;
    }

    public String toString(){
        return getFamilienaam().toUpperCase(); //+ " " + getVoornaam();
    }

}
