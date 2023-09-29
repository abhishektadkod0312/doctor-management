package io.doctor.clinic.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String patientNumber;
    String name;
    Long phone;
    String address;
    ZonedDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = ZonedDateTime.now();
    }
}
