package com.petshop.modelo;

public enum TipoServicio {
    BANO("Baño"),
    CORTE_PELO("Corte de pelo"),
    BANO_Y_CORTE("Baño y corte de pelo");

    private final String descripcion;

    TipoServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Indica si este servicio cuenta contra el cupo diario de baños
    public boolean incluyeBano() {
        return this == BANO || this == BANO_Y_CORTE;
    }
}
