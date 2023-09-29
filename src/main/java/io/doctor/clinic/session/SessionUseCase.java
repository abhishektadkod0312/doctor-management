package io.doctor.clinic.session;

import io.doctor.clinic.medicine.Medicine;
import io.doctor.clinic.medicine.MedicineRepository;
import io.doctor.clinic.medicine.MedicineUseCase;
import io.doctor.clinic.patient.Patient;
import io.doctor.clinic.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionUseCase {
    @Autowired
    SessionRepository repository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    MedicineUseCase medicineUseCase;

    public void createSession(int patient, SessionRequest sessionRequest){
        Optional<Patient> optionalPatient = patientRepository.findById(patient);
        List<Medicine> medicines = new ArrayList<>();
        for (MedicineAssigned medicinesRequest : sessionRequest.getMedicinesRequests()) {
            Optional<Medicine> optionalMedicine = medicineRepository.findById(medicinesRequest.getId());
            Medicine medicine = optionalMedicine.get();
            medicineUseCase.decrease(medicine.getId(), medicinesRequest.getQuantity());
            medicines.add(medicine);
        }

        Session session = Session.builder()
                .medicineAssigned(sessionRequest.getMedicinesRequests())
                .patient(optionalPatient.get())
                .medicineSet(medicines).build();
        repository.save(session);

    }

    public List<Session> sessionList(){
        return repository.findAll();
    }
}
