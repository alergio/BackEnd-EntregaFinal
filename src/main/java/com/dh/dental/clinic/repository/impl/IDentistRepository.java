package com.dh.dental.clinic.repository.impl;

import com.dh.dental.clinic.entity.Dentist;
import com.dh.dental.clinic.repository.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDentistRepository extends IGenericRepository<Dentist, Long> {
    /*
     * esto enrealidad quedo obsoleto, no estabamos pudiendo borrar Turno
     * por mas de que Patient y Dentist tenian el CascadeType.ALL y por eso hicimos esto
     * cambiamos el CascadeType a .REMOVE y esto dejo de ser necesario, pero por las dudas
     * lo dejamos aunque no lo estemos usando, si efectivamente no lo necesitamos, lo borraremos
     * */
    @Override
    @Deprecated
    @Query("DELETE FROM Dentist a WHERE a.id = ?1")
    void forcedDeleteById(Long id);
}
