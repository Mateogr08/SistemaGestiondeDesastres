package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase encargada de gestionar la lista de equipos disponibles en el sistema.
 */
public class GestorEquipos {

    // Lista estática que almacena todos los equipos registrados
    private static List<Equipo> listaEquipos = List.of();

    /**
     * Constructor que inicializa la lista de equipos como una lista mutable vacía.
     */
    public GestorEquipos() {
        this.listaEquipos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo equipo a la lista de equipos.
     *
     * @param e el equipo que se desea agregar
     */
    public void agregarEquipo(Equipo e) {
        listaEquipos.add(e);
    }

    /**
     * Devuelve la lista completa de equipos registrados.
     *
     * @return lista de equipos
     */
    public static List<Equipo> getListaEquipos() {
        return listaEquipos;
    }
}