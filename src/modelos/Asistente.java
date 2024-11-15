package modelos;

public class Asistente {

    private String nombre;
    private String email;
    private boolean esConfirmado;


    public Asistente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.esConfirmado = false; // Se inicia como no confirmado
    }

    //region Métodos Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEsConfirmado() {
        return esConfirmado;
    }
    //endregion
    public void confirmarAsistencia() {
        this.esConfirmado = true;
    }

    public String obtenerInformacion() {
        return "Nombre: " + nombre + "\nEmail: " + email + "\nAsistencia confirmada: " + (esConfirmado ? "Sí" : "No");
    }

    public String toString(){
        return nombre + ";" + email + ";" + esConfirmado;
    }
}
