package com.br.condominio.house.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "TB_DEPENDENTS")
public class DependentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nome")
    private String name;
    @Column(name = "Sobrenome")
    private String lastName;
    @Column(name = "Data_Nascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateBirth;
    @Column(name = "Idade")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private int age;

    //A anotação cascade define como as operações de persistência devem ser propagadas de uma entidade para suas entidades relacionadas.
    //A anotação fetch define como as entidades relacionadas devem ser carregadas do banco de dados.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_son_father", joinColumns = @JoinColumn(name = "son_id"), inverseJoinColumns = @JoinColumn(name = "fathers_id"))
    private List<ResidentModel> fathers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TB_APARTMENT_OCC_DEPENDENT", joinColumns = @JoinColumn(name = "dependent_id"), inverseJoinColumns = @JoinColumn(name = "apartment_id"))
    private Set<ApartmentModel> ap_son = new HashSet<>();

    public DependentModel() {
    }

    public DependentModel(Long id, String name, String lastName, LocalDate dateBirth,
            @Min(value = 0, message = "A idade não pode ser negativa") int age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getAge() {
        return age;
    }

    public List<ResidentModel> getFathers() {
        return fathers;
    }

    public Set<ApartmentModel> getAp_son() {
        return ap_son;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DependentModel other = (DependentModel) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
