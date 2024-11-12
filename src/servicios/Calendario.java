package servicios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

public class Calendario extends JFrame {
    private JTable calendarTable;
    private DefaultTableModel modeloCalendario;
    private JLabel mesAnioLabel;
    private HashMap<String, String> eventos;
    private LocalDate fechaActual;

    public Calendario() {
        setTitle("Calendario de Eventos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        fechaActual = LocalDate.now();
        eventos = new HashMap<>();

        // Panel para el mes y el año
        JPanel topPanel = new JPanel();
        mesAnioLabel = new JLabel();
        JButton prevMonthButton = new JButton("<");
        JButton nextMonthButton = new JButton(">");
        prevMonthButton.addActionListener(e -> changeMonth(-1));
        nextMonthButton.addActionListener(e -> changeMonth(1));

        topPanel.add(prevMonthButton);
        topPanel.add(mesAnioLabel);
        topPanel.add(nextMonthButton);
        add(topPanel, BorderLayout.NORTH);

        // Modelo de la tabla del calendario
        modeloCalendario = new DefaultTableModel(new String[]{"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable = new JTable(modeloCalendario);
        calendarTable.setRowHeight(50);
        calendarTable.setCellSelectionEnabled(false); // Deshabilitar selección
        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        updateCalendar();
    }

    private void changeMonth(int amount) {
        fechaActual = fechaActual.plusMonths(amount);
        updateCalendar();
    }

    private void updateCalendar() {
        modeloCalendario.setRowCount(0);
        mesAnioLabel.setText(fechaActual.getMonth() + " " + fechaActual.getYear());

        YearMonth yearMonth = YearMonth.of(fechaActual.getYear(), fechaActual.getMonthValue());
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue() % 7;

        Object[] week = new Object[7];
        int day = 1;

        for (int i = 0; i < firstDayOfWeek; i++) {
            week[i] = null;
        }
        for (int i = firstDayOfWeek; i < 7; i++) {
            week[i] = day++;
        }
        modeloCalendario.addRow(week);

        while (day <= daysInMonth) {
            week = new Object[7];
            for (int i = 0; i < 7 && day <= daysInMonth; i++) {
                week[i] = day++;
            }
            modeloCalendario.addRow(week);
        }

        highlighteventos();
    }

    private void highlighteventos() {
        for (int row = 0; row < calendarTable.getRowCount(); row++) {
            for (int col = 0; col < calendarTable.getColumnCount(); col++) {
                Object dayValue = calendarTable.getValueAt(row, col);
                if (dayValue != null) {
                    String day = String.valueOf(dayValue);
                    String key = generateEventKey(fechaActual.getYear(), fechaActual.getMonthValue(), Integer.parseInt(day));
                    if (eventos.containsKey(key)) {
                        calendarTable.setValueAt(day + ": " + eventos.get(key), row, col);
                    }
                }
            }
        }
    }

    private String generateEventKey(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

    public void addEvent(int year, int month, int day, String eventName) {
        String key = generateEventKey(year, month, day);
        eventos.put(key, eventName);
        if (year == fechaActual.getYear() && month == fechaActual.getMonthValue()) {
            updateCalendar();
        }
    }
    
}

