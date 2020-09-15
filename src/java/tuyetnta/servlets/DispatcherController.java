/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author tuyet
 */
public class DispatcherController extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String ADMIN_SEARCH_CONTROLLER = "/admin/SearchServlet";
    private final String DELETE_CONTROLLER = "/admin/DeleteServlet";
    private final String UPDATE_CONTROLLER = "/admin/UpdateServlet";
    private final String CREATE_USER_CONTROLLER = "/admin/CreateUserServlet";
    private final String ADD_RANK_CONTROLLER = "/admin/AddUserInPromoServlet";
    private final String SHOW_PROMO_CONTROLLER = "/admin/ShowPromoListServlet";
    private final String UPDATE_RANK_CONTROLLER = "/admin/UpdateRankServlet";
    private final String DELETE_RANK_CONTROLLER = "/admin/DeleteRankServlet";
    private final String CONFIRM_RANK_CONTROLLER = "/admin/ConfirmServlet";
    private static final Logger LOGGER = Logger.getLogger(DispatcherController.class);

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
            String button = request.getParameter("btnAction");
            if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Search")) {
                url = ADMIN_SEARCH_CONTROLLER;
            } else if (button.equals("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("Update")) {
                url = UPDATE_CONTROLLER;
            } else if (button.equals("Back")) {
                url = ADMIN_SEARCH_CONTROLLER;
            } else if (button.equals("Create")) {
                url = CREATE_USER_CONTROLLER;
            } else if (button.equals("Add Rank")) {
                url = ADD_RANK_CONTROLLER;
            } else if (button.equals("Promotion")) {
                url = SHOW_PROMO_CONTROLLER;
            } else if (button.equals("Update Rank")) {
                url = UPDATE_RANK_CONTROLLER;
            } else if (button.equals("Delete Rank")) {
                url = DELETE_RANK_CONTROLLER;
            } else if (button.equals("Confirm")) {
                url = CONFIRM_RANK_CONTROLLER;
            } else {
                request.setAttribute("ERROR", "Invalid Action");
            }
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
