package org.chat;

import org.chat.DTO.PaisDTO;

import java.sql.*;

import static org.chat.Servidor.mostrarTexto;

public class PaisDB {
    Connection conn;

    public PaisDB(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarPais(PaisDTO PaisDTO) {
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS PAISES (pais_id INT PRIMARY KEY AUTO_INCREMENT, pais_nombre VARCHAR(255) NOT NULL)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableQuery);
            String insertQuery = "INSERT INTO PAISES VALUES ('" + PaisDTO.getNombre() + "')";
            stmt.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            mostrarTexto("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }

    public void leerPaises() {
        String query = "SELECT * FROM PAISES";
        try (Connection con = DataBase.establecerConexion()) {
            assert con != null;
            try (PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mostrarTexto("ID: " + rs.getInt("pais_id") + ", Nombre: " + rs.getString("pais_nombre"));
                }
            }
        } catch (SQLException e) {
            mostrarTexto("Error al leer países: " + e.getMessage());
        }
    }

    public void actualizarPais(int id, String nuevoNombre) {
        String query = "UPDATE PAISES SET pais_nombre = ? WHERE pais_ID = ?";
        try (Connection con = DataBase.establecerConexion()) {
            assert con != null;
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nuevoNombre);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                mostrarTexto("País actualizado exitosamente.");
            }
        } catch (SQLException e) {
            mostrarTexto("Error al actualizar país: " + e.getMessage());
        }
    }

    public void eliminarPais(int id) {
        String query = "DELETE FROM PAISES WHERE pais_ID = ?";
        try (Connection con = DataBase.establecerConexion()) {
            assert con != null;
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                mostrarTexto("País eliminado exitosamente.");
            }
        } catch (SQLException e) {
            mostrarTexto("Error al eliminar país: " + e.getMessage());
        }
    }
}
