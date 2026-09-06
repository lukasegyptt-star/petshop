package com.petshop.dao;

import com.petshop.conexion.Conexion;
import com.petshop.modelo.Rol;
import com.petshop.modelo.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public Usuario autenticar(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    public void registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, nombre_completo, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getRol().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage(), e);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre_usuario"),
                rs.getString("contrasena"),
                rs.getString("nombre_completo"),
                Rol.valueOf(rs.getString("rol"))
        );
    }
}
