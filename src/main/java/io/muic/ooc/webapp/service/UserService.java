/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muic.ooc.webapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private DatabaseService databaseService;

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public List<User> getAllUsers() {
        return databaseService.getUsers();
    }

    public User getUser(String username) {
        return databaseService.getUser(username);
    }

    public User getUserByID(Integer id) {
        return databaseService.getUserByID(id);
    }

    public boolean addUser(User user) {
        return databaseService.addNewUser(user);
    }

    public boolean editUser(User user) {
        return databaseService.modifyExistingUser(user);
    }

    public boolean removeUser(User user) {
        return databaseService.deleteUser(user);
    }

    public String getSecurePassword(String password) {
        return databaseService.getSaltedHash(password);
    }
}
