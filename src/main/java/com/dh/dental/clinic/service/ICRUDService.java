package com.dh.dental.clinic.service;

import com.dh.dental.clinic.dto.DTOResponse;

public interface ICRUDService<T> {
    DTOResponse save (T entityDTO);

    DTOResponse listAll();

    DTOResponse searchById(Long id);

    DTOResponse update(T entityDTO);

    DTOResponse delete(Long id);
}
