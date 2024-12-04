package org.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.chat.Servidor.mostrarTexto;

public class PaisDB {
    final String countryTable;

    public PaisDB() {
        countryTable = "PAISES";
    }

    public void insertarPais(int id, String nombre) {
        String query = "INSERT INTO " + countryTable + " (pais_ID, pais_nombre) VALUES (?, ?)";
        try (Connection con = DataBase.establecerConexion()) {
            assert con != null;
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, nombre);
                pstmt.executeUpdate();
                mostrarTexto("País insertado exitosamente.");
            }
        } catch (SQLException e) {
            mostrarTexto("Error al insertar país: " + e.getMessage());
        }
    }

    public void leerPaises() {
        String query = "SELECT * FROM " + countryTable;
        try (Connection con = DataBase.establecerConexion()) {
            assert con != null;
            try (PreparedStatement pstmt = con.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mostrarTexto("ID: " + rs.getInt("pais_ID") + ", Nombre: " + rs.getString("pais_nombre"));
                }
            }
        } catch (SQLException e) {
            mostrarTexto("Error al leer países: " + e.getMessage());
        }
    }

    public void actualizarPais(int id, String nuevoNombre) {
        String query = "UPDATE " + countryTable + " SET pais_nombre = ? WHERE pais_ID = ?";
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
        String query = "DELETE FROM " + countryTable + " WHERE pais_ID = ?";
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
