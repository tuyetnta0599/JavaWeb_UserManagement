/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import tuyetnta.daos.UserDAO;
import tuyetnta.dtos.RoleDTO;
import tuyetnta.dtos.UserDTO;
import tuyetnta.util.PasswordEncrypt;

/**
 *
 * @author tuyet
 */
@WebServlet(name = "CreateUserServlet", urlPatterns = {"/admin/CreateUserServlet"})
public class CreateUserServlet extends HttpServlet {

    Pattern idPattern = Pattern.compile("[A-Za-z0-9]{2,30}");
    Pattern passwordPattern = Pattern.compile("[a-zA-Z0-9]{6,30}");
    Pattern fullnamePattern = Pattern.compile("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s.]{3,30}$");
    Pattern emailPattern = Pattern.compile("([a-zA-Z0-9]{3,50})@([a-zA-Z]{3,10})((\\.([a-zA-Z]{2,5})){1,2})");
    Pattern phonePattern = Pattern.compile("[0-9]{10,13}");
    private static final Logger LOGGER = Logger.getLogger(CreateUserServlet.class);
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String CREATE_USER_CONTROLLER = "/createUser.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        List<String> error = new ArrayList<>();
        String id = request.getParameter("txtId");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String email = request.getParameter("txtEmail");
        String fullname = request.getParameter("txtFullname");
        String phone = request.getParameter("txtPhone");
        String photo = request.getParameter("txtPhoto");
        String role = "sub";
        String status = "active";
        UserDTO user = new UserDTO();
        boolean isSuccess = false;
        try {

            if (!idPattern.matcher(id).matches()) {
                error.add("Username must in rang 2-30 chars\n");
            }
            if (!passwordPattern.matcher(password).matches()) {
                error.add("Password must in range 6-30 chars\n");
            }
            if (!confirm.trim().equals(password) && confirm.equals("")) {
                error.add("Password confirm not matched with password\n");
            }
            if (!fullnamePattern.matcher(fullname).matches()) {
                error.add("Fullname contain only alphabet chars");
            }
            if (!emailPattern.matcher(email).matches()) {
                error.add("Email must be formated: abc@xyz.com or abc@xyz.com.vn\n");
            }
            if (!phonePattern.matcher(phone).matches()) {
                error.add("Phone number must in range 10-13 numbers\n");
            }
            if (!error.isEmpty()) {
                request.setAttribute("MSG_ERROR", error);
            } else {
                UserDAO dao = new UserDAO();
                user.setId(id);
                user.setPassword(PasswordEncrypt.getEncryptedPassword(password));
                user.setEmail(email);
                user.setFullname(fullname);
                user.setPhone(phone);
                user.setPhoto(photo);
                RoleDTO userRole = new RoleDTO();
                if (role.equals("sub")) {
                    userRole.setId(2);
                }
                user.setRole(userRole);
                user.setStatus(status);
                if (dao.createUser(user)) {
                    isSuccess = true;
                    request.setAttribute("CREATED", "Create " + id + " successfull");
                } else {
                    request.setAttribute("CREATED_FAILED", "Create" + id + "failed");
                }
            }
            url = CREATE_USER_CONTROLLER;

        } catch (SQLException | NamingException | NoSuchAlgorithmException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                error.add(id + " is existed. Please choose another username!!");
                request.setAttribute("MSG_ERROR", error);
                url = CREATE_USER_CONTROLLER;
            }
            LOGGER.error(msg);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher("/" + url);
            rd.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
