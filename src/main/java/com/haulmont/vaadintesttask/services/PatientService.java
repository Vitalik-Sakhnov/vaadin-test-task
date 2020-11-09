package com.haulmont.vaadintesttask.services;

import com.haulmont.vaadintesttask.models.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    void update(Long id, Patient patient);

    void save(Patient patient);

    void delete(Patient patient);
}
