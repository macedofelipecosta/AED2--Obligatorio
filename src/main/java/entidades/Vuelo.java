package entidades;

import java.util.Objects;

public class Vuelo implements Comparable<Vuelo>{

    private final String codigoVuelo;
    private double minutos;

    public Vuelo(String codigoVuelo, double minutos) {
        this.codigoVuelo = codigoVuelo;
        this.minutos = minutos;
    }

    public Vuelo(String codigoVuelo) {
        this.codigoVuelo = codigoVuelo;
    }

    public String getCodigoVuelo() {
        return codigoVuelo;
    }

    public double getMinutos() {
        return minutos;
    }


    @Override
    public int compareTo(Vuelo v) {
        String codigo1 = this.getCodigoVuelo();
        String codigo2 = v.getCodigoVuelo();

        return codigo1.compareTo(codigo2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vuelo vuelo = (Vuelo) o;
        return Objects.equals(this.codigoVuelo, vuelo.codigoVuelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoVuelo);
    }


}
