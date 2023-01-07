package be.kuleuven.vrolijkezweters;

public class Wedstrijden {

    private int WedstijdId;
    private float Afstand;
    private String AantalEtappes;
    private float Inschrijvingsgeld;
    private String Datum;
    private String Locatie;

    public int getWedstijdId(){return WedstijdId;}
    public float getAfstand(){return Afstand;}
    public String getAantalEtappes(){return AantalEtappes;}
    public float getInschrijvingsgeld(){return Inschrijvingsgeld;}
    public String getDatum(){return Datum;}
    public String getLocatie(){return Locatie;}

    public void setWedstijdId(int wedstijdId) {this.WedstijdId = wedstijdId;}
    public void setAfstand(float afstand) {this.Afstand = afstand;}
    public void setAantalEtappes(String aantalEtappes) {this.AantalEtappes = aantalEtappes;}
    public void setInschrijvingsgeld(float inschrijvingsgeld) {this.Inschrijvingsgeld = inschrijvingsgeld;}
    public void setDatum(String datum) {this.Datum = datum;}
    public void setLocatie(String locatie) {this.Locatie = locatie;}

}
