package tads;

import entidades.*;

public class Grafo {

    private Aeropuerto[] aeropuertos;
    private Conexion[][] conexiones;
    private final int maxAeropuertos;
    int cantidad = 0;

    public Grafo(int maxAeropuertos) {
        this.maxAeropuertos = maxAeropuertos;
        aeropuertos = new Aeropuerto[maxAeropuertos];
        conexiones = new Conexion[maxAeropuertos][maxAeropuertos];
    }

    public void agregarAeropuerto(String codigo, String nombre) {
        if (cantidad < maxAeropuertos) {
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

    public Lista<Aeropuerto> Adyacentes(String codigoAeropuerto) {
        Lista<Aeropuerto> adyacentes = new Lista<>();
        int posV = buscarPos(new Aeropuerto(codigoAeropuerto));

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

    public String listadoAeropuertosCantEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        int posicionInicial= buscarPos(new Aeropuerto(codigoAeropuertoOrigen));
        boolean[] visitados = new boolean[maxAeropuertos];
        Cola<Integer> cola = new Cola<>();
        String resultado="";
        int saltos=cantidad;

        cola.encolar(posicionInicial);
        visitados[posicionInicial]=true;

            while (!cola.esVacia()) {
                int pos = cola.desencolar();
                resultado = resultado + aeropuertos[pos].toString();
                System.out.println(aeropuertos[pos]);
                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[posicionInicial][i] != null && !visitados[i]) {
                        cola.encolar(i);
                        visitados[i] = true;

                    }
                }
            }

        return resultado;
    }

}
