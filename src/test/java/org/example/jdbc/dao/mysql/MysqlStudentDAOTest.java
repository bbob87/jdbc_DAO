package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.StudentDAO;
import org.example.jdbc.model.Student;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlStudentDAOTest {

    @Test
    public void insert() throws SQLException, DAOException {
        System.out.println("\n||||||  StudentDAO.insert() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        StudentDAO studentDAO = new MysqlStudentDAO(connection);

        Student s = new Student("Rolando","Quintana",new Date(1990,5,2));
        studentDAO.insert(s);
        System.out.println("Assigned id to inserted student: "+ s.getId());
        connection.close();

        System.out.println("||||||||||||||||||||||||||||||||\n");
    }

    @Test
    public void edit() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() throws SQLException {
        System.out.println("\n||||||  StudentDAO.getAll() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Student> list = null;

        MysqlStudentDAO mysqlStudentDAO = new MysqlStudentDAO(connection);
        try {
           list = mysqlStudentDAO.getAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Students:");
        for (Student s: list)
            System.out.println(s.toString());
        connection.close();

        System.out.println("||||||||||||||||||||||||||||||||\n");
    }

    @Test
    public void get() throws SQLException, DAOException {
        System.out.println("\n||||||  StudentDAO.get() |||||| " );

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );
        MysqlStudentDAO mysqlStudentDAO = new MysqlStudentDAO(connection);

        Long id = 1L;

        System.out.println("Student of index "+id+":");
        Student s = mysqlStudentDAO.get(id);
        System.out.println(s.toString());

        System.out.println("||||||||||||||||||||||||||\n" );

    }
}