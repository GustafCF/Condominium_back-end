package com.br.condominio.house.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_TIMESTAMP")
public class TimestampModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private LocalDateTime ticketTime;

    @ManyToOne
    @JoinColumn(name = "id_ticketTime_resident")
    private ResidentModel res;

    @ManyToOne
    @JoinColumn(name = "id_ticketTime_depent")
    private DependentModel dependent;

    
    public TimestampModel() {
    }

    public TimestampModel(UUID id, LocalDateTime ticketTime) {
        this.id = id;
        this.ticketTime = ticketTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(LocalDateTime ticketTime) {
        this.ticketTime = ticketTime;
    }

    public ResidentModel getRes() {
        return res;
    }

    public void setRes(ResidentModel res) {
        this.res = res;
    }

    public DependentModel getDependent() {
        return dependent;
    }

    public void setDependent(DependentModel dependent) {
        this.dependent = dependent;
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
        TimestampModel other = (TimestampModel) obj;
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
