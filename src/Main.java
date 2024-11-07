import modelos.Asistente;
import modelos.Evento;
import modelos.Recurso;
import servicios.EventoServicio;
import servicios.NotificacionServicio;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventoServicio eventoServicio = new EventoServicio();

        boolean salir = false;

        while (!salir) {
            System.out.println("===== Menú Principal =====");
            System.out.println("1. Gestión de Eventos");
            System.out.println("2. Gestión de Asistentes");
            System.out.println("3. Gestión de Recursos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();  // Consume nueva línea

            switch (opcionPrincipal) {
                case 1:
                    menuGestionEventos(scanner, eventoServicio);
                    break;

                case 2:
                    menuGestionAsistentes(scanner, eventoServicio);
                    break;

                case 3:
                    menuGestionRecursos(scanner, eventoServicio);
                    break;

                case 4:
                    salir = true;
                    System.out.println("Saliendo del sistema de gestión de eventos, gracias por elegirnos!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }
        scanner.close();
    }

    // Submenú para la gestión de eventos
    private static void menuGestionEventos(Scanner scanner, EventoServicio eventoServicio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("===== Gestión de Eventos =====");
            System.out.println("1. Crear Evento");
            System.out.println("2. Modificar Evento");
            System.out.println("3. Listar Eventos");
            System.out.println("4. Eliminar Evento");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento: ");
                    String fecha = scanner.nextLine();
                    System.out.print("Ingrese la ubicación del evento: ");
                    String ubicacion = scanner.nextLine();
                    System.out.print("Ingrese una descripción del evento: ");
                    String descripcion = scanner.nextLine();

                    Evento evento = new Evento(nombre, fecha, ubicacion, descripcion);
                    eventoServicio.crearEvento(evento);
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del evento a modificar: ");
                    String nombreEvento = scanner.nextLine();
                    eventoServicio.modificarEvento(nombreEvento);
                    break;

                case 3:
                    System.out.println("===== Listado de Eventos =====");
                    eventoServicio.mostrarDetallesEventos();
                    break;

                case 4:
                    System.out.print("Ingrese el nombre del evento a eliminar: ");
                    String eventoAEliminar = scanner.nextLine();
                    eventoServicio.eliminarEvento(eventoAEliminar);
                    break;

                case 5:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }
    }

    // Submenús para la gestión de Invitados
    private static void menuGestionAsistentes(Scanner scanner, EventoServicio eventoServicio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("===== Gestión de Asistentes =====");
            System.out.println("1. Agregar Invitado a Evento");
            System.out.println("2. Eliminar Invitado de Evento");
            System.out.println("3. Enviar Notificación a Invitados");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume nueva línea

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del evento al que desea agregar un Invitado: ");
                    String nombreEventoAsistente = scanner.nextLine();
                    Evento eventoParaAsistente = eventoServicio.buscarEventoPorNombre(nombreEventoAsistente);

                    if (eventoParaAsistente != null) {
                        System.out.print("Ingrese el nombre del Invitado: ");
                        String nombreAsistente = scanner.nextLine();
                        System.out.print("Ingrese el email del Invitado: ");
                        String emailAsistente = scanner.nextLine();

                        Asistente asistente = new Asistente(nombreAsistente, emailAsistente);
                        eventoParaAsistente.agregarAsistente(asistente);
                        System.out.println("Asistente agregado exitosamente.");
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del evento del que desea eliminar un Invitado: ");
                    String nombreEventoEliminarAsistente = scanner.nextLine();
                    Evento eventoParaEliminarAsistente = eventoServicio.buscarEventoPorNombre(nombreEventoEliminarAsistente);

                    if (eventoParaEliminarAsistente != null) {
                        System.out.print("Ingrese el nombre del Invitado que desea eliminar: ");
                        String nombreAsistenteEliminar = scanner.nextLine();

                        Asistente asistenteAEliminar = null;
                        for (Asistente asistente : eventoParaEliminarAsistente.getAsistentes()) {
                            if (asistente.getNombre().equalsIgnoreCase(nombreAsistenteEliminar)) {
                                asistenteAEliminar = asistente;
                                break;
                            }
                        }

                        if (asistenteAEliminar != null) {
                            eventoParaEliminarAsistente.eliminarAsistente(asistenteAEliminar);
                            System.out.println("Invitado eliminado exitosamente.");
                        } else {
                            System.out.println("Invitado no encontrado en el evento.");
                        }
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el nombre del evento para enviar notificación: ");
                    String nombreEventoNotificacion = scanner.nextLine();
                    Evento eventoParaNotificacion = eventoServicio.buscarEventoPorNombre(nombreEventoNotificacion);

                    if (eventoParaNotificacion != null) {
                        System.out.print("Ingrese el mensaje de notificación: ");
                        String mensaje = scanner.nextLine();
                        NotificacionServicio notificacionServicio = new NotificacionServicio();
                        notificacionServicio.enviarNotificacionATodos(eventoParaNotificacion, mensaje);
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 4:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }
    }

    // Submenú para la gestión de recursos
    private static void menuGestionRecursos(Scanner scanner, EventoServicio eventoServicio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("===== Gestión de Recursos =====");
            System.out.println("1. Agregar Recurso a Evento");
            System.out.println("2. Listar Recursos de Evento");
            System.out.println("3. Eliminar Recurso de Evento");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del evento al que desea agregar un recurso: ");
                    String nombreEventoRecurso = scanner.nextLine();
                    Evento eventoParaRecurso = eventoServicio.buscarEventoPorNombre(nombreEventoRecurso);

                    if (eventoParaRecurso != null) {
                        System.out.print("Ingrese el tipo de recurso (ej: Pantalla, Proyector, etc.): ");
                        String tipoRecurso = scanner.nextLine();
                        System.out.print("Ingrese una descripción para el recurso: ");
                        String descripcionRecurso = scanner.nextLine();

                        Recurso recurso = new Recurso(tipoRecurso, descripcionRecurso);
                        eventoParaRecurso.agregarRecurso(recurso);
                        System.out.println("Recurso agregado exitosamente.");
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del evento para listar los recursos disponibles: ");
                    String nombreEventoListarRecursos = scanner.nextLine();
                    Evento eventoParaListarRecursos = eventoServicio.buscarEventoPorNombre(nombreEventoListarRecursos);

                    if (eventoParaListarRecursos != null) {
                        System.out.println("===== Recursos del Evento '" + eventoParaListarRecursos.getNombre() + "' =====");
                        for (Recurso recurso : eventoParaListarRecursos.getRecursos()) {
                            System.out.println(recurso.obtenerDetalles());
                            System.out.println("-------------");
                        }
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el nombre del evento del que desea eliminar un recurso: ");
                    String nombreEventoEliminarRecurso = scanner.nextLine();
                    Evento eventoParaEliminarRecurso = eventoServicio.buscarEventoPorNombre(nombreEventoEliminarRecurso);

                    if (eventoParaEliminarRecurso != null) {
                        System.out.print("Ingrese el tipo (nombre) del recurso que desea eliminar: ");
                        String tipoRecursoEliminar = scanner.nextLine();

                        Recurso recursoAEliminar = null;
                        for (Recurso recurso : eventoParaEliminarRecurso.getRecursos()) {
                            if (recurso.getTipo().equalsIgnoreCase(tipoRecursoEliminar)) {
                                recursoAEliminar = recurso;
                                break;
                            }
                        }

                        if (recursoAEliminar != null) {
                            eventoParaEliminarRecurso.eliminarRecurso(recursoAEliminar);
                            System.out.println("Recurso eliminado exitosamente.");
                        } else {
                            System.out.println("Recurso no encontrado en el evento.");
                        }
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 4:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println();
        }
    }
}
