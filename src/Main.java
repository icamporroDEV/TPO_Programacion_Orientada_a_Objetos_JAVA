import modelos.Asistente;
import modelos.Evento;
import modelos.Recurso;
import servicios.Calendario;
import servicios.EventoServicio;
import servicios.NotificacionServicio;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventoServicio eventoServicio = new EventoServicio();
        NotificacionServicio notificacionServicio = new NotificacionServicio();

        boolean salir = false;

        while (!salir) {
            System.out.println("===== Menú Principal =====");
            System.out.println("1. Gestión de Eventos");
            System.out.println("2. Gestión de Asistentes");
            System.out.println("3. Gestión de Recursos");
            System.out.println("4. Guardar información en archivo CSV");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcionPrincipal = scanner.nextInt();
            scanner.nextLine();  // Consumir nueva línea
            System.out.println(" ");
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
                    try {
                        eventoServicio.guardarEventos("eventos.csv");
                        System.out.println("Información guardada exitosamente en eventos.csv");
                    } catch (IOException e) {
                        System.out.println("Error al guardar en el archivo CSV: " + e.getMessage());
                    }
                    break;

                case 5:
                    salir = true;
                    System.out.println("Saliendo del sistema de gestión de eventos...");
                    System.exit(0);

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
        System.out.println(" ");
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
            System.out.println("5. Ver calendario");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume nueva línea
            System.out.println(" ");
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento (yyyy/mm/dd): ");
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
                    eventoServicio.mostrarCalendario();
                    break;

                case 6:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        System.out.println(" ");
    }

    // Submenús para la gestión de Invitados
    private static void menuGestionAsistentes(Scanner scanner, EventoServicio eventoServicio) {
        boolean volver = false;

        while (!volver) {
            System.out.println("===== Gestión de Asistentes =====");
            System.out.println("1. Agregar Asistente a Evento");
            System.out.println("2. Eliminar Asistente de Evento");
            System.out.println("3. Enviar Notificación a Asistentes");
            System.out.println("4. Confirmar Asistencia");
            System.out.println("5. Ver Asistentes de Evento");
            System.out.println("6. Agregar Feedback Evento");
            System.out.println("7. Ver Feedback de Evento");
            System.out.println("8. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consume nueva línea
            System.out.println(" ");
            switch (opcion) {
                case 1:
                    eventoServicio.mostrarNombresEventos();
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
                    eventoServicio.mostrarNombresEventos();
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
                    eventoServicio.mostrarNombresEventos();
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
                    System.out.println("Seleccione el evento para confirmar asistencia de un asistente:");
                    eventoServicio.mostrarNombresEventos(); // Muestra solo los nombres de los eventos
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombreEvento = scanner.nextLine();

                    // Muestra asistentes del evento seleccionado
                    Evento evento = eventoServicio.buscarEventoPorNombre(nombreEvento);
                    if (evento != null && !evento.getAsistentes().isEmpty()) {
                        System.out.println("Asistentes del evento:");
                        for (int i = 0; i < evento.getAsistentes().size(); i++) {
                            System.out.println((i + 1) + ". " + evento.getAsistentes().get(i).obtenerInformacion());
                        }

                        // Seleccionar asistente para confirmar asistencia
                        System.out.print("Seleccione el número del asistente para confirmar su asistencia: ");
                        int numeroAsistente = scanner.nextInt();
                        scanner.nextLine(); // Consumir nueva línea

                        if (numeroAsistente > 0 && numeroAsistente <= evento.getAsistentes().size()) {
                            Asistente asistenteSeleccionado = evento.getAsistentes().get(numeroAsistente - 1);
                            asistenteSeleccionado.confirmarAsistencia();
                            System.out.println("Asistencia confirmada para: " + asistenteSeleccionado.getNombre());
                        } else {
                            System.out.println("Número de asistente no válido.");
                        }
                    } else if (evento != null) {
                        System.out.println("No hay asistentes registrados en este evento.");
                    } else {
                        System.out.println("Evento no encontrado.");
                    }
                    break;

                case 5:
                    // Nueva opción para ver asistentes de un evento específico
                    System.out.println("Seleccione el evento para ver los asistentes:");
                    eventoServicio.mostrarNombresEventos();
                    System.out.print("Ingrese el nombre del evento: ");
                    nombreEvento = scanner.nextLine();

                    eventoServicio.mostrarAsistentesEvento(nombreEvento);
                    break;

                case 6:
                    System.out.println("Seleccione el evento para enviar Feedback:");
                    eventoServicio.mostrarNombresEventos(); // Muestra solo los nombres de los eventos
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombreEventoF = scanner.nextLine();
                    Evento eventoF = eventoServicio.buscarEventoPorNombre(nombreEventoF);
                    if (eventoF != null && !eventoF.getAsistentes().isEmpty()) {
                        System.out.println("Asistentes del evento:");
                        for (int i = 0; i < eventoF.getAsistentes().size(); i++) {
                            if(eventoF.getAsistentes().get(i).isEsConfirmado()){
                                System.out.println((i + 1) + ". " + eventoF.getAsistentes().get(i).getNombre());
                            }
                        }
                    }
                    System.out.print("Seleccione el número del asistente para tomar su Feedback: ");
                    int asistenteF = scanner.nextInt();
                    while (asistenteF < 0 || asistenteF > eventoF.getAsistentes().size() || !eventoF.getAsistentes().get(asistenteF-1).isEsConfirmado()){
                        System.out.print("Error, Ingrese nuevamente el numero de asistente: ");
                        asistenteF = scanner.nextInt();
                    }
                    System.out.print("Ingrese la puntuación del evento: ");
                    int puntuacionF = scanner.nextInt();
                    eventoF.agregarFeedback(puntuacionF,asistenteF-1);
                    break;

                case 7:
                    System.out.println("Seleccione el evento para ver su Feedback:");
                    eventoServicio.mostrarNombresEventos(); // Muestra solo los nombres de los eventos
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombreEventoFV = scanner.nextLine();
                    Evento eventoFV = eventoServicio.buscarEventoPorNombre(nombreEventoFV);
                    System.out.println("Nombre         Feedback");
                    for (int i = 0; i < eventoFV.getAsistentes().size(); i++) {
                        System.out.println((eventoFV.getAsistentes().get(i).getNombre() + "               " + eventoFV.getFeedback().get(i)));
                    }
                    int suma = 0; //suma del total de los feedbacks distintos a -1
                    int contador = 0; //cantidad de feedbacks que estan cargados
                    for(int i = 0; i < eventoFV.getFeedback().size(); i++){
                        if (eventoFV.getFeedback().get(i) != -1){
                            suma += eventoFV.getFeedback().get(i);
                            contador++;
                        }
                    }

                    System.out.println("Promedio Feedback = " + (suma/contador));
                    break;

                case 8:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            System.out.println(" ");
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
            System.out.println(" ");
            switch (opcion) {
                case 1:
                    eventoServicio.mostrarNombresEventos();
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
                    eventoServicio.mostrarNombresEventos();
                    System.out.print("Ingrese el nombre del evento para listar los recursos disponibles: ");
                    String nombreEventoListarRecursos = scanner.nextLine();
                    Evento eventoParaListarRecursos = eventoServicio.buscarEventoPorNombre(nombreEventoListarRecursos);

                    if (eventoParaListarRecursos != null) {
                        System.out.println(" ");
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
                    eventoServicio.mostrarNombresEventos();
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
            System.out.println(" ");
        }
    }
}
