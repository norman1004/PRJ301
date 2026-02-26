/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author Quoc Thai
 */
public class RegistrationDAO implements Serializable {

    public boolean checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. create SQL String 15/
                String sql = "Select username "
                        + "From Registration "
                        + "Where username = ? "
                        + "and password = ?";
                //3. create Statement Object 15/
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute Query 15/
                rs = stm.executeQuery();
                //5. Process result 16/
                if (rs.next()) {
                    result = true;
                }//username  and password are matched
            }// connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastName(String searchValue)
            throws SQLException, ClassNotFoundException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. create SQL String 15/
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";

                //3. create Statement Object 15/
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query 15/
                rs = stm.executeQuery();
                //5. Process result 16/
                while (rs.next()) {
                    //5.1 Model se get du lieu tu resultSet
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    //5.2 Model sets data to DTO Object
                    RegistrationDTO dto = new RegistrationDTO(username,
                            password, fullname, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }//account is unavailable
                    this.accounts.add(dto);
                }//end traverse each rows of table
            }// connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public boolean deleteAccount(String username)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. create SQL String 15/
                String sql = "Delete From Registration "
                        + "Where username = ?";

                //3. create Statement Object 15/
                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                //4. Execute Query 15/
                int effectedRows = stm.executeUpdate();
                //5. Process result 16/
                if (effectedRows > 0) {
                    result = true;
                }// connection is available
            }
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    
    public boolean updateAccount(String username, String password, boolean isAdmin)
            throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. create SQL String 15/
                String sql = "Update  Registration "
                        + "Set password = ?, "
                        + "isAdmin = ? "
                        + "Where username = ?";

                //3. create Statement Object 15/
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);

                //4. Execute Query 15/
                int effectedRows = stm.executeUpdate();
                //5. Process result 16/
                if (effectedRows > 0) {
                    result = true;
                }// connection is available
            }
        } finally {

            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
