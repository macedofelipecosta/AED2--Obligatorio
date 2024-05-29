package tads;

import entidades.Objeto;
import entidades.Vuelo;

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

    public Objeto costoMinimoKilometros(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] costos = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];

        Cola<Aeropuerto> vengoP = new Cola<Aeropuerto>();

        String respuesta = "";

        for (int i = 0; i < maxAeropuertos; i++) {
            costos[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        costos[posVOrigen] = 0;
        vengo[maxAeropuertos - 1] = aeropuertos[posVDestino].toString();

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(costos, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        double distanciaNueva = costos[pos] + conexiones[pos][i].kilometros;
                        if (distanciaNueva < costos[i]) {
                            costos[i] = distanciaNueva;
                            vengo[i] = aeropuertos[pos].getCodigo();
                            vengoP.encolar(aeropuertos[pos]);

                        }
                    }
                }
            }
        }
        String[] vengoSinDuplicadosAux = Arrays.stream(vengo).distinct().toArray(String[]::new);
        String[] aux = new String[vengoP.getLargo() + 1];
        int i = 0;
        while (!vengoP.esVacia()) {
            Aeropuerto aeropuerto = vengoP.desencolar();
            for (int j = 0; j < vengoSinDuplicadosAux.length; j++) {
                if (aeropuerto.getCodigo() == vengoSinDuplicadosAux[j]) {
                    aux[i] = aeropuerto.toString();
                }
            }

            //aux[i] = vengoP.desencolar().toString();
            i++;
        }
        aux[i] = aeropuertos[posVDestino].toString();


        String[] vengoSinDuplicados = Arrays.stream(aux).distinct().toArray(String[]::new);

        for (int z = 0; z < vengoSinDuplicados.length; z++) {
            if (vengoSinDuplicados[z] != null) {
                respuesta = respuesta + vengoSinDuplicados[z];
            }
        }
        int lastIndex = respuesta.lastIndexOf('|'); // elimina la ultima barra recta
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }

        Objeto r = new Objeto(respuesta, (int) costos[posVDestino]);
        return r;
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

    public Objeto costoMinimoMinutos(String codigoOrigen, String codigoDestino) {
        int posVOrigen = buscarPos(new Aeropuerto(codigoOrigen));
        int posVDestino = buscarPos(new Aeropuerto(codigoDestino));

        boolean[] visitados = new boolean[maxAeropuertos];
        double[] tiempos = new double[maxAeropuertos];
        String[] vengo = new String[maxAeropuertos];

        Cola<Aeropuerto> vengoP = new Cola<Aeropuerto>();

        String respuesta = "";

        for (int i = 0; i < maxAeropuertos; i++) {
            tiempos[i] = Integer.MAX_VALUE;
            visitados[i] = false;
        }
        tiempos[posVOrigen] = 0;
        vengo[maxAeropuertos - 1] = aeropuertos[posVDestino].getCodigo();

        for (int v = 0; v < cantidad; v++) {
            int pos = obtenerSiguenteVerticeNoVisitadoDeMenorCosto(tiempos, visitados);

            if (pos != -1) {
                visitados[pos] = true;

                for (int i = 0; i < conexiones.length; i++) {
                    if (conexiones[pos][i] != null && !visitados[i]) {
                        int menorTiempo = conexiones[pos][i].devolverTiempoMenor();
                        double tiempoNuevo = tiempos[pos] + menorTiempo;
                        if (tiempoNuevo < tiempos[i]) {
                            tiempos[i] = tiempoNuevo;
                            vengo[i] = aeropuertos[pos].getCodigo();
                            vengoP.encolar(aeropuertos[pos]); // me sale el aeropuerto 2 porque me lo encola y despues no lo saca

                        }
                    }
                }
            }
        }

        String[] vengoSinDuplicadosAux = Arrays.stream(vengo).distinct().toArray(String[]::new);
        String[] aux = new String[vengoP.getLargo() + 1];
        int i = 0;
        while (!vengoP.esVacia()) {
            Aeropuerto aeropuerto = vengoP.desencolar();
            for (int j = 0; j < vengoSinDuplicadosAux.length; j++) {
                if (aeropuerto.getCodigo() == vengoSinDuplicadosAux[j]) {
                    aux[i] = aeropuerto.toString();
                }
            }

            //aux[i] = vengoP.desencolar().toString();
            i++;
        }
        aux[i] = aeropuertos[posVDestino].toString();


        String[] vengoSinDuplicados = Arrays.stream(aux).distinct().toArray(String[]::new);

        for (int z = 0; z < vengoSinDuplicados.length; z++) {
            if (vengoSinDuplicados[z] != null) {
                // respuesta = respuesta + buscarPorCodigo(vengoSinDuplicados[z]);
                respuesta = respuesta + vengoSinDuplicados[z];
            }
        }
        int lastIndex = respuesta.lastIndexOf('|'); // elimina la ultima barra recta
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }

        Objeto r = new Objeto(respuesta, (int) tiempos[posVDestino]);
        return r;
    }


}
