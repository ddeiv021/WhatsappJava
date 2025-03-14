package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VistaCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton, logoutButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaCliente frame = new VistaCliente();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VistaCliente() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Chat Cliente");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(240, 240, 240));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 414, 350);
        contentPane.add(scrollPane);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBounds(10, 380, 320, 30);
        textField.setColumns(10);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBorder(new LineBorder(new Color(100, 100, 100), 1, true));
        contentPane.add(textField);

        sendButton = new JButton("Enviar");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBounds(340, 380, 80, 30);
        sendButton.setBackground(new Color(72, 133, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(new LineBorder(new Color(72, 133, 255), 2, true));
        sendButton.setOpaque(true);
        contentPane.add(sendButton);

        logoutButton = new JButton("Cerrar sesión");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBounds(10, 420, 150, 30);
        logoutButton.setBackground(new Color(255, 72, 72));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new LineBorder(new Color(255, 72, 72), 2, true));
        logoutButton.setOpaque(true);
        contentPane.add(logoutButton);
    }
}
