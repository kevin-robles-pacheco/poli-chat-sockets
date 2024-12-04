package org.chat;

import org.chat.DTO.CargoDTO;

import java.sql.Connection;
import java.sql.Statement;

import static org.chat.Servidor.mostrarTexto;

public class CargoDB {
    Connection conn;
    final String cargosTable = "CARGOS";

    public CargoDB(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarCargo(CargoDTO cargoDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + cargosTable + " ("
                    + "cargo_id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "cargo_nombre VARCHAR(50) NOT NULL, "
                    + "cargo_sueldo_minimo DOUBLE NOT NULL, "
                    + "cargo_sueldo_maximo DOUBLE NOT NULL"
                    + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);

            String insertQuery = "INSERT INTO " + cargosTable + " (cargo_nombre, cargo_sueldo_minimo, cargo_sueldo_maximo) VALUES ('"
                    + cargoDTO.getCargoNombre() + "', '"
                    + cargoDTO.getCargoSueldoMinimo() + "', '"
                    + cargoDTO.getCargoSueldoMaximo() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar cargo: " + e.getMessage());
            return false;
        }
    }
    public void leerCargos() { /* lógica */ }
    public void actualizarCargo(int id, String nuevoNombre, double sueldoMin, double sueldoMax) { /* lógica */ }
    public void eliminarCargo(int id) { /* lógica */ }
}
