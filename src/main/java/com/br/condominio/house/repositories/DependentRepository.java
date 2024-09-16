package com.br.condominio.house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.DependentModel;

@Repository
public interface DependentRepository extends JpaRepository<DependentModel, Long> {
    List<DependentModel> findByName(String name);
}
