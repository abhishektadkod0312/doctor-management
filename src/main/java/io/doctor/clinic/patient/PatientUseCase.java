package io.doctor.clinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientUseCase {
    @Autowired
    PatientRepository repository;

    public List<Patient> getList(){
        return repository.findAll();
    }

    public void save(Patient patient){
        repository.save(patient);
    }


    public List<Patient> search(String keyword){
        return repository.findByNameLike((new StringBuilder().append("%").append(keyword).append("%")).toString());
    }


}
