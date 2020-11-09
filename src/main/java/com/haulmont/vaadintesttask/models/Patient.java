package com.haulmont.vaadintesttask.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "Patient")
public class Patient {
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

    @Column(length = 11, name = "phone")
    @Pattern(regexp = "[1-9]\\d{10}")
    @NonNull
    private String phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Recipe> recipes;

    public String toString(){
        return id + ": " + surname + " " + name + " " + patronymic;
    }
}
