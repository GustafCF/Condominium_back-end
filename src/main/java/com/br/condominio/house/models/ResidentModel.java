package com.br.condominio.house.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "TB_RESIDENTES")
public class ResidentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "Nome")
    private String residentName;
    @Column(name = "Sobrenome")
    private String lastName;
    @Column(name = "Data_Nascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @Column(name = "Idade")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private int age;
    @Column(name = "Proprietario")
    private Boolean proprietario;
    @Column(name = "Resigtro_GERAL")
    private String rg;
    @Column(name = "CPF")
    @CPF(message = "CPF inválido")
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<CarModel> car = new ArrayList<>();
    
    public ResidentModel() {
    }

    public ResidentModel(UUID id, String residentName, String lastName, LocalDate dataNascimento,
            @Min(value = 0, message = "A idade não pode ser negativa") int age, Boolean proprietario, String rg,
            @CPF(message = "CPF inválido") String cpf) {
        this.id = id;
        this.residentName = residentName;
        this.lastName = lastName;
        this.dataNascimento = dataNascimento;
        this.age = age;
        this.proprietario = proprietario;
        this.rg = rg;
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getProprietario() {
        return proprietario;
    }

    public void setProprietario(Boolean proprietario) {
        this.proprietario = proprietario;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<CarModel> getCar() {
        return car;
    }

    public void setCar(List<CarModel> car) {
        this.car = car;
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
        ResidentModel other = (ResidentModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}