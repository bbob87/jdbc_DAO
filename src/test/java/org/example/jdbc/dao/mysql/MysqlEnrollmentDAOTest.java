package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.EnrollmentDAO;
import org.example.jdbc.model.Enrollment;
import org.example.jdbc.model.Student;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MysqlEnrollmentDAOTest {

    @Test
    public void insert() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() throws SQLException {
        System.out.println("\n|||||||||| Enrollment.getAll() |||||||||||");

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Enrollment> list = null;

        MysqlEnrollmentDAO mysqlEnrollmentDAO = new MysqlEnrollmentDAO(connection);
        try {
            list = mysqlEnrollmentDAO.getAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Enrollments:");
        for (Enrollment en: list)
            System.out.println(en.toString());

        connection.close();
        System.out.println("||||||||||||||||||||||||||||||||");

    }

    @Test
    public void get() throws SQLException, DAOException {
        System.out.println("\n|||||||||| Enrollment.get() |||||||||||");

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );
        MysqlEnrollmentDAO mysqlEnrollmentDAO = new MysqlEnrollmentDAO(connection);

        Enrollment en = new Enrollment();
        Enrollment.EnrollmentId Id = en.getEnrollmentId(5,8,2001);

        en = mysqlEnrollmentDAO.get(Id);

        System.out.println("Enrollment of index "+ Id.toString() + ":");
        System.out.println(en.toString());
        System.out.println("||||||||||||||||||||||||||||||||");
    }

    @Test
    public void getAllfilterBy() throws SQLException {
        System.out.println("\n|||||||||| Enrollment.getAllfilterBy(id, FilterCriteria) |||||||||||");

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Enrollment> list = null;

        long x_id = 2;

        MysqlEnrollmentDAO mysqlEnrollmentDAO = new MysqlEnrollmentDAO(connection);
        try {
            list = mysqlEnrollmentDAO.getAllfilterBy(x_id, EnrollmentDAO.FilteringCriteria.STUDENT_ID);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Enrollments with x_id "+x_id+" :");
        for (Enrollment en: list)
            System.out.println(en.toString());

        connection.close();
        System.out.println("||||||||||||||||||||||||||||||||");

    }

    @Test
    public void getAllfilterBy2() throws SQLException {
        System.out.println("\n|||||||||| Enrollment.getAllfilterBy(id, FilterCriteria 2) |||||||||||");

        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        Connection connection = DriverManager.getConnection(jdbcURL,"learner", "learner" );

        List<Enrollment> list = null;

        long x_id = 2001;

        MysqlEnrollmentDAO mysqlEnrollmentDAO = new MysqlEnrollmentDAO(connection);
        try {
            list = mysqlEnrollmentDAO.getAllfilterBy(x_id, EnrollmentDAO.FilteringCriteria.ENROLLMENT_YEAR);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        System.out.println("All Enrollments with x_id "+x_id+" :");
        for (Enrollment en: list)
            System.out.println(en.toString());

        connection.close();
        System.out.println("||||||||||||||||||||||||||||||||");

    }
}