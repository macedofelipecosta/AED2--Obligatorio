package tads;

public class Grafo {

    private Aeropuerto[] aeropuertos;
    private Conexion[][] conexiones;
    private final int maxConexiones;
    int cantidad = 0;

    public Grafo(int maxConexiones) {
        this.maxConexiones = maxConexiones;
        aeropuertos = new Aeropuerto[maxConexiones];
        conexiones = new Conexion[maxConexiones][maxConexiones];
    }

    public void agregarAeropuerto(String codigo, String nombre) {
        if (cantidad < maxConexiones) {
            int posLibre = obtenerPosLibre();
            aeropuertos[posLibre] = new Aeropuerto(codigo, nombre);
            cantidad++;
        }
    }

    public void borrarVertice(String codigo) {
        int posVaBorrar = buscarPos(new Aeropuerto(codigo));

        for (int i = 0; i < conexiones.length; i++) {
            conexiones[posVaBorrar][i] = null;
            conexiones[i][posVaBorrar] = null;
        }
        aeropuertos[posVaBorrar] = null;
        cantidad--;
    }

    public void agregarConexion(String vInicial, String vFinal, double kilometros) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial));
        int posVFinal = buscarPos(new Aeropuerto(vFinal));

        conexiones[posVInicial][posVFinal] = new Conexion(kilometros);
    }

    public void borrarConexion(String vInicial, String vFinal) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial));
        int posVFinal = buscarPos(new Aeropuerto(vFinal));

        conexiones[posVInicial][posVFinal] = null;
    }

    public Conexion obtenerConexion(String vInicial, String vFinal) {
        int posVInicial = buscarPos(new Aeropuerto(vInicial));
        int posVFinal = buscarPos(new Aeropuerto(vFinal));

        if (posVInicial == -1 || posVFinal == -1) {
            return null;
        } else {
            return conexiones[posVInicial][posVFinal];
        }

    }

    public boolean existeAeropuerto(String codigoAeropuerto) {
        int posABuscar = buscarPos(new Aeropuerto(codigoAeropuerto));
        return posABuscar >= 0;
    }

    public Lista<Aeropuerto> Adyacentes(String nombreAeropuerto) {
        Lista<Aeropuerto> adyacentes = new Lista<>();
        int posV = buscarPos(new Aeropuerto(nombreAeropuerto));

        for (int i = 0; i < conexiones.length; i++) {
            if (conexiones[posV][i] != null) {
                adyacentes.insertar(aeropuertos[i]);
            }
        }
        return adyacentes;
    }

    private int buscarPos(Aeropuerto a) {
        for (int i = 0; i < aeropuertos.length; i++) {
            if (aeropuertos[i] != null && aeropuertos[i].equals(a)) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosLibre() {
        for (int i = 0; i < aeropuertos.length; i++) {
            if (aeropuertos[i] == null) {
                return i;
            }
        }
        return -1;
    }


}
