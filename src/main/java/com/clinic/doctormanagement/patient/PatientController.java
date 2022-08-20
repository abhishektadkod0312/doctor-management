package com.clinic.doctormanagement.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {
    @Autowired
    PatientService patientService;
    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<PatientModel> getPatients(){
        return patientService.getPatients();
    }

    @RequestMapping(value = "/patient", method = RequestMethod.POST)
    public void savePatient(@RequestBody PatientModel patientModel){
        patientService.savePatient(patientModel);
    }
}
