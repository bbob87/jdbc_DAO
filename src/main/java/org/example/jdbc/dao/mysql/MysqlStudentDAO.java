package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.StudentDAO;
import org.example.jdbc.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlStudentDAO implements StudentDAO {

    //A prepared statement for each DAO method below
    final String INSERT = "INSERT INTO student ( first_name, last_name, birth_date) VALUES ( ?, ?, ?)";
    final String UPDATE = "UPDATE student SET first_name='?', last_name='?', birth_date='?' WHERE id=?";
    final String DELETE = "DELETE FROM student WHERE id = ?";
    final String GETALL = "SELECT s.id, s.first_name, s.last_name, s.birth_date FROM student s";
    final String GETONE = "SELECT s.id, s.first_name, s.last_name, s.birth_date FROM student s WHERE s.id = ?";

    private Connection connection;

    public MysqlStudentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Student s) throws DAOException {
        //final String INSERT = "INSERT INTO student ( first_name, last_name, birth_date) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // The second argument is needed to be able to retrieve later the key that
            // the SQL server generated for this statement when it was executed
            ps = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,s.getFirst_name());
            ps.setString(2,s.getLast_name());
            ps.setDate(3, new java.sql.Date(s.getBirth_date().getTime()));

            //now we execute the SQL statement
            if(ps.executeUpdate() == 0)
                throw new DAOException("Error executing insert Student query.");

            rs = ps.getGeneratedKeys(); //Retrieves any auto-generated keys created as a result of
            // executing this Statement object. If this Statement object did not generate any keys,
            // an empty ResultSet object is returned.

            if (rs.next())
                s.setId(rs.getLong(1));
            else
                throw new DAOException("No keys were generated for this PreparedStatement");

        } catch (SQLException e) {
            throw new DAOException("Error inserting a Student in the database",e);
        } finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing ResultSet", e);
                }
            }

        }
    }

    @Override
    public void edit(Student s, Long id) throws DAOException {
        //final String UPDATE = "UPDATE student SET first_name='?', last_name='?', birth_date='?' WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(UPDATE);

            ps.setString(1, s.getFirst_name());
            ps.setString(2, s.getLast_name());
            ps.setDate(3, new java.sql.Date(s.getBirth_date().getTime()));
            ps.setLong(4, id);

            if(ps.executeUpdate() == 0){
                throw new DAOException("Failed executing update query with Prepared Statement");
            }
        }catch (SQLException e){
            throw new DAOException("Error executing edit Student query", e);
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }

    }

    @Override
    public void delete(Long id) throws DAOException {
        //final String DELETE = "DELETE FROM student WHERE id = ?";
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(DELETE);

            ps.setLong(1, id);

            if(ps.executeUpdate()== 0)
                throw new DAOException("Failed executing update query with Prepared Statement");

        }catch (SQLException e){
            throw new DAOException("Error deleting a Student from the database", e);
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }
    }

    // get a Student object from the row pointed to by the result set cursor
    /* This method could be included as a generic method in some parent superclass or
     * interface. The information about the class into which the ResultSet wants to be converted
     * can be obtained through reflection  */

    private Student convertFromRSrow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        java.util.Date birth_date = rs.getTimestamp("birth_date");//attention to the conversion between java.util.Date and
        // java.sql.Date .
        Student s  = new Student(first_name, last_name, birth_date);
        s.setId(id);
        return s;
    }

    // customize this to limit the maximum length of the returned result set,
    // because we may be dealing with very large databases !
    // May return an empty list in case the table is empty
    @Override
    public List<Student> getAll() throws DAOException {
        //final String GETALL = "select s.id, s.first_name, s.last_name, s.birth_date from student s";

        List<Student> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(GETALL);
            rs = ps.executeQuery();

            while (rs.next())
                list.add(convertFromRSrow(rs));

        }catch (SQLException e){
            throw new DAOException("Error getting all Students from the database", e);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing ResultSet", e);
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }

        return list;
    }



    @Override
    public Student get(Long id) throws DAOException {
        //final String GETONE = "SELECT s.id, s.first_name, s.last_name, s.birth_date FROM student s WHERE s.id = ?";

        Student s = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(GETONE);

            ps.setLong(1,id); //'id' is of type Long, but the id column in the database is of type int, so?
            rs = ps.executeQuery();

            if (rs.next())
                s = convertFromRSrow(rs);
            else
                throw new DAOException("There is no Student with id " + id);

        } catch (SQLException e) {
            throw new DAOException("Error getting a Student from the database", e);
        } finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing ResultSet", e);
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }
        return s;
    }
}
