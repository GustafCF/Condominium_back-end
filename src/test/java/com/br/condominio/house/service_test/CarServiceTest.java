package com.br.condominio.house.service_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.models.ResidentModel;
import com.br.condominio.house.repositories.CarRepository;
import com.br.condominio.house.repositories.ResidentRepository;
import com.br.condominio.house.services.CarService;

public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ResidentRepository residentRepository;

    private ResidentModel resident;
    private CarModel car;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        resident = new ResidentModel();
        resident.setId(1L);
        resident.setResidentName("John");
        resident.setLastName("Doe");
        resident.setDataNascimento(LocalDate.of(1999, 1, 12));
        resident.setAge(25);
        resident.setProprietario(true);
        resident.setRg("123456");
        resident.setCpf("56048536151");

        car = new CarModel();
        car.setId(1L);
        car.setModel("ModelTest");
        car.setBrand("BarndTest");
        car.setType("TypeTest");
        car.setPlate("PLACA-TEST");
        car.setAno(1900);
    }

    @Test
    void shouldRegisterCarForResidentSuccessfully() {
        when(residentRepository.findById(anyLong())).thenReturn(Optional.of(resident));
        when(carRepository.save(any(CarModel.class))).thenReturn(car);

        CarModel savedCar = carService.registerCarForResident(1L, car);

        assertNotNull(savedCar);
        assertEquals("ModelTest", savedCar.getModel());
        assertEquals("BarndTest", savedCar.getBrand());
        assertEquals("TypeTest", savedCar.getType());
        assertEquals("PLACA-TEST", savedCar.getPlate());
        assertEquals(1900, savedCar.getAno());
        assertEquals(resident, savedCar.getOwner());

        verify(residentRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(CarModel.class));
    }

    @Test
    void shouldThrowExceptionWhenResidentNotFound() {
        when(residentRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            carService.registerCarForResident(2L, car);
        });

        assertEquals("Resident not found with id: 2", exception.getMessage());
        verify(carRepository, never()).save(any(CarModel.class));
    }

}
