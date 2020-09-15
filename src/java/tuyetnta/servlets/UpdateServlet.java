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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tuyetnta.daos.UserDAO;
import tuyetnta.dtos.RoleDTO;
import tuyetnta.dtos.UserDTO;
import tuyetnta.util.PasswordEncrypt;

/**
 *
 * @author tuyet
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/admin/UpdateServlet"})
public class UpdateServlet extends HttpServlet {

    Pattern passwordPattern = Pattern.compile("[a-zA-Z0-9]{6,30}");
    Pattern fullnamePattern = Pattern.compile("^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀẾỂưăạảấầẩẫậắằẳẵặẹẻẽềếểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s.]{3,30}$");
    Pattern emailPattern = Pattern.compile("([a-zA-Z0-9]{3,50})@([a-zA-Z]{3,10})((\\.([a-zA-Z]{2,5})){1,2})");
    Pattern phonePattern = Pattern.compile("[0-9]{10,13}");
    private static final Logger LOGGER = Logger.getLogger(UpdateServlet.class);
    private static final String ADMIN_CONTROLLER = "/admin/SearchServlet";
    private static final String ERROR_PAGE = "/error.jsp";

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
        try {
            String id = request.getParameter("txtId");
            String password = request.getParameter("txtPassword");
            String email = request.getParameter("txtEmail");
            String fullname = request.getParameter("txtFullname");
            String phone = request.getParameter("txtPhone");
            String photo = request.getParameter("txtPhoto");
            String role = request.getParameter("isAdmin");

            UserDAO dao = new UserDAO();
            List<String> error = new ArrayList();
            if (!password.isEmpty() && !passwordPattern.matcher(password).matches()) {
                error.add("Password must in range 6-30 chars");
            }
            if (!fullnamePattern.matcher(fullname).matches()) {
                error.add("Fullname contain only alphabet chars");
            }
            if (!emailPattern.matcher(email).matches()) {
                error.add("Email must be formated: abc@xyz.com or abc@xyz.com.vn");
            }
            if (!phonePattern.matcher(phone).matches()) {
                error.add("Phone number must in range 10-13 numbers");
            }
            if (!error.isEmpty()) {
                request.setAttribute("MSG_ERROR", error);
            } else {
                UserDTO user = new UserDTO();
                user.setId(id);
                if (password.isEmpty()) {
                    user.setPassword(password);
                } else {
                    user.setPassword(PasswordEncrypt.getEncryptedPassword(password));
                }
                user.setEmail(email);
                user.setFullname(fullname);
                user.setPhone(phone);
                user.setPhoto(photo);
                HttpSession session = request.getSession();
                UserDTO userSession = (UserDTO) session.getAttribute("USER");
                if (id.equals(userSession.getId()) && role == null) {
                    request.setAttribute("MSG_ERROR", "Cannot change your role");
                } else {
                    RoleDTO userRole = new RoleDTO();
                    if (role != null && role.equals("ON")) {
                        userRole.setId(1);
                    } else {
                        userRole.setId(2);
                    }
                    user.setRole(userRole);
                    if (dao.update(user)) {
                        request.setAttribute("MSG_SUCCESS", "Updated Username: " + id);
                    } else {
                        request.setAttribute("MSG_ERROR", "Username: " + id + " not found.");
                    }
                }
            }
            url = ADMIN_CONTROLLER;
        } catch (NoSuchAlgorithmException | SQLException | NamingException ex) {
            request.setAttribute("ERROR", "Something was wrong");
            LOGGER.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
