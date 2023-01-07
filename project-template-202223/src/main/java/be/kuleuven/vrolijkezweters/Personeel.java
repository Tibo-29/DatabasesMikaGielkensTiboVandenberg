package be.kuleuven.vrolijkezweters;

public class Personeel {
    private int PersoneelId;
    private String Naam;
    private String Functie;
    private String Vrijwilliger;
    private float LoonPerUur;
    private float LoonNogTeKrijgen;

    public int getPersoneelId() {return PersoneelId;}
    public String getNaam() {return Naam;}
    public String getFunctie() {return Functie;}
    public String getVrijwilliger() {return Vrijwilliger;}
    public float getLoonPerUur() {return LoonPerUur;}
    public float getLoonNogTeKrijgen() {return LoonNogTeKrijgen;}

    public void setPersoneelId(int personeelId) {this.PersoneelId = personeelId;}
    public void setNaam(String naam) {this.Naam = naam;}
    public void setFunctie(String functie) {this.Functie = functie;}
    public void setVrijwilliger(String vrijwilliger) {this.Vrijwilliger = vrijwilliger;}
    public void setLoonPerUur(float loonPerUur) {this.LoonPerUur = loonPerUur;}
    public void setLoonNogTeKrijgen(float loonNogTeKrijgen) {this.LoonNogTeKrijgen = loonNogTeKrijgen;}

}
