
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.daos;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tuyetnta.dtos.RoleDTO;
import tuyetnta.dtos.UserDTO;
import tuyetnta.util.MyConnection;
import tuyetnta.util.PasswordEncrypt;

/**
 *
 * @author tuyet
 */
public class UserDAO implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    private Connection conn = null;
    private PreparedStatement prStm = null;
    private ResultSet rs = null;

    private void closeConn() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (prStm != null) {
                prStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }

    public UserDTO checkLogin(String id, String password) throws SQLException, NamingException, NoSuchAlgorithmException {
        UserDTO user = null;
        try {
            conn = MyConnection.getMyConnection();
            String encryptedPassword = PasswordEncrypt.getEncryptedPassword(password);
            String sql = "SELECT u.id, u.fullname, r.name AS roleName FROM dbo.users AS u INNER JOIN dbo.roles AS r ON r.id = u.idRole WHERE u.id = ? AND u.password = ? AND u.status = 'active'";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, id);
            prStm.setString(2, encryptedPassword);
            rs = prStm.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setFullname(rs.getNString("fullname"));
                user.setId(rs.getString("id"));
                user.setRole(new RoleDTO(rs.getString("roleName")));
            }
        } finally {
            closeConn();
        }
        return user;
    }

    public List<UserDTO> search(String searchValue, String role) throws NamingException, SQLException {
        List<UserDTO> users = new ArrayList<>();
        try {
            conn = MyConnection.getMyConnection();
            String sql = "SELECT u.id, u.email, u.fullname, u.phone, u.photo, r.name AS roleName FROM dbo.users AS u INNER JOIN dbo.roles AS r ON r.id = u.idRole WHERE (? IS NULL OR  r.name = ?) AND u.fullname LIKE ? COLLATE Latin1_General_CI_AI AND u.status = 'active'";
            prStm = conn.prepareStatement(sql);
            prStm.setObject(1, role, Types.VARCHAR);
            prStm.setObject(2, role, Types.VARCHAR);
            prStm.setNString(3, "%" + searchValue + "%");
            rs = prStm.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setId(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getNString("fullname"));
                user.setPhone(rs.getString("phone"));
                user.setPhoto(rs.getString("photo"));
                user.setRole(new RoleDTO(rs.getString("roleName")));
                users.add(user);
            }
        } finally {
            closeConn();
        }
        return users;
    }

    public boolean delete(String id) throws SQLException, NamingException {
        boolean result = false;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "UPDATE dbo.users SET status = 'inactive' WHERE id = ? AND status = 'active'";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, id);
            int rowEffect = prStm.executeUpdate();
            result = rowEffect > 0;
        } finally {
            closeConn();
        }
        return result;
    }

    public boolean update(UserDTO user) throws SQLException, NamingException {
        try {
            conn = MyConnection.getMyConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE dbo.users SET fullname = ?, email = ?, phone = ?, photo = ?, idRole = ? WHERE id = ? AND status = 'active'";
            prStm = conn.prepareStatement(sql);
            prStm.setNString(1, user.getFullname());
            prStm.setString(2, user.getEmail());
            prStm.setString(3, user.getPhone());
            prStm.setString(4, user.getPhoto());
            prStm.setInt(5, user.getRole().getId());
            prStm.setString(6, user.getId());
            int rowEffect = prStm.executeUpdate();
            if (rowEffect == 0) {
                return false;
            }
            if (!user.getPassword().isEmpty()) {
                sql = "UPDATE dbo.users SET password = ? WHERE id = ? AND status = 'active'";
                prStm = conn.prepareStatement(sql);
                prStm.setString(1, user.getPassword());
                prStm.setString(2, user.getId());
                rowEffect = prStm.executeUpdate();
                if (rowEffect == 0) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
        } finally {
            closeConn();
        }
        return true;
    }
    
    public boolean createUser(UserDTO user) throws SQLException, NamingException {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "INSERT INTO users(id, password, email, fullname, phone, photo, idRole, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, user.getId());
            prStm.setString(2, user.getPassword());
            prStm.setString(3, user.getEmail());
            prStm.setString(4, user.getFullname());
            prStm.setString(5, user.getPhone());
            prStm.setString(6, user.getPhoto());
            prStm.setInt(7, user.getRole().getId());
            prStm.setString(8, user.getStatus());
            check = prStm.executeUpdate() > 0;
        } finally {
            closeConn();
        }
        return check;
    }

    public UserDTO getUserByID(String id) throws SQLException, NamingException {
        UserDTO user = null;
        try {
            String sql = "SELECT u.id, u.email, u.fullname, u.phone, u.photo, r.name AS roleName FROM dbo.users AS  u INNER JOIN dbo.roles AS r ON r.id = u.idRole WHERE u.id = ? AND u.status = 'active'";
            conn = MyConnection.getMyConnection();
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, id);
            rs = prStm.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setId(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getNString("fullname"));
                user.setPhone(rs.getString("phone"));
                user.setPhoto(rs.getString("photo"));
                user.setRole(new RoleDTO(rs.getString("roleName")));
            }
        } finally {
            closeConn();
        }
        return user;
    }
}
