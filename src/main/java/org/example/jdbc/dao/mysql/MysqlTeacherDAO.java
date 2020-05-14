package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.TeacherDAO;
import org.example.jdbc.model.Student;
import org.example.jdbc.model.Teacher;

import javax.swing.undo.AbstractUndoableEdit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlTeacherDAO implements TeacherDAO {

    /*
        private Long id = null;
    private String first_name;
    private String last_name;
    * */

    final String INSERT = "INSERT INTO teacher (first_name, last_name) VALUES ( ?, ?)";
    final String UPDATE = "UPDATE teacher SET first_name='?', last_name='?' WHERE id=?";
    final String DELETE = "DELETE FROM teacher WHERE id = ?";
    final String GETALL = "SELECT s.id, s.first_name, s.last_name FROM teacher s";
    final String GETONE = "SELECT s.id, s.first_name, s.last_name FROM teacher s WHERE s.id = ?";


    Connection connection;

    public MysqlTeacherDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Teacher t) throws DAOException {
        //final String INSERT = "INSERT INTO teacher (id, first_name, last_name) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs=null;

        try {
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, t.getFirst_name());
            ps.setString(2, t.getLast_name());

            if (ps.executeUpdate()==0)
                throw new DAOException("Error executing update query");

            rs = ps.getGeneratedKeys();

            if (rs.next())
                t.setId(rs.getLong(1));
            else
                throw new DAOException("No key generated for this ResultSet");



        } catch (SQLException e) {
            throw new DAOException("Error inserting a Teacher into the database", e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatement", e);
                }
            }
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the ResultSet", e);
                }
            }
        }
    }

    @Override
    public void edit(Teacher t, Long id) throws DAOException {
        //final String UPDATE = "UPDATE teacher SET first_name='?', last_name='?' WHERE id=?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getFirst_name());
            ps.setString(2, t.getLast_name());
            ps.setLong(3, t.getId());

            if(ps.executeUpdate()==0)
                throw new DAOException("Error executing update query");

        } catch (SQLException e) {
            throw new DAOException("Error editing a Teacher in the database", e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatement", e);
                }
            }
        }


    }

    @Override
    public void delete(Long id) throws DAOException {
        //final String DELETE = "DELETE FROM teacher WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(DELETE);
            ps.setLong(1, id);

            if(ps.executeUpdate()==0)
                throw new DAOException("Error executing update query");

        } catch (SQLException e) {
            throw new DAOException( "Error deleting Teacher from the database", e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatement", e);
                }
            }
        }
    }


    private Teacher convertFromRSrow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        Teacher t = new Teacher(first_name, last_name);
        t.setId(id);
        return t;
    }

    @Override
    public List<Teacher> getAll() throws DAOException {
        //inal String GETALL = "SELECT s.id, s.first_name, s.last_name FROM teacher s";

        List<Teacher> list= new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(GETALL);
            rs = ps.executeQuery();

            while (rs.next())
                list.add(convertFromRSrow(rs));

        } catch (SQLException e) {
            throw new DAOException( "Error getting all Teachers from the database", e);
        } finally {
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing ResultSet", e);
                }
            }
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }

        return list;
    }

    @Override
    public Teacher get(Long id) throws DAOException {
        //final String GETONE = "SELECT s.id, s.first_name, s.last_name FROM teacher s WHERE s.id = ?";
        Teacher t = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(GETONE);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next())
                t = convertFromRSrow(rs);
            else
                throw new DAOException("There is no Teacher with id " + id);

        } catch (SQLException e) {
            throw new DAOException("Error getting one Teacher from the database", e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatament", e);
                }
            }
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the ResultSet", e);
                }
            }
        }
        return t;
    }
}
