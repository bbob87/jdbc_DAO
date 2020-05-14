package org.example.jdbc.model;

/*create table subject (
	id int not null auto_increment,
    subject_name varchar(64) not null,
    teacher_id int not null,
    unique key id_uk (id),
    foreign key teacher_id_fk(teacher_id) references teacher(id)
 );*/

import java.util.Objects;

public class Subject {
    private Long id = null;
    private String subject_name;
    private Long teacher_id;   //This actually is a foreign key, so it would be better to put it as
    //an object 'Teacher'. But then the we would need to use the DAO from this teacher too, whenever
    // we use the DAO of Subject. In this way there will be a coupling between the different DAOs, which may
    // be no so good when there are too many of them. To keep the DAOs independent we put this here as
    // a Long. Later we will use it as a key (what it actually is) together with the Teacher DAO.

    public Subject(/*Long id,*/ String subject_name, Long teacher_id) {
        //this.id = id;
        this.subject_name = subject_name;
        this.teacher_id = teacher_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subject_name='" + subject_name + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id.equals(subject.id) &&
                subject_name.equals(subject.subject_name) &&
                teacher_id.equals(subject.teacher_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject_name, teacher_id);
    }
}
