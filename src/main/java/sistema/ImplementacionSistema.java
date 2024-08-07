package sistema;

import entidades.Aerolinea;
import entidades.Objeto;
import entidades.Pasajero;
import entidades.Vuelo;
import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;
import tads.ABBAerolineas;
import tads.ABBPasajeros;
import tads.Conexion;
import tads.Grafo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplementacionSistema implements Sistema {
    ABBPasajeros Pasajeros;
    ABBPasajeros PasajerosPlatino;
    ABBPasajeros PasajerosFrecuente;
    ABBPasajeros PasajerosEstandar;
    ABBAerolineas Aerolineas;

    Grafo Conexiones;

    private static int maxAeropuertos;
    private static int maxAerolineas;

    @Override
    public Retorno inicializarSistema(int maxAeropuertos, int maxAerolineas) {
        if (maxAeropuertos <= 5) return Retorno.error1("Canitdad de aeropuertos menor o igual a 5");
        if (maxAerolineas <= 3) return Retorno.error2("Cantidad de aerolineas menor o igual a 3");
        Pasajeros = new ABBPasajeros();
        PasajerosPlatino = new ABBPasajeros();
        PasajerosFrecuente = new ABBPasajeros();
        PasajerosEstandar = new ABBPasajeros();
        Aerolineas = new ABBAerolineas();
        Conexiones = new Grafo(maxAeropuertos);
        ImplementacionSistema.maxAerolineas = maxAerolineas;
        ImplementacionSistema.maxAeropuertos = maxAeropuertos;
        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, String telefono, Categoria categoria) {

        if (cedula == null || nombre == null || telefono == null || categoria == null) {
            return Retorno.error1("Uno de los parametros esta vacio o es null");
        }
        if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            return Retorno.error1("Uno de los parametros esta vacio o es null");
        }
        if (validarCedula(cedula)) {
            return Retorno.error2("La cedula tiene un formato invalido!");
        }
        Pasajero p = new Pasajero(cedula);

        if (Pasajeros.pertenece(p)) {
            return Retorno.error3("El pasajero ya existe en el Sistema!");
        }
        p.setNombre(nombre);
        p.setTelefono(telefono);
        p.setCategoria(categoria);

        Pasajeros.insertar(p);
        if (p.getCategoria() == Categoria.PLATINO) {
            PasajerosPlatino.insertar(p);
        }
        if (p.getCategoria() == Categoria.FRECUENTE) {
            PasajerosFrecuente.insertar(p);
        }
        if (p.getCategoria() == Categoria.ESTANDAR) {
            PasajerosEstandar.insertar(p);
        }
        return Retorno.ok();
    }

    /**
     * toma una cadena de texto representando una cédula y devuelve true si la cédula
     * tiene el formato correcto y el primer dígito no es cero, y false en caso contrario
     */
    public static boolean validarCedula(String cedula) {
        // Expresión regular para validar el formato de la cédula
        String regex = "^(?!0)\\d{1,3}(\\.\\d{3}){2}-\\d{1}$|^(?!0)\\d{3}(\\.\\d{3}){1}-\\d{1}$";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher
        Matcher matcher = pattern.matcher(cedula);

        // Verificar si la cédula coincide con el patrón, se puso ! para poder utilizarlo en el if de altaPasajero
        return !matcher.matches();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return Retorno.error1("La cedula esta vacia o es null!");
        }
        if (validarCedula(cedula)) {
            return Retorno.error2("La cedula no tiene formato valido!");
        }
        Pasajero p = new Pasajero(cedula);
        if (Pasajeros.obtener(p) == null) {
            return Retorno.error3("El pasajero no existe!");
        }
        int elementorRecorridos = Pasajeros.cantidadElementosRecorridos(p);
        return Retorno.ok(elementorRecorridos, Pasajeros.obtener(p).toString());
        /*
         * Ver el método para que devuelva el pasajero y el int de los elementos recorridos en el mismo return ???
         * */
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.ok(Pasajeros.listarAscendente());
    }

    @Override
    public Retorno listarPasajerosPorCategoria(Categoria categoria) {
        if (categoria == null) {
            return Retorno.ok("");
        }
        if (categoria.getTexto().equals("Frecuente")) {
            return Retorno.ok(PasajerosFrecuente.listarAscendente());
        }
        if (categoria.getTexto().equals("Estándar")) {
            return Retorno.ok(PasajerosEstandar.listarAscendente());
        }
        if (categoria.getTexto().equals("Platino")) {
            return Retorno.ok(PasajerosPlatino.listarAscendente());
        }
        return Retorno.ok("");
    }

    @Override
    public Retorno registrarAerolinea(String codigo, String nombre) {

        if (maxAerolineas == 0) {
            return Retorno.error1("Cantidad maxima de Aerolineas alcanzada");
        }
        if (codigo == null || codigo.isEmpty() || nombre == null || nombre.isEmpty()) {
            return Retorno.error2("Codigo o Nombre vacios o nulos!");
        }
        Aerolinea a = new Aerolinea(codigo, nombre);
        if (Aerolineas.pertenece(a)) {
            return Retorno.error3("Aerolinea ya registrada!");
        }
        Aerolineas.insertar(a);
        maxAerolineas--;
        return Retorno.ok();
    }

    @Override
    public Retorno listarAerolineasDescendente() {
        return Retorno.ok(Aerolineas.listarDescendente());
    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {
        // queda ver lo del minimo o maximo de aeropuertos
        if (maxAeropuertos == 0) {
            return Retorno.error1("Cantidad maxima de Aeropuertos alcanzado ");
        }

        if (codigo == null || codigo.isEmpty() || nombre == null || nombre.isEmpty()) {
            return Retorno.error2("Codigo o Nombre vacios o nulos!");
        }
        if (Conexiones.existeAeropuerto(codigo)) {
            return Retorno.error3("Aeropuerto ya registrado!");
        }
        Conexiones.agregarAeropuerto(codigo, nombre);
        maxAeropuertos--;
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        if (kilometros <= 0) {
            return Retorno.error1("Kilometros menor o igual a cero!");
        }
        if (codigoAeropuertoDestino == null || codigoAeropuertoDestino.isEmpty() || codigoAeropuertoOrigen == null || codigoAeropuertoOrigen.isEmpty()) {
            return Retorno.error2("Codigo Aeropuerto origen o destino es nulo o vacio!");
        }

        if (!Conexiones.existeAeropuerto(codigoAeropuertoOrigen)) {
            return Retorno.error3("Aeropuerto de origen no existe!");
        }

        if (!Conexiones.existeAeropuerto(codigoAeropuertoDestino)) {
            return Retorno.error4("Aeropuerto de destino no existe!");
        }

        if (Conexiones.obtenerConexion(codigoAeropuertoOrigen, codigoAeropuertoDestino) != null) {
            return Retorno.error5("Ya existe esta conexion!");
        }

        Conexiones.agregarConexion(codigoAeropuertoOrigen, codigoAeropuertoDestino, kilometros);

        return Retorno.ok();
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoAeropuertoDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, String codigoAerolinea) {
        if (combustible <= 0 || minutos <= 0 || costoEnDolares <= 0) {
            return Retorno.error1("Costo en dolares, minutos o combustible no puede ser menor o igual a cero!");
        }
        if (codigoCiudadOrigen == null || codigoAeropuertoDestino == null || codigoDeVuelo == null || codigoAerolinea == null) {
            return Retorno.error2("Alguno de los parametros es nulo!");
        }
        if (codigoCiudadOrigen.isEmpty() || codigoAeropuertoDestino.isEmpty() || codigoDeVuelo.isEmpty() || codigoAerolinea.isEmpty()) {
            return Retorno.error2("Alguno de los parametros esta vacío!");
        }

        if (!Conexiones.existeAeropuerto(codigoCiudadOrigen)) {
            return Retorno.error3("El aeropuerto de origen no existe!");
        }

        if (!Conexiones.existeAeropuerto(codigoAeropuertoDestino)) {
            return Retorno.error4("El aeropuerto de destino no existe!");
        }
        Aerolinea aerolinea = new Aerolinea(codigoAerolinea);
        if (!Aerolineas.pertenece(aerolinea)) {
            return Retorno.error5("La aerolinea indicada no existe!");
        }
        Conexion conexion = Conexiones.obtenerConexion(codigoCiudadOrigen, codigoAeropuertoDestino);
        if (conexion == null) {
            return Retorno.error6("La conexion entre Aeropuerto de origen y destino no existe!");
        }
        Vuelo vuelo = new Vuelo(codigoDeVuelo);
        if (conexion.getVuelos().existe(vuelo)) {
            return Retorno.error7("Ya existe un vuelo con este codigo!");
        }
        Vuelo nuevoVuelo = new Vuelo(codigoDeVuelo, minutos);
        conexion.getVuelos().insertar(nuevoVuelo);
        conexion.getCodigosAerolineas().insertar(codigoAerolinea);
        conexion.getListaMinutos().insertar((int) nuevoVuelo.getMinutos());
        return Retorno.ok();
    }

    @Override
    public Retorno listadoAeropuertosCantDeEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        if (cantidad < 0) {
            return Retorno.error1("Cantidad de escalas menor a 1!");
        }
        if (!Conexiones.existeAeropuerto(codigoAeropuertoOrigen)) {
            return Retorno.error2("Aeropuerto de origen no existe!");
        }
        Aerolinea a = new Aerolinea(codigoAerolinea);
        if (!Aerolineas.pertenece(a)) {
            return Retorno.error3("Aerolinea no registrada!");
        }

        return Retorno.ok(Conexiones.listadoAeropuertosCantEscalas(codigoAeropuertoOrigen, cantidad, codigoAerolinea));
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if (codigoCiudadOrigen == null || codigoCiudadDestino == null) {
            return Retorno.error1("Alguno de los codigos es nulo!");
        }
        if (codigoCiudadOrigen.isEmpty() || codigoCiudadDestino.isEmpty()) {
            return Retorno.error1("Alguno de los codigos esta vacio!");
        }
        if (!Conexiones.existeAeropuerto(codigoCiudadDestino)) {
            return Retorno.error4("No existe aeropuerto destino!");
        }
        if (!Conexiones.existeAeropuerto(codigoCiudadOrigen)) {
            return Retorno.error3("No existe aeropuerto de origen!");
        }
        if (Conexiones.costoMinimoKilometros(codigoCiudadOrigen, codigoCiudadDestino).dato2 == Integer.MAX_VALUE) {
            return Retorno.error2("No hay camino!");
        }

        Objeto obj= Conexiones.costoMinimoKilometros(codigoCiudadOrigen, codigoCiudadDestino);
        return Retorno.ok(obj.dato2,obj.dato1);
    }

    @Override
    public Retorno viajeCostoMinimoEnMinutos(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        if (codigoAeropuertoOrigen == null || codigoAeropuertoDestino == null) {
            return Retorno.error1("Alguno de los codigos es nulo!");
        }
        if (codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino.isEmpty()) {
            return Retorno.error1("Alguno de los codigos esta vacio!");
        }
        if (!Conexiones.existeAeropuerto(codigoAeropuertoDestino)) {
            return Retorno.error4("No existe aeropuerto destino!");
        }
        if (!Conexiones.existeAeropuerto(codigoAeropuertoOrigen)) {
            return Retorno.error3("No existe aeropuerto de origen!");
        }
        if (Conexiones.costoMinimoKilometros(codigoAeropuertoOrigen, codigoAeropuertoDestino).dato2 == Integer.MAX_VALUE) {
            return Retorno.error2("No hay camino!");
        }

        Objeto obj = Conexiones.costoMinimoMinutos(codigoAeropuertoOrigen, codigoAeropuertoDestino);
        return Retorno.ok(obj.dato2, obj.dato1);
    }


}
