package com.br.condominio.house.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.TimestampModel;

@Repository
public interface TimestampRepository extends JpaRepository<TimestampModel, UUID> {

    List<TimestampModel> findByRes(String res);

    List<TimestampModel> findByDependent(String dependent);

}
