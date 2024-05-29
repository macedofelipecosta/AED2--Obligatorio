package tads;

    public class Cola<T extends Comparable<T>> {

        private NodoCola<T> inicio;
        private NodoCola<T> fin;

        public int getLargo() {
            return largo;
        }

        private int largo;

        public void encolar(T dato) {
            if (this.inicio == null) {
                inicio = new NodoCola<T>(dato);
                fin = inicio;
            } else {
                fin.setSig(new NodoCola<T>(dato));
                fin = fin.getSig();
            }
            this.largo++;
        }

        public void encolarOrdenado(T dato) {
            NodoCola<T> nuevoNodo = new NodoCola<>(dato);

            if (inicio == null) {  // Si la cola está vacia
                inicio = nuevoNodo;
                fin = nuevoNodo;

            } else if (inicio.getDato().compareTo(dato) > 0) {  // Insertar al inicio
                nuevoNodo.setSig(inicio);
                inicio = nuevoNodo;
            } else {
                NodoCola<T> actual = inicio;
                while (actual.getSig() != null && actual.getSig().getDato().compareTo(dato) < 0) {
                    actual = actual.getSig();
                }
                nuevoNodo.setSig(actual.getSig());
                actual.setSig(nuevoNodo);
                if (nuevoNodo.getSig() == null) {  // Si se inserto al final, actualizar fin
                    fin = nuevoNodo;
                }
            }
            this.largo++;
        }


        // Pre: !esVacia()
        public T desencolar() {
            T dato = this.inicio.getDato();
            inicio = inicio.getSig();
            this.largo--;
            if(this.inicio == null) {
                fin = null;
            }
            return dato;
        }

        public boolean esVacia() {
            return this.largo != 0;
        }

        private class NodoCola<Q> {
            private final Q dato;
            private NodoCola<Q> sig;

            public NodoCola(Q dato) {
                this.dato = dato;
            }

            public Q getDato() {
                return dato;
            }

            public NodoCola<Q> getSig() {
                return sig;
            }

            public void setSig(NodoCola<Q> sig) {
                this.sig = sig;
            }

            @Override
            public String toString() {
                return dato.toString();
            }

        }

    }


