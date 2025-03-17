package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static final int PUERTO = 8080;
    private static List<OrganizacionCliente> clientes = new ArrayList<>(); // Almacén de clientes
    private static List<String> historial = new ArrayList<>();  // Almacenar el historial de mensajes

    public void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto: " + PUERTO);

            while (true) {
                // Acepta nuevas conexiones de clientes
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Se organiza la conexión del cliente y se inicia un hilo para gestionarla
                OrganizacionCliente organizacionCliente = new OrganizacionCliente(socket, clientes, historial);
                clientes.add(organizacionCliente);
                new Thread(organizacionCliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void reenviarMensaje(String mensaje, OrganizacionCliente remitente) {
        // Guardar el mensaje en el historial
        historial.add(mensaje);

        // Enviar el mensaje a todos los clientes conectados
        for (OrganizacionCliente cliente : clientes) {
            if (cliente != remitente) { 
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    public static synchronized void eliminarCliente(OrganizacionCliente cliente) {
        clientes.remove(cliente);
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}
