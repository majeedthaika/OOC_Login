/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.servlet;

import io.muic.ooc.webapp.service.User;
import io.muic.ooc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private UserService userService;

    public void setUserManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        User user = userService.getUser(username);

        if (user != null) {
            request.setAttribute("user", username);

            StringBuilder userDetails = new StringBuilder(
                    "<table class=\"table table-bordered table-striped\">" +
                            "<thead class=\"thead-inverse\"><tr><th>UserInfo</th><th></th></thead>");
            userDetails.append("<tr><td>Username</td><td>"+user.getUserName()+"</td></tr>");
//            userDetails.append("<tr><td>Password</td><td>"+user.getPassword()+"</td></tr>");
            userDetails.append("<tr><td>Email</td><td>"+user.getEmail()+"</td></tr>");
            userDetails.append("<tr><td>FirstName</td><td>"+user.getFirstName()+"</td></tr>");
            userDetails.append("<tr><td>LastName</td><td>"+user.getLastName()+"</td></tr>");
            userDetails.append("</tbody></table>");
            request.setAttribute("userDetails", userDetails);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/user.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String username = request.getParameter("username");
//        request.setAttribute("username", username);
//        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/user.jsp");
//        rd.include(request, response);
//    }

}
