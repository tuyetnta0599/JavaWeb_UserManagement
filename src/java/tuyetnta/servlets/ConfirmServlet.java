/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tuyetnta.daos.PromotionDAO;
import tuyetnta.dtos.PromotionDTO;
import tuyetnta.dtos.UserDTO;
import tuyetnta.object.Cart;

/**
 *
 * @author tuyet
 */
@WebServlet(name = "ConfirmServlet", urlPatterns = {"/admin/ConfirmServlet"})
public class ConfirmServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ConfirmServlet.class);
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String SHOW_PROMO_CONTROLLER = "/admin/ShowPromoListServlet";

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
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        try {
            String id = request.getParameter("txtUserID");
            int rank = Integer.parseInt(request.getParameter("txtRank"));
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                PromotionDAO dao = new PromotionDAO();
                UserDTO user = new UserDTO();
                user.setId(id);
                PromotionDTO promo = new PromotionDTO();
                promo.setUser(user);
                promo.setRank(rank);
                String error = dao.confirm(promo);
                if (error == null) {
                    request.setAttribute("MSG_SUCCESS", "Added " + id + " to Promotion successffull");
                } else {
                    request.setAttribute("MSG_ERROR", error);
                }
            } else {
                request.setAttribute("MSG_ERROR", "Some thing was wrong");
            }
            url = SHOW_PROMO_CONTROLLER;
        } catch (SQLException | NamingException | NumberFormatException | NullPointerException ex) {
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
