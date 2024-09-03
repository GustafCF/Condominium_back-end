package com.br.condominio.house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.CarModel;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {

}
