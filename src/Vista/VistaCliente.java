package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class VistaCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton, logoutButton;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    private String username;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaCliente frame = new VistaCliente();
                    frame.setVisible(true);
                    frame.connectToServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public VistaCliente() {
        // Solicitar el nombre de usuario al inicio
        username = JOptionPane.showInputDialog(this, "Ingresa tu nombre de usuario:", "Bienvenido", JOptionPane.PLAIN_MESSAGE);
        if (username == null || username.trim().isEmpty()) {
            username = "Usuario"; // Valor por defecto
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Chat Cliente - " + username); 
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

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Botón de Cerrar sesión
        logoutButton = new JButton("Cerrar sesión");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBounds(10, 420, 150, 30);
        logoutButton.setBackground(new Color(255, 72, 72));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new LineBorder(new Color(255, 72, 72), 2, true));
        logoutButton.setOpaque(true);
        contentPane.add(logoutButton);

        // ActionListener para cerrar sesión
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
     // Configurar acción al cerrar ventana con la X
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarSesion(); 
            }
        });

    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 8080);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Envio el nombre de usuario al servidor
            salida.println(username);
            salida.println(" se ha conectado");

            // Hilo para recibir mensajes del servidor
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String mensaje;
                    try {
                        while ((mensaje = entrada.readLine()) != null) {
                            textArea.append(mensaje + "\n");
                            textArea.setCaretPosition(textArea.getDocument().getLength());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            salida.println(message); // Enviar mensaje al servidor sin nombre de usuario
            textArea.append("Tú: " + message + "\n"); 
            textField.setText(""); 
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    // Cierre de sesión
    private void cerrarSesion() {
        try {
             
            socket.close(); 
            entrada.close(); 
            salida.close(); 
            JOptionPane.showMessageDialog(this, "Sesión cerrada con éxito.");
            System.exit(0); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
