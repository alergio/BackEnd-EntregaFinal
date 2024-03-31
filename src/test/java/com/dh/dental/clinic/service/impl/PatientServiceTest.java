package com.dh.dental.clinic.service.impl;

import com.dh.dental.clinic.dto.AddressDTO;
import com.dh.dental.clinic.dto.DTOResponse;
import com.dh.dental.clinic.dto.PatientDTO;
import com.dh.dental.clinic.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    ModelMapper modelMapper = new ModelMapper();
    PatientDTO patientDTOSaved;

    DTOResponse<PatientDTO> patientDTOResponseSave;
    DTOResponse<PatientDTO> patientDTOResponseUpdate;
    DTOResponse<PatientDTO> patientDTOResponseDelete;
    DTOResponse<PatientDTO> patientDTOResponseSearchById;
    DTOResponse<PatientDTO> patientDTOResponseSearchByIdWithException;

    Long entityID;
    Long addressID;

    public void assertResponse(DTOResponse<PatientDTO> patientDTOResponse, String patientDTOExpected)
            throws JsonProcessingException {

        String patientDTOResponseJson = objectMapper.writeValueAsString(patientDTOResponse);

        assertEquals(patientDTOExpected, patientDTOResponseJson);
    }

    @BeforeAll
    void setUp() throws JsonProcessingException {
        // data initialize
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Av italia");
        addressDTO.setNumber("123");
        addressDTO.setState("Montevideo");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setDni("12345");
        patientDTO.setName("Raul");
        patientDTO.setSurname("Perez");
        patientDTO.setRegistrationDate(LocalDate.now());
        patientDTO.setAddressDTO(addressDTO);

        // prepare save
        patientDTOResponseSave = patientService.save(patientDTO);

        patientDTOSaved = (PatientDTO) patientDTOResponseSave.getData().get("Patient");
        entityID = patientDTOSaved.getId();
        addressID = patientDTOSaved.getAddressDTO().getId();

        // prepare update
        PatientDTO patientDTOForUpdate = modelMapper.map(patientDTO, PatientDTO.class);
        patientDTOForUpdate.setId(entityID);
        patientDTOForUpdate.setName("Juan Sebastian");
        patientDTOResponseUpdate = patientService.update(patientDTOForUpdate);

        // prepare searchById
        patientDTOResponseSearchById = patientService.searchById(entityID);

        // prepare delete
        patientDTOResponseDelete = patientService.delete(entityID);
    }

    @Test
    @Order(1)
    void save() throws JsonProcessingException {
        String patientDtoExpected = "{\"statusCode\":200,\"message\":\"Patient saved successfully. {}\"" +
                ",\"data\":{\"Patient\":{\"id\":" + entityID + ",\"name\":\"Raul\",\"surname\":\"Perez\",\"dni\":\"12345\"," +
                "\"registrationDate\":[2024,3,31],\"addressDTO\":{\"id\":" + addressID + ",\"street\":\"Av italia\",\"number\"" +
                ":\"123\",\"state\":\"Montevideo\"},\"appointmentDTOList\":[]}}}";
        assertResponse(patientDTOResponseSave, patientDtoExpected);
    }

    @Test
    @Order(2)
    void update() throws JsonProcessingException {
        String patientDtoExpected = "{\"statusCode\":200,\"message\":\"Patient updated successfully: {}\"" +
                ",\"data\":{\"Patient\":{\"id\":" + entityID + ",\"name\":\"Juan Sebastian\",\"surname\":\"Perez\",\"dni\":\"12345\"" +
                ",\"registrationDate\":[2024,3,31],\"addressDTO\":{\"id\":" + addressID + ",\"street\":\"Av italia\",\"number\":\"123\"" +
                ",\"state\":\"Montevideo\"},\"appointmentDTOList\":[]}}}";

        assertResponse(patientDTOResponseUpdate, patientDtoExpected);
    }

    @Test
    @Order(3)
    void searchById() throws JsonProcessingException {
        String patientDTOExpected = "{\"statusCode\":200,\"message\":\"Patient successfully found {}" +
                "\",\"data\":{\"Patient\":{\"id\":" + entityID + ",\"name\":\"Juan Sebastian\",\"surname\":\"Perez\",\"dni\":\"12345\"," +
                "\"registrationDate\":[2024,3,31],\"addressDTO\":{\"id\":" + addressID + ",\"street\":\"Av italia\",\"number\":\"123\"," +
                "\"state\":\"Montevideo\"},\"appointmentDTOList\":[]}}}";

        assertResponse(patientDTOResponseSearchById, patientDTOExpected);
    }

    @Test
    @Order(4)
    void delete() throws JsonProcessingException {
        String patientDTOExpected = "{\"statusCode\":200" +
                ",\"message\":\"Patient deleted succesfully: {}\"," +
                "\"data\":{\"Patient\":{\"id\":" + entityID + ",\"name\":\"Juan Sebastian\"," +
                "\"surname\":\"Perez\",\"dni\":\"12345\",\"registrationDate\":[2024,3,31]," +
                "\"addressDTO\":{\"id\":" + addressID + ",\"street\":\"Av italia\",\"number\":\"123\"," +
                "\"state\":\"Montevideo\"},\"appointmentDTOList\":[]}}}";
        assertResponse(patientDTOResponseDelete, patientDTOExpected);
    }

    @Test
    @Order(5)
    void searchByIdWithException() {
        assertThrows(ResourceNotFoundException.class, () ->{
            patientDTOResponseSearchByIdWithException = patientService.searchById(entityID);
        });
    }

}