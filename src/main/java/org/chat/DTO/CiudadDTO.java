package org.chat.DTO;

public class CiudadDTO {
    private int ciudId;
    private String ciudNombre;
    private int ciudPaisId;

    public CiudadDTO() {}

    public CiudadDTO(int ciudId, String ciudNombre, int ciudPaisId) {
        this.ciudId = ciudId;
        this.ciudNombre = ciudNombre;
        this.ciudPaisId = ciudPaisId;
    }

    public int getCiudId() {
        return ciudId;
    }

    public void setCiudId(int ciudId) {
        this.ciudId = ciudId;
    }

    public String getCiudNombre() {
        return ciudNombre;
    }

    public void setCiudNombre(String ciudNombre) {
        this.ciudNombre = ciudNombre;
    }

    public int getCiudPaisId() {
        return ciudPaisId;
    }

    public void setCiudPaisId(int ciudPaisId) {
        this.ciudPaisId = ciudPaisId;
    }
}
