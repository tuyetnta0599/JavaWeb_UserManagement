/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author tuyet
 */
public class MyConnection implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MyConnection.class);

    public static Connection getMyConnection() throws NamingException, SQLException {
        try {
            Connection conn = null;
            Context currContext = new InitialContext();
            Context tomContext = (Context) currContext.lookup("java:comp/env");
            DataSource ds = (DataSource) tomContext.lookup("LAB1");
            conn = ds.getConnection();
            return conn;
        } catch (SQLException | NamingException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }
}
