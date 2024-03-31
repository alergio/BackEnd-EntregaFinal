package com.dh.dental.clinic.service.impl;

import com.dh.dental.clinic.dto.DTOResponse;
import com.dh.dental.clinic.mapper.*;
import com.dh.dental.clinic.service.ICRUDService;
import com.dh.dental.clinic.dto.PatientDTO;
import com.dh.dental.clinic.entity.Patient;
import com.dh.dental.clinic.repository.impl.IPatientRepository;
import org.springframework.stereotype.Service;
@Service
public class PatientService implements ICRUDService<PatientDTO> {
    private final IPatientRepository patientRepository;
    private final CRUDMapper<PatientDTO, Patient> crudMapper;
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        this.crudMapper = new CRUDMapper<PatientDTO, Patient>(PatientDTO.class, Patient.class, patientRepository);
    }
    @Override
    public DTOResponse<PatientDTO> save(PatientDTO patientDTO) {
        return crudMapper.create(patientDTO);
    }

    @Override
    public DTOResponse<PatientDTO> listAll() {
        return crudMapper.readAll();
    }

    @Override
    public DTOResponse<PatientDTO> searchById(Long id) {
        return crudMapper.readById(id);
    }

    @Override
    public DTOResponse<PatientDTO> update(PatientDTO patientDTO) {
        return crudMapper.update(patientDTO);
    }

    @Override
    public DTOResponse<PatientDTO> delete(Long id) {
        return crudMapper.delete(id);
    }
}
