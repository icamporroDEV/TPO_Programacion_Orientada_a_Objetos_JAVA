package modelos;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Evento implements Comparable<Evento> {

    private String nombre;
    private String fecha;
    private String ubicacion;
    private String descripcion;
    private List<Asistente> asistentes;
    private List<Recurso> recursos;
    private List<Integer> feedback;


    public Evento(String nombre, String fecha, String ubicacion, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.asistentes = new ArrayList<>();
        this.recursos = new ArrayList<>();
        this.feedback = new ArrayList<>();
    }

    @Override
    public int compareTo(Evento otroEvento) {
        return this.nombre.compareToIgnoreCase(otroEvento.getNombre());
    }

    //region Métodos Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public List<Integer> getFeedback() {
        return feedback;
    }
    //endregion

    // Métodos la gestión de asistentes
    public void agregarAsistente(Asistente asistente) {
        this.asistentes.add(asistente);
        this.feedback.add(-1);
    }

    public void eliminarAsistente(Asistente asistente) {
        this.asistentes.remove(asistente);
    }

    // Métodos para gestión de recursos
    public void agregarRecurso(Recurso recurso) {
        this.recursos.add(recurso);
    }

    public void eliminarRecurso(Recurso recurso) {
        this.recursos.remove(recurso);
    }

    public void agregarFeedback(int puntuacion, int asistente) {
        while (puntuacion < 0 || puntuacion > 10) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Error, Ingrese nuevamente la puntuación: ");
            puntuacion = scanner.nextInt();
        }
        this.feedback.set(asistente, puntuacion);
    }

    public String obtenerDetalles() {
        String detalles = "Nombre: " + nombre + "\n" +
                "Fecha: " + fecha + "\n" +
                "Ubicación: " + ubicacion + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Número de asistentes: " + asistentes.size() + "\n" +
                "Número de recursos: " + recursos.size() + "\n";
        return detalles;
    }

    public int buscarAsistente(String nombre){
        int pos = -1;
        for(Asistente asistente: asistentes){
            if(asistente.getNombre().equals(nombre)){
                pos = asistentes.indexOf(asistente);
            }
        }
        return pos;
    }

    public String FormatoEventoCSV() {
        return String.format("%s;%s;%s;%s",
                nombre, fecha, ubicacion, descripcion);
    }
}
