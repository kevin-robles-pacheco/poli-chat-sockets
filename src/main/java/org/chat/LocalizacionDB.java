package org.chat;

import org.chat.DTO.LocalizacionDTO;

import java.sql.Connection;
import java.sql.Statement;

import static org.chat.Servidor.mostrarTexto;

public class LocalizacionDB {
    Connection conn;
    final String locationTable = "LOCALIZACIONES";

    public LocalizacionDB(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarLocalizacion(LocalizacionDTO LocalizacionDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + locationTable + " (localiz_id INT PRIMARY KEY AUTO_INCREMENT, localiz_direccion VARCHAR(255) NOT NULL, localiz_ciudad_id INT NOT NULL, FOREIGN KEY (localiz_ciudad_id) REFERENCES CIUDADES(ciud_id))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            String insertQuery = "INSERT INTO " + locationTable + " (localiz_direccion, localiz_ciudad_id) VALUES ('" + LocalizacionDTO.getLocalizDireccion() + "', '" + LocalizacionDTO.getLocalizCiudadId() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }

    }
    public void leerLocalizaciones() { /* lógica */ }
    public void actualizarLocalizacion(int id, String nuevaDireccion, int ciudadId) { /* lógica */ }
    public void eliminarLocalizacion(int id) { /* lógica */ }
}
