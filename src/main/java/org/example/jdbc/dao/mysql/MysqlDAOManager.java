package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.*;
import org.example.jdbc.model.Enrollment;
import org.example.jdbc.model.Teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDAOManager implements DAOManager {
    private Connection connection;

    private static StudentDAO studentDAO = null;
    private static EnrollmentDAO enrollmentDAO = null;
    private static SubjectDAO subjectDAO = null;
    private static TeacherDAO teacherDAO = null;

    //The exception that might be thron here is not linked to any specific DAO, so we put it
    // as a general SQLException
    public MysqlDAOManager (String host, int port, String username, String password, String database) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database+"?serverTimezone=UTC",username,password);
    }


    @Override
    public EnrollmentDAO getEnrollmentDAO() {
        if (enrollmentDAO == null)
            enrollmentDAO = new MysqlEnrollmentDAO(connection);
        return enrollmentDAO;
    }

    @Override
    public StudentDAO getStudentDAO() {
        if (studentDAO == null)
            studentDAO = new MysqlStudentDAO(connection);
        return studentDAO;
    }

    @Override
    public SubjectDAO getSubjectDAO() {
        if (subjectDAO == null)
            subjectDAO = new MysqlSubjectDAO(connection);
        return subjectDAO;
    }

    @Override
    public TeacherDAO getTeacherDAO() {
        if (teacherDAO == null)
            teacherDAO = new MysqlTeacherDAO(connection);
        return teacherDAO;
    }
}
