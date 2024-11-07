package modelos;

public class Recurso {

    private String tipo; // Nombre del recurso, "Luces", "Pantalla", "Proyector"
    private String descripcion;
    private boolean disponible;

    public Recurso(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponible = true; // Se inicia como Disponible
    }

    //region Métodos Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    //endregion

    public void marcarComoDisponible() {
        this.disponible = true;
    }

    public void marcarComoNoDisponible() {
        this.disponible = false;
    }

    public String obtenerDetalles() {
        return "Tipo: " + tipo + "\nDescripción: " + descripcion + "\nDisponible: " + (disponible ? "Sí" : "No");
    }
}
