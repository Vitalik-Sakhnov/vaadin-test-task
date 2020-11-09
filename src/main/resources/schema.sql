drop table recipe if exists;
drop table patient if exists;
drop table doctor if exists;

create table patient
(
    ID               BIGINT IDENTITY
                constraint patient_pk
                          primary key,
    NAME       VARCHAR(100) not null,
    SURNAME    VARCHAR(100) not null,
    PATRONYMIC VARCHAR(100) not null,
    PHONE      VARCHAR(11) not null
);

create table doctor
(
    ID                    BIGINT IDENTITY
                     constraint doctor_pk
                              primary key,
    NAME           VARCHAR(100) not null,
    SURNAME        VARCHAR(100) not null,
    PATRONYMIC     VARCHAR(100) not null,
    SPECIALIZATION       VARCHAR(100) not null
);

create table recipe
(
    ID                  BIGINT IDENTITY
        constraint table_name_pk
            primary key,
    DESCRIPTION   VARCHAR(300) not null,
    PATIENT_ID          BIGINT not null
        constraint recipe_patient_id_fk
            references patient,
    DOCTOR_ID           BIGINT not null
        constraint recipe_doctor_id_fk
            references doctor,
    CREATION_DATE                  DATE,
    VALIDITY            BIGINT not null,
    PRIORITY      VARCHAR(30) not null
);




