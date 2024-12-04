package org.chat;

import org.chat.DTO.DepartamentoDTO;

import java.sql.Connection;
import java.sql.Statement;

import static org.chat.Servidor.mostrarTexto;

public class DepartamentoDB {
    Connection conn;
    final String departmentTable = "DEPARTAMENTOS";

    public DepartamentoDB(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarDepartamento(DepartamentoDTO departamentoDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + departmentTable + " (dpto_id INT PRIMARY KEY AUTO_INCREMENT, dpto_nombre VARCHAR(255) NOT NULL, dpto_localiz_id INT NOT NULL, FOREIGN KEY (dpto_localiz_id) REFERENCES LOCALIZACIONES(localiz_id))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            String insertQuery = "INSERT INTO " + departmentTable + " (dpto_nombre, dpto_localiz_id) VALUES ('" + departamentoDTO.getDptoNombre() + "', '" + departamentoDTO.getDptoLocalizId() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }

    public void leerDepartamentos() { /* lógica */ }
    public void actualizarDepartamento(int id, String nuevoNombre, int localizId) { /* lógica */ }
    public void eliminarDepartamento(int id) { /* lógica */ }
}
