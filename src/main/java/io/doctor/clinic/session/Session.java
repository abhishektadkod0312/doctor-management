package io.doctor.clinic.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.doctor.clinic.BsonType;
import io.doctor.clinic.medicine.Medicine;
import io.doctor.clinic.patient.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static io.doctor.clinic.BsonType.BSON;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = BSON, typeClass = BsonType.class)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    Patient patient;

    @Type(type = BSON)
    List<MedicineAssigned> medicineAssigned;

    @JsonIgnore
    @ManyToMany
    List<Medicine> medicineSet;

    long money;
    int quantity;
    ZonedDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.createdAt = ZonedDateTime.now();
    }
}
