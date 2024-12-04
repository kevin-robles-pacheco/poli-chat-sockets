package org.chat.DTO;

public class LocalizacionDTO {
    private int localizId;
    private String localizDireccion;
    private int localizCiudadId;

    public LocalizacionDTO() {}

    public LocalizacionDTO(int localizId, String localizDireccion, int localizCiudadId) {
        this.localizId = localizId;
        this.localizDireccion = localizDireccion;
        this.localizCiudadId = localizCiudadId;
    }

    public int getLocalizId() {
        return localizId;
    }

    public void setLocalizId(int localizId) {
        this.localizId = localizId;
    }

    public String getLocalizDireccion() {
        return localizDireccion;
    }

    public void setLocalizDireccion(String localizDireccion) {
        this.localizDireccion = localizDireccion;
    }

    public int getLocalizCiudadId() {
        return localizCiudadId;
    }

    public void setLocalizCiudadId(int localizCiudadId) {
        this.localizCiudadId = localizCiudadId;
    }
}
