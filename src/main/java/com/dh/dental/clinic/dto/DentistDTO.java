package com.dh.dental.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DentistDTO implements EntityIdentificatorDTO {
    private Long id;
    private String name;
    private String surname;
    private String enrollment;
    private Set<AppointmentDTO> appointmentDTOList = new HashSet<>();
}
