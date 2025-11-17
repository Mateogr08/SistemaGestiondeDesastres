package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Representa un nodo dentro de una estructura de distribución de recursos.
 * Cada nodo contiene un recurso, una ubicación destino y una lista de nodos hijos.
 */
public class NodoDistribucion {

    private Recurso recurso;
    private Ubicacion destino;
    private final List<NodoDistribucion> hijos;

    /**
     * Constructor que inicializa un nodo de distribución con un recurso y una ubicación destino.
     *
     * @param recurso el recurso que se va a distribuir desde este nodo
     * @param destino la ubicación destino asociada al nodo
     */
    public NodoDistribucion(Recurso recurso, Ubicacion destino) {
        this.recurso = recurso;
        this.destino = destino;
        this.hijos = new ArrayList<>();
    }

    /**
     * Devuelve el recurso asociado al nodo.
     *
     * @return el recurso del nodo
     */
    public Recurso getRecurso() {
        return recurso;
    }

    /**
     * Establece el recurso que se distribuirá desde este nodo.
     *
     * @param recurso el nuevo recurso a asignar
     */
    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    /**
     * Devuelve la ubicación destino del nodo.
     *
     * @return la ubicación destino
     */
    public Ubicacion getDestino() {
        return destino;
    }

    /**
     * Establece la ubicación destino del nodo.
     *
     * @param destino la nueva ubicación destino
     */
    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    /**
     * Devuelve la lista de nodos hijos que dependen de este nodo.
     *
     * @return lista de nodos hijos
     */
    public List<NodoDistribucion> getHijos() {
        return hijos;
    }

    /**
     * Agrega un nodo hijo a la lista de distribución.
     *
     * @param hijo el nodo hijo que se desea agregar
     */
    public void agregarHijo(NodoDistribucion hijo) {
        if (hijo != null) {
            hijos.add(hijo);
        }
    }

    /**
     * Devuelve una representación en texto del nodo de distribución,
     * incluyendo el nombre del recurso, destino y cantidad de hijos.
     *
     * @return cadena descriptiva del nodo
     */
    @Override
    public String toString() {
        return "NodoDistribucion{" +
                "recurso=" + recurso.getNombre() +
                ", destino=" + (destino != null ? destino.getNombre() : "null") +
                ", hijos=" + hijos.size() +
                '}';
    }
}