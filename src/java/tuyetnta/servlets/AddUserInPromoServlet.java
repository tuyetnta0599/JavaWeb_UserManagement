/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import tuyetnta.dtos.PromotionDTO;
import tuyetnta.dtos.UserDTO;
import tuyetnta.object.Cart;

/**
 *
 * @author tuyet
 */
@WebServlet(name = "AddUserInPromoServlet", urlPatterns = {"/admin/AddUserInPromoServlet"})
public class AddUserInPromoServlet extends HttpServlet {

    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ADMIN_SEARCH_PAGE = "/admin/SearchServlet";

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
            String userID = request.getParameter("txtId");
            String role = request.getParameter("txtRole");
            if (role.equals("admin")) {
                request.setAttribute("MSG_ERROR", "Admin cannot at to Promotion List");
            } else {
                int rank = 5;
                PromotionDTO promo = new PromotionDTO();
                UserDTO user = new UserDTO();
                user.setId(userID);
                promo.setUser(user);
                promo.setRank(rank);
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();
                }
                if (cart.addToCart(promo)) {
                    request.setAttribute("MSG_ERROR", userID + "has already exits");
                } else {
                    request.setAttribute("MSG_SUCCESS", "Added " + userID + " to Promotion successfull");
                    session.setAttribute("CART", cart);
                }
            }
            url = ADMIN_SEARCH_PAGE;
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
