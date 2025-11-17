package co.edu.uniquindio.model;

import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * Clase encargada de gestionar la evacuación de zonas afectadas,
 * priorizando aquellas con mayor nivel de urgencia.
 */
public class GestorEvacuacion {

    private final PriorityQueue<Ubicacion> colaEvacuacion;

    /**
     * Constructor que inicializa la cola de evacuación con prioridad
     * basada en el nivel de urgencia de cada ubicación (mayor urgencia primero).
     */
    public GestorEvacuacion() {
        this.colaEvacuacion = new PriorityQueue<>(
                Comparator.comparingInt(Ubicacion::getNivelUrgencia).reversed()
        );
    }

    /**
     * Agrega una zona a la cola de evacuación si no ha sido registrada previamente.
     * Evita duplicados y mantiene el orden de prioridad.
     *
     * @param ubicacion la zona que se desea evacuar
     */
    public void agregarZonaEvacuacion(Ubicacion ubicacion) {
        if (!colaEvacuacion.contains(ubicacion)) {
            colaEvacuacion.add(ubicacion);
        } else {
            System.out.println("La zona " + ubicacion.getNombre() + " ya está en la lista de evacuación.");
        }
    }

    /**
     * Devuelve la siguiente zona prioritaria para evacuar sin retirarla de la cola.
     *
     * @return la ubicación con mayor urgencia o null si no hay zonas pendientes
     */
    public Ubicacion obtenerZonaPrioritaria() {
        return colaEvacuacion.peek();
    }

    /**
     * Ejecuta la evacuación de la zona más urgente, retirándola de la cola.
     * Muestra un mensaje indicando la acción realizada.
     */
    public void ejecutarEvacuacion() {
        Ubicacion zonaEvacuada = colaEvacuacion.poll();
        if (zonaEvacuada != null) {
            System.out.println("Evacuando zona prioritaria: " + zonaEvacuada.getNombre() +
                    " (Urgencia: " + zonaEvacuada.getNivelUrgencia() + ")");
        } else {
            System.out.println("No hay zonas pendientes de evacuación.");
        }
    }

    /**
     * Devuelve la cantidad de zonas pendientes en la cola de evacuación.
     *
     * @return número de zonas pendientes
     */
    public int getZonasPendientes() {
        return colaEvacuacion.size();
    }

    /**
     * Devuelve la cola de evacuación actual.
     *
     * @return cola de evacuación con las zonas registradas
     */
    public PriorityQueue<Ubicacion> getColaEvacuacion() {
        return colaEvacuacion;
    }

    /**
     * Devuelve una representación textual del estado del gestor de evacuación,
     * incluyendo la cantidad de zonas pendientes y la siguiente en prioridad.
     *
     * @return cadena descriptiva del gestor
     */
    @Override
    public String toString() {
        return "GestorEvacuacion{" +
                "zonasPendientes=" + colaEvacuacion.size() +
                ", siguiente=" + (colaEvacuacion.peek() != null ? colaEvacuacion.peek().getNombre() : "Ninguna") +
                '}';
    }
}