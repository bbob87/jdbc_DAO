package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.StudentDAO;
import org.example.jdbc.model.Student;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlDAOManagerTest {

    @Test
    public void getEnrollmentDAO() {
    }

    @Test
    public void getStudentDAO() throws SQLException, DAOException {
        //    public MysqlDAOManager (String host, int port, String username, String password, String database) throws SQLException {
        MysqlDAOManager mysqlDAOManager = new MysqlDAOManager("localhost", 3306,"learner","learner","db");

        StudentDAO studentDAO = mysqlDAOManager.getStudentDAO();

        List<Student> list = studentDAO.getAll();

        for (Student s: list)
            System.out.println(s.toString());

        Student s2 = new Student("Leonel Raul", "Morejon",new java.util.Date(1987,31,1));
        studentDAO.insert(s2);

        System.out.println();

        list = studentDAO.getAll();
        for (Student s: list)
            System.out.println(s.toString());


    }


    @Test
    public void getSubjectDAO() {
    }

    @Test
    public void getTeacherDAO() {
    }
}