package co.edu.uniquindio.model;
/**
 * Clase encargada de generar reportes relacionados con recursos y evacuaciones
 * a partir de los gestores correspondientes.
 */
public class GeneradorReportes {

    private GestorRecursos gestorRecursos;
    private GestorEvacuacion gestorEvacuacion;

    /**
     * Constructor que inicializa el generador de reportes con los gestores de recursos y evacuación.
     *
     * @param gestorRecursos   instancia del gestor de recursos
     * @param gestorEvacuacion instancia del gestor de evacuación
     */
    public GeneradorReportes(GestorRecursos gestorRecursos, GestorEvacuacion gestorEvacuacion) {
        this.gestorRecursos = gestorRecursos;
        this.gestorEvacuacion = gestorEvacuacion;
    }

    /**
     * Genera un reporte textual del inventario global de recursos.
     *
     * @return cadena con el reporte de recursos
     */
    public String generarReporteRecursos() {
        return "Reporte de Recursos: " + gestorRecursos.getInventarioGlobal();
    }

    /**
     * Genera un reporte textual del número de zonas pendientes de evacuación.
     *
     * @return cadena con el reporte de evacuaciones
     */
    public String generarReporteEvacuaciones() {
        return "Zonas pendientes de evacuación: " + gestorEvacuacion.getColaEvacuacion().size();
    }
}