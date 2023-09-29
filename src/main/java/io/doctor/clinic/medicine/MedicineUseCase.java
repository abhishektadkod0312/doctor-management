package io.doctor.clinic.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineUseCase {
    @Autowired
    MedicineRepository repository;

    public List<Medicine> getList(){
        return repository.findAll();
    }

    public void save(Medicine medicine){
        repository.save(medicine);
    }

    public void decrease(int id, int quantity){
        Optional<Medicine> medicineOptional = repository.findById(id);
        if(medicineOptional.isPresent()) {
            Medicine medicine = medicineOptional.get();
            int current = medicine.getStock() - quantity;
            if(current>=0) {
                if (current < medicine.getStockLimit()) {
                    //code for notifying
                }
                medicine.setStock(current);
                repository.save(medicine);
            }
        }
    }

    public void increase(int id, int quantity){
        Optional<Medicine> medicineOptional = repository.findById(id);
        if(medicineOptional.isPresent()) {
            Medicine medicine = medicineOptional.get();
            int current = medicine.getStock() + quantity;
            medicine.setStock(current);
            repository.save(medicine);
        }
    }


    public List<Medicine> search(String keyword){
        return repository.findByNameLike((new StringBuilder().append("%").append(keyword).append("%")).toString());
    }

    public Medicine getFromId(int id) {
        return repository.findById(id).get();
    }
}
