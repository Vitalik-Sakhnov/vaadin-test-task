package com.haulmont.vaadintesttask.services.impl;

import com.haulmont.vaadintesttask.models.Patient;
import com.haulmont.vaadintesttask.repositories.PatientRepository;
import com.haulmont.vaadintesttask.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Transactional
    @Override
    public void update(Long id, Patient createdPatient) {
        Patient patient = patientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        patient.setName(createdPatient.getName());
        patient.setSurname(createdPatient.getSurname());
        patient.setPatronymic(createdPatient.getPatronymic());
        patient.setPhone(createdPatient.getPhone());
    }

    @Override
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    @Transactional
    @Override
    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }
}
