package com.clinic.doctormanagement.patient;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "patient")
public class PatientModel {
    @Id @GeneratedValue
    int id;
    String name;
    String disease;
    String phone;
}
