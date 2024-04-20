package com.dh.dental.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDTO implements EntityIdentificatorDTO {
    private Long id;
    private LocalDateTime dateAppointment;
    private PatientDTO patientDTO;
    private DentistDTO dentistDTO;
}
