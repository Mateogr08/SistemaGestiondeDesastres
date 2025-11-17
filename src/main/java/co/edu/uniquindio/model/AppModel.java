package co.edu.uniquindio.model;

import java.util.Arrays;
/**
 * Clase principal del modelo de la aplicación.
 * Implementa el patrón Singleton para centralizar el acceso a los gestores del sistema.
 * Permite inicializar datos de prueba para facilitar la visualización en la interfaz.
 */
public class AppModel {

    private static AppModel instance;

    private final GestorRecursos gestorRecursos;
    private final GestorEvacuacion gestorEvacuacion;
    private final GestorUsuarios gestorUsuarios;
    private final GrafoRutas grafoRutas;
    private final GestorEquipos gestorEquipos;

    /**
     * Constructor privado que inicializa todos los gestores del sistema.
     * Se utiliza solo una vez como parte del patrón Singleton.
     */
    private AppModel() {
        this.gestorRecursos = new GestorRecursos();
        this.gestorEvacuacion = new GestorEvacuacion();
        this.gestorUsuarios = new GestorUsuarios();
        this.grafoRutas = new GrafoRutas();
        this.gestorEquipos = new GestorEquipos();
    }

    /**
     * Devuelve la instancia única de AppModel.
     * Si no existe, la crea de forma sincronizada.
     *
     * @return instancia única de AppModel
     */
    public static synchronized AppModel getInstance() {
        if (instance == null) instance = new AppModel();
        return instance;
    }

    /**
     * Devuelve el gestor de equipos.
     *
     * @return instancia de GestorEquipos
     */
    public GestorEquipos getGestorEquipos() { return gestorEquipos; }

    /**
     * Devuelve el gestor de recursos.
     *
     * @return instancia de GestorRecursos
     */
    public GestorRecursos getGestorRecursos() {
        return gestorRecursos;
    }

    /**
     * Devuelve el gestor de evacuación.
     *
     * @return instancia de GestorEvacuacion
     */
    public GestorEvacuacion getGestorEvacuacion() {
        return gestorEvacuacion;
    }

    /**
     * Devuelve el gestor de usuarios.
     *
     * @return instancia de GestorUsuarios
     */
    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    /**
     * Devuelve el grafo de rutas.
     *
     * @return instancia de GrafoRutas
     */
    public GrafoRutas getGrafoRutas() {
        return grafoRutas;
    }

    /**
     * Carga datos de prueba en el sistema para facilitar la visualización inicial en la interfaz.
     * Incluye usuarios, recursos globales, ubicaciones, rutas, zonas de evacuación y equipos.
     */
    public void cargarDatosPrueba() {
        // usuarios
        gestorUsuarios.registrarUsuario(new Usuario("Admin Demo", "admin", "123", Rol.ADMINISTRADOR));
        gestorUsuarios.registrarUsuario(new Usuario("Operador Demo", "oper", "123", Rol.OPERADOR));

        // recursos globales
        Recurso agua = new Recurso("Agua", TipoRecurso.AGUA, 1000);
        Recurso alimentos = new Recurso("Alimentos", TipoRecurso.ALIMENTOS, 800);
        Recurso kits = new Recurso("Kits Médicos", TipoRecurso.MEDICINAS, 300);
        Recurso equipos = new Recurso("Equipo Rescate", TipoRecurso.EQUIPO_RESCATE, 20);

        gestorRecursos.registrarRecursoGlobal(agua);
        gestorRecursos.registrarRecursoGlobal(alimentos);
        gestorRecursos.registrarRecursoGlobal(kits);
        gestorRecursos.registrarRecursoGlobal(equipos);

        // Ubicaciones existentes
        Ubicacion ciudadA = new Ubicacion("Ciudad A", "Ciudad", 1200, 9, 4.5709, -74.2973);
        Ubicacion ciudadB = new Ubicacion("Ciudad B", "Refugio", 400, 6, 4.6097, -74.0817);
        Ubicacion centroC = new Ubicacion("Centro C", "Centro de Ayuda", 200, 8, 4.6250, -74.1000);
        Ubicacion refugioD = new Ubicacion("Refugio D", "Refugio", 300, 7, 4.6400, -74.1500);
        Ubicacion ciudadE = new Ubicacion("Ciudad E", "Ciudad", 500, 3, 4.5800, -74.2000);
        Ubicacion ciudadF = new Ubicacion("Ciudad F", "Ciudad", 350, 2, 4.5900, -74.1200);
        Ubicacion centroG = new Ubicacion("Centro G", "Centro de Ayuda", 150, 5, 4.6200, -74.1300);
        Ubicacion refugioH = new Ubicacion("Refugio H", "Refugio", 250, 7, 4.6000, -74.1600);

        // Asignar recursos
        ciudadA.agregarRecurso(agua, 150);
        ciudadA.agregarRecurso(kits, 50);
        ciudadB.agregarRecurso(alimentos, 70);
        centroC.agregarRecurso(kits, 40);
        refugioD.agregarRecurso(agua, 60);
        ciudadE.agregarRecurso(alimentos, 80);
        ciudadF.agregarRecurso(alimentos, 60);
        centroG.agregarRecurso(kits, 30);
        refugioH.agregarRecurso(agua, 50);

        // Agregar todas las ubicaciones al grafo
        for (Ubicacion u : Arrays.asList(ciudadA, ciudadB, centroC, refugioD, ciudadE, ciudadF, centroG, refugioH)) {
            grafoRutas.agregarUbicacion(u);
        }

        // Agregar rutas bidireccionales
        grafoRutas.agregarRuta(ciudadA, ciudadB, 25);
        grafoRutas.agregarRuta(ciudadB, ciudadA, 25);
        grafoRutas.agregarRuta(ciudadB, centroC, 12);
        grafoRutas.agregarRuta(centroC, ciudadB, 12);
        grafoRutas.agregarRuta(ciudadA, centroC, 30);
        grafoRutas.agregarRuta(centroC, ciudadA, 30);
        grafoRutas.agregarRuta(centroC, refugioD, 15);
        grafoRutas.agregarRuta(refugioD, centroC, 15);
        grafoRutas.agregarRuta(ciudadE, ciudadA, 20);
        grafoRutas.agregarRuta(ciudadA, ciudadE, 20);
        grafoRutas.agregarRuta(ciudadF, ciudadB, 10);
        grafoRutas.agregarRuta(ciudadB, ciudadF, 10);
        grafoRutas.agregarRuta(centroG, centroC, 8);
        grafoRutas.agregarRuta(centroC, centroG, 8);
        grafoRutas.agregarRuta(refugioH, refugioD, 12);
        grafoRutas.agregarRuta(refugioD, refugioH, 12);
        grafoRutas.agregarRuta(ciudadF, centroG, 14);
        grafoRutas.agregarRuta(centroG, ciudadF, 14);
        grafoRutas.agregarRuta(ciudadE, refugioH, 7);
        grafoRutas.agregarRuta(refugioH, ciudadE, 7);

        // Zonas de evacuación
        for (Ubicacion z : Arrays.asList(ciudadA, centroC, refugioD)) {
            gestorEvacuacion.agregarZonaEvacuacion(z);
        }

        // Equipos
        gestorEquipos.agregarEquipo(new Equipo("Equipo Alpha", Arrays.asList("Jhan", "Ana"), ciudadA));
        gestorEquipos.agregarEquipo(new Equipo("Equipo Beta", Arrays.asList("Luis", "Maria"), refugioD));
        gestorEquipos.agregarEquipo(new Equipo("Equipo Gamma", Arrays.asList("Carlos", "Sofía"), centroC));
        gestorEquipos.agregarEquipo(new Equipo("Equipo Omega", Arrays.asList("Andres", "Sara"), refugioH));
    }
}