package org.chat;

import org.chat.DTO.HistoricoDTO;

import java.sql.Connection;
import java.sql.Statement;

import static org.chat.Servidor.mostrarTexto;

public class HistoricoDB {
    Connection conn;
    final String historicTable = "HISTORICO";
    public HistoricoDB(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarHistorico(HistoricoDTO historicoDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + historicTable + " (emphist_id INT PRIMARY KEY AUTO_INCREMENT, emphist_fecha_retiro DATE NOT NULL, emphist_cargo_id INT NOT NULL, emphist_dpto_id INT NOT NULL, emphist_emp_id INT NOT NULL, FOREIGN KEY (emphist_cargo_id) REFERENCES CARGOS(cargo_id), FOREIGN KEY (emphist_dpto_id) REFERENCES DEPARTAMENTOS(dpto_id), FOREIGN KEY (emphist_emp_id) REFERENCES EMPLEADOS(emp_id))";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            String insertQuery = "INSERT INTO " + historicTable + " (emphist_fecha_retiro, emphist_cargo_id, emphist_dpto_id, emphist_emp_id) VALUES ('" + historicoDTO.getEmphistFechaRetiro() + "', '" + historicoDTO.getEmphistCargoId() + "', '" + historicoDTO.getEmphistDptoId() + "', '" + historicoDTO.getEmphistEmpId() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }

    }
    public void leerHistoricos() { /* lógica */ }
    public void actualizarHistorico(int id, java.sql.Date nuevaFecha, int cargoId, int dptoId) { /* lógica */ }
    public void eliminarHistorico(int id) { /* lógica */ }
}
