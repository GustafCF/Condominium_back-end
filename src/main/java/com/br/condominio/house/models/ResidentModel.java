package com.br.condominio.house.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.condominio.house.models.dto.LoginRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "TB_RESIDENTS")
public class ResidentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nome")
    private String residentName;
    @Column(name = "Sobrenome")
    private String lastName;
    @Column(name = "Data_Nascimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @Column(name = "Idade")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private Integer age;
    @Column(name = "Proprietario")
    private Boolean proprietario;
    @Column(name = "Resigtro_GERAL")
    private String rg;
    @Column(name = "CPF")
    @CPF(message = "CPF inválido")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "nome_usuario")
    private String username;
    @Column(name = "senha")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<CarModel> car = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "fathers", cascade = CascadeType.ALL)
    private List<DependentModel> dependent = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "TB_APARTMENT_RESIDENT", joinColumns = @JoinColumn(name = "resident_id"), inverseJoinColumns = @JoinColumn(name = "apartment_id"))
    private Set<ApartmentModel> ap = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "TB_USER_ROLE",
            joinColumns = @JoinColumn(name = "resident_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "res", cascade = CascadeType.ALL)
    private List<TimestampModel> ticket = new ArrayList<>();

    public ResidentModel() {
    }

    public ResidentModel(Long id, String residentName, String lastName, LocalDate dataNascimento,
            @Min(value = 0, message = "A idade não pode ser negativa") Integer age, Boolean proprietario, String rg,
            @CPF(message = "CPF inválido") String cpf, String email, String username, String password) {
        this.id = id;
        this.residentName = residentName;
        this.lastName = lastName;
        this.dataNascimento = dataNascimento;
        this.age = age;
        this.proprietario = proprietario;
        this.rg = rg;
        this.cpf = cpf;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CarModel> getCar() {
        return car;
    }

    public List<DependentModel> getDependent() {
        return dependent;
    }

    public Set<ApartmentModel> getAp() {
        return ap;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public List<TimestampModel> getTicket() {
        return ticket;
    }

    public boolean LoginValidation(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
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
        ResidentModel other = (ResidentModel) obj;
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
