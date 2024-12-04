package org.chat;

import org.chat.DTO.CiudadDTO;

import java.sql.Connection;
import java.sql.Statement;

import static org.chat.Servidor.mostrarTexto;

public class CiudadDB {
    Connection conn;
    public CiudadDB(Connection conn) {
        this.conn = conn;
    }

    final String cityTable = "CIUDADES";
    public boolean insertarCiudad(CiudadDTO CiudadDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + cityTable + " (ciud_id INT PRIMARY KEY AUTO_INCREMENT, ciud_nombre VARCHAR(255) NOT NULL, ciud_pais_id INT NOT NULL)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            String insertQuery = "INSERT INTO " + cityTable + " VALUES ('" + CiudadDTO.getCiudNombre() + "', '" + CiudadDTO.getCiudPaisId() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }
    public void leerCiudades() { /* lógica */ }
    public void actualizarCiudad(int id, String nuevoNombre, int paisId) { /* lógica */ }
    public void eliminarCiudad(int id) { /* lógica */ }
}
