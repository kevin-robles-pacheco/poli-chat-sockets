package org.chat;

import org.chat.DTO.EmpleadoDTO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.chat.Servidor.mostrarTexto;

public class EmpleadoDB {
    Connection conn;
    public EmpleadoDB(Connection conn){
        this.conn = conn;
    }
    public boolean insertarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS EMPLEADOS (" +
                    "empl_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "empl_primer_nombre VARCHAR(50), " +
                    "empl_segundo_nombre VARCHAR(50), " +
                    "empl_email VARCHAR(100), " +
                    "empl_fecha_nac DATE, " +
                    "empl_sueldo DECIMAL(10, 2), " +
                    "empl_comision DECIMAL(10, 2), " +
                    "empl_cargo_ID INT, " +
                    "empl_dpto_ID INT, " +
                    "empl_Gerente_ID INT, " +
                    "empl_emphist_ID INT)";
            Statement stmt2 = conn.createStatement();
            stmt2.executeUpdate(createTableQuery);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaNAc = empleadoDTO.getEmplFechaNac();
            Date utilDate = sdf.parse(fechaNAc); // Convierte el String a java.util.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            String insertQuery = "INSERT INTO EMPLEADOS (empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, empl_sueldo, empl_comision, empl_cargo_ID, empl_dpto_ID, empl_Gerente_ID, empl_emphist_ID) VALUES ('" +
                    empleadoDTO.getEmplPrimerNombre() + "', '" +
                    empleadoDTO.getEmplSegundoNombre() + "', '" +
                    empleadoDTO.getEmplEmail() + "', '" +
                    sqlDate + "', '" +
                    empleadoDTO.getEmplSueldo() + "', '" +
                    empleadoDTO.getEmplComision() + "', '" +
                    empleadoDTO.getEmplCargoId() + "', '" +
                    empleadoDTO.getEmplDptoId() + "', '" +
                    empleadoDTO.getEmplGerenteId() + "', '" +
                    "1')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }
    public List<EmpleadoDTO> leerEmpleados() {
        List<EmpleadoDTO> listEmpleados = new ArrayList<>();

        String query = "SELECT empl_ID, empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, "
                + "empl_sueldo, empl_comision, empl_cargo_ID, empl_dpto_ID, empl_Gerente_ID, empl_emphist_ID "
                + "FROM EMPLEADOS";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                EmpleadoDTO empleadoDTO = null;
                empleadoDTO = new EmpleadoDTO();
                empleadoDTO.setEmplId(rs.getInt("empl_ID"));
                empleadoDTO.setEmplPrimerNombre(rs.getString("empl_primer_nombre"));
                empleadoDTO.setEmplSegundoNombre(rs.getString("empl_segundo_nombre"));
                empleadoDTO.setEmplEmail(rs.getString("empl_email"));
                empleadoDTO.setEmplFechaNac(rs.getDate("empl_fecha_nac").toString()); // Convertimos la fecha a String
                empleadoDTO.setEmplSueldo(rs.getDouble("empl_sueldo"));
                empleadoDTO.setEmplComision(rs.getDouble("empl_comision"));
                empleadoDTO.setEmplCargoId(rs.getInt("empl_cargo_ID"));
                empleadoDTO.setEmplDptoId(rs.getInt("empl_dpto_ID"));
                empleadoDTO.setEmplGerenteId(rs.getObject("empl_Gerente_ID") != null ? rs.getInt("empl_Gerente_ID") : null);
                empleadoDTO.setEmplEmphistId(rs.getObject("empl_emphist_ID") != null ? rs.getInt("empl_emphist_ID") : null);

                listEmpleados.add(empleadoDTO);
            }

        } catch (SQLException e) {
            mostrarTexto("Error al obtener el empleado: " + e.getMessage());
        }

        return listEmpleados;
    }

    public EmpleadoDTO obtenerEmpleado(int id) {
        EmpleadoDTO empleadoDTO = null;
        String query = "SELECT empl_ID, empl_primer_nombre, empl_segundo_nombre, empl_email, empl_fecha_nac, "
                + "empl_sueldo, empl_comision, empl_cargo_ID, empl_dpto_ID, empl_Gerente_ID, empl_emphist_ID "
                + "FROM EMPLEADOS WHERE empl_ID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                empleadoDTO = new EmpleadoDTO();
                empleadoDTO.setEmplId(rs.getInt("empl_ID"));
                empleadoDTO.setEmplPrimerNombre(rs.getString("empl_primer_nombre"));
                empleadoDTO.setEmplSegundoNombre(rs.getString("empl_segundo_nombre"));
                empleadoDTO.setEmplEmail(rs.getString("empl_email"));
                empleadoDTO.setEmplFechaNac(rs.getDate("empl_fecha_nac").toString()); // Convertimos la fecha a String
                empleadoDTO.setEmplSueldo(rs.getDouble("empl_sueldo"));
                empleadoDTO.setEmplComision(rs.getDouble("empl_comision"));
                empleadoDTO.setEmplCargoId(rs.getInt("empl_cargo_ID"));
                empleadoDTO.setEmplDptoId(rs.getInt("empl_dpto_ID"));
                empleadoDTO.setEmplGerenteId(rs.getObject("empl_Gerente_ID") != null ? rs.getInt("empl_Gerente_ID") : null);
                empleadoDTO.setEmplEmphistId(rs.getObject("empl_emphist_ID") != null ? rs.getInt("empl_emphist_ID") : null);
            }

        } catch (SQLException e) {
            mostrarTexto("Error al obtener el empleado: " + e.getMessage());
        }

        return empleadoDTO;
    }
    public void actualizarEmpleado(int id, String nuevoEmail, double nuevoSueldo) { /* lógica */ }
    public void eliminarEmpleado(int id) { /* lógica */ }
}
