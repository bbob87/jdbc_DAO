package org.example.jdbc.dao;

public interface DAOManager {

    EnrollmentDAO getEnrollmentDAO();

    StudentDAO getStudentDAO();

    SubjectDAO getSubjectDAO();

    TeacherDAO getTeacherDAO();



}
