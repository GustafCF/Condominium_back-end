package com.br.condominio.house.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_APARTAMENT")
public class ApartmentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int apartment;
    private String block;

    @JsonIgnore
    @ManyToMany(mappedBy = "ap", cascade = CascadeType.ALL)
    private Set<ResidentModel> occupant = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "ap_son", cascade = CascadeType.ALL)
    private Set<DependentModel> occ_son = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ap_pk", cascade = CascadeType.ALL)
    private Set<ParkingModel> vacancy = new HashSet<>();

    public ApartmentModel() {
    }

    public ApartmentModel(int apartment, String block) {
        this.apartment = apartment;
        this.block = block;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Set<ResidentModel> getOccupant() {
        return occupant;
    }

    public Set<DependentModel> getOcc_son() {
        return occ_son;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + apartment;
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
        ApartmentModel other = (ApartmentModel) obj;
        if (apartment != other.apartment) {
            return false;
        }
        return true;
    }

}
