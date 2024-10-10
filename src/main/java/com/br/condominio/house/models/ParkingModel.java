package com.br.condominio.house.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "TB_PARKING")
public class ParkingModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Min(value = 0, message = "O número não pode ser negativo")
    @Column(name = "vagancy_id")
    private int number;

    @ManyToOne
    @JoinColumn(name = "apartment")
    private ApartmentModel ap_pk;

    public ParkingModel() {
    }

    public ParkingModel(@Min(value = 0, message = "O número não pode ser negativo") int number, ApartmentModel ap_pk) {
        this.number = number;
        this.ap_pk = ap_pk;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ApartmentModel getAp_pk() {
        return ap_pk;
    }

    public void setAp_pk(ApartmentModel ap_pk) {
        this.ap_pk = ap_pk;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ParkingModel other = (ParkingModel) obj;
        if (number != other.number) {
            return false;
        }
        return true;
    }
}
