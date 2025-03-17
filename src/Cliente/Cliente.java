package Cliente;

import Vista.VistaCliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private static final String SERVIDOR = "localhost"; // o la IP del servidor
    private static final int PUERTO = 8080;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private VistaCliente vistaCliente;

    // Constructor de Cliente
    public Cliente(String servidor, int puerto, String nombreUsuario, VistaCliente vistaCliente) {
        this.vistaCliente = vistaCliente;
        try {
            this.socket = new Socket(servidor, puerto);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(nombreUsuario); // Enviar nombre de usuario al servidor

            // Hilo para recibir mensajes del servidor
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String mensaje;
                    try {
                        while ((mensaje = entrada.readLine()) != null) {
                            vistaCliente.recibirMensaje(mensaje);  // Enviar mensaje a VistaCliente
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

    // Método para enviar mensajes al servidor
    public void enviarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            salida.println(mensaje);  // Enviar mensaje al servidor
        }
    }

    // Cerrar la conexión
    public void cerrarConexion() {
        try {
            socket.close();
            entrada.close();
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
