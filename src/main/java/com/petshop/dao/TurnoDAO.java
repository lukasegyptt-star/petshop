package com.petshop.dao;

import com.petshop.conexion.Conexion;
import com.petshop.modelo.EstadoTurno;
import com.petshop.modelo.TipoServicio;
import com.petshop.modelo.Turno;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TurnoDAO {

    private static final int MAXIMO_BANOS_POR_DIA = 10;

    // Cuenta los turnos de ese día que incluyen baño (BANO o BANO_Y_CORTE)
    public int contarBanosDelDia(LocalDate fecha) {
        String sql = "SELECT COUNT(*) FROM turnos WHERE fecha = ? AND tipo_servicio IN ('BANO','BANO_Y_CORTE')";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar baños del día: " + e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Intenta agendar un turno. Devuelve false si ya se llegó al cupo
     * de 10 baños para ese día y el servicio pedido incluye baño.
     */
    public boolean agendar(Turno turno) {
        if (turno.getTipoServicio().incluyeBano()
                && contarBanosDelDia(turno.getFecha()) >= MAXIMO_BANOS_POR_DIA) {
            return false;
        }

        String sql = "INSERT INTO turnos (mascota_id, tipo_servicio, fecha, hora, estado, banador_id, creado_por) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, turno.getMascotaId());
            ps.setString(2, turno.getTipoServicio().name());
            ps.setDate(3, Date.valueOf(turno.getFecha()));
            ps.setTime(4, Time.valueOf(turno.getHora()));
            ps.setString(5, EstadoTurno.PENDIENTE.name());
            if (turno.getBanadorId() != null) {
                ps.setInt(6, turno.getBanadorId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setInt(7, turno.getCreadoPor());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error al agendar turno: " + e.getMessage(), e);
        }
    }

    public void finalizar(int turnoId) {
        String sql = "UPDATE turnos SET estado = 'FINALIZADO' WHERE id = ?";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setInt(1, turnoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al finalizar turno: " + e.getMessage(), e);
        }
    }

    public List<Turno> listarPorFecha(LocalDate fecha) {
        List<Turno> lista = new ArrayList<>();
        String sql = "SELECT * FROM turnos WHERE fecha = ? ORDER BY hora";
        try (PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar turnos: " + e.getMessage(), e);
        }
        return lista;
    }

    private Turno mapear(ResultSet rs) throws SQLException {
        Integer banadorId = rs.getObject("banador_id") != null ? rs.getInt("banador_id") : null;
        return new Turno(
                rs.getInt("id"),
                rs.getInt("mascota_id"),
                TipoServicio.valueOf(rs.getString("tipo_servicio")),
                rs.getDate("fecha").toLocalDate(),
                rs.getTime("hora").toLocalTime(),
                EstadoTurno.valueOf(rs.getString("estado")),
                banadorId,
                rs.getInt("creado_por")
        );
    }
}
