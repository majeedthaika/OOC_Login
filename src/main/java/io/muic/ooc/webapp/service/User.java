package io.muic.ooc.webapp.service;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    public User(String firstName, String lastName, String username, String password, String email) {
        this.firstName = (firstName != null) ? firstName : "";
        this.lastName = (lastName != null) ? lastName : "";
        this.username = (username != null) ? username : "";
        this.password = (password != null) ? password : "";
        this.email = (email != null) ? email : "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{firstName=" + firstName +
                ", lastName=" + lastName + ", username=" + username +
                ", password=" + password + ", email=" + email + '}';
    }

//    public String toHTMLString() {
//        return "<tr><th scope=\"row\">"+username+"</th><td>"+password+"</td><td>"+firstName+"</td>" +
//                "<td>"+lastName+"</td><td>"+email+"</td></tr>";
//    }
}
