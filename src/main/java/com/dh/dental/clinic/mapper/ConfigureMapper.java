package com.dh.dental.clinic.mapper;

import com.dh.dental.clinic.dto.*;
import com.dh.dental.clinic.entity.Address;
import com.dh.dental.clinic.entity.Appointment;
import com.dh.dental.clinic.entity.Dentist;
import com.dh.dental.clinic.entity.Patient;
import org.apache.log4j.Logger;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.HashSet;
import java.util.Set;

public class ConfigureMapper {

    private static final Logger LOGGER = Logger.getLogger(ConfigureMapper.class);

    public ModelMapper modelMapper = new ModelMapper();

    public ModelMapper configureMapper() {




        /* ---------------------------------------------------------------------- */

        // TypeMap for Appointment
        TypeMap<Appointment, AppointmentDTO> appointmentMap = modelMapper.createTypeMap(Appointment.class, AppointmentDTO.class);
        TypeMap<AppointmentDTO, Appointment> appointmentDTOMap = modelMapper.createTypeMap(AppointmentDTO.class, Appointment.class);

        // Converter for Appointment skiping AppointmentList in Patient
        Converter<Patient, PatientDTO> patientConverter = context -> {

            Patient source = context.getSource();
            PatientDTO destination = new PatientDTO();

            destination.setId(source.getId());

            if (source.getName() != null) {
                destination.setName(source.getName());
            }

            if (source.getSurname() != null){
                destination.setSurname(source.getSurname());
            }

            if (source.getDni() != null) {
                destination.setDni(source.getDni());
            }

            if (source.getRegistrationDate() != null) {
                destination.setRegistrationDate(source.getRegistrationDate());
            }

            if (source.getAddress() != null) {
                destination.setAddressDTO(modelMapper.map(source.getAddress(), AddressDTO.class));
            }

            return destination;
        };

        // Converter for Appointment skiping AppointmentList in Dentist
        Converter<Dentist, DentistDTO> dentistConverter = context -> {

            Dentist source = context.getSource();
            DentistDTO destination = new DentistDTO();

            destination.setId(source.getId());

            if (source.getName() != null){
                destination.setName(source.getName());
            }

            if (source.getSurname() != null){
                destination.setSurname(source.getSurname());
            }

            if (source.getEnrollment() != null){
                destination.setEnrollment(source.getEnrollment());
            }

            return destination;
        };

        Converter<PatientDTO, Patient> patientDTOConverter = context -> {
            PatientDTO source = context.getSource();
            Patient destination = context.getDestination() != null
                    && source.getId() == context.getDestination().getId()
                    ? context.getDestination()
                    : new Patient();

            destination.setId(source.getId());

            if (source.getName() != null) {
                destination.setName(source.getName());
            }

            if (source.getSurname() != null){
                destination.setSurname(source.getSurname());
            }

            if (source.getDni() != null) {
                destination.setDni(source.getDni());
            }

            if (source.getRegistrationDate() != null) {
                destination.setRegistrationDate(source.getRegistrationDate());
            }

            if (source.getAddressDTO() != null) {
                destination.setAddress(modelMapper.map(source.getAddressDTO(), Address.class));
            }

            return destination;
        };

        Converter<DentistDTO, Dentist> dentistDTOConverter = context -> {
            DentistDTO source = context.getSource();
            Dentist destination = context.getDestination() != null
                    && source.getId() == context.getDestination().getId()
                    ? context.getDestination()
                    : new Dentist();

            destination.setId(source.getId());

            if (source.getName() != null){
                destination.setName(source.getName());
            }

            if (source.getSurname() != null){
                destination.setSurname(source.getSurname());
            }

            if (source.getEnrollment() != null){
                destination.setEnrollment(source.getEnrollment());
            }

            return destination;
        };

        // saving mappings
        appointmentMap.addMappings(mapper -> mapper.using(patientConverter).map(
                Appointment::getPatient, AppointmentDTO::setPatientDTO));
        appointmentMap.addMappings(mapper -> mapper.using(dentistConverter).map(
                Appointment::getDentist, AppointmentDTO::setDentistDTO));

        appointmentDTOMap.addMappings(mapper -> mapper.using(patientDTOConverter).map(
                AppointmentDTO::getPatientDTO, Appointment::setPatient));
        appointmentDTOMap.addMappings(mapper -> mapper.using(dentistDTOConverter).map(
                AppointmentDTO::getDentistDTO, Appointment::setDentist));


        /* ---------------------------------------------------------------------- */


        // TypeMap for Patient and PatientMap
        TypeMap<Patient, PatientDTO> patientMap = modelMapper.createTypeMap(Patient.class, PatientDTO.class);
        TypeMap<PatientDTO, Patient> patientDTOMap = modelMapper.createTypeMap(PatientDTO.class , Patient.class);

        // Converter for AppointmentList skiping Patient info
        Converter<Set<Appointment>, Set<AppointmentDTO>> appointmentPatientConverter = context -> {
            Set<Appointment> sourcePatientSet = context.getSource();
            Set<AppointmentDTO> destinationPatientSet = new HashSet<>();

            for (Appointment appointment : sourcePatientSet) {

                AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
                appointmentDTO.setPatientDTO(null);

                destinationPatientSet.add(appointmentDTO);
            }

            return destinationPatientSet;
        };

        // Converter for AddressDTO
        Converter<AddressDTO, Address> addressDTOConverter = context -> {
            AddressDTO sourceAddress = context.getSource();
            Address destinationAddress = context.getDestination() != null ? context.getDestination() : new Address();

            if (sourceAddress.getId() != null) {
                destinationAddress.setId(sourceAddress.getId());
            }

            if (sourceAddress.getStreet() != null) {
                destinationAddress.setStreet(sourceAddress.getStreet());
            }

            if (sourceAddress.getNumber() != null) {
                destinationAddress.setNumber(sourceAddress.getNumber());
            }

            if (sourceAddress.getState() != null) {
                destinationAddress.setState(sourceAddress.getState());
            }

            return destinationAddress;
        };

        patientMap.addMappings(mapper -> mapper
                .using(appointmentPatientConverter)
                .map(Patient::getAppointmentList, PatientDTO::setAppointmentDTOList));

        patientMap.addMapping(Patient::getAddress, PatientDTO::setAddressDTO);


        patientDTOMap.addMappings( mapper -> mapper
                .using(addressDTOConverter)
                .map(PatientDTO::getAddressDTO, Patient::setAddress));


        /* ---------------------------------------------------------------------- */


        // TypeMap for Dentist
        TypeMap<Dentist, DentistDTO> dentistMap = modelMapper.createTypeMap(Dentist.class, DentistDTO.class);

        // Converter for AppointmentList skiping Dentist info
        Converter<Set<Appointment>, Set<AppointmentDTO>> appointmentDentistConverter = context -> {
            Set<Appointment> sourceDentistSet = context.getSource();
            Set<AppointmentDTO> destinationDentistSet = new HashSet<>();

            for (Appointment appointment : sourceDentistSet) {

                AppointmentDTO appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
                appointmentDTO.setDentistDTO(null);

                destinationDentistSet.add(appointmentDTO);
            }

            return destinationDentistSet;
        };

        // saving map
        dentistMap.addMappings(mapper -> mapper
                .using(appointmentDentistConverter)
                .map(Dentist::getAppointmentList, DentistDTO::setAppointmentDTOList));


        /* ---------------------------------------------------------------------- */


        return modelMapper;
    }

}
