/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package pe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.model.registration.RegistrationCreateError;
import pe.model.registration.RegistrationDAO;
import pe.model.registration.RegistrationDTO;

/**
 *
 * @author Quoc Thai
 */
@WebServlet(name="CreateAccountServlet", urlPatterns={"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {
   private static final String ERROR_PAGE = "createNewAccount.jsp";
   private static final String LOGIN_PAGE = "login.html";
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        //1. Controller lay toan bo thong tin nguoi dung
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        
        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;
        try {
           //2. Controller handles all user's errors
           if(username.trim().length() < 6 || 
                   username.trim().length() > 20){
               foundErr = true;
               errors.setUsernameLengthErr("Username is required typing from 6 to 20 characters");
           }
           
           if(password.trim().length() < 6 || 
                   password.trim().length() > 30){
               foundErr = true;
               errors.setPasswordLengthErr("Password is required typing from 6 to 30 characters");
           } else if(!confirm.trim().equals(password.trim())){
               foundErr = true;
               errors.setConfirmNoMatchErr("Confirm must match password");
           }
           
           if(fullname.trim().length() < 6 || 
                   fullname.trim().length() > 50){
               foundErr = true;
               errors.setFullnameLengthErr("Fullname is required typing from 6 to 50 characters");
           }
           
           if (foundErr){
               //User error type --> cache --> store errors to attribute of request scope
               request.setAttribute("CREATE_ERRORS", errors);
               //show
           }else{//no error
           
           //3. Controller calls methods of Model
           //3.1 Controller initalizes a DAO Object
           RegistrationDAO dao = new RegistrationDAO();
           //3.2 Controller calls methods of DAO Object
           RegistrationDTO account = 
                   new RegistrationDTO(username, password, fullname, foundErr);
           boolean result = dao.creatAccount(account);
           //4. Controller proccesses result
           if(result){
               url = LOGIN_PAGE;
           }//insert action is successful
           }//end no error
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("CreateAccountServlet_SQL " + ex.getMessage());
            if(errMsg.contains("duplicate")){
                errors.setUsernameIsExisted(username + " is EXISTED");
                request.setAttribute("CREATE_ERRORS", errors);//update scope
                //scope can duoc update de trinh bay loi
            }
        } catch (ClassNotFoundException ex) {
            log("CreateAccountServlet_Class Not Found " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
