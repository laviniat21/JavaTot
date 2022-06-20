public class Doctor {
    private int codDoctor;
    private String nume;
    private int codSectie;

    public Doctor(int codDoctor, String nume, int codSectie) {
        this.codDoctor = codDoctor;
        this.nume = nume;
        this.codSectie = codSectie;
    }

    public int getCodDoctor() {
        return codDoctor;
    }

    public void setCodDoctor(int codDoctor) {
        this.codDoctor = codDoctor;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCodSectie() {
        return codSectie;
    }

    public void setCodSectie(int codSectie) {
        this.codSectie = codSectie;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "codDoctor=" + codDoctor +
                ", nume='" + nume + '\'' +
                ", codSectie=" + codSectie +
                '}';
    }
}
