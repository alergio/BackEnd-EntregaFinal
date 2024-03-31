package com.dh.dental.clinic.service.impl;

import com.dh.dental.clinic.dto.DTOResponse;
import com.dh.dental.clinic.dto.DentistDTO;
import com.dh.dental.clinic.dto.PatientDTO;
import com.dh.dental.clinic.entity.Dentist;
import com.dh.dental.clinic.entity.Patient;
import com.dh.dental.clinic.mapper.*;
import com.dh.dental.clinic.repository.impl.IDentistRepository;
import com.dh.dental.clinic.service.ICRUDService;
import org.springframework.stereotype.Service;

@Service
public class DentistService implements ICRUDService<DentistDTO> {

    private final IDentistRepository dentistRepository;
    private final CRUDMapper<DentistDTO, Dentist> crudMapper;

    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
        this.crudMapper = new CRUDMapper<DentistDTO, Dentist>(DentistDTO.class, Dentist.class, dentistRepository);

    }

    @Override
    public DTOResponse<DentistDTO> save(DentistDTO dentistDTO) {
        return crudMapper.create(dentistDTO);
    }

    @Override
    public DTOResponse<DentistDTO> listAll() {
        return crudMapper.readAll();
    }

    @Override
    public DTOResponse<DentistDTO> searchById(Long id) {
        return crudMapper.readById(id);
    }

    @Override
    public DTOResponse<DentistDTO> update(DentistDTO dentistDTO) {
        return crudMapper.update(dentistDTO);
    }

    @Override
    public DTOResponse<DentistDTO> delete(Long id){
        return crudMapper.delete(id);
    }
}
