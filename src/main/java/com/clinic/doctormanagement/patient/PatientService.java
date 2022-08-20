package com.clinic.doctormanagement.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;
    public List<PatientModel> getPatients() {
        return patientRepository.findAll();
    }

    public void savePatient(PatientModel patientModel) {
        patientRepository.save(patientModel);
    }
}
