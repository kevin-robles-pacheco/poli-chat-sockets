package org.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.chat.Servidor.mostrarTexto;

public class DataBase {
    static final String USER = "root";
    static final String PASSWORD = "rootpassword";
    static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DATOS;encrypt=true;trustServerCertificate=true";

    public static Connection establecerConexion() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            mostrarTexto("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
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
