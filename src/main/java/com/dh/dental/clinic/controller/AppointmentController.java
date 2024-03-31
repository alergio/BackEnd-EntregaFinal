package com.dh.dental.clinic.controller;

import com.dh.dental.clinic.dto.AppointmentDTO;
import com.dh.dental.clinic.service.ICRUDService;
import com.dh.dental.clinic.service.impl.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final ICRUDService<AppointmentDTO> appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.save(appointmentDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.searchById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.listAll());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAppointment(@RequestBody AppointmentDTO appointmentDTO){
        return ResponseEntity.ok(appointmentService.update(appointmentDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.delete(id));
    }
}
