package tads;

public class Arista {
    public double kilometros;

    public Arista(double kilometros) {
        this.kilometros = kilometros;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    @Override
    public String toString() {
        return "Arista{" +
                "kilometros=" + kilometros +
                '}';
    }
}
