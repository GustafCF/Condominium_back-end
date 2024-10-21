package com.br.condominio.house.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.condominio.house.models.dto.LoginRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "TB_FUNCIONARIOS")
public class FunctionaryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "Nome")
    private String name;
    @Column(name = "Sobrenome")
    private String lastName;
    @Column(name = "Email")
    private String email;
    @Column(name = "Rg")
    private String rg;
    @CPF(message = "CPF inválido ou com formato incorreto")
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "Endereço")
    private String address;
    @Column(name = "Nome_Usuario", unique = true)
    @NotBlank
    private String username;
    @Column(name = "Senha")
    @NotBlank
    private String password;

    @ManyToMany
    @JoinTable(
            name = "functionary_role",
            joinColumns = @JoinColumn(name = "functionary_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleModel> ls_roles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "func", cascade = CascadeType.ALL)
    private List<TimestampModel> ticket = new ArrayList<>();

    public FunctionaryModel() {
    }

    public FunctionaryModel(Long id, String name, String lastName, String email, String rg,
            @CPF(message = "CPF inválido ou com formato incorreto") String cpf, String cep, String address,
            @NotBlank String username, @NotBlank String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.cep = cep;
        this.address = address;
        this.username = username;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<RoleModel> getLs_roles() {
        return ls_roles;
    }

    public List<TimestampModel> getTicket() {
        return ticket;
    }

    public boolean LoginValidationFunc(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
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
        FunctionaryModel other = (FunctionaryModel) obj;
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
