package com.br.condominio.house.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.ResidentModel;

@Repository
public interface ResidentRepository extends JpaRepository<ResidentModel, Long> {

    Optional<ResidentModel> findByResidentName(String residentName);
}
