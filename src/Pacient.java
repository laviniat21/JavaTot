public class Pacient implements Cloneable, Comparable<Pacient>
{
    private int codPacient;
    private String nume;
    private int varsta;
    private int codDoctor;

    public Pacient(int codPacient, String nume, int varsta, int codDoctor) {
        this.codPacient = codPacient;
        this.nume = nume;
        this.varsta = varsta;
        this.codDoctor = codDoctor;
    }

    public int getCodPacient() {
        return codPacient;
    }

    public void setCodPacient(int codPacient) {
        this.codPacient = codPacient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCodDoctor() {
        return codDoctor;
    }

    public void setCodDoctor(int codDoctor) {
        this.codDoctor = codDoctor;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "codPacient=" + codPacient +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", codDoctor=" + codDoctor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pacient pacient = (Pacient) o;
        return codPacient == pacient.codPacient;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Pacient clona=(Pacient)super.clone();
        clona.codPacient=this.codPacient;
        clona.nume=this.nume;
        clona.varsta=this.varsta;
        clona.codDoctor=this.codDoctor;
        return clona;
    }

    @Override
    public int compareTo(Pacient pacient) {
        if(this.varsta<pacient.varsta)
            return 1;
        else
            return 0;
    }
}
