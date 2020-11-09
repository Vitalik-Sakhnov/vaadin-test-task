package com.haulmont.vaadintesttask.services.impl;

import com.haulmont.vaadintesttask.models.Doctor;
import com.haulmont.vaadintesttask.repositories.DoctorRepository;
import com.haulmont.vaadintesttask.services.DoctorService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(doctor -> Hibernate.initialize(doctor.getRecipes()));
        return doctors;
    }

    @Transactional
    @Override
    public void update(Long id, Doctor createdDoctor) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        doctor.setName(createdDoctor.getName());
        doctor.setSurname(createdDoctor.getSurname());
        doctor.setPatronymic(createdDoctor.getPatronymic());
        doctor.setSpecialization(createdDoctor.getSpecialization());
    }

    @Override
    public void save(Doctor doctor) {
        if (doctor != null) {
            doctorRepository.save(doctor);
        }
    }

    @Transactional
    @Override
    public void delete(Doctor doctor) {
        doctorRepository.delete(doctor);
    }
}
