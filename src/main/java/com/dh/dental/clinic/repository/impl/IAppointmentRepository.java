package com.dh.dental.clinic.repository.impl;

import com.dh.dental.clinic.entity.Appointment;
import com.dh.dental.clinic.entity.Patient;
import com.dh.dental.clinic.repository.IGenericRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IAppointmentRepository extends IGenericRepository<Appointment, Long> {
    /*
     * esto enrealidad quedo obsoleto, no estabamos pudiendo borrar Turno
     * por mas de que Patient y Dentist tenian el CascadeType.ALL y por eso hicimos esto
     * cambiamos el CascadeType a .REMOVE y esto dejo de ser necesario, pero por las dudas
     * lo dejamos aunque no lo estemos usando, si efectivamente no lo necesitamos, lo borraremos
     * */
    @Override
    @Deprecated
    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.id = ?1")
    void forcedDeleteById(Long id);
}
