package tads;

import entidades.Vuelo;

import java.util.Iterator;
import java.util.Objects;

public class Conexion {
    public final double kilometros;
   final private Lista<Vuelo> vuelos;
    final private Lista<String> codigosAerolineas;
    final private Lista<Integer> listaMinutos;


    public Conexion(double kilometros) {
        this.kilometros = kilometros;
        vuelos = new Lista<Vuelo>();
        codigosAerolineas = new Lista<String>();
        listaMinutos= new Lista<Integer>();

    }

    public Lista<Integer> getListaMinutos() {
        return listaMinutos;
    }
    public Lista<Vuelo> getVuelos() {
        return vuelos;
    }

    public Lista<String> getCodigosAerolineas() {
        return codigosAerolineas;
    }

    public int devolverTiempoMenor() {
        if (this.listaMinutos.esVacia()) {
            return Integer.MAX_VALUE; // cualquier valor que indique que la lista está vacía
        }

        int menor = Integer.MAX_VALUE;
        Iterator<Integer> it = listaMinutos.iterator();

        while (it.hasNext()) {
            int valor = it.next();
            if (valor < menor) {
                menor = valor;
            }
        }

        return menor;
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
