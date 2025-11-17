package co.edu.uniquindio.model;

import java.util.*;

public class GrafoRutas {
    private final Map<Ubicacion, List<Ruta>> adyacencias;

    //Devuelve el mapa de adyacencias (grafo completo)

    public Map<Ubicacion, List<Ruta>> getAdyacencias() {
        return adyacencias;
    }
    /**
     * Constructor que inicializa el grafo de rutas como un mapa vacío.
     */
    public GrafoRutas() {
        this.adyacencias = new HashMap<>();
    }

    /**
     * Agrega una nueva ubicación al grafo si aún no existe.
     *
     * @param ubicacion la ubicación que se desea agregar al grafo
     */
    public void agregarUbicacion(Ubicacion ubicacion) {
        adyacencias.putIfAbsent(ubicacion, new ArrayList<>());
    }

    /**
     * Agrega una ruta dirigida entre dos ubicaciones con una distancia específica.
     * Si la ruta ya existe, no se agrega nuevamente.
     *
     * @param origen    ubicación de origen
     * @param destino   ubicación de destino
     * @param distancia distancia entre origen y destino
     */
    public void agregarRuta(Ubicacion origen, Ubicacion destino, double distancia) {
        if (origen == null || destino == null) {
            System.out.println("Error: origen o destino nulo.");
            return;
        }

        agregarUbicacion(origen);
        agregarUbicacion(destino);

        boolean existe = adyacencias.get(origen).stream()
                .anyMatch(r -> r.getDestino().equals(destino));

        if (!existe) {
            adyacencias.get(origen).add(new Ruta(origen, destino, distancia, true));
        }
    }

    /**
     * Calcula el camino más corto entre dos ubicaciones usando el algoritmo de Dijkstra.
     * Solo considera rutas disponibles.
     *
     * @param origen  ubicación inicial
     * @param destino ubicación final
     * @return lista de ubicaciones que representan el camino más corto, o lista vacía si no hay ruta
     */
    public List<Ubicacion> obtenerCaminoMasCorto(Ubicacion origen, Ubicacion destino) {
        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            System.out.println("Una o ambas ubicaciones no existen en el grafo.");
            return Collections.emptyList();
        }

        Map<Ubicacion, Double> distancias = new HashMap<>();
        Map<Ubicacion, Ubicacion> anteriores = new HashMap<>();

        for (Ubicacion u : adyacencias.keySet()) {
            distancias.put(u, Double.MAX_VALUE);
        }
        distancias.put(origen, 0.0);

        PriorityQueue<Ubicacion> cola = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        cola.add(origen);

        while (!cola.isEmpty()) {
            Ubicacion actual = cola.poll();
            for (Ruta ruta : adyacencias.getOrDefault(actual, Collections.emptyList())) {
                if (!ruta.isDisponible()) continue;

                double nuevaDist = distancias.get(actual) + ruta.getDistancia();
                if (nuevaDist < distancias.get(ruta.getDestino())) {
                    distancias.put(ruta.getDestino(), nuevaDist);
                    anteriores.put(ruta.getDestino(), actual);
                    cola.add(ruta.getDestino());
                }
            }
        }

        List<Ubicacion> camino = new ArrayList<>();
        for (Ubicacion at = destino; at != null; at = anteriores.get(at)) {
            camino.add(at);
        }
        Collections.reverse(camino);

        if (camino.isEmpty() || !camino.get(0).equals(origen)) {
            System.out.println("No existe una ruta entre " + origen.getNombre() + " y " + destino.getNombre());
            return Collections.emptyList();
        }

        return camino;
    }

    /**
     * Devuelve el mapa completo de adyacencias del grafo.
     *
     * @return mapa de ubicaciones con sus rutas asociadas
     */
    public Map<Ubicacion, List<Ruta>> getAdyacencias() {
        return adyacencias;
    }

    /**
     * Devuelve una lista de todas las ubicaciones presentes en el grafo.
     *
     * @return lista de ubicaciones
     */
    public List<Ubicacion> getUbicaciones() {
        return new ArrayList<>(adyacencias.keySet());
    }

    /**
     * Representación en texto del grafo, mostrando cada ubicación y sus rutas disponibles.
     *
     * @return cadena que describe el grafo de rutas
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Grafo de Rutas:\n");
        for (Map.Entry<Ubicacion, List<Ruta>> entry : adyacencias.entrySet()) {
            sb.append("  ").append(entry.getKey().getNombre()).append(" → ");
            for (Ruta r : entry.getValue()) {
                sb.append(r.getDestino().getNombre()).append(" (").append(r.getDistancia()).append(" km), ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }}