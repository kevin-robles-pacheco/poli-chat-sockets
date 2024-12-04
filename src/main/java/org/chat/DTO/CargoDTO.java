package org.chat.DTO;

public class CargoDTO {
    private int cargoId;
    private String cargoNombre;
    private double cargoSueldoMinimo;
    private double cargoSueldoMaximo;

    public CargoDTO() {}

    public CargoDTO(int cargoId, String cargoNombre, double cargoSueldoMinimo, double cargoSueldoMaximo) {
        this.cargoId = cargoId;
        this.cargoNombre = cargoNombre;
        this.cargoSueldoMinimo = cargoSueldoMinimo;
        this.cargoSueldoMaximo = cargoSueldoMaximo;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoNombre() {
        return cargoNombre;
    }

    public void setCargoNombre(String cargoNombre) {
        this.cargoNombre = cargoNombre;
    }

    public double getCargoSueldoMinimo() {
        return cargoSueldoMinimo;
    }

    public void setCargoSueldoMinimo(double cargoSueldoMinimo) {
        this.cargoSueldoMinimo = cargoSueldoMinimo;
    }

    public double getCargoSueldoMaximo() {
        return cargoSueldoMaximo;
    }

    public void setCargoSueldoMaximo(double cargoSueldoMaximo) {
        this.cargoSueldoMaximo = cargoSueldoMaximo;
    }
}
