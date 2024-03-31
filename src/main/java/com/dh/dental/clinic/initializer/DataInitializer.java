package com.dh.dental.clinic.initializer;

import com.dh.dental.clinic.entity.Address;
import com.dh.dental.clinic.entity.Appointment;
import com.dh.dental.clinic.entity.Dentist;
import com.dh.dental.clinic.entity.Patient;
import com.dh.dental.clinic.repository.impl.IAppointmentRepository;
import com.dh.dental.clinic.repository.impl.IDentistRepository;
import com.dh.dental.clinic.repository.impl.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private IAppointmentRepository appointmentRepository;
    @Autowired
    private IDentistRepository dentistRepository;
    @Autowired
    private IPatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        // Pacientes

        Address address1 = new Address();
        Address address2 = new Address();
        Address address3 = new Address();

        Patient patient1 = new Patient();
        Patient patient2 = new Patient();
        Patient patient3 = new Patient();

        address1.setStreet("Azara");
        address1.setNumber("1797");
        address1.setState("Canelones");

        address2.setStreet("Bvar Artigas");
        address2.setNumber("1378");
        address2.setState("Montevideo");

        address3.setStreet("Av Italia");
        address3.setNumber("2124");
        address3.setState("Montevideo");

        patient1.setName("Esteban");
        patient1.setSurname("Gimenez");
        patient1.setDni("4445601");
        patient1.setRegistrationDate(LocalDate.now());
        patient1.setAddress(address1);

        patient2.setName("Mariana");
        patient2.setSurname("Estevanez");
        patient2.setDni("1244567");
        patient2.setRegistrationDate(LocalDate.now());
        patient2.setAddress(address2);

        patient3.setName("Mario");
        patient3.setSurname("Martinez");
        patient3.setDni("1235");
        patient3.setRegistrationDate(LocalDate.now());
        patient3.setAddress(address3);

        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);


        // Dentistas

        Dentist dentist1 = new Dentist();
        Dentist dentist2 = new Dentist();
        Dentist dentist3 = new Dentist();

        dentist1.setName("Maria");
        dentist1.setSurname("Loreiro");
        dentist1.setEnrollment("MFG356");

        dentist2.setName("Martin");
        dentist2.setSurname("Poe");
        dentist2.setEnrollment("AF43G7");

        dentist3.setName("Romina");
        dentist3.setSurname("Mariandes");
        dentist3.setEnrollment("AVG467");

        dentistRepository.save(dentist1);
        dentistRepository.save(dentist2);
        dentistRepository.save(dentist3);


        // Turnos

        Appointment appointment1 = new Appointment();
        Appointment appointment2 = new Appointment();
        Appointment appointment3 = new Appointment();

        appointment1.setDentist(dentist1);
        appointment1.setPatient(patient1);
        appointment1.setDateAppointment(LocalDateTime.now());

        appointment2.setDentist(dentist2);
        appointment2.setPatient(patient2);
        appointment2.setDateAppointment(LocalDateTime.now());

        appointment3.setDentist(dentist3);
        appointment3.setPatient(patient1);
        appointment3.setDateAppointment(LocalDateTime.now());

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

    }
}
