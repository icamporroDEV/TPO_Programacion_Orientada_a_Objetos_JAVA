package servicios;

import modelos.Asistente;
import modelos.Evento;

public class NotificacionServicio {

    // Envía una notificación a un invitado especificado
    public void enviarNotificacion(Asistente asistente, String mensaje) {
        if (asistente != null) {
            System.out.println("Enviando notificación a: " + asistente.getNombre());
            System.out.println("Email: " + asistente.getEmail());
            System.out.println("Mensaje: " + mensaje);
            System.out.println("Notificación enviada exitosamente.\n");
        } else {
            System.out.println("Asistente no válido. No se pudo enviar la notificación.");
        }
    }

    // Envía notificaciones a todos los asistentes de un evento
    public void enviarNotificacionATodos(Evento evento, String mensaje) {
        if (evento != null && !evento.getAsistentes().isEmpty()) {
            System.out.println("Enviando notificaciones a todos los asistentes del evento: " + evento.getNombre());
            for (Asistente asistente : evento.getAsistentes()) {
                enviarNotificacion(asistente, mensaje);
            }
        } else {
            System.out.println("No hay asistentes registrados para el evento o el evento no es válido.");
        }
    }
}
