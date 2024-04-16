package sistema;

import entidades.*;
import interfaz.*;
import tads.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImplementacionSistema implements Sistema {
    ABBPasajeros ABBPasajeros;
    ABBPasajeros ABBPasajerosPlatino;
    ABBPasajeros ABBPasajerosFrecuente;
    ABBPasajeros ABBPasajerosEstandar;
    ABBGenerico<Aerolinea> ABBAerolineas;

    @Override
    public Retorno inicializarSistema(int maxAeropuertos, int maxAerolineas) {
        if (maxAeropuertos <= 5) return Retorno.error1("Canitdad de aeropuertos menor o igual a 5");
        if (maxAerolineas <= 3) return Retorno.error2("Cantidad de aerolineas menor o igual a 3");
         ABBPasajeros = new ABBPasajeros();
         ABBPasajerosPlatino = new ABBPasajeros();
         ABBPasajerosFrecuente = new ABBPasajeros();
         ABBPasajerosEstandar = new ABBPasajeros();
         ABBAerolineas = new ABBGenerico();
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
        if (!validarCedula(cedula)) {
            return Retorno.error2("La cedula tiene un formato invalido!");
        }
        Pasajero p= new Pasajero(cedula);

        if (ABBPasajeros.pertenece(p)){
            return Retorno.error3("El pasajero ya existe en el Sistema!");
        }
        p.setNombre(nombre);
        p.setTelefono(telefono);
        p.setCategoria(categoria);

        ABBPasajeros.insertar(p);
        if (p.getCategoria()==Categoria.PLATINO){ABBPasajerosPlatino.insertar(p);}
        if (p.getCategoria()==Categoria.FRECUENTE){ABBPasajerosFrecuente.insertar(p);}
        if (p.getCategoria()==Categoria.ESTANDAR){ABBPasajerosEstandar.insertar(p);}
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

        // Verificar si la cédula coincide con el patrón
        return matcher.matches();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {
        if (cedula==null || cedula.isEmpty()){return Retorno.error1("La cedula esta vacia o es null!");}
        if (!validarCedula(cedula)){return Retorno.error2("La cedula no tiene formato valido!");}
        Pasajero p= new Pasajero(cedula);
        if (ABBPasajeros.obtener(p)==null){return Retorno.error3("El pasajero no existe!");}
        System.out.println(ABBPasajeros.obtener(p).toString());
        return Retorno.ok();
        return Retorno.ok
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarPasajerosPorCategoria(Categoria categoria) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarAerolinea(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarAerolineasDescendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoAeropuertoDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, String codigoAerolinea) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoAeropuertosCantDeEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoCiudadOrigen, String codigoCiudadDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoEnMinutos(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        return Retorno.noImplementada();
    }


}
