package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
Clase Servidor: Gestiona la conexión de múltiples clientes a través de hilos (Thread)
Se encarga de aceptar conexiones, gestionar clientes y reenviar mensajes.
*/

public class Servidor {
    private static final int PUERTO = 8080; // Puerto de escucha del Servidor
    private static List<OrganizacionCliente> clientes = new ArrayList<>(); // Almacén de clientes
    private static List<String> historial = new ArrayList<>();  // Almacenar el historial de mensajes

// Iniciar Servidor y espera conexiones de clientes.
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
                
                // Inicia hilo para manejar la comunicación con este cliente
                new Thread(organizacionCliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Reenvía mensajes a todas los clientes excepto al remitente.
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

// Elimina cliente de la lista cuando se desconecta
    public static synchronized void eliminarCliente(OrganizacionCliente cliente) {
        clientes.remove(cliente);
    }

// Ejecución del Servidor
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}
