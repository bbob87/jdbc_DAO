package org.example.jdbc.model;

 /*create table teacher (
	id int not null auto_increment,
    first_name varchar(32) not null,
    last_name varchar(64) not null,
    unique key id_uk(id)
 );*/

import java.util.Date;
import java.util.Objects;

public class Teacher {
    private Long id = null;
    private String first_name;
    private String last_name;

    public Teacher(/*Long id,*/ String first_name, String last_name) {
        //this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id.equals(teacher.id) &&
                first_name.equals(teacher.first_name) &&
                last_name.equals(teacher.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name);
    }
}
