package io.doctor.clinic.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicineController {
    @Autowired
    MedicineUseCase useCase;

    @GetMapping("/medicines")
    public List<Medicine> getMedicines(){
        return useCase.getList();
    }

    @GetMapping("/medicine/{id}")
    public Medicine getMedicines(@PathVariable int id){
        return useCase.getFromId(id);
    }

    @PostMapping("/medicine")
    public void saveMedicine(@RequestBody Medicine medicine){
        useCase.save(medicine);
    }

    @PostMapping("/meds/increase")
    public void increase(@RequestBody ChangeStock data){
        useCase.increase(data.getId(), data.getQuantity());
    }

    @PostMapping("/meds/decrease")
    public void decrease(@RequestBody ChangeStock data){
        useCase.decrease(data.getId(), data.getQuantity());
    }

    @GetMapping("/search/medicine/{keyword}")
    public List<Medicine> search(@PathVariable String keyword){
        return useCase.search(keyword);
    }
}
