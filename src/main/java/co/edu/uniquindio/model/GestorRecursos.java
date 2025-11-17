package co.edu.uniquindio.model;

import java.util.*;

/**
 * Clase encargada de gestionar el inventario de recursos, su asignación a ubicaciones
 * y la distribución mediante un árbol de nodos. También coordina el envío de equipos de rescate.
 */
public class GestorRecursos {

    private final List<Recurso> inventarioGlobal;
    private final Map<Ubicacion, List<Recurso>> recursosPorUbicacion;
    private final ArbolDistribucion arbolDistribucion;

    /**
     * Constructor que inicializa el gestor con un inventario vacío,
     * sin recursos asignados y un árbol de distribución vacío.
     */
    public GestorRecursos() {
        this.inventarioGlobal = new ArrayList<>();
        this.recursosPorUbicacion = new HashMap<>();
        this.arbolDistribucion = new ArbolDistribucion();
    }

    /**
     * Registra un recurso en el inventario global si no es nulo.
     *
     * @param recurso el recurso a registrar
     */
    public void registrarRecursoGlobal(Recurso recurso) {
        if (recurso == null) {
            System.out.println("No se puede registrar un recurso nulo.");
            return;
        }
        inventarioGlobal.add(recurso);
        System.out.println("Recurso registrado globalmente: " + recurso.getNombre());
    }

    /**
     * Asigna una cantidad específica de un recurso a una ubicación afectada,
     * actualizando el inventario global y el árbol de distribución.
     *
     * @param destino  ubicación afectada
     * @param recurso  recurso a asignar
     * @param cantidad cantidad a asignar
     * @return true si la asignación fue exitosa, false en caso contrario
     */
    public boolean asignarRecurso(Ubicacion destino, Recurso recurso, int cantidad) {
        if (destino == null || recurso == null) {
            System.out.println("Error: destino o recurso nulo.");
            return false;
        }

        if (cantidad <= 0) {
            System.out.println("La cantidad a asignar debe ser mayor que cero.");
            return false;
        }

        if (recurso.getCantidadDisponible() < cantidad) {
            System.out.println("No hay suficiente cantidad disponible de " + recurso.getNombre());
            return false;
        }

        recurso.setCantidadDisponible(recurso.getCantidadDisponible() - cantidad);

        recursosPorUbicacion.putIfAbsent(destino, new ArrayList<>());
        Recurso recursoAsignado = new Recurso(recurso.getNombre(), recurso.getTipo(), cantidad);
        recursosPorUbicacion.get(destino).add(recursoAsignado);

        actualizarArbolDistribucion(destino, recursoAsignado);

        System.out.println("Suministros " + cantidad + " unidades de " + recurso.getNombre() + " asignadas a " + destino.getNombre());
        return true;
    }

    /**
     * Devuelve la lista de recursos asignados a una ubicación específica.
     *
     * @param ubicacion la ubicación consultada
     * @return lista de recursos asignados a esa ubicación
     */
    public List<Recurso> obtenerRecursosPorUbicacion(Ubicacion ubicacion) {
        return recursosPorUbicacion.getOrDefault(ubicacion, new ArrayList<>());
    }

    /**
     * Actualiza el árbol de distribución agregando un nuevo nodo con el recurso asignado.
     *
     * @param destino ubicación destino del recurso
     * @param recurso recurso asignado
     */
    private void actualizarArbolDistribucion(Ubicacion destino, Recurso recurso) {
        NodoDistribucion nodo = new NodoDistribucion(recurso, destino);

        if (arbolDistribucion.getRaiz() == null) {
            arbolDistribucion.setRaiz(nodo);
        } else {
            arbolDistribucion.getRaiz().agregarHijo(nodo);
        }

        arbolDistribucion.distribuirRecursos();
    }

    /**
     * Devuelve el inventario global de recursos.
     *
     * @return lista de recursos registrados globalmente
     */
    public List<Recurso> getInventarioGlobal() {
        return inventarioGlobal;
    }

    /**
     * Devuelve el árbol de distribución actual.
     *
     * @return estructura del árbol de distribución
     */
    public ArbolDistribucion getArbolDistribucion() {
        return arbolDistribucion;
    }

    /**
     * Asigna equipos de rescate a zonas críticas, buscando la base más cercana con disponibilidad.
     *
     * @param ubicaciones lista de ubicaciones disponibles
     * @param grafo       grafo de rutas para calcular distancias
     */
    public void asignarEquiposDeRescate(List<Ubicacion> ubicaciones, GrafoRutas grafo) {
        if (ubicaciones == null || ubicaciones.isEmpty() || grafo == null) {
            System.out.println("No hay datos suficientes para asignar equipos de rescate.");
            return;
        }

        for (Ubicacion zona : ubicaciones) {
            if (zona.esZonaCritica()) {
                Ubicacion base = buscarBaseMasCercanaConEquipos(zona, ubicaciones, grafo);
                if (base != null) {
                    Recurso equipo = obtenerRecursoDisponible(base, TipoRecurso.EQUIPO_RESCATE);
                    if (equipo != null) {
                        asignarRecurso(zona, equipo, 1);
                        System.out.println("Equipo de rescate enviado desde " + base.getNombre() + " hacia " + zona.getNombre());
                    }
                } else {
                    System.out.println("No hay bases disponibles con equipos para " + zona.getNombre());
                }
            }
        }
    }

    /**
     * Busca la base más cercana con equipos de rescate disponibles.
     *
     * @param destino     ubicación crítica
     * @param ubicaciones lista de ubicaciones disponibles
     * @param grafo       grafo de rutas
     * @return ubicación de la base más cercana con equipos disponibles
     */
    private Ubicacion buscarBaseMasCercanaConEquipos(Ubicacion destino, List<Ubicacion> ubicaciones, GrafoRutas grafo) {
        Ubicacion baseMasCercana = null;
        double distanciaMin = Double.MAX_VALUE;

        for (Ubicacion origen : ubicaciones) {
            if (tieneEquiposDisponibles(origen)) {
                List<Ubicacion> camino = grafo.obtenerCaminoMasCorto(origen, destino);
                if (!camino.isEmpty()) {
                    double distancia = calcularDistanciaTotal(camino, grafo);
                    if (distancia >= 0 && distancia < distanciaMin) {
                        distanciaMin = distancia;
                        baseMasCercana = origen;
                    }
                }
            }
        }
        return baseMasCercana;
    }

    /**
     * Calcula la distancia total de una ruta entre ubicaciones.
     *
     * @param camino lista de ubicaciones en la ruta
     * @param grafo  grafo de rutas
     * @return distancia total en kilómetros
     */
    private double calcularDistanciaTotal(List<Ubicacion> camino, GrafoRutas grafo) {
        double total = 0;
        Map<Ubicacion, List<Ruta>> adyacencias = grafo.getAdyacencias();

        for (int i = 0; i < camino.size() - 1; i++) {
            Ubicacion actual = camino.get(i);
            Ubicacion siguiente = camino.get(i + 1);

            for (Ruta r : adyacencias.getOrDefault(actual, Collections.emptyList())) {
                if (r.getDestino().equals(siguiente)) {
                    total += r.getDistancia();
                    break;
                }
            }
        }
        return total;
    }

    /**
     * Verifica si una ubicación tiene equipos de rescate disponibles.
     *
     * @param ubicacion ubicación a verificar
     * @return true si hay equipos disponibles, false si no
     */
    private boolean tieneEquiposDisponibles(Ubicacion ubicacion) {
        List<Recurso> recursos = obtenerRecursosPorUbicacion(ubicacion);
        return recursos.stream().anyMatch(r -> r.getTipo() == TipoRecurso.EQUIPO_RESCATE && r.getCantidadDisponible() > 0);
    }

    /**
     * Obtiene un recurso disponible de un tipo específico en una ubicación.
     *
     * @param ubicacion ubicación a consultar
     * @param tipo      tipo de recurso buscado
     * @return recurso disponible o null si no hay
     */
    private Recurso obtenerRecursoDisponible(Ubicacion ubicacion, TipoRecurso tipo) {
        List<Recurso> recursos = obtenerRecursosPorUbicacion(ubicacion);
        for (Recurso r : recursos) {
            if (r.getTipo() == tipo && r.getCantidadDisponible() > 0) {
                return r;
            }
        }
        return null;
    }

    /**
     * Devuelve una representación textual del estado actual del gestor de recursos.
     *
     * @return cadena descriptiva del inventario global y recursos por ubicación
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GestorRecursos:\n");
        sb.append("  Inventario Global:\n");
        for (Recurso r : inventarioGlobal) {
            sb.append("    - ").append(r).append("\n");
        }
        sb.append("  Recursos por Ubicación:\n");
        for (Map.Entry<Ubicacion, List<Recurso>> e : recursosPorUbicacion.entrySet()) {
            sb.append("    ").append(e.getKey().getNombre()).append(": ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }
}