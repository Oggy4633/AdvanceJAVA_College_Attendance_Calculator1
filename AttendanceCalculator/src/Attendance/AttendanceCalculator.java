package Attendance;

import javax.swing.*;
import java.awt.*;

public class AttendanceCalculator extends JFrame {

    JTextField name, roll, total, attended;
    JLabel percent, status, lectureInfo;
    JProgressBar progress;

    Color navy = new Color(27, 43, 88);
    Color bg = new Color(242, 246, 252);
    Color green = new Color(35, 170, 120);

    AttendanceCalculator() {

        setTitle("Campus | Attendance Hub");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(bg);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // HEADER
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(navy);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("CAMPUS  |  ATTENDANCE HUB");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel subtitle = new JLabel("SMART STUDENT DASHBOARD");
        subtitle.setForeground(new Color(170, 210, 240));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));

        header.add(title);
        header.add(subtitle);

        // LEFT CARD
        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 231, 241)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JLabel formTitle = new JLabel("Student Details");
        formTitle.setFont(new Font("Arial", Font.BOLD, 22));
        formTitle.setForeground(navy);
        form.add(formTitle);

        form.add(label("Student Name"));
        name = field();
        form.add(name);

        form.add(label("Roll Number"));
        roll = field();
        form.add(roll);

        form.add(label("Total Lectures"));
        total = field();
        form.add(total);

        form.add(label("Lectures Attended"));
        attended = field();
        form.add(attended);

        JButton calculate = new JButton("Calculate Attendance");
        calculate.setBackground(navy);
        calculate.setForeground(Color.WHITE);
        calculate.setFont(new Font("Arial", Font.BOLD, 14));
        calculate.setFocusPainted(false);

        JButton clear = new JButton("Clear All");
        clear.setFont(new Font("Arial", Font.BOLD, 14));
        clear.setFocusPainted(false);

        form.add(calculate);
        form.add(clear);

        // RIGHT CARD
        JPanel resultCard = new JPanel(new GridLayout(0, 1, 12, 12));
        resultCard.setBackground(Color.WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 231, 241)),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)));

        JLabel resultTitle = new JLabel("Attendance Overview");
        resultTitle.setFont(new Font("Arial", Font.BOLD, 22));
        resultTitle.setForeground(navy);

        JLabel sem = new JLabel("YOUR ATTENDANCE SUMMARY");
        sem.setFont(new Font("Arial", Font.PLAIN, 12));
        sem.setForeground(Color.GRAY);

        percent = new JLabel("-- %", SwingConstants.CENTER);
        percent.setFont(new Font("Arial", Font.BOLD, 38));
        percent.setForeground(navy);

        progress = new JProgressBar(0, 100);
        progress.setValue(0);
        progress.setStringPainted(true);
        progress.setForeground(green);
        progress.setBackground(new Color(230, 236, 245));
        progress.setPreferredSize(new Dimension(250, 25));

        status = new JLabel("Waiting for calculation",
                SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 16));
        status.setForeground(Color.GRAY);

        lectureInfo = new JLabel("Lectures: -- / --",
                SwingConstants.CENTER);
        lectureInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel tip = new JLabel(
                "<html><center>Stay consistent!<br>"
                + "Track your attendance regularly.</center></html>",
                SwingConstants.CENTER);
        tip.setForeground(new Color(90, 105, 130));
        tip.setFont(new Font("Arial", Font.ITALIC, 13));

        resultCard.add(resultTitle);
        resultCard.add(sem);
        resultCard.add(percent);
        resultCard.add(progress);
        resultCard.add(status);
        resultCard.add(lectureInfo);
        resultCard.add(tip);

        // TWO-COLUMN CONTENT
        JPanel content = new JPanel(new GridLayout(1, 2, 15, 0));
        content.setBackground(bg);
        content.add(form);
        content.add(resultCard);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);

        add(main);

        calculate.addActionListener(e -> calculateAttendance());

        clear.addActionListener(e -> {
            name.setText("");
            roll.setText("");
            total.setText("");
            attended.setText("");

            percent.setText("-- %");
            progress.setValue(0);
            status.setText("Waiting for calculation");
            status.setForeground(Color.GRAY);
            lectureInfo.setText("Lectures: -- / --");
        });

        setVisible(true);
    }

    JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        l.setForeground(navy);
        return l;
    }

    JTextField field() {
        JTextField f = new JTextField();
        f.setFont(new Font("Arial", Font.PLAIN, 15));
        f.setPreferredSize(new Dimension(200, 32));
        return f;
    }

    void calculateAttendance() {
        try {
            String student = name.getText().trim();
            String rollNo = roll.getText().trim();

            int t = Integer.parseInt(total.getText().trim());
            int a = Integer.parseInt(attended.getText().trim());

            if (student.isEmpty() || rollNo.isEmpty()
                    || t <= 0 || a < 0 || a > t) {
                JOptionPane.showMessageDialog(
                        this, "Please enter valid details!");
                return;
            }

            double p = a * 100.0 / t;

            percent.setText(String.format("%.2f%%", p));
            progress.setValue((int) p);
            lectureInfo.setText("Lectures: " + a + " / " + t);

            String resultStatus = p >= 75
                    ? "ELIGIBLE"
                    : "ATTENDANCE SHORTAGE";

            status.setText(resultStatus);

            if (p >= 75) {
                status.setForeground(green);
                progress.setForeground(green);
            } else {
                status.setForeground(Color.RED);
                progress.setForeground(Color.RED);
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Student: " + student
                    + "\nRoll Number: " + rollNo
                    + String.format("\nAttendance: %.2f%%", p)
                    + "\nStatus: " + resultStatus,
                    "Attendance Report",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this, "Enter valid lecture numbers!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new AttendanceCalculator());
    }
}