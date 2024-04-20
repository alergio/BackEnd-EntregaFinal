package com.dh.dental.clinic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    private Long id;
    private String street;
    private String number;
    private String state;
}
