package org.example.jdbc.dao;

import org.example.jdbc.model.Enrollment;

import java.util.List;

public interface EnrollmentDAO extends DAO<Enrollment, Enrollment.EnrollmentId> {

    enum FilteringCriteria{
        STUDENT_ID, SUBJECT_ID, ENROLLMENT_YEAR
    }

    /*Additional methods this interface adds*/
    // The second parameter will specify weather we want to filter the result by student_id, subject_id or enrollment_year
    List<Enrollment> getAllfilterBy(long x_id, FilteringCriteria filteringCriteria) throws DAOException;

}
