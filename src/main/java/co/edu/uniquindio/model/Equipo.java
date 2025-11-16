package co.edu.uniquindio.model;

import java.util.List;
import java.util.stream.Collectors;

public class Equipo {

    private String nombre;
    private List<String> integrantes;
    private Ubicacion zonaAsignada;

    public Equipo(String nombre, List<String> integrantes, Ubicacion zonaAsignada) {
        this.nombre = nombre;
        this.integrantes = integrantes;
        this.zonaAsignada = null; //Sin asignar inicialmente
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getIntegrantes() {
        return integrantes;
    }

    public String getIntegrantesString(){
        return integrantes.stream().collect(Collectors.joining(", "));
    }

    public Ubicacion getZonaAsignada() {
        return zonaAsignada;
    }

    public void setZonaAsignada(Ubicacion zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }
}
