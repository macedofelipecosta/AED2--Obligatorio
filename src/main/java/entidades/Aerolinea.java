package entidades;

import java.util.Objects;

public class Aerolinea implements Comparable<Aerolinea> {

    private final String codigo;
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public Aerolinea(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Aerolinea(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aerolinea aerolinea = (Aerolinea) o;
        return Objects.equals(codigo, aerolinea.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre;
    }

    /**
     *
     **/
    @Override
    public int compareTo(Aerolinea otraAerolinea) {
        // Comparar los códigos basados en los dígitos
        String codigo1 = this.getCodigo();
        String codigo2 = otraAerolinea.getCodigo();

        return codigo1.compareTo(codigo2);
    }


}
