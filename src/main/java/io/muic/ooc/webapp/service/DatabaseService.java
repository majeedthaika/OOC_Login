package io.muic.ooc.webapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

public class DatabaseService {

    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void getDatabaseConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/ooc_test?"
                        + "user=ooc&password=oocpass");
    }

    public boolean containsUser(String username)  {
        boolean hasUser = false;
        try {
            getDatabaseConnection();
            preparedStatement = connect.prepareStatement
                    ("SELECT * from USER where username=?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            hasUser = resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return hasUser;
    }

    public User getUser(String name) {
        User user = null;
        try {
            getDatabaseConnection();
            preparedStatement = connect.prepareStatement
                    ("SELECT * from USER where username=?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                user = new User(first_name, last_name, username, password, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return user;
    }

    public String getPassword(String username) {
        User user = getUser(username);
        if (user != null) {
            return user.getPassword();
        } else {
            return "";
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            getDatabaseConnection();
            statement = connect.createStatement();
            resultSet = statement
                    .executeQuery("select * from USER");

            while (resultSet.next()) {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                users.add(new User(first_name, last_name, username, password, email));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return users;
    }

    public boolean addNewUser(User user) {
        boolean success = false;
        try {
            getDatabaseConnection();
            preparedStatement = connect
                    .prepareStatement("INSERT into USER VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return success;
    }

    public boolean modifyExistingUser(User user) {
        boolean success = false;
        try {
            getDatabaseConnection();
            preparedStatement = connect.
                    prepareStatement("UPDATE USER SET password=?, email=?, first_name=?, last_name=? WHERE username=?");
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getUserName());
            preparedStatement.executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return success;
    }

    public boolean deleteUser(User user) {
        boolean success = false;
        try {
            getDatabaseConnection();
            preparedStatement = connect.
                    prepareStatement("DELETE FROM USER WHERE username=?");
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return success;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    /** Computes a salted PBKDF2 hash of given plaintext password
     suitable for storing in a database.
     Empty passwords are not supported. */
    public static String getSaltedHash(String password) {
        String saltedHash= null;
        try {
            byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
            // store the salt with the password
            saltedHash = Base64.encodeBase64String(salt) + "$" + hash(password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saltedHash;
    }

    /** Checks whether given plaintext password corresponds
     to a stored salted hash of the password. */
    public static boolean checkPassword(String password, String stored) {
        boolean match = false;
        try {
            String[] saltAndPass = stored.split("\\$");
            if (saltAndPass.length != 2) {
                throw new IllegalStateException(
                        "The stored password have the form 'salt$hash'");
            }
            String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
            match = hashOfInput.equals(saltAndPass[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return match;
    }

    // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    private static String hash(String password, byte[] salt) {
        String hashed = null;
        try {
            if (password == null || password.length() == 0)
                throw new IllegalArgumentException("Empty passwords are not supported.");
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            SecretKey key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, iterations, desiredKeyLen)
            );
            hashed = Base64.encodeBase64String(key.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashed;
    }
}