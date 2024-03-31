package com.dh.dental.clinic.service.impl;

import com.dh.dental.clinic.dto.DTOResponse;
import com.dh.dental.clinic.dto.DentistDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DentistServiceTest {

    @Autowired
    private DentistService dentistService;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    ModelMapper modelMapper = new ModelMapper();
    DentistDTO dentistDTOSaved;

    DTOResponse<DentistDTO> dentistDTOResponseSave;
    DTOResponse<DentistDTO> dentistDTOResponseUpdate;
    DTOResponse<DentistDTO> dentistDTOResponseDelete;
    DTOResponse dentistDTOResponseSearchById;

    Long entityID;

    public void assertResponse(DTOResponse<DentistDTO> dentistDTOResponse, String dentistDTOExpected)
            throws JsonProcessingException {

        String dentistDTOResponseJson = objectMapper.writeValueAsString(dentistDTOResponse);
        assertEquals(dentistDTOExpected, dentistDTOResponseJson);
    }

    @BeforeAll
    void setUp() throws JsonProcessingException {

        DentistDTO dentistDTO = new DentistDTO();
        dentistDTO.setName("Horacio");
        dentistDTO.setSurname("Gimenez");
        dentistDTO.setEnrollment("ABA458");

        // prepare save
        dentistDTOResponseSave = dentistService.save(dentistDTO);

        dentistDTOSaved = (DentistDTO) dentistDTOResponseSave.getData().get("Dentist");
        entityID = dentistDTOSaved.getId();

        // prepare update
        DentistDTO dentistDTOForUpdate = modelMapper.map(dentistDTO, DentistDTO.class);
        dentistDTOForUpdate.setId(entityID);
        dentistDTOForUpdate.setName("Ana Maria");
        dentistDTOForUpdate.setSurname("Prada");
        dentistDTOResponseUpdate = dentistService.update(dentistDTOForUpdate);

        // prepare searchById
        dentistDTOResponseSearchById = dentistService.searchById(entityID);

        // prepare delete
        dentistDTOResponseDelete = dentistService.delete(entityID);
    }

    @Test
    @Order(1)
    void save() throws JsonProcessingException {
        String dentistDtoExpected = "{\"statusCode\":200,\"message\":\"Dentist saved successfully. {}\"" +
         ",\"data\":{\"Dentist\":{\"id\":" + entityID + ",\"name\":\"Horacio\",\"surname\":\"Gimenez\",\"enrollment\":\"ABA458\"" +
                ",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseSave, dentistDtoExpected);
    }

    @Test
    @Order(2)
    void update() throws JsonProcessingException {
        String dentistDtoExpected = "{\"statusCode\":200,\"message\":\"Dentist updated successfully: {}\"" +
        ",\"data\":{\"Dentist\":{\"id\":" + entityID + ",\"name\":\"Ana Maria\",\"surname\":\"Prada\",\"enrollment\":\"ABA458\"" +
                ",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseUpdate, dentistDtoExpected);
    }

    @Test
    @Order(3)
    void searchById() throws JsonProcessingException {
        String dentistDTOExpected = "{\"statusCode\":200,\"message\":\"Dentist successfully found {}" +
        "\",\"data\":{\"Dentist\":{\"id\":" + entityID + ",\"name\":\"Ana Maria\",\"surname\":\"Prada\",\"enrollment\":\"ABA458\"" +
                ",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseSearchById, dentistDTOExpected);
    }

    @Test
    @Order(4)
    void delete() throws JsonProcessingException {
        String dentistDTOExpected = "{\"statusCode\":200" +
                ",\"message\":\"Dentist deleted succesfully: {}\"," +
                "\"data\":{\"Dentist\":{\"id\":" + entityID + ",\"name\":\"Ana Maria\"," +
                "\"surname\":\"Prada\",\"enrollment\":\"ABA458\",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseDelete, dentistDTOExpected);
    }
}