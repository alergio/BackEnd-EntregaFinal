package com.dh.dental.clinic.controller;

import com.dh.dental.clinic.service.ICRUDService;
import com.dh.dental.clinic.dto.PatientDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/patient")
public class PatientController {

    private final ICRUDService<PatientDTO> patientService;

    public PatientController(ICRUDService<PatientDTO> patientService){
        this.patientService = patientService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPatient(@RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.save(patientDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id){
        return ResponseEntity.ok(patientService.searchById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients(){
        return ResponseEntity.ok(patientService.listAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody PatientDTO patientDTO){
        return ResponseEntity.ok(patientService.update(patientDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id){
        return ResponseEntity.ok(patientService.delete(id));
    }
}
