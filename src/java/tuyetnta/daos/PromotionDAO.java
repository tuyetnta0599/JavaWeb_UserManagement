/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import tuyetnta.dtos.PromotionDTO;
import tuyetnta.util.MyConnection;

/**
 *
 * @author tuyet
 */
public class PromotionDAO implements Serializable {

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

    public String confirm(PromotionDTO dto) throws SQLException, NamingException {
        try {
            conn = MyConnection.getMyConnection();
            String sql = "SELECT name AS roleName FROM dbo.users JOIN dbo.roles ON roles.id = users.idRole WHERE users.id = ? AND status = 'active'";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, dto.getUser().getId());
            rs = prStm.executeQuery();
            if (!rs.next()) {
                return "User is not exits";
            }
            String roleName = rs.getString("roleName");
            if (roleName.equals("admin")) {
                return "Cannot add admin to Promotion List";
            }

            //check if user added promotion
            sql = "SELECT userID FROM dbo.promotionList WHERE userID = ?";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, dto.getUser().getId());
            rs = prStm.executeQuery();
            if (rs.next()) {
                return "User added to Promotion";
            }

            //insert userID to promotion
            sql = "INSERT INTO promotionList(userID, rank) VALUES(?, ?)";
            prStm = conn.prepareStatement(sql);
            prStm.setString(1, dto.getUser().getId());
            prStm.setInt(2, dto.getRank());
            int rowEffect = prStm.executeUpdate();
            if (rowEffect == 0) {
                return "Insert user to rank failed";
            }
            return null;
        } finally {
            closeConn();
        }
    }
}
