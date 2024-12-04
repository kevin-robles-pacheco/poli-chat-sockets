package org.chat;

import org.chat.DTO.EmpleadoDTO;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.chat.Servidor.mostrarTexto;

public class EmpleadoDB {
    Connection conn;
    String employeTable = "EMPLEADOS";
    public EmpleadoDB(Connection conn){
        this.conn = conn;
    }
    public boolean insertarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + employeTable + " (" +
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
            Statement stmt2 = conn.createStatement();
            stmt2.executeUpdate(createTableQuery);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNAc = empleadoDTO.getEmplFechaNac();
            Date convertedCurrentDate = sdf.parse(fechaNAc);

            String insertQuery = "INSERT INTO " + employeTable + " (empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, empl_sueldo, empl_comision, empl_cargo_ID, empl_dpto_ID, empl_Gerente_ID) VALUES ('" +
                    empleadoDTO.getEmplPrimerNombre() + "', '" +
                    empleadoDTO.getEmplSegundoNombre() + "', '" +
                    empleadoDTO.getEmplEmail() + "', '" +
                    convertedCurrentDate + "', '" +
                    empleadoDTO.getEmplSueldo() + "', '" +
                    empleadoDTO.getEmplComision() + "', '" +
                    empleadoDTO.getEmplCargoId() + "', '" +
                    empleadoDTO.getEmplDptoId() + "', '" +
                    empleadoDTO.getEmplGerenteId() + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }
    public void leerEmpleados() { /* lógica */ }
    public void actualizarEmpleado(int id, String nuevoEmail, double nuevoSueldo) { /* lógica */ }
    public void eliminarEmpleado(int id) { /* lógica */ }
}
