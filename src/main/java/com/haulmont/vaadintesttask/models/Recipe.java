package com.haulmont.vaadintesttask.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 300)
    @Column(length = 300, name = "description")
    @NonNull
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "patient_id")
    @NonNull
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "doctor_id")
    @NonNull
    private Doctor doctor;

    @NonNull
    private LocalDate creationDate;

    @NonNull
    private Long validity; //validity in months

    @Enumerated(EnumType.STRING)
    @Column(length = 30, name = "priority")
    @NonNull
    private Priority priority;

    @Transient
    public LocalDate getExpireDate() {
        return this.getCreationDate().plusMonths(this.getValidity());
    }
}
