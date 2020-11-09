package com.haulmont.vaadintesttask.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 100)
    @Column(length = 100, name = "name")
    @NonNull
    private String name;

    @Size(min = 1, max = 100)
    @Column(length = 100, name = "surname")
    @NonNull
    private String surname;

    @Size(min = 1, max = 100)
    @Column(length = 100, name = "patronymic")
    @NonNull
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "specialization")
    @NonNull
    private Specialization specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Recipe> recipes;

    public String toString() {
        return id + ": " + surname + " " + name + " " + patronymic + " " + specialization;
    }
}
