package tads;

public class Arista {
    public int kilometros;

    public Arista(int kilometros) {
        this.kilometros = kilometros;
    }

    public int getKilometros() {
        return kilometros;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }

    @Override
    public String toString() {
        return "Arista{" +
                "kilometros=" + kilometros +
                '}';
    }
}
