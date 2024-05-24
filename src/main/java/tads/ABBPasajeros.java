package tads;

import entidades.Pasajero;

public class ABBPasajeros<Pasajero extends Comparable<Pasajero>> {

    private NodoABB raiz;

    public void insertar(Pasajero dato) {
        if (raiz == null) {
            raiz = new NodoABB(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB nodo, Pasajero dato) {
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

    public boolean pertenece(Pasajero dato) {
        return pertenece(raiz, dato);
    }

    private boolean pertenece(NodoABB nodo, Pasajero dato) {
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

    public Pasajero obtener(Pasajero dato) {
        return obtener(raiz, dato);
    }

    private Pasajero obtener(NodoABB nodo, Pasajero dato) {
        if (nodo != null) {
            if (nodo.dato.equals(dato))
                return nodo.dato;
            else if (dato.compareTo(nodo.dato) < 0) {
                return obtener(nodo.izq, dato);
            } else {
                return obtener(nodo.der, dato);
            }
        } else {
            return null;
        }
    }

    public int cantidadElementosRecorridos(Pasajero dato){
       return cantidadElementosRecorridos(raiz, dato,0);
    }
    private int cantidadElementosRecorridos(NodoABB nodo, Pasajero dato, int num) {
        if (nodo==null){return 0;}
        if (nodo.dato.equals(dato)){return num;}
        if (dato.compareTo(nodo.dato) < 0) {
            return cantidadElementosRecorridos(nodo.izq, dato, num+1);
        } else {
            return cantidadElementosRecorridos(nodo.der, dato,num+1);
        }
    }

    public String listarAscendente() {
        String respuesta=listarAscendente(raiz);
        int lastIndex = respuesta.lastIndexOf('|');
        if (lastIndex != -1) {
            respuesta = respuesta.substring(0, lastIndex) + respuesta.substring(lastIndex + 1);
        }
        return respuesta;
    }

    private String listarAscendente(NodoABB nodo) {
        if (nodo != null) {
            return listarAscendente(nodo.izq) + "" + nodo.dato + "|" + listarAscendente(nodo.der);
        } else {
            return "";
        }
    }

    private class NodoABB {
        private Pasajero dato;
        private NodoABB izq;
        private NodoABB der;

        public NodoABB(Pasajero dato) {
            this.dato = dato;
        }
    }
}
