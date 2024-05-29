package tads;

public class ABBAerolineas<Aerolinea extends Comparable<Aerolinea>> {
    private NodoABB raiz;

    public void insertar(Aerolinea dato) {
        if (raiz == null) {
            raiz = new NodoABB(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB nodo, Aerolinea dato) {
        if (dato.compareTo(nodo.dato) < 0) {
            if (nodo.izq == null) {
                nodo.izq = new NodoABB(dato);
            } else {
                insertar(nodo.izq, dato);
            }
        } else if (dato.compareTo(nodo.dato) > 0) {
            if (nodo.der == null) {
                nodo.der = new NodoABB(dato);
            } else {
                insertar(nodo.der, dato);
            }
        }
    }

    public boolean pertenece(Aerolinea dato) {
        return pertenece(raiz, dato);
    }

    private boolean pertenece(NodoABB nodo, Aerolinea dato) {
        if (nodo != null) {
            if (nodo.dato.equals(dato))
                return true;
            else if (dato.compareTo(nodo.dato) < 0) {
                return pertenece(nodo.izq, dato);
            } else {
                return pertenece(nodo.der, dato);
            }
        } else {
            return false;
        }
    }

    public String listarDescendente() {
        String respuesta=listarDescendente(raiz);
        int lastIndex = respuesta.lastIndexOf('|');
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }
        return respuesta;
    }

    private String listarDescendente(NodoABB nodo) {
        if (nodo != null) {
            return listarDescendente(nodo.der) + nodo.dato +"|"+ listarDescendente(nodo.izq);
        } else {
            return "";
        }
    }

    private class NodoABB {
        final private Aerolinea dato;
        private NodoABB izq;
        private NodoABB der;

        public NodoABB(Aerolinea dato) {
            this.dato = dato;
        }
    }

}
