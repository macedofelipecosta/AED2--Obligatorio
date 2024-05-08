package entidades;

import java.util.Objects;

public class Aerolinea implements Comparable<Aerolinea> {

    private String codigo;
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * **/
    @Override
    public int compareTo(Aerolinea otraAerolinea) {
        // Comparar los códigos basados en los primeros dos caracteres
        String codigo1 = this.getCodigo().substring(0, 2); // ocasiona error de desborde para cdigos de 1 digito
        String codigo2 = otraAerolinea.getCodigo().substring(0, 2);
        int comparacion = codigo1.compareTo(codigo2);

        // Si los dos primeros caracteres son iguales, comparar los números
        if (comparacion == 0) {
            // Obtener los números después de los dos primeros caracteres
            int num1 = Integer.parseInt(this.getCodigo().substring(2));
            int num2 = Integer.parseInt(otraAerolinea.getCodigo().substring(2));
            // Comparar los números
            comparacion = Integer.compare(num1, num2);
        }
        return comparacion;
    }
}
