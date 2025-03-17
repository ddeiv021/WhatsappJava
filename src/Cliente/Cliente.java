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

    public Cliente(String servidor, int puerto, String nombreUsuario, VistaCliente vistaCliente) {
        this.vistaCliente = vistaCliente;
        try {
            this.socket = new Socket(servidor, puerto);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(nombreUsuario); // Enviar nombre de usuario al servidor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
