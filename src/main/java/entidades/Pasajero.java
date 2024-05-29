package entidades;

import interfaz.Categoria;

import java.util.Objects;

public class Pasajero implements Comparable<Pasajero> {
    private final String cedula;
    private String nombre;
    private String telefono;
    private Categoria categoria;

    public Pasajero(String cedula){
        this.cedula=cedula;
        this.nombre=null;
        this.telefono=null;
        this.categoria=null;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return Objects.equals(this.cedula, pasajero.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return cedula+";" + nombre+";" + telefono+";" + categoria.getTexto();
    }

    @Override
    public int compareTo(Pasajero o) {
        String thisCedula = cedula.replaceAll("[^0-9]", "");
        String recivedCi = o.cedula.replaceAll("[^0-9]", "");

        int ci= Integer.parseInt(thisCedula);
        int recive= Integer.parseInt(recivedCi);

        return Integer.compare(ci,recive);
    }


}
