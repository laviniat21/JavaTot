import java.io.Serializable;

public class Specializare implements Serializable {
    private int codSectie;
    private String nume;
    private int etaj;
    private int nrLocuri;

    public Specializare(int codSectie, String nume, int etaj, int nrLocuri) {
        this.codSectie = codSectie;
        this.nume = nume;
        this.etaj = etaj;
        this.nrLocuri = nrLocuri;
    }

    public int getCodSectie() {
        return codSectie;
    }
    public void setCodSectie(int codSectie) {
        this.codSectie = codSectie;
    }
    public String getNume() {
        return nume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public int getEtaj() {
        return etaj;
    }
    public void setEtaj(int etaj) {
        this.etaj = etaj;
    }
    public int getNrLocuri() {
        return nrLocuri;
    }
    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Sectii{" +
                "codSectie=" + codSectie +
                ", nume='" + nume + '\'' +
                ", etaj=" + etaj +
                ", nrLocuri=" + nrLocuri +
                '}';
    }



}
