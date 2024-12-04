package org.chat.DTO;

public class DepartamentoDTO {
    private int dptoId;
    private String dptoNombre;
    private int dptoLocalizId;

    public DepartamentoDTO() {}

    public DepartamentoDTO(int dptoId, String dptoNombre, int dptoLocalizId) {
        this.dptoId = dptoId;
        this.dptoNombre = dptoNombre;
        this.dptoLocalizId = dptoLocalizId;
    }

    public int getDptoId() {
        return dptoId;
    }

    public void setDptoId(int dptoId) {
        this.dptoId = dptoId;
    }

    public String getDptoNombre() {
        return dptoNombre;
    }

    public void setDptoNombre(String dptoNombre) {
        this.dptoNombre = dptoNombre;
    }

    public int getDptoLocalizId() {
        return dptoLocalizId;
    }

    public void setDptoLocalizId(int dptoLocalizId) {
        this.dptoLocalizId = dptoLocalizId;
    }
}
