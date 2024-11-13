package servicios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public class Calendario extends JFrame {
    private JTable tablaCalendario;
    private DefaultTableModel modeloCalendario;
    private JLabel mesAnioLabel;
    private HashMap<String, String> eventos;
    private LocalDate fechaActual;

    public void verCalendario(){
        setTitle("Calendario de Eventos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        fechaActual = LocalDate.now();
        eventos = new HashMap<>();

        // Panel para el mes y el año
        JPanel panelSuperior = new JPanel();
        mesAnioLabel = new JLabel();
        JButton botonMesPrevio = new JButton("<");
        JButton botonMesSiguiente = new JButton(">");
        botonMesPrevio.addActionListener(e -> cambiarMes(-1));
        botonMesSiguiente.addActionListener(e -> cambiarMes(1));

        panelSuperior.add(botonMesPrevio);
        panelSuperior.add(mesAnioLabel);
        panelSuperior.add(botonMesSiguiente);
        add(panelSuperior, BorderLayout.NORTH);

        // Modelo de la tabla del calendario
        modeloCalendario = new DefaultTableModel(new String[]{"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCalendario = new JTable(modeloCalendario);
        tablaCalendario.setRowHeight(50);
        tablaCalendario.setCellSelectionEnabled(false); // Deshabilitar selección
        add(new JScrollPane(tablaCalendario), BorderLayout.CENTER);
        actualizarCalendario();
    }

    private void cambiarMes(int amount) {
        fechaActual = fechaActual.plusMonths(amount);
        actualizarCalendario();
    }

    private void actualizarCalendario() {
        modeloCalendario.setRowCount(0);
        mesAnioLabel.setText(fechaActual.getMonth() + " " + fechaActual.getYear());

        YearMonth anioMes = YearMonth.of(fechaActual.getYear(), fechaActual.getMonthValue());
        int diasMes = anioMes.lengthOfMonth();
        int primerDiaSemana = anioMes.atDay(1).getDayOfWeek().getValue() % 7;

        Object[] semana = new Object[7];
        int dia = 1;

        for (int i = 0; i < primerDiaSemana; i++) {
            semana[i] = null;
        }
        for (int i = primerDiaSemana; i < 7; i++) {
            semana[i] = dia++;
        }
        modeloCalendario.addRow(semana);

        while (dia <= diasMes) {
            semana = new Object[7];
            for (int i = 0; i < 7 && dia <= diasMes; i++) {
                semana[i] = dia++;
            }
            modeloCalendario.addRow(semana);
        }
        ponerEventoCalendario();
    }

    private void ponerEventoCalendario() {
        for (int fila = 0; fila < tablaCalendario.getRowCount(); fila++) {
            for (int col = 0; col < tablaCalendario.getColumnCount(); col++) {
                Object valorDia = tablaCalendario.getValueAt(fila, col);
                if (valorDia != null) {
                    String dia = String.valueOf(valorDia);
                    String nombre = auxiliarEvento(fechaActual.getYear(), fechaActual.getMonthValue(), Integer.parseInt(dia));
                    if (eventos.containsKey(nombre)) {
                        tablaCalendario.setValueAt(dia + ": " + eventos.get(nombre), fila, col);
                    }
                }
            }
        }
    }

    private String auxiliarEvento(int anio, int mes, int dia) {
        return anio + "-" + mes + "-" + dia;
    }

    public void addEvent(int anio, int mes, int dia, String nombreEvento) {
        String fecha = auxiliarEvento(anio, mes, dia);
        eventos.put(fecha, nombreEvento);
        if (anio == fechaActual.getYear() && mes == fechaActual.getMonthValue()) {
            actualizarCalendario();
        }
    }
}

