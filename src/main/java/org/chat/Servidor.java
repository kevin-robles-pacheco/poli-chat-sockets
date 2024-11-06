package org.chat;

import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor {

    private final List<ClientHandler> clientes = new ArrayList<>();
    final String COMANDO_TERMINACION = "chao";

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        Scanner sc = new Scanner(System.in);

        mostrarTexto("Ingresa la IP en el formato [127.0.0.1 por defecto]: ");
        String ip = sc.nextLine();
        if (ip.length() <= 0) ip = "127.0.0.1";

        mostrarTexto("Ingresa el puerto [5050 por defecto]: ");
        String puerto = sc.nextLine();
        if (puerto.length() <= 0) puerto = "5050";

        servidor.iniciarServidor(ip, Integer.parseInt(puerto));
    }

    public static void mostrarTexto(String s) {
        System.out.println(s);
    }

    public void iniciarServidor(String ip, int puerto) {
        try {
            ServerSocket serverSocket = new ServerSocket(puerto, 0, InetAddress.getByName(ip));
            mostrarTexto("Servidor iniciado en " + ip + ":" + puerto);

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler cliente = new ClientHandler(socket, this);
                clientes.add(cliente);
                cliente.start();
            }
        } catch (IOException e) {
            mostrarTexto("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Enviar mensaje a todos los clientes conectados
    public synchronized void enviarMensajeATodos(String mensaje, ClientHandler emisor) {
        for (ClientHandler cliente : clientes) {
            if (cliente != emisor) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    // Remover cliente de la lista al desconectarse
    public synchronized void removerCliente(ClientHandler cliente) {
        clientes.remove(cliente);
        mostrarTexto("El usuario : " + cliente.getNombreUsuario() + " se desconecto.");
    }

    // Clase interna para manejar a cada cliente de forma independiente
    private class ClientHandler extends Thread {
        private final Socket socket;
        private DataInputStream entrada;
        private DataOutputStream salida;
        private final Servidor servidor;
        private String nombreUsuario;

        public ClientHandler(Socket socket, Servidor servidor) {
            this.socket = socket;
            this.servidor = servidor;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        @Override
        public void run() {
            try {
                entrada = new DataInputStream(socket.getInputStream());
                salida = new DataOutputStream(socket.getOutputStream());

                // Recibir el nombre de usuario al conectarse
                nombreUsuario = entrada.readUTF();
                mostrarTexto(nombreUsuario + " se ha conectado.");

                // Notificar a otros clientes sobre la nueva conexión
                servidor.enviarMensajeATodos(nombreUsuario + " se ha unido al chat.", this);

                // Manejar mensajes de chat
                String mensaje;
                while (true) {
                    mensaje = entrada.readUTF();
                    if (mensaje.equalsIgnoreCase(COMANDO_TERMINACION)) {
                        mostrarTexto(nombreUsuario + " se ha desconectado.");
                        servidor.enviarMensajeATodos(nombreUsuario + " ha salido del chat.", this);
                        break;
                    }
                    mostrarTexto("[" + nombreUsuario + "] => " + mensaje);
                    servidor.enviarMensajeATodos("[" + nombreUsuario + "] => " + mensaje, this);
                }
            } catch (IOException e) {
                mostrarTexto("Error en la conexión con el cliente: " + e.getMessage());
            } finally {
                servidor.removerCliente(this);
                cerrarConexion();
            }
        }

        public void enviarMensaje(String mensaje) {
            try {
                salida.writeUTF(mensaje);
                salida.flush();
            } catch (IOException e) {
                mostrarTexto("Error al enviar mensaje a " + nombreUsuario + ": " + e.getMessage());
            }
        }

        public void cerrarConexion() {
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                mostrarTexto("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
