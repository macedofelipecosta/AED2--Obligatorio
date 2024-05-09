package tads;

public class ABBVuelos <Vuelo extends Comparable<Vuelo>> {
    private NodoABB raiz;

    public void insertar(Vuelo dato) {
        if (raiz == null) {
            raiz = new NodoABB(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB nodo, Vuelo dato) {
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

    public boolean pertenece(Vuelo dato) {
        return pertenece(raiz, dato);
    }

    private boolean pertenece(NodoABB nodo, Vuelo dato) {
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

    public Vuelo obtener(Vuelo dato) {
        return obtener(raiz, dato);
    }

    private Vuelo obtener(NodoABB nodo, Vuelo dato) {
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

    public int cantidadElementosRecorridos(Vuelo dato) {
        return cantidadElementosRecorridos(raiz, dato, 0);
    }

    private int cantidadElementosRecorridos(NodoABB nodo, Vuelo dato, int num) {
        if (nodo == null) {
            return num;
        }
        if (nodo.dato.equals(dato)) {
            return num;
        }
        if (dato.compareTo(nodo.dato) < 0) {
            return cantidadElementosRecorridos(nodo.izq, dato, num + 1);
        } else {
            return cantidadElementosRecorridos(nodo.der, dato, num + 1);
        }
    }

    public int cantidadVuelosRegistradas() {
        return cantidadVuelosRegistradas(raiz);
    }

    private int cantidadVuelosRegistradas(NodoABB nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + cantidadVuelosRegistradas(nodo.izq ) +
                cantidadVuelosRegistradas(nodo.der);
    }

    public String listarAscendente() {
        return listarAscendente(raiz);
    }

    private String listarAscendente(NodoABB nodo) {
        if (nodo != null) {
            return listarAscendente(nodo.izq) + "" + nodo.dato + "|" + listarAscendente(nodo.der);
        } else {
            return "";
        }
    }

    public String listarDescendente(){return listarDescendente(raiz);}
    private String listarDescendente(NodoABB nodo){
        if (nodo != null) {
            return listarAscendente(nodo.der) + "" + nodo.dato + "|" + listarAscendente(nodo.izq);
        } else {
            return "";
        }
    }
    private class NodoABB {
        private Vuelo dato;
        private NodoABB izq;
        private NodoABB der;

        public NodoABB(Vuelo dato) {
            this.dato = dato;
        }
    }


}
