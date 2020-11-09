package com.haulmont.vaadintesttask.repositories;

import com.haulmont.vaadintesttask.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
