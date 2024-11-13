package servicios;

import modelos.Asistente;
import modelos.Evento;

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
    private Calendario calendario = new Calendario();

    public EventoServicio() {
        this.eventos = new ArrayList<>();
    }

    public void crearEvento(Evento evento) {
        eventos.add(evento);
        System.out.println("Evento '" + evento.getNombre() + "' creado exitosamente.");
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

    public void eliminarEvento(String nombre) {
        Evento evento = buscarEventoPorNombre(nombre);
        if (evento != null) {
            eventos.remove(evento);
            System.out.println("Evento '" + nombre + "' eliminado exitosamente.");
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

    public void guardarEventos(String nombreArchivo) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Nombre;Fecha;Ubicacion;Descripcion;Asistentes;Recursos\n"); // Encabezado CSV
            for (Evento evento : eventos) {
                writer.write(evento.FormatoCSV() + "\n");
            }
        }
    }

    public void mostrarCalendario(){

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
}
