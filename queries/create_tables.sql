 /*create table student (
	id int not null auto_increment,
    first_name varchar(32) not null,
    last_name varchar(64) not null,
    birth_date date not null,
    unique key id_uk (id)
 );*/
 /*create table teacher (
	id int not null auto_increment,
    first_name varchar(32) not null,
    last_name varchar(64) not null,
    unique key id_uk(id)
 );*/
 /*create table subject (
	id int not null auto_increment,
    subject_name varchar(64) not null,
    teacher_id int not null,
    unique key id_uk (id),
    foreign key teacher_id_fk(teacher_id) references teacher(id)
 );*/
 /*create table enrollment (
	student_id int not null,
    subject_id int not null,
    enrollment_year year not null,
    mark int,
    primary key (student_id, subject_id, enrollment_year),
    foreign key student_id_fk(student_id) references student(id),
    foreign key subject_id_fk(subject_id) references subject(id)
 );*/
 
 