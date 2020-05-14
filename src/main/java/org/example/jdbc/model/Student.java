package org.example.jdbc.model;

 /*create table student (
	id int not null auto_increment,
    first_name varchar(32) not null,
    last_name varchar(64) not null,
    birth_date date not null,
    unique key id_uk (id)
 );*/

import java.util.Date;
import java.util.Objects;

public class Student {
    private Long id = null;
    private String first_name;
    private String last_name;
    private Date birth_date;

    //The field 'id' will be set when a Student object is persisted into the DB; will be set by the DB engine
    // See method MysqlStudentDAO.insert(). A null value will means that the object has not been persisted.
    public Student(/*Long id, */String first_name, String last_name, Date birth_date) {
        //this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) &&
                first_name.equals(student.first_name) &&
                last_name.equals(student.last_name) &&
                birth_date.equals(student.birth_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, birth_date);
    }
}
