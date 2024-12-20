package org.chat;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class Servidor {

    private final List<ClientHandler> clientes = new ArrayList<>();
    final String COMANDO_TERMINACION = "chao";
    public static String ip;

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        Scanner sc = new Scanner(System.in);

        mostrarTexto("Ingresa la IP en el formato [127.0.0.1 por defecto]: ");
        ip = sc.nextLine();
        if (ip.length() <= 0) ip = "127.0.0.1";

        mostrarTexto("Ingresa el puerto [5050 por defecto]: ");
        String puerto = sc.nextLine();
        if (puerto.length() <= 0) puerto = "5050";

        servidor.iniciarServidor(ip, Integer.parseInt(puerto));
    }

    public static void conexionPorDefecto(){

    }

    public static void mostrarTexto(String s) {
        System.out.println(s);
    }

    public void iniciarServidor(String ip, int puerto) {
        try {
            ServerSocket serverSocket = new ServerSocket(puerto, 0, InetAddress.getByName(ip));
            mostrarTexto("Servidor iniciado en " + ip + ":" + puerto);
            boolean noErrors = true;
            while (noErrors) {
                try {
                    Socket socket = serverSocket.accept();
                    ClientHandler cliente = new ClientHandler(socket, this);
                    clientes.add(cliente);
                    cliente.start();
                } catch (IOException ex){
                    noErrors = false;
                }
            }
        } catch (Exception e) {
            mostrarTexto("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    // Enviar mensaje a todos los clientes conectados
    public synchronized void enviarMensajeATodos(String mensaje, ClientHandler emisor) {
        for (ClientHandler cliente : clientes) {
            cliente.enviarMensaje(mensaje);
        }
    }

    // Remover cliente de la lista al desconectarse
    public synchronized void removerCliente(ClientHandler cliente) {
        clientes.remove(cliente);
        mostrarTexto("El usuario : " + cliente.getNombreUsuario() + " se desconecto.");
    }

    // Clase interna para manejar a cada cliente de forma independiente
    public class ClientHandler extends Thread {
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
                servidor.enviarMensajeATodos(nombreUsuario + " se ha conectado.", this);
                servidor.enviarMensajeATodos(nombreUsuario + " escriba la palabra 'menu' para ver opciones.", this);

                // Manejar mensajes de chat
                String mensaje;
                while (true) {
                    mensaje = entrada.readUTF();
                    if (mensaje.equalsIgnoreCase(COMANDO_TERMINACION)) {
                        mostrarTexto(nombreUsuario + " se ha desconectado.");
                        servidor.enviarMensajeATodos(nombreUsuario + " ha salido del chat.", this);
                        break;
                    }

                    if (mensaje.equalsIgnoreCase("menu")) {
                        Helpers helpers = new Helpers();
                        helpers.mostrarOpciones(servidor, this, entrada, nombreUsuario);
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

    // Clase interna para manejar la conexión con la base de datos
    private static class conexiondb {
        final String URL;
        final String USER;
        final String PASSWORD;
        static Connection con;

        public conexiondb(String baseDatos) {
            URL = "jdbc:mysql://localhost:3306/";
            USER = "root";
            PASSWORD = "rootpassword";
            conexiondb.con = this.establecerConexion(baseDatos);
        }

        public Connection establecerConexion(String baseDatos) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                mostrarTexto("Conexión a la base de datos exitosa.");
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + baseDatos;
                Statement stmt = con.createStatement();
                stmt.executeUpdate(createDatabaseQuery);
                mostrarTexto("Usando: " + baseDatos);
                con = DriverManager.getConnection(URL + baseDatos, USER, PASSWORD);
            } catch (Exception e) {
                mostrarTexto("Error al conectar a la base de datos " + baseDatos + ": " + e.getMessage());
            }
            return con;
        }

        public static boolean insertarEmpleado(String primer_nombre, String segundo_nombre, String email, String fecha_nac, String sueldo, String comision, String cargo_ID, String dpto_ID, String gerente_ID) {
            try {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS empleados (" +
                        "empl_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "empl_primer_nombre VARCHAR(50), " +
                        "empl_segundo_nombre VARCHAR(50), " +
                        "empl_email VARCHAR(100), " +
                        "empl_fecha_nac DATE, " +
                        "empl_sueldo DECIMAL(10, 2), " +
                        "empl_comision DECIMAL(10, 2), " +
                        "empl_cargo_ID INT, " +
                        "empl_dpto_ID INT, " +
                        "empl_Gerente_ID INT)";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(createTableQuery);

                String insertQuery = "INSERT INTO empleados (empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, empl_sueldo, empl_comision, empl_cargo_ID, empl_dpto_ID, empl_Gerente_ID) VALUES ('" + primer_nombre + "', '" + segundo_nombre + "', '" + email + "', '" + fecha_nac + "', '" + sueldo + "', '" + comision + "', '" + cargo_ID + "', '" + dpto_ID + "', '" + gerente_ID + "')";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(insertQuery);
                return true;
            } catch (Exception e) {
                mostrarTexto("Error al insertar empleado: " + e.getMessage());
                return false;
            }
        }
    }
}