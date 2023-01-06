package be.kuleuven.vrolijkezweters;

public class Loper {
    private int LoperId;
    private String Naam;
    private int Leeftijd;
    private int Gewicht;
    private float TotaleLooptijd;
    private float TotaleAfstand;


    public int getLoperId() {
        return LoperId;
    }
    public String getNaam() {
        return Naam;
    }
    public int getLeeftijd() {
        return Leeftijd;
    }
    public int getGewicht() {
        return Gewicht;
    }
    public float getTotaleLooptijd() {
        return TotaleLooptijd;
    }
    public float getTotaleAfstand() {
        return TotaleAfstand;
    }

    public void setLoperId(int loperId){this.LoperId = loperId;}
    public void setNaam(String naam){this.Naam = naam;}
    public void setLeeftijd(int leeftijd){this.Leeftijd = leeftijd;}
    public void setGewicht(int gewicht){this.Gewicht = gewicht;}
    public void setTotaleLooptijd(float totaleLooptijd){this.TotaleLooptijd = totaleLooptijd;}
    public void setTotaleAfstand(float totaleAfstand){this.TotaleAfstand = totaleAfstand;}

}
