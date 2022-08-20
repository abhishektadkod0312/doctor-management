package com.clinic.doctormanagement.patient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String getPatients(){
        return "hi";
    }
}
