package Cliente;

import Vista.VistaCliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/*
Clase Cliente: Se encarga de conectarse a el servidor creado a través de sockets.
*/
public class Cliente {
    private static final String SERVIDOR = "localhost"; // Dirección del servidor
    private static final int PUERTO = 8080; // Puerto de Conexión
    private Socket socket; // Socket de conexión
    private BufferedReader entrada; // Flujo de entrada para recibir datos del servidor
    private PrintWriter salida; // Flujo de salida para enviar datos al servidor
    private VistaCliente vistaCliente; // Referencia a la interfaz gráfica

    // Constructor de Cliente
    public Cliente(String servidor, int puerto, String nombreUsuario, VistaCliente vistaCliente) {
        this.vistaCliente = vistaCliente;
        try {
            // Estableece conexión con el servidor
            this.socket = new Socket(servidor, puerto);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            // Enviar nombre de usuario al servidor
            salida.println(nombreUsuario); 

            // Hilo para recibir mensajes del servidor
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String mensaje;
                    try {
                        while ((mensaje = entrada.readLine()) != null) {
                            // Enviar el mensaje recibido a la interfaz gráfica
                            vistaCliente.recibirMensaje(mensaje);  
                        }
                    } catch (IOException e) {
                    	if (!socket.isClosed()) {
                            // Manejar el error si la conexión se cierra inesperadamente
                            System.out.println("Se cerró la conexión o ocurrió un error al leer del socket.");
                    	}
                    }
                }
            });
            // Iniciar hilo para recibir mensajes
            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar mensajes al servidor
    public void enviarMensaje(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            salida.println(mensaje);  
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
