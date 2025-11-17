package co.edu.uniquindio.model;

import java.util.List;
/**
 * Clase encargada de simular el transporte entre ubicaciones utilizando un grafo de rutas.
 */
public class SimuladorRutas {
    private GrafoRutas grafo;

    /**
     * Constructor que inicializa el simulador con un grafo de rutas dado.
     *
     * @param grafo el grafo de rutas que se utilizará para las simulaciones
     */
    public SimuladorRutas(GrafoRutas grafo) {
        this.grafo = grafo;
    }

    /**
     * Simula el transporte entre dos ubicaciones mostrando el camino más corto calculado.
     *
     * @param origen  ubicación de inicio del recorrido
     * @param destino ubicación de destino del recorrido
     */
    public void simularTransporte(Ubicacion origen, Ubicacion destino) {
        List<Ubicacion> camino = grafo.obtenerCaminoMasCorto(origen, destino);
        System.out.println("Ruta simulada: " + camino);
    }

    /**
     * Muestra por consola la ruta óptima (más corta) entre dos ubicaciones.
     * Internamente utiliza el método de simulación de transporte.
     *
     * @param origen  ubicación de inicio
     * @param destino ubicación de destino
     */
    public void mostrarRutaOptima(Ubicacion origen, Ubicacion destino) {
        System.out.println("Ruta más corta entre " + origen.getNombre() + " y " + destino.getNombre() + ":");
        simularTransporte(origen, destino);
    }

    /**
     * Devuelve el grafo de rutas utilizado por el simulador.
     *
     * @return el grafo de rutas
     */
    public GrafoRutas getGrafo() {
        return grafo;
    }
}