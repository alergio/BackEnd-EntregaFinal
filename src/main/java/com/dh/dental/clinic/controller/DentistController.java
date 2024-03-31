package com.dh.dental.clinic.controller;

import com.dh.dental.clinic.dto.DentistDTO;
import com.dh.dental.clinic.service.ICRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/dentist")
public class DentistController {

    private ICRUDService<DentistDTO> dentistService;

    public DentistController(ICRUDService<DentistDTO> dentistService){
        this.dentistService = dentistService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDentist(@RequestBody DentistDTO dentistDTO){
        return ResponseEntity.ok(dentistService.save(dentistDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDentist(@PathVariable Long id){
        return ResponseEntity.ok(dentistService.searchById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDentist(){
        return ResponseEntity.ok(dentistService.listAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDentist(@RequestBody DentistDTO dentistDTO){
        return ResponseEntity.ok(dentistService.update(dentistDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDentist(@PathVariable Long id){
        return ResponseEntity.ok(dentistService.delete(id));
    }

}
