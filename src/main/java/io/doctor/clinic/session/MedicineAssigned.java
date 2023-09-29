package io.doctor.clinic.session;

import io.doctor.clinic.medicine.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineAssigned {
    int id;
    String name;
    int quantity;
    long money;
}
