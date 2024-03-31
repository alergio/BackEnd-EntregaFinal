package com.dh.dental.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DentistDTO implements EntityIdentificatorDTO {
    private Long id;
    private String name;
    private String surname;
    private String enrollment;
    private Set<AppointmentDTO> appointmentDTOList = new HashSet<>();
}
