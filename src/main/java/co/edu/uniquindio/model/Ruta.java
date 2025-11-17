package co.edu.uniquindio.model;

public class Ruta {

    private Ubicacion origen;
    private Ubicacion destino;
    private double distancia;
    private boolean disponible;

    public Ruta(Ubicacion origen, Ubicacion destino, double distancia, boolean disponible) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.disponible = disponible;
    }

    public Ubicacion getOrigen() {
        return origen;
    }

    public void setOrigen(Ubicacion origen) {
        this.origen = origen;
    }

    public Ubicacion getDestino() {
        return destino;
    }

    public void setDestino(Ubicacion destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "origen=" + origen +
                ", destino=" + destino +
                ", distancia=" + distancia +
                ", disponible=" + disponible +
                '}';
    }
}
