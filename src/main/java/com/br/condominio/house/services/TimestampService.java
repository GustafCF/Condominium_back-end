package com.br.condominio.house.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.TimestampModel;
import com.br.condominio.house.repositories.DependentRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.TimestampRepository;
import com.br.condominio.house.services.exceptions.UnauthorizedException;

@Service
public class TimestampService {

    private final TimestampRepository timestampRepository;
    private final ResidentRepository residentRepository;
    private final DependentRepository dependentRepository;

    public TimestampService(TimestampRepository timestampRepository, ResidentRepository residentRepository, DependentRepository dependentRepository) {
        this.timestampRepository = timestampRepository;
        this.residentRepository = residentRepository;
        this.dependentRepository = dependentRepository;
    }

    public TimestampModel times(Long id) {
        Optional<ResidentModel> res = residentRepository.findById(id);
        Optional<DependentModel> dependent = dependentRepository.findById(id);

        if (res.isEmpty() && dependent.isEmpty()) {
            throw new UnauthorizedException("Não autorizado");
        }
        TimestampModel ticket = new TimestampModel();
        ticket.setTicketTime(LocalDateTime.now());
        if (res.isEmpty()) {
            ticket.setRes(res.get());
        }
        if (dependent.isEmpty()) {
            ticket.setDependent(dependent.get());
        }
        return timestampRepository.save(ticket);
    }

}
