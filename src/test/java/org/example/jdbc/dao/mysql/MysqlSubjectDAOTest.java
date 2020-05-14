package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.StudentDAO;
import org.example.jdbc.dao.SubjectDAO;
import org.example.jdbc.model.Student;
import org.example.jdbc.model.Subject;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlSubjectDAOTest {

    @Test
    public void insert() throws SQLException, DAOException {
        System.out.println("\n||||||  SubjectDAO.insert() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        SubjectDAO SubjectDAO = new MysqlSubjectDAO(connection);

        Subject s = new Subject("Programacion Concurrente",3L);
        SubjectDAO.insert(s);
        System.out.println("Assigned id to inserted Subject: "+ s.getId());
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
        System.out.println("\n||||||  SubjectDAO.getAll() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Subject> list = null;

        MysqlSubjectDAO mysqlSubjectDAO = new MysqlSubjectDAO(connection);
        try {
            list = mysqlSubjectDAO.getAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Subjects:");
        for (Subject s: list)
            System.out.println(s.toString());
        connection.close();

        System.out.println("||||||||||||||||||||||||||||||||\n");
    }

    @Test
    public void get() throws SQLException, DAOException {
        System.out.println("\n||||||  SubjectDAO.get() |||||| " );

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );
        MysqlSubjectDAO mysqlSubjectDAO = new MysqlSubjectDAO(connection);

        Long id = 6L;

        System.out.println("Subject of index "+id+":");
        Subject s = mysqlSubjectDAO.get(id);
        System.out.println(s.toString());

        System.out.println("||||||||||||||||||||||||||\n" );

    }
}