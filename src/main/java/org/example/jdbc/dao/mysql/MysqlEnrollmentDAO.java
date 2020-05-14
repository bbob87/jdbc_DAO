package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.EnrollmentDAO;
import org.example.jdbc.model.Enrollment;
import org.example.jdbc.model.Student;

import javax.swing.text.html.parser.Entity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlEnrollmentDAO implements EnrollmentDAO {

    /*
    private long student_id;
    private long subject_id;
    private int enrollment_year;
    private Integer mark = null;*/

    /* For the case of the enrollment entity, the key will be composed of three fields: student_id, subject_id and
    *  enrollment_year. Therefore all of them need to be specified in the WHERE clause */

    final String INSERT = "INSERT INTO enrollment (student_id, subject_id, enrollment_year, mark) VALUES (?, ?, ?, ?)";

    final String UPDATE = "UPDATE enrollment SET student_id = ?, subject_id=?, enrollment_year=?, mark=? " +
                          "WHERE student_id = ? AND subject_id = ? AND enrollment_year = ?";
    final String DELETE = "DELETE FROM enrollment " +
                          "WHERE student_id = ? AND subject_id = ? AND enrollment_year = ?";

    final String GETALL = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e";

    //The selection criteria always matches the unique or primary key chosen for the table's rows!

    final String GETONE = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e " +
            "WHERE e.student_id = ? AND e.subject_id = ? AND e.enrollment_year = ?";

    // Here I use a substring <substring> as a placeholder for the column label. Also,
    // I cast the int record of the column to a string and compare that to the query record also converted to string.
    // This allow for an exact comparison. Comparing the records as int will cause = 2 to give the same result as = 2001
    // in mysql, and I don't want that.
    final String GETALLFILTER ="SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e WHERE CAST(e.<column_label> AS CHAR) = ?";

    //CAST(cd.id AS CHAR)

    final String GETALLFILTERstudent = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e WHERE e.student_id = ?";

    final String GETALLFILTERsubject = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e" +
            "WHERE e.subject_id = ?";
    final String GETALLFILTERyear = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e" +
            "WHERE e.enrollment_year = ?";



    Connection connection;

    public MysqlEnrollmentDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Enrollment e) throws DAOException {
        //final String INSERT = "INSERT INTO enrollment (student_id, subject_id, enrollment_year, mark) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(INSERT);

            ps.setLong(1, e.getId().getStudent_id());
            ps.setLong(2, e.getId().getSubject_id());
            ps.setInt(3, e.getId().getEnrollment_year());

            //If the mark is null, a 'null' should me inserted into the database, not zero. So we do:
            if (e.getMark()!= null)
                ps.setInt(4, e.getMark());
            else
                ps.setNull(4, Types.INTEGER);  // here we indicate to the db to insert a null integer

            //now we execute the SQL statement
            if(ps.executeUpdate() == 0){
                throw new DAOException("Error executing insert Enrollment query.");
            }

        } catch (SQLException ex) {
            throw new DAOException("Error inserting a Enrollment in the database",ex);
        } finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing Prepared statement", ex);
                }
            }
        }
    }

    @Override
    public void edit(Enrollment e, Enrollment.EnrollmentId id) throws DAOException {
        //final String UPDATE = "UPDATE enrollment SET student_id = ?, subject_id=?, enrollment_year=?, mark=? " +
        //        "WHERE student_id = ? AND subject_id = ? AND enrollment_year = ?";

        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(UPDATE);

            ps.setLong(1, e.getId().getStudent_id());
            ps.setLong(2, e.getId().getSubject_id());
            ps.setInt(3, e.getId().getEnrollment_year());
            ps.setLong(4, e.getMark());

            ps.setLong(5, id.getStudent_id());
            ps.setLong(6, id.getSubject_id());
            ps.setInt(7, id.getEnrollment_year());

            if(ps.executeUpdate() == 0){
                throw new DAOException("Failed executing update query with Prepared Statement");
            }
        }catch (SQLException ex){
            throw new DAOException("Error executing edit Enrollment query", ex);
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing PreparedStatement", ex);
                }
            }
        }
    }

    @Override
    public void delete(Enrollment.EnrollmentId Id) throws DAOException {

        final String DELETE = "DELETE FROM enrollment " +
                "WHERE student_id = ? AND subject_id = ? AND enrollment_year = ?";

        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(DELETE);
            ps.setLong(1, Id.getStudent_id());
            ps.setLong(2, Id.getSubject_id());
            ps.setInt(3, Id.getEnrollment_year());

            if(ps.executeUpdate()== 0)
                throw new DAOException("Failed executing update query with PreparedStatement");

        }catch (SQLException ex){
            throw new DAOException("Error setting the PreparedStatement fields", ex);
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing PreparedStatement", ex);
                }
            }
        }
    }

    private Enrollment convertFromRSrow(ResultSet rs) throws SQLException {

        long student_id  = rs.getLong("student_id");
        long subject_id = rs.getLong("subject_id");
        int enrollment_year = rs.getInt("enrollment_year");

        //The mark for this enrollment record can be null. That must be taken into account
        // explicitly, because otherwise jdbc will interpret null as integer 0.
        Integer mark = rs.getInt("mark");
        if (rs.wasNull()) mark = null;
        return new Enrollment(student_id, subject_id, enrollment_year, mark);
    }

    @Override
    public List<Enrollment> getAll() throws DAOException {
        //final String GETALL = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e";

        List<Enrollment> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = connection.prepareStatement(GETALL);
            rs = ps.executeQuery();

            while (rs.next())
                list.add(convertFromRSrow(rs));

        }catch (SQLException ex){
            throw new DAOException("Error getting all Students from the database", ex);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing ResultSet", ex);
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing PreparedStatement", ex);
                }
            }
        }

        return list;
    }

    @Override
    public Enrollment get(Enrollment.EnrollmentId Id) throws DAOException {
        // final String GETONE = "SELECT e.student_id, e.subject_id, e.enrollment_year, e.mark FROM enrollment e " +
        //            "WHERE e.student_id = ? AND e.subject_id = ? AND e.enrollment_year = ?";

        Enrollment e = null;

        PreparedStatement ps = null;
        ResultSet rs=null;

        try {
            ps = connection.prepareStatement(GETONE);
            ps.setLong(1, Id.getStudent_id());
            ps.setLong(2, Id.getSubject_id());
            ps.setInt(3, Id.getEnrollment_year());

            rs = ps.executeQuery();

            if (rs.next())
                 e = convertFromRSrow(rs);
            else
                throw new DAOException("There is no Enrollment with id " + Id.toString());

        } catch (SQLException ex) {
            throw new DAOException("Error getting a Student from the database", ex);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing ResultSet", ex);
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing PreparedStatement", ex);
                }
            }
        }

        return e;

    }


    @Override
    public List<Enrollment> getAllfilterBy(long x_id, EnrollmentDAO.FilteringCriteria filteringCriteria) throws DAOException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Enrollment> list = new ArrayList<>();

        String columnLabel = null;

        switch (filteringCriteria){
            case STUDENT_ID:
                columnLabel = "student_id";
                break;
            case SUBJECT_ID:
                columnLabel = "subject_id";
                break;
            case ENROLLMENT_YEAR:
                columnLabel = "enrollment_year";
                break;
            default:
                throw new DAOException("Unknown filtering criteria.");
        }

          //String str = GETALLFILTER.replaceAll("<column_label>",columnLabel);

        try{
            ps = connection.prepareStatement(GETALLFILTER.replaceAll("<column_label>",columnLabel));

            ps.setString(1, String.valueOf(x_id));  // x_id will be converted to its string representation

            rs = ps.executeQuery();

            while (rs.next())
                list.add(convertFromRSrow(rs));

        }catch (SQLException ex){
            throw new DAOException("Error getting all Students from the database", ex);
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing ResultSet", ex);
                }
            }
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException ex){
                    throw new DAOException("Error closing PreparedStatement", ex);
                }
            }
        }

        return list;
    }




}
