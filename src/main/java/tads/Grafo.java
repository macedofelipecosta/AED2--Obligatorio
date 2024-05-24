package tads;

import java.util.Arrays;

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
        int posicionInicial = buscarPos(new Aeropuerto(codigoAeropuertoOrigen));
        boolean[] visitados = new boolean[maxAeropuertos];
        Cola<Integer> cola = new Cola<>();
        String resultado = "";
        int pos;
        int escalas = 0;
        cola.encolar(posicionInicial);
        visitados[posicionInicial] = true;


        while (!cola.esVacia()) {
            pos = cola.desencolar();

            resultado = resultado + aeropuertos[pos].toString();
            System.out.println(aeropuertos[pos]);

            for (int i = 0; i < conexiones.length; i++) {
                if (conexiones[posicionInicial][i] != null && !visitados[i]) {

                    Lista<String> aux = conexiones[posicionInicial][i].getCodigosAerolineas();
                    if (aux.existe(codigoAerolinea)) {
                        cola.encolarOrdenado(i);
                        visitados[i] = true;
                    }

                }
            }

            if (escalas <= cantidad && pos != 0 && escalas != 1) {
                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        Lista<String> aux = conexiones[pos][i].getCodigosAerolineas();
                        if (aux.existe(codigoAerolinea)) {
                            cola.encolarOrdenado(i);
                            visitados[i] = true;
                        }
                    }
                }

            }
            escalas++;
        }
        int lastIndex = resultado.lastIndexOf('|');
        if (lastIndex != -1) {
            resultado = resultado.substring(0, lastIndex) + resultado.substring(lastIndex + 1);
        }
        return resultado;
    }

    public double costoMinimoKilometrosDouble(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] costos = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];

        for (int i = 0; i < maxAeropuertos; i++) {
            costos[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        costos[posVOrigen] = 0;


        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(costos, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        double distanciaNueva = costos[pos] + conexiones[pos][i].kilometros;
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva;
                            vengo[i] = aeropuertos[pos].toString();
                        }
                    }
                }
            }
        }

        return costos[posVDestino];
    }

    public String costoMinimoKilometrosTxt(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] costos = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];
        String respuesta = "";

        for (int i = 0; i < maxAeropuertos; i++) {
            costos[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        costos[posVOrigen] = 0;
        vengo[0] = aeropuertos[posVOrigen].toString();
        vengo[maxAeropuertos-1] = aeropuertos[posVDestino].toString();

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(costos, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        double distanciaNueva = costos[pos] + conexiones[pos][i].kilometros;
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva;
                            vengo[i] = aeropuertos[pos].toString();
                        }
                    }
                }
            }
        }
        String[] vengoSinDuplicados = Arrays.stream(vengo).distinct().toArray(String[]::new);
        for (int i = 0; i < vengoSinDuplicados.length; i++) {
            if (vengoSinDuplicados[i] != null) {
                respuesta = respuesta + vengoSinDuplicados[i];
            }
        }
        int lastIndex = respuesta.lastIndexOf('|');
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }
        return respuesta;
    }

    private int obtenerSiguenteVerticeNoVisitadoDeMenorCosto(double[] costos, boolean[] visitados) {
        int posMin = -1;
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < maxAeropuertos; i++) {
            if (!visitados[i] && costos[i] < min) {
                min = costos[i];
                posMin = i;
            }
        }
        return posMin;
    }

    public double costoMinimoMinutosDouble(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] tiempo = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];

        for (int i = 0; i < maxAeropuertos; i++) {
            tiempo[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        tiempo[posVOrigen] = 0;


        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(tiempo, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                       double minutos=0;
                        if (conexiones[pos][i].getVuelos().devolverPerimero()!=null){
                            minutos= conexiones[pos][i].getVuelos().devolverPerimero().getMinutos();
                        }
                        double nuevoTiempo = tiempo[pos] + minutos;
                        if (nuevoTiempo < tiempo[i]) {
                            tiempo[i] = nuevoTiempo;
                            vengo[i] = aeropuertos[pos].toString();
                        }
                    }
                }
            }
        }

        return tiempo[posVDestino];
    }

    public String costoMinimoMinutosTxt(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] tiempo = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];
        String respuesta = "";

        for (int i = 0; i < maxAeropuertos; i++) {
            tiempo[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        tiempo[posVOrigen] = 0;
        vengo[0] = aeropuertos[posVOrigen].toString();
        vengo[maxAeropuertos-1] = aeropuertos[posVDestino].toString();

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(tiempo, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        double minutos=0;
                        if (conexiones[pos][i].getVuelos().devolverPerimero()!=null){
                            minutos= conexiones[pos][i].getVuelos().devolverPerimero().getMinutos();

                            double nuevoTiempo = tiempo[pos] + minutos;
                            if (nuevoTiempo < tiempo[i]) {
                                tiempo[i] = nuevoTiempo;
                                vengo[i] = aeropuertos[pos].toString();
                            }
                        }

                    }
                }
            }
        }
        String[] vengoSinDuplicados = Arrays.stream(vengo).distinct().toArray(String[]::new);
        for (int i = 0; i < vengoSinDuplicados.length; i++) {
            if (vengoSinDuplicados[i] != null) {
                respuesta = respuesta + vengoSinDuplicados[i];
            }
        }
        int lastIndex = respuesta.lastIndexOf('|');
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }
        return respuesta;
    }

}
