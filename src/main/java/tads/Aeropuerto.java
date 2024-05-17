package tads;

import java.util.Objects;

public class Aeropuerto {
    private String codigo;
    private String nombre;

    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre=nombre;
    }
    public Aeropuerto(String codigo){
        this.codigo=codigo;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aeropuerto aeropuerto = (Aeropuerto) o;
        return Objects.equals(codigo, aeropuerto.codigo);
    }

    @Override
    public String toString() {
        return codigo +";"+ nombre;
    }


    public int compareTo(Aeropuerto otroAeropuerto) {
        String codigo1 = this.getCodigo();
        String codigo2 = otroAeropuerto.getCodigo();
        int comparacion = codigo1.compareTo(codigo2);

        return comparacion;
    }
}
