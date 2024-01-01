package com.ifrn.sge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
public class Car implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @NotBlank
    @NotNull
    private String idPlate;

    @NotBlank
    private String ownerName;

    public String getIdPlate() {
        return idPlate;
    }

    public void setIdPlate(String idPlate) {
        this.idPlate = idPlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Car() {
    }

    public Car(String idPlate, String ownerName) {
        this.idPlate = idPlate;
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "Car{" +
                "idPlate='" + idPlate + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
