package tads;


public class ABBAeropuertos<Aeropuerto extends Comparable<Aeropuerto>>{
    private NodoABB raiz;

    public void insertar(Aeropuerto dato) {
        if (raiz == null) {
            raiz = new NodoABB(dato);
        } else {
            insertar(raiz, dato);
        }
    }

    private void insertar(NodoABB nodo, Aeropuerto dato) {
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

    public boolean pertenece(Aeropuerto dato) {
        return pertenece(raiz, dato);
    }

    private boolean pertenece(NodoABB nodo, Aeropuerto dato) {
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

    public Aeropuerto obtener(Aeropuerto dato) {
        return obtener(raiz, dato);
    }

    private Aeropuerto obtener(NodoABB nodo, Aeropuerto dato) {
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

    public int cantidadElementosRecorridos(Aeropuerto dato) {
        return cantidadElementosRecorridos(raiz, dato, 0);
    }

    private int cantidadElementosRecorridos(NodoABB nodo, Aeropuerto dato, int num) {
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

    public int cantidadAerolineasRegistradas() {
        return cantidadAerolineasRegistradas(raiz, 0);
    }

    private int cantidadAerolineasRegistradas(NodoABB nodo, int num) {
        if (nodo == null) {
            return num;
        }
        return cantidadAerolineasRegistradas(nodo.izq, num + 1) +
                cantidadAerolineasRegistradas(nodo.der, num + 1);
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
        private Aeropuerto dato;
        private NodoABB izq;
        private NodoABB der;

        public NodoABB(Aeropuerto dato) {
            this.dato = dato;
        }
    }
    
}
