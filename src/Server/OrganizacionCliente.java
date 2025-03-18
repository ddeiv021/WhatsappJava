package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
/*
Clase OrganizacionCliente: Representa a un cliente conectado al servidor.
Se encarga de recibir y enviar mensajes, así como gestionar su desconexión.
*/
public class OrganizacionCliente implements Runnable {
    private Socket socket; // Socket de conexión con el cliente
    private BufferedReader entrada; // Flujo de entrada para recibir mensajes
    private PrintWriter salida; // Flujo de salida para enviar mensajes
    private List<OrganizacionCliente> clientes; // Lista de clientes conectados
    private List<String> historial; // Historial de mensajes
    private String nombreUsuario; // Nombre del usuario conectado 

// Constructor de OrganizacionCliente
    public OrganizacionCliente(Socket socket, List<OrganizacionCliente> clientes, List<String> historial) {
        this.socket = socket;
        this.clientes = clientes;
        this.historial = historial;
    } 

// Método ejecutado en un hilo para gestionar la comunicación con el cliente
    @Override
    public void run() { 
        try {
            // Preparar streams de entrada y salida
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Solicitar y leer el nombre de usuario 
            nombreUsuario = entrada.readLine();
            System.out.println(nombreUsuario + " se ha conectado.");

            Servidor.reenviarMensaje(nombreUsuario + " se ha conectado.", this);
            
            // Enviar el historial a este cliente
            enviarHistorial();

            // Escuchar los mensajes del cliente y reenviarlos
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                Servidor.reenviarMensaje(nombreUsuario + ": " + mensaje, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Enviar mensaje de desconexión a todos los demás clientes
                Servidor.reenviarMensaje(nombreUsuario + " se ha desconectado.", this);
              
                // Cerrar la conexión y eliminar cliente de la lista
                socket.close();
                Servidor.eliminarCliente(this);
                System.out.println(nombreUsuario + " se ha desconectado.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para enviar un mensaje a este cliente
    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    // Enviar el historial de mensajes al cliente
    private void enviarHistorial() {
        synchronized (historial) {
            for (String mensaje : historial) {
                enviarMensaje(mensaje); 
            }
        }
    }
}
