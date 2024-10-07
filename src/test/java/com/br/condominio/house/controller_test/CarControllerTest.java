package com.br.condominio.house.controller_test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.br.condominio.house.controllers.CarController;
import com.br.condominio.house.models.CarModel;
import com.br.condominio.house.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testFindAll() throws Exception {
        CarModel car1 = new CarModel(1L, "Toyota", "Corolla", "Sedan", "ABC1234", 2020, null);
        CarModel car2 = new CarModel(2L, "Honda", "Civic", "Sedan", "DEF5678", 2019, null);

        List<CarModel> cars = Arrays.asList(car1, car2);

        when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(car1.getId()))
                .andExpect(jsonPath("$[1].id").value(car2.getId()));
    }

    @Test
    void testFindById() throws Exception {
        CarModel car = new CarModel(1L, "Toyota", "Corolla", "Sedan", "ABC1234", 2020, null);

        when(carService.findById(1L)).thenReturn(car);

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.brand").value(car.getBrand()))
                .andExpect(jsonPath("$.model").value(car.getModel()));
    }

    @Test
    void testRegisterCar() throws Exception {
        CarModel car = new CarModel(1L, "Toyota", "Corolla", "Sedan", "ABC1234", 2020, null);
        Long residentId = 1L;

        when(carService.registerCarForResident(eq(residentId), any(CarModel.class))).thenReturn(car);

        mockMvc.perform(post("/cars/register/" + residentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.brand").value(car.getBrand()))
                .andExpect(jsonPath("$.model").value(car.getModel()));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).delete(1L);

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).delete(1L);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
