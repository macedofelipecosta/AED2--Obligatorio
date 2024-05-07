package tads;

import java.util.Objects;

public class Vertice {
    private String codigo;

    public Vertice(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(codigo, vertice.codigo);
    }

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "codigo='" + codigo + '\'' +
                '}';
    }
}
