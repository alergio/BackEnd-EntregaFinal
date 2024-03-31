package com.dh.dental.clinic.service.impl;

import com.dh.dental.clinic.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentServiceTest {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DentistService dentistService;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    ModelMapper modelMapper = new ModelMapper();
    PatientDTO patientDTOSaved;
    DentistDTO dentistDTOSaved;
    AppointmentDTO appointmentDTOSaved;

    DTOResponse<PatientDTO> patientDTOResponseSave;
    DTOResponse<PatientDTO> patientDTOResponseDelete;
    DTOResponse<DentistDTO> dentistDTOResponseSave;
    DTOResponse<DentistDTO> dentistDTOResponseDelete;
    DTOResponse<AppointmentDTO> appointmentDTOResponseSave;
    DTOResponse<AppointmentDTO> appointmentDTOResponseUpdate;
    DTOResponse<AppointmentDTO> appointmentDTOResponseSearchById;
    DTOResponse<AppointmentDTO> appointmentDTOResponseDelete;
    Long patientID;
    Long patientAddressID;
    Long dentistID;
    Long appointmentID;

    public void assertResponse(DTOResponse actualDTOResponse, String expectedDTOResponse)
            throws JsonProcessingException {
        String actualtDTOResponseJson = objectMapper.writeValueAsString(actualDTOResponse);
        assertEquals(expectedDTOResponse, actualtDTOResponseJson);
    }

    @BeforeAll
    void setUp() throws JsonProcessingException {
        // data initialize
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Av italia For Test");
        addressDTO.setNumber("12345");
        addressDTO.setState("Montevideo City");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setDni("1212");
        patientDTO.setName("Raul For Test");
        patientDTO.setSurname("Perez Testeable");
        patientDTO.setRegistrationDate(LocalDate.of(2022, 1, 1));

        patientDTO.setAddressDTO(addressDTO);

        DentistDTO dentistDTO = new DentistDTO();
        dentistDTO.setName("Martin Dentista For Test");
        dentistDTO.setSurname("Gimenez Testeable");
        dentistDTO.setEnrollment("C4CC2");

        // prepare patient save
        patientDTOResponseSave = patientService.save(patientDTO);
        patientDTOSaved = (PatientDTO) patientDTOResponseSave.getData().get("Patient");
        patientID = patientDTOSaved.getId();
        patientAddressID = patientDTOSaved.getAddressDTO().getId();

        // prepare dentist save
        dentistDTOResponseSave = dentistService.save(dentistDTO);
        dentistDTOSaved = (DentistDTO) dentistDTOResponseSave.getData().get("Dentist");
        dentistID = dentistDTOSaved.getId();


        // prepare appointment
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        PatientDTO patientDTOForAppointment = new PatientDTO();
        DentistDTO dentistDTOForAppointment = new DentistDTO();

        patientDTOForAppointment.setId(patientID);
        dentistDTOForAppointment.setId(dentistID);

        appointmentDTO.setDateAppointment(LocalDateTime.of(2022, 1, 1, 10, 30));
        appointmentDTO.setPatientDTO(patientDTOForAppointment);
        appointmentDTO.setDentistDTO(dentistDTOForAppointment);

        // appointment save
        appointmentDTOResponseSave = appointmentService.save(appointmentDTO);
        appointmentDTOSaved = (AppointmentDTO) appointmentDTOResponseSave.getData().get("Appointment");
        appointmentID = appointmentDTOSaved.getId();

        // appointment update
        AppointmentDTO appointmentDTOForUpdate = modelMapper.map(appointmentDTO, AppointmentDTO.class);
        appointmentDTOForUpdate.setId(appointmentID);
        appointmentDTOForUpdate.setDateAppointment(LocalDateTime.of(2035, 5, 5, 15, 30));
        appointmentDTOResponseUpdate = appointmentService.update(appointmentDTOForUpdate);

        // appointment search by id
        appointmentDTOResponseSearchById = appointmentService.searchById(appointmentID);

        // delete all entities
        appointmentDTOResponseDelete = appointmentService.delete(appointmentID);
        dentistDTOResponseDelete = dentistService.delete(dentistID);
        patientDTOResponseDelete = patientService.delete(patientID);
    }

    @Test
    @Order(1)
    void savePatient() throws JsonProcessingException {
        String patientDtoExpected = "{\"statusCode\":200,\"message\":\"Patient saved successfully. {}\"" +
                ",\"data\":{\"Patient\":{\"id\":" + patientID + ",\"name\":\"Raul For Test\",\"surname\":\"Perez Testeable\",\"dni\":\"1212\"," +
                "\"registrationDate\":[2022,1,1],\"addressDTO\":{\"id\":" + patientAddressID + ",\"street\":\"Av italia For Test\",\"number\"" +
                ":\"12345\",\"state\":\"Montevideo City\"},\"appointmentDTOList\":[]}}}";
        assertResponse(patientDTOResponseSave, patientDtoExpected);
    }

    @Test
    @Order(2)
    void saveDentist() throws JsonProcessingException {
        String dentistDTOExpected = "{\"statusCode\":200,\"message\":\"Dentist saved successfully. {}" +
                "\",\"data\":{\"Dentist\":{\"id\":" + dentistID + ",\"name\":\"Martin Dentista For Test\",\"surname\"" +
                ":\"Gimenez Testeable\",\"enrollment\":\"C4CC2\",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseSave, dentistDTOExpected);
    }

    @Test
    @Order(3)
    void saveAppointment() throws JsonProcessingException {
        String appointmentResponseExpected = "{\"statusCode\":200,\"message\":\"Appointment saved successfully. {}\"" +
                ",\"data\":{\"Appointment\":{\"id\":" + appointmentID +",\"dateAppointment\":" + "[2022,1,1,10,30]" + ",\"patientDTO\":" +
                "{\"id\":" + patientID + ",\"name\":null,\"surname\":null,\"dni\":null,\"registrationDate\":null,\"addressDTO\":null," +
                "\"appointmentDTOList\":[]},\"dentistDTO\":{\"id\":" + dentistID + ",\"name\":null,\"surname\":null,\"enrollment" +
                "\":null,\"appointmentDTOList\":[]}}}}";
        assertResponse(appointmentDTOResponseSave, appointmentResponseExpected);
    }

    @Test
    @Order(4)
    void updateAppointment() throws JsonProcessingException {
        String appointmentResponseExpected = "{\"statusCode\":200,\"message\":\"Appointment updated" +
                " successfully: {}\",\"data\":{\"Appointment\":{\"id\":" + appointmentID + ",\"dateAppointment\"" +
                ":[2035,5,5,15,30],\"patientDTO\":{\"id\":" + patientID + ",\"name\":\"Raul For Test\"" +
                ",\"surname\":\"Perez Testeable\",\"dni\":\"1212\",\"registrationDate\"" +
                ":[2022,1,1],\"addressDTO\":{\"id\":" + patientAddressID + ",\"street\":\"Av italia For Test\"," +
                "\"number\":\"12345\",\"state\":\"Montevideo City\"},\"appointmentDTOList\"" +
                ":[]},\"dentistDTO\":{\"id\":" + dentistID + ",\"name\":\"Martin Dentista For Test\",\"surname\":" +
                "\"Gimenez Testeable\",\"enrollment\":\"C4CC2\",\"appointmentDTOList\":[]}}}}";

        assertResponse(appointmentDTOResponseUpdate, appointmentResponseExpected);
    }

    @Test
    @Order(5)
    void searchByIdAppointment() throws JsonProcessingException {
        String appointmentResponseExpected = "{\"statusCode\":200,\"message\":\"Appointment successfully found {}\"" +
                ",\"data\":{\"Appointment\":{\"id\":" + appointmentID + ",\"dateAppointment\":" + "[2035,5,5,15,30]" + ",\"patientDTO\"" +
                ":{\"id\":" + patientID + ",\"name\":\"Raul For Test\",\"surname\":\"Perez Testeable\",\"dni\":\"1212\",\"registrationDate\"" +
                ":[2022,1,1],\"addressDTO\":{\"id\":" + patientAddressID + ",\"street\":\"Av italia For Test\",\"number\":\"12345\",\"state\":" +
                "\"Montevideo City\"},\"appointmentDTOList\":[]},\"dentistDTO\":{\"id\":" + dentistID + ",\"name\":\"Martin Dentista For Test\"" +
                ",\"surname\":\"Gimenez Testeable\",\"enrollment\":\"C4CC2\",\"appointmentDTOList\":[]}}}}";

        assertResponse(appointmentDTOResponseSearchById, appointmentResponseExpected);
    }

    @Test
    @Order(6)
    void deletePatient() throws JsonProcessingException {
        String patientResponseExpected = "{\"statusCode\":200,\"message\":" +
                "\"Patient deleted succesfully: {}\",\"data\":{\"Patient\"" +
                ":{\"id\":" + patientID + ",\"name\":\"Raul For Test\",\"surname\":" +
                "\"Perez Testeable\",\"dni\":\"1212\",\"registrationDate\"" +
                ":[2022,1,1],\"addressDTO\":{\"id\":" + patientAddressID + ",\"street\":\"Av italia For Test\"" +
                ",\"number\":\"12345\",\"state\":\"Montevideo City\"},\"appointmentDTOList\":[]}}}";
        assertResponse(patientDTOResponseDelete, patientResponseExpected);
    }

    @Test
    @Order(7)
    void deleteDentist() throws JsonProcessingException {
        String dentistResponseExpected = "{\"statusCode\":200,\"message\":" +
                "\"Dentist deleted succesfully: {}\",\"data\":{\"Dentist\"" +
                ":{\"id\":" + dentistID + ",\"name\":\"Martin Dentista For Test\",\"surname\"" +
                ":\"Gimenez Testeable\",\"enrollment\":\"C4CC2\",\"appointmentDTOList\":[]}}}";
        assertResponse(dentistDTOResponseDelete, dentistResponseExpected);
    }

    @Test
    @Order(8)
    void deleteAppointment() throws JsonProcessingException {
        String appointmentResponseExpected = "{\"statusCode\":200,\"message\":" +
                "\"Appointment deleted succesfully: {}\",\"data\":{\"Appointment\":" +
                "{\"id\":" + appointmentID + ",\"dateAppointment\":[2035,5,5,15,30],\"patientDTO\"" +
                ":{\"id\":" + patientID + ",\"name" +
                "\":\"Raul For Test\",\"surname\":\"Perez Testeable\",\"dni\":\"1212\"," +
                "\"registrationDate\":[2022,1,1],\"addressDTO\":{\"id\":" + patientAddressID + ",\"street\"" +
                ":\"Av italia For Test\",\"number\":\"12345\",\"state\":\"Montevideo City\"}" +
                ",\"appointmentDTOList\":[]},\"dentistDTO\":{\"id\":" + dentistID + ",\"name\":" +
                "\"Martin Dentista For Test\",\"surname\":\"Gimenez Testeable\",\"enrollment\"" +
                ":\"C4CC2\",\"appointmentDTOList\":[]}}}}";
        assertResponse(appointmentDTOResponseDelete, appointmentResponseExpected);
    }

}