package io.doctor.clinic.medicine;

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
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String company;
    int stock;
    int stockLimit;
    long money;
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
