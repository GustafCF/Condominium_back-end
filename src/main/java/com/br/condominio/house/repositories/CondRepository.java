package com.br.condominio.house.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.CondModel;

@Repository
public interface CondRepository extends JpaRepository<CondModel, UUID>{

}
