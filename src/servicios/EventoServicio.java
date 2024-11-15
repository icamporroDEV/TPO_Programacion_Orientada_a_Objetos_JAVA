package servicios;

import modelos.Asistente;
import modelos.Evento;
import modelos.Recurso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public class EventoServicio {
    private List<Evento> eventos;


    public EventoServicio() {
        this.eventos = new ArrayList<>();
    }

    public void crearEvento(Evento evento){
        eventos.add(evento);
        System.out.println("Evento " + evento.getNombre() + " creado exitosamente.");
    }

    public Evento buscarEventoPorNombre(String nombre) {
        Evento eventoReferencia = new Evento(nombre, "", "", ""); // Evento temporal para comparar por nombre
        for (Evento evento : eventos) {
            if (evento.compareTo(eventoReferencia) == 0) { // Usamos compareTo para comparar por nombre
                return evento;
            }
        }
        return null; // Devuelve null si no encuentra el evento
    }

    public void modificarEvento(String nombre) {
        Evento evento = buscarEventoPorNombre(nombre);
        if (evento != null) {
            System.out.println("Evento encontrado: " + evento.getNombre());
            System.out.println("===== Elija Modificación =====");
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Modificar Fecha");
            System.out.println("2. Modificar Ubicación");
            System.out.println("3. Modificar Descripción");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            System.out.println(" ");

            switch (opcion){
                case 1:
                    System.out.print("Ingrese la nueva fecha del evento (yyyy/mm/dd): ");
                    evento.setFecha(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Ingrese la nueva ubicación del evento: ");
                    evento.setUbicacion(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Ingrese la nueva descripción del evento: ");
                    evento.setDescripcion(scanner.nextLine());
                    break;
            }
            System.out.println("Evento modificado exitosamente.");
        } else {
            System.out.println("Evento '" + nombre + "' no encontrado.");
        }
    }

    public List<Evento> listarEventos() {
        return eventos;
    }

    public void eliminarEvento(String nombre){
        Evento evento = buscarEventoPorNombre(nombre);
        if (evento != null) {
            eventos.remove(evento);

        } else {
            System.out.println("Evento '" + nombre + "' no encontrado.");
        }
    }

    public void mostrarDetallesEventos() {
        if (eventos.isEmpty()) {
            System.out.println("No hay eventos registrados.");
        } else {
            for (Evento evento : eventos) {
                System.out.println(evento.obtenerDetalles());
                System.out.println("-------------");
            }
        }
    }

    public void mostrarNombresEventos() {
        System.out.println("===== Lista de Eventos =====");
        for (Evento evento : eventos) {
            System.out.println("- " + evento.getNombre());
        }
    }

    public void mostrarAsistentesEvento(String nombreEvento) {
        Evento evento = buscarEventoPorNombre(nombreEvento);
        if (evento != null && !evento.getAsistentes().isEmpty()) {
            System.out.println("===== Asistentes del Evento: " + nombreEvento + " =====");
            for (Asistente asistente : evento.getAsistentes()) {
                System.out.println(asistente.obtenerInformacion());
            }
        } else if (evento != null) {
            System.out.println("No hay asistentes registrados en el evento.");
        } else {
            System.out.println("Evento no encontrado.");
        }
    }

    public void guardarEventos() {
        try (FileWriter writer = new FileWriter("eventos.csv")) {
            for (Evento evento : eventos) {
                writer.append(evento.FormatoEventoCSV() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void guardarRecursos(){
        try (FileWriter writer = new FileWriter("recursos.csv")) {
            for (Evento evento : eventos) {
                for(Recurso recurso : evento.getRecursos()){
                    writer.append(evento.getNombre() + ";" + recurso.FormatoRecursoCSV() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void guardarAsistentes(){
        try (FileWriter writer = new FileWriter("asistentes.csv")) {
            for (Evento evento : eventos) {
                for(Asistente asistente : evento.getAsistentes()){
                    writer.append(evento.getNombre() + ";" + asistente.toString() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void guardarFeedback(){
        try (FileWriter writer = new FileWriter("feedback.csv")) {
            for (Evento evento : eventos) {
                List<Integer> aux = evento.getFeedback();
                for(int i = 0; i < evento.getAsistentes().size(); i++){
                    writer.append(evento.getNombre() + ";" + evento.getAsistentes().get(i).getNombre() + ";" + aux.get(i) + "\n");
                }
            }
        }catch(IOException e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void mostrarCalendario(){
        Calendario calendario = new Calendario();
        calendario.setVisible(true);
        calendario.verCalendario();
        for (Evento evento : eventos){
            String fecha = evento.getFecha();
            String[] aux = fecha.split("/");
            int dia = Integer.parseInt(aux[2]);
            int mes = Integer.parseInt(aux[1]);
            int anio = Integer.parseInt(aux[0]);
            calendario.agregarEventoCalendario(anio, mes, dia, evento.getNombre());
        }
    }

    public void cargarDatosEventos(){
        BufferedReader lector = null;
        String linea = "";
        try{
            lector = new BufferedReader(new FileReader("eventos.csv"));
            while((linea = lector.readLine()) != null){
                String[] fila = linea.split(";");
                Evento evento = new Evento(fila[0], fila[1], fila[2], fila[3]);
                eventos.add(evento);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void cargarDatosRecursos(){
        BufferedReader lector = null;
        String linea = "";
        try{
            lector = new BufferedReader(new FileReader("recursos.csv"));
            while((linea = lector.readLine()) != null){
                String[] fila = linea.split(";");
                Recurso recurso = new Recurso(fila[1], fila[2]);
                Evento auxiliar = buscarEventoPorNombre(fila[0]);
                auxiliar.agregarRecurso(recurso);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void cargarDatosAsistentes(){
        BufferedReader lector = null;
        String linea = "";
        try{
            lector = new BufferedReader(new FileReader("asistentes.csv"));
            while((linea = lector.readLine()) != null){
                String[] fila = linea.split(";");
                Asistente asistente = new Asistente(fila[1], fila[2]);
                if (fila[3].equals("true")){
                    asistente.confirmarAsistencia();
                }
                Evento auxiliar = buscarEventoPorNombre(fila[0]);
                auxiliar.agregarAsistente(asistente);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void cargarDatosFeedback(){
        BufferedReader lector = null;
        String linea = "";
        try{
            lector = new BufferedReader(new FileReader("feedback.csv"));
            while((linea = lector.readLine()) != null){
                String[] fila = linea.split(";");
                Evento auxiliar = buscarEventoPorNombre(fila[0]);
                if(Integer.parseInt(fila[2]) >= 0){
                    auxiliar.agregarFeedback(Integer.parseInt(fila[2]), auxiliar.buscarAsistente(fila[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }

    public void cargarDatos(){
        cargarDatosEventos();
        cargarDatosRecursos();
        cargarDatosAsistentes();
        cargarDatosFeedback();
    }
}
