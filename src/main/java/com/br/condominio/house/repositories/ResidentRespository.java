package com.br.condominio.house.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.ResidentModel;

@Repository
public interface ResidentRespository extends JpaRepository<ResidentModel, UUID > {
    List<ResidentModel> findByResidentName(String residentName);

}
