package com.br.condominio.house.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_APARTAMENTO")
public class ApartmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int apartment;
    private String block; 

    @JsonIgnore
    @ManyToMany(mappedBy = "ap")
    private Set<ResidentModel> occupant = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "ap_son")
    private Set<DependentModel> occ_son = new HashSet<>();

    public ApartmentModel(){
    }

    public ApartmentModel(Long id, int apartment, String block){
        this.id = id;
        this.apartment = apartment;
        this.block = block;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApartmentModel other = (ApartmentModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
