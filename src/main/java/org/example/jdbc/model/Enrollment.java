package org.example.jdbc.model;

 /*create table enrolment (
	student_id int not null,
    subject_id int not null,
    enrolment_year year not null,
    mark int,
    primary key (student_id, subject_id, enrolment_year),
    foreign key student_id_fk(student_id) references student(id),
    foreign key subject_id_fk(subject_id) references subject(id)
 );*/

import java.util.Objects;

public class Enrollment {

    /**  These three fields constitute the primary key for a row in the Enrollment table.
    Therefore the best approach is to encapsulate them. We create an inner class 'EnrollmentId' with them.*/
    /*private long student_id;
    private long subject_id;
    private int enrollment_year;
    */

    /******************  inner class beginning  ************************/
    public class EnrollmentId {
        private long student_id;
        private long subject_id;
        private int enrollment_year;

        public EnrollmentId() {
            this.student_id = -1;
            this.subject_id = -1;
            this.enrollment_year = -1;
        }

        public EnrollmentId(long student_id, long subject_id, int enrollment_year) {
            this.student_id = student_id;
            this.subject_id = subject_id;
            this.enrollment_year = enrollment_year;
        }

        public long getStudent_id() {
            return student_id;
        }

        public void setStudent_id(long student_id) {
            this.student_id = student_id;
        }

        public long getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(long subject_id) {
            this.subject_id = subject_id;
        }

        public int getEnrollment_year() {
            return enrollment_year;
        }

        public void setEnrollment_year(int enrollment_year) {
            this.enrollment_year = enrollment_year;
        }

        @Override
        public String toString() {
            return "EnrollmentId{" +
                    "student_id=" + student_id +
                    ", subject_id=" + subject_id +
                    ", enrollment_year=" + enrollment_year +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EnrollmentId that = (EnrollmentId) o;
            return student_id == that.student_id &&
                    subject_id == that.subject_id &&
                    enrollment_year == that.enrollment_year;
        }

        @Override
        public int hashCode() {
            return Objects.hash(student_id, subject_id, enrollment_year);
        }
    }
    /***************** inner class end  ********************/

    public EnrollmentId getEnrollmentId(long student_id, long subject_id, int enrollment_year){
        return new EnrollmentId(student_id, subject_id, enrollment_year);

    }


    /** class's fields*/
    // The id in this case is not as simple as an autoincrement. Instead is composed by the set of the first three
    // columns student_id, subject_id and enrollment_year.
    // Thus the record id will be set by the user before persisting the object
    // in the database, not by the database engine when persisting the object.
    private EnrollmentId Id = null;
    private Integer mark = null;   //null means there is no mark for the student enrolled in this course.
    // A null mark can aslo be inserted in the database, because we didn't marked the column as "not null"

    /** Constructor */
    // The parameterles constructor will fill the fields with no-sense values
    public Enrollment(){
        Id = new EnrollmentId();
        this.mark = -1;
    }

    public Enrollment(long student_id, long subject_id, int enrollment_year, Integer mark){
        Id = new EnrollmentId(student_id, subject_id, enrollment_year);
        this.mark = mark;
    }

    /** Methods */
    public EnrollmentId getId() {
        return Id;
    }

    public void setId(EnrollmentId id) {
        Id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "Id={" + Id.toString() +
                " }, mark=" + mark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Id.equals(that.Id) &&
                mark.equals(that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, mark);
    }
}
