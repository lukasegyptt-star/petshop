package com.petshop.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int id;
    private int mascotaId;
    private TipoServicio tipoServicio;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoTurno estado;
    private Integer banadorId;
    private int creadoPor;

    public Turno() {}

    public Turno(int id, int mascotaId, TipoServicio tipoServicio, LocalDate fecha, LocalTime hora,
                 EstadoTurno estado, Integer banadorId, int creadoPor) {
        this.id = id;
        this.mascotaId = mascotaId;
        this.tipoServicio = tipoServicio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.banadorId = banadorId;
        this.creadoPor = creadoPor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMascotaId() { return mascotaId; }
    public void setMascotaId(int mascotaId) { this.mascotaId = mascotaId; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }

    public Integer getBanadorId() { return banadorId; }
    public void setBanadorId(Integer banadorId) { this.banadorId = banadorId; }

    public int getCreadoPor() { return creadoPor; }
    public void setCreadoPor(int creadoPor) { this.creadoPor = creadoPor; }

    @Override
    public String toString() {
        return "[" + id + "] Mascota #" + mascotaId + " - " + tipoServicio.getDescripcion()
                + " - " + fecha + " " + hora + " - " + estado;
    }
}
