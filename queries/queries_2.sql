/*a transaction */
begin;
insert into teacher (id, first_name, last_name) values (50,'Pepito','Perez')
insert into subject (id, subject_name, teacher_id) values (110,'Fundamentos de Bases de Datos',50)
commit;