package be.kuleuven.vrolijkezweters;

public class Loper {
    private int LoperId;
    private String Naam;
    private String Leeftijd;
    private String Gewicht;
    private float TotaleLooptijd;
    private float TotaleAfstand;


    public int getLoperId() {return LoperId;}
    public String getNaam() {
        return Naam;
    }
    public String getLeeftijd() {
        return Leeftijd;
    }
    public String getGewicht() {
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
    public void setLeeftijd(String leeftijd){this.Leeftijd = leeftijd;}
    public void setGewicht(String gewicht){this.Gewicht = gewicht;}
    public void setTotaleLooptijd(float totaleLooptijd){this.TotaleLooptijd = totaleLooptijd;}
    public void setTotaleAfstand(float totaleAfstand){this.TotaleAfstand = totaleAfstand;}

}
