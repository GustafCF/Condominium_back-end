package com.br.condominio.house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.condominio.house.models.EmailModel;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
