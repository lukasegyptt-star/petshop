package com.petshop.dao;

import com.petshop.conexion.Conexion;
import com.petshop.modelo.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    public void registrar(Mascota mascota) {
        String sql = "INSERT INTO mascotas (nombre, especie, raza, cliente_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setString(1, mascota.getNombre());
            ps.setString(2, mascota.getEspecie());
            ps.setString(3, mascota.getRaza());
            ps.setInt(4, mascota.getClienteId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar mascota: " + e.getMessage(), e);
        }
    }

    // Cada mascota solo puede tener un dueño, pero un cliente puede tener varias mascotas
    public List<Mascota> listarPorCliente(int clienteId) {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE cliente_id = ? ORDER BY id";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mascotas: " + e.getMessage(), e);
        }
        return lista;
    }

    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id = ?";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mascota: " + e.getMessage(), e);
        }
        return null;
    }

    private Mascota mapear(ResultSet rs) throws SQLException {
        return new Mascota(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("especie"),
                rs.getString("raza"),
                rs.getInt("cliente_id")
        );
    }
}
