package org.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.chat.Servidor.mostrarTexto;

public class DataBase {
    static final String USER = "sa";
    static final String PASSWORD = "";
    static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DATOS;encrypt=true;trustServerCertificate=true";


    public static Connection establecerConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            mostrarTexto("Error al conectar a la base de datos: " + e.getMessage());
        }
        return null;
    }
    public void cerrarConexion(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                mostrarTexto("Conexión cerrada.");
            }
        } catch (SQLException e) {
            mostrarTexto("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
