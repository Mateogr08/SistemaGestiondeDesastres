package co.edu.uniquindio.model;

public class Usuario {

    private String nombre;
    private String nombreUsuario;
    private String contrasena;
    private Rol rol;

    public Usuario(String nombre, String nombreUsuario, String contrasena, Rol rol) {

        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if(nombreUsuario == null || nombreUsuario.isBlank()){
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }

        if(contrasena == null || contrasena.isBlank()){
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        if(rol == null){
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if(nombreUsuario == null || nombreUsuario.isBlank()){
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        if(contrasena == null || contrasena.isBlank()){
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        if(rol == null){
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        this.rol = rol;
    }

    public boolean validarContrasena(String contrasenaIngresada) {
       return this.contrasena.equals(contrasenaIngresada);
    }

    @Override
    public String toString() {
        return String.format("Usuario[nombre = '%s', usuario = '%s', rol = '%s'", nombre, nombreUsuario, rol);
    }
}
