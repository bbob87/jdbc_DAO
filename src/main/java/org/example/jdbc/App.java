package org.example.jdbc;

//import com.mysql.jdbc.*;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {
    Connection connection = null;

    //constructor
    public App(String username, String password) throws SQLException {
        try{
            connect(username, password);
            query("Lopez");
            //transaction();
        }finally {
            close();
        }
    }

    public void connect(String username, String password) throws SQLException {
        //the database name I'm wanna connect to is 'db'
        String jdbcURL = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
        connection = DriverManager.getConnection(jdbcURL,username, password );

        /******** We want to start a TRANSACTION here, and all following SQL statements will be part of
         ******** it until we call commit() ********************************************************/
        connection.setAutoCommit(false );
        /***********************************************************************************/
    }

    public void close() throws SQLException{
        if (connection != null)
            connection.close();
    }

    public void query(String lastName) throws SQLException {

        //Dont use this approach: SQL injection attack, can do whatever with our database !!
        //String queryString = "select t.id, t.first_name, t.last_name from student t where last_name = '"+lastName+"'";

        //This is the secure approach:
        String queryString = "select t.id, t.first_name, t.last_name from student t where last_name = ? and id < ?";
        PreparedStatement pstmt = connection.prepareStatement(queryString);
        //Statement stmt = connection.createStatement();

        pstmt.setString(1, lastName);  //one-start format !
        pstmt.setInt(2, 5);  //one-start format !

        ResultSet rs = pstmt.executeQuery(); //use  'executeQuery()' when we do a 'select', i.e. we expect a result

        while (rs.next()){  //rs is iterable, so we can use method next()
            int student_id = rs.getInt("id");  //we pass the exact column name
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            System.out.println("Student: "+student_id+ ". Name: "+first_name+" "+last_name);
        }
        //always close these two things
        rs.close();
        pstmt.close();
    }
    /*From http://www.devx.com/tips/Tip/26465
    By default, JDBC connections start in autocommit mode. This means that every executed statement is
    treated as a separate transaction. A transaction is simply an operation that is irreversibly completed.

    The first call of setAutoCommit(false) on a connection and each call of commit()
    implicitly mark the start of a transaction. Transactions can
    be undone before they are committed by calling rollback()
    You will typically rollback a transaction when one of its constituent statements fails.*/
    public void transaction() throws SQLException {
        final String insertTeacherPSquery = "insert into teacher(id, first_name, last_name) values (?, ?, ?)";
        final String insertSubjectPSquery = "insert into subject(id, subject_name, teacher_id) values (?, ?, ?)";

        PreparedStatement teacherPS = null;
        PreparedStatement subjectPS = null;

        try{
           //some query in the transacion may fail, for instance, if we are introducing a record
            //with a duplicated id

            teacherPS = connection.prepareStatement(insertTeacherPSquery);
            teacherPS.setInt(1,60);
            teacherPS.setString(2,"Jose");
            teacherPS.setString(3,"Perez");

            teacherPS.executeUpdate();   //use  'executeUpdate()' when we do an 'insert', i.e. we don't expect a result

            subjectPS = connection.prepareStatement(insertSubjectPSquery);
            subjectPS.setInt(1,100);
            subjectPS.setString(2,"Fundamentos de Bases de Datos 2");
            subjectPS.setInt(3, 60);

            subjectPS.executeUpdate();

            /********************************************************/
            connection.commit(); // "commit means: '... finish this transaction and let's start another. '"
            /********************************************************/

            System.out.println("Transaction done !");

        }catch (SQLException e){
            //We manually do the rollback in case an error occur when accessing the database before the commit() !
            //The rollback undoes all changes made since the transaction started.
            connection.rollback();
            e.printStackTrace();
        }finally {
            if (teacherPS != null){
                teacherPS.close();
            }
            if (subjectPS != null){
                subjectPS.close();
            }
        }

    }



    //I dont know what is this
    private static final Logger LOG = Logger.getLogger(String.valueOf(App.class));

    public static void main( String[] args )    {

        // not needed in modern version of the jdbc driver. Now the jdbc driver is loaded automatically
        // into the JVM so the DriverMAnager (sql API) can find/use it
        //Class.forName("com.mysql.jdbc.Driver");

        try{
            new App("learner","learner");

            System.out.println("I'm in !! ");

        }catch (SQLException e){
            e.printStackTrace();
        } /*finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
