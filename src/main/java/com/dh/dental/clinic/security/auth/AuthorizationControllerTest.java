package com.dh.dental.clinic.security.auth;

import com.dh.dental.clinic.dto.PatientDTO;
import com.dh.dental.clinic.service.ICRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping()
public class AuthorizationControllerTest {

    /*
    * ok esto funciona bien, lo del /user y /admin en el SecurityConfiguration funciona barbaro
    * hay que modificar los controladores para que respondan en base a este criterio
    * */

    private final ICRUDService<PatientDTO> patientService;

    public AuthorizationControllerTest(ICRUDService<PatientDTO> patientService){
        this.patientService = patientService;
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllPatientsOne(){
        return ResponseEntity.ok(patientService.listAll());
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllPatientsTwo(){
        return ResponseEntity.ok(patientService.listAll());
    }

}
