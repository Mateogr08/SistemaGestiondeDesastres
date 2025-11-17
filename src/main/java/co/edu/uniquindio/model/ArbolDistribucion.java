package co.edu.uniquindio.model;

import java.util.HashMap;
import java.util.Map;

public class ArbolDistribucion {

    private NodoDistribucion raiz;

    public ArbolDistribucion() {
        this.raiz = null;
    }

    public NodoDistribucion getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoDistribucion raiz) {
        this.raiz = raiz;
    }

    /** Distribuye los recursos a partir de la raíz */
    public void distribuirRecursos() {
        if (raiz == null) {
            System.out.println("No hay recursos para distribuir. El árbol está vacío.");
            return;
        }
        System.out.println("Distribuyendo recursos desde: " + raiz.getRecurso().getNombre());
        distribuirDesdeNodo(raiz, 1);
    }

    private void distribuirDesdeNodo(NodoDistribucion nodo, int nivel) {
        String indent = "  ".repeat(nivel);
        for (NodoDistribucion hijo : nodo.getHijos()) {
            System.out.println(indent + "Distribuyendo " + hijo.getRecurso().getNombre() +
                    " hacia " + (hijo.getDestino() != null ? hijo.getDestino().getNombre() : "Sin destino"));
            distribuirDesdeNodo(hijo, nivel + 1);
        }
    }

    /** Mostrar jerarquía completa del árbol */
    public void mostrarJerarquia() {
        if (raiz == null) {
            System.out.println("El árbol de distribución está vacío.");
        } else {
            System.out.println("Jerarquía de distribución:");
            mostrarJerarquiaDesdeNodo(raiz, 0);
        }
    }

    private void mostrarJerarquiaDesdeNodo(NodoDistribucion nodo, int nivel) {
        System.out.println("  ".repeat(nivel) + "- " + nodo.getRecurso().getNombre() +
                (nodo.getDestino() != null ? " → " + nodo.getDestino().getNombre() : ""));
        for (NodoDistribucion hijo : nodo.getHijos()) {
            mostrarJerarquiaDesdeNodo(hijo, nivel + 1);
        }
    }

    /** Método que resuelve tu error: devuelve un resumen de recursos por ubicación */
    public Map<Ubicacion, Map<Recurso, Integer>> obtenerResumenPorUbicacion() {
        Map<Ubicacion, Map<Recurso, Integer>> resumen = new HashMap<>();
        if (raiz != null) {
            recorrerNodo(raiz, resumen);
        }
        return resumen;
    }

    private void recorrerNodo(NodoDistribucion nodo, Map<Ubicacion, Map<Recurso, Integer>> resumen) {
        if (nodo.getDestino() != null) {
            resumen.putIfAbsent(nodo.getDestino(), new HashMap<>());
            Map<Recurso, Integer> recursos = resumen.get(nodo.getDestino());

            recursos.merge(nodo.getRecurso(), nodo.getRecurso().getCantidadDisponible(), Integer::sum);
        }

        for (NodoDistribucion hijo : nodo.getHijos()) {
            recorrerNodo(hijo, resumen);
        }
    }

    @Override
    public String toString() {
        return "ArbolDistribucion{" +
                "raiz=" + (raiz != null ? raiz.getRecurso().getNombre() : "null") +
                '}';
    }
}
