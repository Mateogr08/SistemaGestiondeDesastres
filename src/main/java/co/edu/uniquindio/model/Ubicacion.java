package co.edu.uniquindio.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ubicacion {

    private String nombre;
    private String tipo;
    private int personasAfectadas;
    private int nivelUrgencia;
    private final Map<Recurso, Integer> recursos;
    private double latitud;
    private double longitud;

    /**
     * Crea una nueva instancia de Ubicacion con todos sus atributos.
     *
     * @param nombre nombre de la ubicación (no puede ser vacío)
     * @param tipo tipo de ubicación (no puede ser vacío)
     * @param personasAfectadas cantidad de personas afectadas (>= 0)
     * @param nivelUrgencia nivel de urgencia entre 1 y 10
     * @param latitud latitud geográfica
     * @param longitud longitud geográfica
     *
     * @throws IllegalArgumentException si algún parámetro obligatorio es inválido
     */
    public Ubicacion(String nombre, String tipo, int personasAfectadas, int nivelUrgencia,
                     double latitud, double longitud) {

        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la ubicación no puede estar vacío.");

        if (tipo == null || tipo.isBlank())
            throw new IllegalArgumentException("El tipo de ubicación no puede estar vacío.");

        if (personasAfectadas < 0)
            throw new IllegalArgumentException("El número de personas afectadas no puede ser negativo.");

        this.nombre = nombre;
        this.tipo = tipo;
        this.personasAfectadas = personasAfectadas;
        this.nivelUrgencia = validarNivelUrgencia(nivelUrgencia);
        this.recursos = new HashMap<>();
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /** @return nombre de la ubicación */
    public String getNombre() { return nombre; }

    /**
     * Establece un nuevo nombre para la ubicación.
     * @param nombre nombre válido
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return tipo de ubicación */
    public String getTipo() { return tipo; }

    /**
     * Establece el tipo de la ubicación.
     * @param tipo tipo de ubicación
     */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return cantidad de personas afectadas */
    public int getPersonasAfectadas() { return personasAfectadas; }

    /**
     * Cambia la cantidad de personas afectadas.
     * @param personasAfectadas número de personas
     */
    public void setPersonasAfectadas(int personasAfectadas) { this.personasAfectadas = personasAfectadas; }

    /** @return nivel de urgencia (1 a 10) */
    public int getNivelUrgencia() { return nivelUrgencia; }

    /**
     * Actualiza el nivel de urgencia asegurando que esté entre 1 y 10.
     * @param nivelUrgencia nivel deseado
     */
    public void setNivelUrgencia(int nivelUrgencia) {
        this.nivelUrgencia = validarNivelUrgencia(nivelUrgencia);
    }

    /** @return mapa de recursos con su cantidad */
    public Map<Recurso, Integer> getRecursos() { return recursos; }

    /** @return latitud geográfica */
    public double getLatitud() { return latitud; }

    /**
     * Establece la latitud.
     * @param latitud coordenada geográfica
     */
    public void setLatitud(double latitud) { this.latitud = latitud; }

    /** @return longitud geográfica */
    public double getLongitud() { return longitud; }

    /**
     * Establece la longitud.
     * @param longitud coordenada geográfica
     */
    public void setLongitud(double longitud) { this.longitud = longitud; }

    /**
     * Agrega una cantidad específica de un recurso a la ubicación. Si el recurso
     * ya existe, se suma la cantidad.
     *
     * @param recurso recurso a agregar
     * @param cantidad cantidad positiva
     *
     * @throws IllegalArgumentException si el recurso es nulo o la cantidad es inválida
     */
    public void agregarRecurso(Recurso recurso, int cantidad) {
        if (recurso == null || cantidad <= 0)
            throw new IllegalArgumentException("Recurso nulo o cantidad inválida.");
        recursos.merge(recurso, cantidad, Integer::sum);
    }

    /**
     * Consume una cantidad de un recurso existente. Si la cantidad restante llega a 0,
     * se elimina del mapa.
     *
     * @param recurso recurso afectado
     * @param cantidad cantidad a consumir
     */
    public void consumirRecurso(Recurso recurso, int cantidad) {
        if (recurso == null || cantidad <= 0 || !recursos.containsKey(recurso)) return;

        int restante = recursos.get(recurso) - cantidad;
        if (restante <= 0) recursos.remove(recurso);
        else recursos.put(recurso, restante);
    }

    /**
     * @return un mapa de todos los recursos disponibles en la ubicación
     */
    public Map<Recurso, Integer> getRecursosDisponibles() {
        return recursos;
    }

    /**
     * Verifica si la ubicación está en un estado crítico.
     * Una zona crítica tiene nivel de urgencia mayor o igual a 7.
     *
     * @return true si es zona crítica, false en caso contrario
     */
    public boolean esZonaCritica() { return nivelUrgencia >= 7; }

    /**
     * Genera un resumen básico de la ubicación.
     *
     * @return texto con datos principales
     */
    public String resumen() {
        return String.format("📍 %s (%s) | Afectados: %d | Urgencia: %d | Recursos: %d",
                nombre, tipo, personasAfectadas, nivelUrgencia, recursos.size());
    }

    /**
     * Convierte la lista de recursos y sus cantidades en un string legible.
     *
     * @return lista de recursos en formato "Recurso xCantidad"
     */
    public String recursosComoString() {
        return recursos.entrySet()
                .stream()
                .map(e -> e.getKey().getNombre() + " x" + e.getValue())
                .collect(Collectors.joining(", "));
    }

    /**
     * Asegura que el nivel de urgencia esté dentro del rango permitido (1 - 10).
     *
     * @param nivel nivel deseado
     * @return nivel ajustado si está fuera de rango
     */
    private int validarNivelUrgencia(int nivel) {
        return Math.max(1, Math.min(nivel, 10));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ubicacion)) return false;
        Ubicacion that = (Ubicacion) o;
        return Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() { return Objects.hash(nombre); }

    @Override
    public String toString() {
        return String.format("Ubicacion{nombre='%s', tipo='%s', personas=%d, urgencia=%d, recursos=%d, lat=%.6f, lon=%.6f}",
                nombre, tipo, personasAfectadas, nivelUrgencia, recursos.size(), latitud, longitud);
    }
}
