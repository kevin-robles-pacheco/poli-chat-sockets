package org.chat.DTO;

public class PaisDTO {
    int paisId;
    String paisNombre;

    public PaisDTO() {};

    public PaisDTO(int paisId, String paisNombre) {
        this.paisId = paisId;
        this.paisNombre = paisNombre;
    }

    public int getPaisId() {
        return paisId;
    }

    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }

    public String getNombre() {
        return paisNombre;
    }

    public void setNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }
}
