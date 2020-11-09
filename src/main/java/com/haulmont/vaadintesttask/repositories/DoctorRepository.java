package com.haulmont.vaadintesttask.repositories;

import com.haulmont.vaadintesttask.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
