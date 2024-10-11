package com.br.condominio.house.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.FunctionaryModel;

@Repository
public interface FunctionaryRepository extends JpaRepository<FunctionaryModel, Long> {

    Optional<FunctionaryModel> findByName(String name);

    Optional<FunctionaryModel> findByUsername(String username);

}
