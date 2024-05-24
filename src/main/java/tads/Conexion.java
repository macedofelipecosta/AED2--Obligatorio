package tads;

import entidades.Vuelo;

import java.util.Objects;

public class Conexion {
    public double kilometros;

    private Lista<Vuelo> vuelos;
    private Lista<String> codigosAerolineas;

    public Conexion(double kilometros) {
        this.kilometros = kilometros;
        vuelos = new Lista<Vuelo>();
        codigosAerolineas = new Lista<String>();

    }

    public Lista<Vuelo> getVuelos() {
        return vuelos;
    }
    public Lista<String> getCodigosAerolineas(){
        return codigosAerolineas;
    }
    public double getKilometros() {
        return kilometros;
    }
    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "kilometros=" + kilometros +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conexion conexion)) return false;
        return Double.compare(kilometros, conexion.kilometros) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kilometros);
    }
}
