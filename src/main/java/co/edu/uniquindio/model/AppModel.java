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

        // ubicaciones
        Ubicacion ciudadA = new Ubicacion("Ciudad A", "Ciudad", 1200, 9, null);
        Ubicacion ciudadB = new Ubicacion("Ciudad B", "Refugio", 400, 6, null);
        Ubicacion centroC = new Ubicacion("Centro C", "Centro de Ayuda", 200, 8, null);

        // añadir recursos a ubicaciones
        ciudadA.agregarRecurso(agua, 100);
        ciudadB.agregarRecurso(alimentos, 50);
        centroC.agregarRecurso(kits, 30);

        // agregar ubicaciones y rutas al grafo
        grafoRutas.agregarUbicacion(ciudadA);
        grafoRutas.agregarUbicacion(ciudadB);
        grafoRutas.agregarUbicacion(centroC);

        grafoRutas.agregarRuta(ciudadA, ciudadB, 25);
        grafoRutas.agregarRuta(ciudadB, centroC, 12);
        grafoRutas.agregarRuta(ciudadA, centroC, 30);

        // registrar zonas en cola de evacuación
        gestorEvacuacion.agregarZonaEvacuacion(ciudadA);
        gestorEvacuacion.agregarZonaEvacuacion(centroC);

        // registrar equipos
        gestorEquipos.agregarEquipo(new Equipo("Equipo Alpha", Arrays.asList("Juan", "Ana"), null));
        gestorEquipos.agregarEquipo(new Equipo("Equipo Beta", Arrays.asList("Luis", "Maria"), null));
    }
}