package be.kuleuven.vrolijkezweters;

public class Etappe {
    private int EtappeId;
    private int Wedstrijdid;
    private String EtappeNummer;
    // float voor Begin- en Eind-locatie? Wordt zo momenteel gebruikt in database
    private float BeginLocatie;
    private float EindLocatie;
    private float EtappeAfstand;


    public int getEtappeId() {return EtappeId;
    }
    public int getWedstrijdid() {
        return Wedstrijdid;
    }
    public String getEtappeNummer() {
        return EtappeNummer;
    }
    public float getBeginLocatie() {
        return BeginLocatie;
    }
    public float getEindLocatie() {
        return EindLocatie;
    }
    public float getEtappeAfstand() {
        return EtappeAfstand;
    }

    public void setEtappeId(int etappeId){this.EtappeId = etappeId;}
    public void setWedstrijdid(int wedstrijdid){this.Wedstrijdid = wedstrijdid;}
    public void setEtappeNummer(String etappeNummer){this.EtappeNummer = etappeNummer;}
    public void setBeginLocatie(float beginLocatie){this.BeginLocatie = beginLocatie;}
    public void setEindLocatie(float eindLocatie){this.EindLocatie = eindLocatie;}
    public void setEtappeAfstand(float etappeAfstand){this.EtappeAfstand = etappeAfstand;}

}
