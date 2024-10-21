package com.br.condominio.house.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.br.condominio.house.models.DependentModel;
import com.br.condominio.house.models.FunctionaryModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.models.TimestampModel;
import com.br.condominio.house.repositories.DependentRepository;
import com.br.condominio.house.repositories.FunctionaryRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.repositories.TimestampRepository;
import com.br.condominio.house.services.exceptions.UnauthorizedException;

@Service
public class TimestampService {

    private final TimestampRepository timestampRepository;
    private final ResidentRepository residentRepository;
    private final DependentRepository dependentRepository;
    private final FunctionaryRepository functionaryRepository;

    public TimestampService(TimestampRepository timestampRepository, ResidentRepository residentRepository, DependentRepository dependentRepository, FunctionaryRepository functionaryRepository) {
        this.timestampRepository = timestampRepository;
        this.residentRepository = residentRepository;
        this.dependentRepository = dependentRepository;
        this.functionaryRepository = functionaryRepository;
    }

    public TimestampModel times(Long id) {
        Optional<ResidentModel> res = residentRepository.findById(id);
        Optional<DependentModel> dependent = dependentRepository.findById(id);
        Optional<FunctionaryModel> func = functionaryRepository.findById(id);

        if (res.isEmpty() && dependent.isEmpty() && func.isEmpty()) {
            throw new UnauthorizedException("Não autorizado");
        }
        TimestampModel ticket = new TimestampModel();
        ticket.setTicketTime(LocalDateTime.now());
        if (res.isPresent()) {
            ticket.setRes(res.get());
        }
        if (dependent.isPresent()) {
            ticket.setDependent(dependent.get());
        }
        if (func.isPresent()) {
            ticket.setFunc(func.get());
        }
        return timestampRepository.save(ticket);
    }

}
