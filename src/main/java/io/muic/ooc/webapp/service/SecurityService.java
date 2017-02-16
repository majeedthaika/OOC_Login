/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import javax.servlet.http.HttpServletRequest;

public class SecurityService {

    private DatabaseService databaseService;

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    
    public boolean isAuthorized(HttpServletRequest request) {
        String username = (String) request.getSession()
                .getAttribute("username");
        // do checking
       return (username != null && databaseService.containsUser(username));
    }
    
    public boolean authenticate(String username, String password, HttpServletRequest request) {
        String passwordInDB = databaseService.getPassword(username);
        if (databaseService.checkPassword(password, passwordInDB)) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }
    
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
