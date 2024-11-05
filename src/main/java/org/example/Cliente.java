package org.example;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private DataInputStream bufferDeEntrada = null;
    private DataOutputStream bufferDeSalida = null;
    Scanner teclado = new Scanner(System.in);
    final String COMANDO_TERMINACION = "chao";

    public static void main(String[] argumentos) {
        Cliente cliente = new Cliente();
        Scanner escaner = new Scanner(System.in);

        mostrarTexto("Por favor ingrese un nombre de usuario: ");
        String nombre = escaner.nextLine();
        if (nombre.length() <= 0) nombre = "Cliente";

        mostrarTexto("Ingresa la IP: [127.0.0.1 por defecto] ");
        String ip = escaner.nextLine();
        if (ip.length() <= 0) ip = "127.0.0.1";

        mostrarTexto("Puerto: [5050 por defecto] ");
        String puerto = escaner.nextLine();
        if (puerto.length() <= 0) puerto = "5050";

        cliente.ejecutarConexion(ip, Integer.parseInt(puerto), nombre);
    }

    public void levantarConexion(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            mostrarTexto("Conectado al servidor: " + socket.getInetAddress().getHostAddress());
        } catch (Exception e) {
            mostrarTexto("Error al levantar conexion: " + e.getMessage());
            System.exit(0);
        }
    }

    public static void mostrarTexto(String s) {
        System.out.println(s);
    }

    public void abrirFlujos() {
        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en la apertura de flujos");
        }
    }

    public void enviar(String s) {
        try {
            bufferDeSalida.writeUTF(s);
            bufferDeSalida.flush();
        } catch (IOException e) {
            mostrarTexto("Error en enviar(): " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            bufferDeEntrada.close();
            bufferDeSalida.close();
            socket.close();
            mostrarTexto("Conexion terminada");
        } catch (IOException e) {
            mostrarTexto("Error al cerrarConexion(): " + e.getMessage());
        } finally {
            System.exit(0);
        }
    }

    public void ejecutarConexion(String ip, int puerto, String nombre) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    levantarConexion(ip, puerto);
                    abrirFlujos();

                    // Enviar nombre de usuario al servidor al conectarse
                    enviar(nombre);
                    recibirDatos();
                } finally {
                    cerrarConexion();
                }
            }
        });
        hilo.start();

        // Iniciar el proceso para enviar mensajes después de conectarse
        escribirDatos(nombre);
    }

    public void recibirDatos() {
        String st = "";
        try {
            do {
                st = (String) bufferDeEntrada.readUTF();
                mostrarTexto("\n" + st);  // Mensaje del servidor o de otro cliente
                System.out.print("\n[Usted] => ");
            } while (!st.equals(COMANDO_TERMINACION));
        } catch (IOException e) {
            mostrarTexto("Error en recibirDatos(): " + e.getMessage());
        }
    }

    public void escribirDatos(String nombre) {
        String entrada = "";
        while (true) {
            System.out.print("[" + nombre + "] => ");
            entrada = teclado.nextLine();
            if (entrada.equalsIgnoreCase(COMANDO_TERMINACION)) {
                enviar(COMANDO_TERMINACION);
                break;
            } else {
                enviar(entrada);
            }
        }
        cerrarConexion();
    }
}
