package servicios;

import modelos.Evento;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EventoServicio {
    private List<Evento> eventos;

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
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la nueva fecha del evento: ");
            evento.setFecha(scanner.nextLine());
            System.out.print("Ingrese la nueva ubicación del evento: ");
            evento.setUbicacion(scanner.nextLine());
            System.out.print("Ingrese la nueva descripción del evento: ");
            evento.setDescripcion(scanner.nextLine());
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
}
