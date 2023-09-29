package io.doctor.clinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    PatientUseCase useCase;

    @GetMapping("/patients")
    public List<Patient> getMedicines(){
        return useCase.getList();
    }

    @PostMapping("/patient")
    public void saveMedicine(@RequestBody Patient patient){
        useCase.save(patient);
    }

    @GetMapping("/search/patient/{keyword}")
    public List<Patient> search(@PathVariable String keyword){
        return useCase.search(keyword);
    }
}
