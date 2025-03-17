package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class OrganizacionCliente implements Runnable {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private List<OrganizacionCliente> clientes;
    private List<String> historial;
    private String nombreUsuario;

    public OrganizacionCliente(Socket socket, List<OrganizacionCliente> clientes, List<String> historial) {
        this.socket = socket;
        this.clientes = clientes;
        this.historial = historial;
    } 

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

            // Escuchar los mensajes del cliente
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                // Reenviar el mensaje a todos los clientes
                Servidor.reenviarMensaje(nombreUsuario + ": " + mensaje, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Enviar mensaje de desconexión a todos los clientes
                Servidor.reenviarMensaje(nombreUsuario + " se ha desconectado.", this);
              
                // Cerrar la conexión
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
                enviarMensaje(mensaje); // Enviar cada mensaje almacenado en el historial
            }
        }
    }
}
