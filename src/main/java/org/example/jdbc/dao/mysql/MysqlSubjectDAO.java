package org.example.jdbc.dao.mysql;

import org.example.jdbc.dao.DAO;
import org.example.jdbc.dao.DAOException;
import org.example.jdbc.dao.SubjectDAO;
import org.example.jdbc.model.Student;
import org.example.jdbc.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlSubjectDAO implements SubjectDAO {

    /*
    private Long id = null;
    private String subject_name;
    private Long teacher_id;
    * */

    final String INSERT = "INSERT INTO subject (subject_name, teacher_id) VALUES (?, ?)";
    final String UPDATE = "UPDATE subject SET subject_name='?', teacher_id=? WHERE id=?";
    final String DELETE = "DELETE FROM subject WHERE id = ?";
    final String GETALL = "SELECT s.id, s.subject_name, s.teacher_id FROM subject s";
    final String GETONE = "SELECT s.id, s.subject_name, s.teacher_id FROM subject s WHERE s.id = ?";

    private Connection connection;

    public MysqlSubjectDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Subject s) throws DAOException {
        //final String INSERT = "INSERT INTO subject ( subject_name, teacher_id) VALUES (?, ?)";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, s.getSubject_name());
            ps.setLong(2, s.getTeacher_id());

            if (ps.executeUpdate() == 0)
                throw new DAOException("Error executing insert Subject query.");

            rs = ps.getGeneratedKeys();

            if (rs.next())
                s.setId(rs.getLong(1));
            else
                throw new DAOException("No keys were generated for this PreparedStatement");

        } catch (SQLException e) {
            throw new DAOException("Error inserting a Subject into de database", e);
        }finally {
            if (ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatement", e);
                }
            }
            if (rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the ResultSet", e);
                }
            }
        }
    }

    @Override
    public void edit(Subject s, Long id) throws DAOException {
        //final String UPDATE = "UPDATE subject SET subject_name='?', teacher_id=? WHERE id=?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(UPDATE);
            ps.setString(1, s.getSubject_name());
            ps.setLong(2, s.getTeacher_id());
            ps.setLong(3, s.getId());

            if (ps.executeUpdate() == 0){
                throw new DAOException("Error executing insert Subject query.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error editing a Subject in the database", e);
        } finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing te PreparedStatement", e);
                }
            }
        }

    }

    @Override
    public void delete(Long id) throws DAOException {
        //final String DELETE = "DELETE FROM subject WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(DELETE);
            ps.setLong(1, id);

            if (ps.executeUpdate() ==0)
                throw new DAOException("Failed executing update query");

        } catch (SQLException e) {
            throw new DAOException("Error deleting Subject from the database", e);
        }finally {
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing PreparedStatement", e);
                }
            }
        }

    }

    private Subject convertFromRSrow(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String subject_name = rs.getString("subject_name");
        Long teacher_id = rs.getLong("teacher_id");
        Subject s = new Subject(subject_name, teacher_id);
        s.setId(id);
        return s;
    }

    @Override
    public List<Subject> getAll() throws DAOException {
        //final String GETALL = "SELECT s.id, s.subject_name, s.teacher_id FROM subject s";

        List<Subject> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(GETALL);
            rs = ps.executeQuery();

            while (rs.next())
                list.add(convertFromRSrow(rs) );

        } catch (SQLException e) {
            throw new DAOException("Error deleting all Subjects from the database", e);
        }finally {
            if(rs!=null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the ResultSet");
                }
            }
            if(ps!=null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Error closing the PreparedStatement");
                }
            }
        }
        return list;
    }

    @Override
    public Subject get(Long id) throws DAOException {
        //final String GETONE = "SELECT s.id, s.subject_name, s.teacher_id FROM subject s WHERE s.id = ?";

        Subject s = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(GETONE);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (rs.next())
                s = convertFromRSrow(rs);
            else
                throw new DAOException("There is no Subject with id " + id);

        } catch (SQLException e) {
            throw new DAOException("Error selecting one Subject from the database", e);
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

        return s;
    }
}
