insert into patient (id, name, surname, patronymic, phone)values (1, 'Yuriy', 'Ivanov', 'Yurievich', '89657412584');
insert into patient (name, surname, patronymic, phone)values ('Ivan', 'Yuriev', 'Aleksandrovich', '73698254185');
insert into patient (name, surname, patronymic, phone)values ('Alex', 'Polovinkin', 'Petrovich', '95684758125');
insert into patient (name, surname, patronymic, phone)values ('Yuliya', 'Anisimova', 'Vitaliyevna', '69532865479');
insert into patient (name, surname, patronymic, phone)values ('Vasiliy', 'Petrov', 'Petrovich', '25136548745');
insert into patient (name, surname, patronymic, phone)values ('Alexey', 'Shestakov', 'Ivanovich', '23235614859');
insert into patient (name, surname, patronymic, phone)values ('Vitaliy', 'Vitalyev', 'Stanislavovich', '87598475142');
insert into patient (name, surname, patronymic, phone)values ('Sergey', 'Glebushkin', 'Igorevich', '75863012054');
insert into patient (name, surname, patronymic, phone)values ('Bogdan', 'Bogdanov', 'Aleksandrovich', '95687230140');
insert into patient (name, surname, patronymic, phone)values ('Vyacheslav', 'Vyacheslavov', 'Ostapovich', '89547632540');

insert into doctor (id, name, surname, patronymic, specialization)values (1, 'Lidia', 'Shabunina', 'Tikhonovna', 'CARDIOLOGIST');
insert into doctor (name, surname, patronymic, specialization)values ('Marina', 'Shvernika', 'Rodionovna', 'THERAPIST');
insert into doctor (name, surname, patronymic, specialization)values ('Roman', 'Yumatov', 'Ippolitovich', 'TRAUMATOLOGIST');
insert into doctor (name, surname, patronymic, specialization)values ('August', 'Yablochkin', 'Evgrafovich', 'SURGEON');
insert into doctor (name, surname, patronymic, specialization)values ('Milena', 'Kabitsina', 'Trofimovna', 'IMMUNOLOGIST');
insert into doctor (name, surname, patronymic, specialization)values ('Yaroslava', 'Ostroverhova', 'Ilyevna', 'ALLERGIST');
insert into doctor (name, surname, patronymic, specialization)values ('Nazar', 'Karachev', 'Fomevich', 'OPHTHALMOLOGIST');
insert into doctor (name, surname, patronymic, specialization)values ('Rodion', 'Suvorov', 'Iosifovich', 'PSYCHOTHERAPIST');
insert into doctor (name, surname, patronymic, specialization)values ('Yakub', 'Erokhin', 'Davydovich', 'SURGEON');
insert into doctor (name, surname, patronymic, specialization)values ('Valentin', 'Yanchevsky', 'Daniilovich', 'HEPATOLOGIST');

insert into recipe (id, description, patient_id, doctor_id, creation_date, validity, priority)values (1, 'Rp.: Amantadini 100 mg D.t.d. N 30 in tab. S.: Inside, after meals with a small amount ' ||
           'of liquid, preferably in the first half of the day, 1 tablet', 2, 4, '2020-11-06', 3,'CITO');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Acidi aminophenylbutyrici 250 mg D.t.d. N 20 in tab. S.: Inside, 1 tablet 2 times a day', 1, 4, '2020-09-11', 8,'CITO');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Pulv. “Gemasa” 5000 ME – 1 ml D.t.d. N 10 S.: Dilute in 0.5 ml of 0.9% sodium chloride solution ' ||
           'intravitreally according to the doctors scheme', 8, 5, '2020-11-08', 2,'CITO');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Olanzapini 5 mg D.t.d. N 30 in tab. S.: Inside 1 tablet 2 times a day', 3, 6, '2020-11-10', 7,'STATIM');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Verapamili 0,04 D.t.d. N 30 in tab. S.: Inside with plenty of liquid 2 times a day', 1, 2, '2020-10-10', 10,'NORMAL');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Amantadini 100 mg D.t.d. N 30 in tab. S.: Inside, after meals with a small amount ' ||
           'of liquid, preferably in the first half of the day, 1 tablet', 9, 4, '2020-11-25', 8,'CITO');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Acidi aminophenylbutyrici 250 mg D.t.d. N 20 in tab. S.: Inside, 1 tablet 2 times a day', 7, 4, '2020-11-12', 9,'NORMAL');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Pulv. “Gemasa” 5000 ME – 1 ml D.t.d. N 10 S.: Dilute in 0.5 ml of 0.9% sodium chloride solution ' ||
           'intravitreally according to the doctors scheme', 7, 5, '2020-11-05', 2,'STATIM');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Verapamili 0,04 D.t.d. N 30 in tab. S.: Inside with plenty of liquid 3 times a day', 1, 1, '2020-11-27', 3,'NORMAL');
insert into recipe (description, patient_id, doctor_id, creation_date, validity, priority)values ('Rp.: Pulv. “Gemasa” 5000 ME – 1 ml D.t.d. N 10 S.: Dilute in 0.5 ml of 0.9% sodium chloride solution ' ||
           'intravitreally according to the doctors scheme', 7, 5, '2020-11-04', 4,'CITO');
