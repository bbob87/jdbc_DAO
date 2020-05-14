package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.StudentDAO;
import org.example.jdbc.dao.TeacherDAO;
import org.example.jdbc.model.Student;
import org.example.jdbc.model.Teacher;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlTeacherDAOTest {

    @Test
    public void insert() throws SQLException, DAOException {
        System.out.println("\n||||||  TeacherDAO.insert() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        TeacherDAO TeacherDAO = new MysqlTeacherDAO(connection);

        Teacher s = new Teacher("Deirel","Paz");
        TeacherDAO.insert(s);
        System.out.println("Assigned id to inserted Teacher: "+ s.getId());
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
        System.out.println("\n||||||  TeacherDAO.getAll() |||||| " );
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Teacher> list = null;

        MysqlTeacherDAO mysqlTeacherDAO = new MysqlTeacherDAO(connection);
        try {
            list = mysqlTeacherDAO.getAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Teachers:");
        for (Teacher s: list)
            System.out.println(s.toString());
        connection.close();

        System.out.println("||||||||||||||||||||||||||||||||\n");
    }

    @Test
    public void get() throws SQLException, DAOException {
        System.out.println("\n||||||  TeacherDAO.get() |||||| " );

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );
        MysqlTeacherDAO mysqlTeacherDAO = new MysqlTeacherDAO(connection);

        Long id = 1L;

        System.out.println("Teacher of index "+id+":");
        Teacher s = mysqlTeacherDAO.get(id);
        System.out.println(s.toString());

        System.out.println("||||||||||||||||||||||||||\n" );

    }
}