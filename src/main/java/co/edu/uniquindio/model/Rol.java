package co.edu.uniquindio.model;

public enum Rol {

    ADMINISTRADOR ("Administrador del sistema"),
    OPERADOR("Operador del sistema");

    private final String descripcion;

    Rol(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String toString(){
        return descripcion;
    }
}
