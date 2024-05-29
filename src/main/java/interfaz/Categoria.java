package interfaz;

public enum Categoria {
    PLATINO("Platino"),
    FRECUENTE("Frecuente"),
    ESTANDAR("Estándar");

    private final String texto;

    Categoria(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

}
