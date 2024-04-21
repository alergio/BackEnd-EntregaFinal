package com.dh.dental.clinic.controller;
import com.dh.dental.clinic.service.impl.DentistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DentistService dentistService;


    // test de integracion
    @Test
    void getDentist() throws Exception {



        mockMvc.perform(get("/dentist/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.Dentist.name").value("Maria"))
                .andExpect(jsonPath("$.data.Dentist.surname").value("Loreiro"))
                .andExpect(jsonPath("$.data.Dentist.enrollment").value("MFG356"));

    }

}
