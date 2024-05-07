package tads;

public class Grafo {

        private Vertice[] vertices;
        private Arista[][] aristas;
        private final int maxVertices;
        int cantidad = 0;

        public Grafo(int maxVertices) {
            this.maxVertices = maxVertices;
            vertices = new Vertice[maxVertices];
            aristas = new Arista[maxVertices][maxVertices];
        }

        public void agregarVertice(String codigo) {
            if (cantidad < maxVertices) {
                int posLibre = obtenerPosLibre();
                vertices[posLibre] = new Vertice(codigo);
                cantidad++;
            }
        }

        public void borrarVertice(String codigo) {
            int posVaBorrar = buscarPos(new Vertice(codigo));

            for (int i = 0; i < aristas.length; i++) {
                aristas[posVaBorrar][i] = null;
                aristas[i][posVaBorrar] = null;
            }
            vertices[posVaBorrar] = null;
            cantidad--;
        }

        public void agregarArista(String vInicial, String vFinal, double kilometros) {
            int posVInicial = buscarPos(new Vertice(vInicial));
            int posVFinal = buscarPos(new Vertice(vFinal));

            aristas[posVInicial][posVFinal] = new Arista(kilometros);
        }

        public void borrarArista(String vInicial, String vFinal) {
            int posVInicial = buscarPos(new Vertice(vInicial));
            int posVFinal = buscarPos(new Vertice(vFinal));

            aristas[posVInicial][posVFinal] = null;
        }

        public Arista obtenerArista(String vInicial, String vFinal) {
            int posVInicial = buscarPos(new Vertice(vInicial));
            int posVFinal = buscarPos(new Vertice(vFinal));

            return aristas[posVInicial][posVFinal];
        }

        public boolean existeVertice(String nombreVertice){
            int posABuscar= buscarPos(new Vertice(nombreVertice));
            return posABuscar>=0;
        }

        public Lista<Vertice> adyacentes(String nombreVertice){
            Lista<Vertice> adyacentes=new Lista<>();
            int posV = buscarPos(new Vertice(nombreVertice));

            for (int i = 0; i < aristas.length; i++) {
                if(aristas[posV][i]!=null){
                    adyacentes.insertar(vertices[i]);
                }
            }
            return adyacentes;
        }

        private int buscarPos(Vertice v) {
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i] != null && vertices[i].equals(v)) {
                    return i;
                }
            }
            return -1;
        }

        private int obtenerPosLibre() {
            for (int i = 0; i < vertices.length; i++) {
                if (vertices[i] == null) {
                    return i;
                }
            }
            return -1;
        }


}
