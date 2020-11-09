package com.haulmont.vaadintesttask.services;

import com.haulmont.vaadintesttask.models.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> findAll();

    void update(Long id, Doctor doctor);

    void save(Doctor doctor);

    void delete(Doctor doctor);
}
