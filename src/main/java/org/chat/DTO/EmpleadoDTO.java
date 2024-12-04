package org.chat.DTO;

import java.util.Date;

public class EmpleadoDTO {
    private int emplId;
    private String emplPrimerNombre;
    private String emplSegundoNombre;
    private String emplEmail;
    private String emplFechaNac;
    private double emplSueldo;
    private double emplComision;
    private int emplCargoId;
    private int emplDptoId;
    private Integer emplGerenteId;
    private Integer emplEmphistId;

    public EmpleadoDTO() {}

    public EmpleadoDTO(int emplId, String emplPrimerNombre, String emplSegundoNombre, String emplEmail, String emplFechaNac, double emplSueldo, double emplComision, int emplCargoId, int emplDptoId, Integer emplGerenteId, Integer emplEmphistId) {
        this.emplId = emplId;
        this.emplPrimerNombre = emplPrimerNombre;
        this.emplSegundoNombre = emplSegundoNombre;
        this.emplEmail = emplEmail;
        this.emplFechaNac = emplFechaNac;
        this.emplSueldo = emplSueldo;
        this.emplComision = emplComision;
        this.emplCargoId = emplCargoId;
        this.emplDptoId = emplDptoId;
        this.emplGerenteId = emplGerenteId;
        this.emplEmphistId = emplEmphistId;
    }

    public int getEmplId() {
        return emplId;
    }

    public void setEmplId(int emplId) {
        this.emplId = emplId;
    }

    public String getEmplPrimerNombre() {
        return emplPrimerNombre;
    }

    public void setEmplPrimerNombre(String emplPrimerNombre) {
        this.emplPrimerNombre = emplPrimerNombre;
    }

    public String getEmplSegundoNombre() {
        return emplSegundoNombre;
    }

    public void setEmplSegundoNombre(String emplSegundoNombre) {
        this.emplSegundoNombre = emplSegundoNombre;
    }

    public String getEmplEmail() {
        return emplEmail;
    }

    public void setEmplEmail(String emplEmail) {
        this.emplEmail = emplEmail;
    }

    public String getEmplFechaNac() {
        return emplFechaNac;
    }

    public void setEmplFechaNac(String emplFechaNac) {
        this.emplFechaNac = emplFechaNac;
    }

    public double getEmplSueldo() {
        return emplSueldo;
    }

    public void setEmplSueldo(double emplSueldo) {
        this.emplSueldo = emplSueldo;
    }

    public double getEmplComision() {
        return emplComision;
    }

    public void setEmplComision(double emplComision) {
        this.emplComision = emplComision;
    }

    public int getEmplCargoId() {
        return emplCargoId;
    }

    public void setEmplCargoId(int emplCargoId) {
        this.emplCargoId = emplCargoId;
    }

    public int getEmplDptoId() {
        return emplDptoId;
    }

    public void setEmplDptoId(int emplDptoId) {
        this.emplDptoId = emplDptoId;
    }

    public Integer getEmplGerenteId() {
        return emplGerenteId;
    }

    public void setEmplGerenteId(Integer emplGerenteId) {
        this.emplGerenteId = emplGerenteId;
    }

    public Integer getEmplEmphistId() {
        return emplEmphistId;
    }

    public void setEmplEmphistId(Integer emplEmphistId) {
        this.emplEmphistId = emplEmphistId;
    }
}
