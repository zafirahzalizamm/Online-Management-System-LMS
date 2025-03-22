//Author : Luqman,Afif,Rafie,Zafirah,Zafri


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class User {
    private String username;
    private String password;
    private String role;
    private String name;
    private String specialization;
    /**
     * 
     * Constructor for creating a new instance of User
     * 
     * @param username       the username of the user
     * @param password       the password of the user
     * @param role           the role of the user
     * @param account_name   the account name of the user
     * @param specialization the specialization of the user
     */
    public User(String username, String password, String role, String name, String specialization) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.specialization = specialization;
    }
    /**
     * Method to get the "password" instance variable
     * @param username the user to set
     */
    public String getUsername() {
        return username;
    }
    /**
     * Method to get the "password" instance variable
     * @param username the user to set
     */
    public String getPassword() {
        return password;
    }
    /**
     * Method to get the "role" instance variable
     * @param role the user to set
     */
    public String getRole() {
        return role;
    }
    /**
     * Method to get the "name" instance variable
     * @param name the user to set
     */
    public String getName() {
        return name;
    }
    /**
     * Method to get the "name" instance variable
     * @param specialization the user to set
     */
    public String getSpecialization() {
        return specialization;
    }
}

/**
 * 
 * UserrModel is a class that represents the data model for the homepage.
 * 
 * It contains fields for username, password, role, account name, and
 * specialization
 * 
 * and methods to access these fields.
 */
class UserModel {

    private String username;
    private String password;
    private String role;
    private String name;
    private String specialization;
    private static final String USERS_CSV_FILE = "users.csv";
    private static List<User> users;

    static {
        try {
            users = readUsersFromCsv();
        } catch (IOException e) {
            System.err.println("Error reading users.csv file: " + e.getMessage());
        }
    }
    /**
     * 
     * Constructor for creating a new instance of LecturerModel
     * 
     * @param username       the username of the user
     * @param password       the password of the user
     */
    public UserModel(LoginView view) {
        this.username = view.getUsername();
        this.password = view.getPassword();
    }
/**
 * Method to check a user's login credentials and store their role, name, and specialization.
 * Iterates through a list of "users" and compares the provided "username" and "password" with 
 * the corresponding values for each user. If a match is found, the role, name, and specialization
 * for the matching user are stored in the class's instance variables and the method returns true.
 * If no match is found, the method returns false.
 *
 * @return boolean indicating if login was successful or not
 */
    public boolean checkLogin() {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                role = user.getRole();
                name = user.getName();
                specialization = user.getSpecialization();
                return true;
            }
        }
        return false;
    }
    /**
     * Method to set the "username" instance variable
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Method to set the "password" instance variable
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * 
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }
    /**
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @return the specialization of the user
     */
    public String getSpecialization() {
        return specialization;
    }

    private static List<User> readUsersFromCsv() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String username = values[0];
                String password = values[1];
                String role = values[2];
                String name = values[3];
                String specialization = values[4];
                users.add(new User(username, password, role, name, specialization));
            }
        }
        return users;
    }
}

/**
 * 
 * LoginView is a class that represents the user interface for a user.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
class LoginView extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JPanel jp;
    private JLabel usernameLabel, passwordLabel, welcome, empty;
    /**
     * 
     * Constructor for creating a new instance of LoginView
     * 
     */
    public LoginView() {
        // create and initialize the login form
        empty = new JLabel("");
        welcome = new JLabel("Welcome to MMU Online Management System");
        usernameLabel = new JLabel("Username"); // Usename label
        usernameField = new JTextField(15); // set length of tesxt
        passwordLabel = new JLabel("Password"); // Password label
        passwordField = new JPasswordField(15); // set length of text

        loginButton = new JButton("Log In"); // LogIn button

        // create panel to put form elements
        jp = new JPanel(new GridLayout(4, 1));
        add(jp);
        setSize(550, 200);
        // make page visible to the user
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("MMU Online Management System"); // set title
        add(jp, BorderLayout.CENTER); // set border to panel

        jp.add(welcome);
        jp.add(empty);
        jp.add(usernameLabel); // set username label to panel
        jp.add(usernameField); // set username text field to panel
        jp.add(passwordLabel); // set password label to panel
        jp.add(passwordField); // set password text field to panel
        jp.add(loginButton); // set button to panel

    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
    /**
     * Method for setting an action listener for the login button
     * 
     * @param listener the action listener to be set
     */
    public void setLoginButtonActionListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
    /**
     * Displays an error message using JOptionPane
     * 
     * @param message the error message to display
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Method for clearing the page
     */
    public void clearpage() {
        dispose();
    }
    /**
     * 
     * Constructor for creating a new instance of showAdminPAge
     * 
     * @param username       the username of the lecturer
     * @param password       the password of the lecturer
     * @param role           the role of the lecturer
     * @param account_name   the account name of the lecturer
     * @param specialization the specialization of the lecturer
     */
    public void showAdminPage(String username, String password, String role, String name, String specialization) {
        // create and show the admin page
        AdminPage userpage = new AdminPage(username, password, role, name, specialization); // pass
                                                                                            // data
                                                                                            // to
                                                                                            // Userpage.java
        // make page visible to the user
        userpage.setVisible(true);
    }
    /**
     * 
     * Constructor for creating a new instance of showLecturerPAge
     * 
     * @param username       the username of the lecturer
     * @param password       the password of the lecturer
     * @param role           the role of the lecturer
     * @param account_name   the account name of the lecturer
     * @param specialization the specialization of the lecturer
     */
    public void showLecturerPage(String username, String password, String role, String name, String specialization) {
        // create and show the lecturer page
        LecturerPage userpage = new LecturerPage(username, password, role, name, specialization); // pass
                                                                                                  // data
                                                                                                  // to
                                                                                                  // Userpage.java
        // make page visible to the user
        userpage.setVisible(true);
    }
    /**
     * 
     * Constructor for creating a new instance of showStudentPAge
     * 
     * @param username       the username of the lecturer
     * @param password       the password of the lecturer
     * @param role           the role of the lecturer
     * @param account_name   the account name of the lecturer
     * @param specialization the specialization of the lecturer
     */
    public void showStudentPage(String username, String password, String role, String name, String specialization) {
        // create and show the student page
        StudentPage userpage = new StudentPage(username, password, role, name, specialization); // pass
                                                                                                // data
                                                                                                // to
                                                                                                // Userpage.java
        // make page visible to the user
        userpage.setVisible(true);
    }
}

/**
 * 
 * LoginController is a class that acts as a link between the model and the
 * view.
 * 
 * It receives user input from the view and updates the model accordingly.
 * 
 * It also sets action listeners for the buttons in the view to respond to user
 * actions.
 */
class LoginController {
    private UserModel model;
    private LoginView view;
    /**
     * 
     * Constructor for creating a new instance of r=LoginController
     * 
     * @param model the data model for the lecturer
     * 
     * @param view  the user interface for the lecturer
     */
    public LoginController(UserModel model, LoginView view) {
        this.model = model;
        this.view = view;
        view.setLoginButtonActionListener(new LoginButtonActionListener());
    }
    /**
     * Method for setting an action listener for the login button
     * 
     * @param listener the action listener to be set
     */
    private class LoginButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();
            String role, name, specialization;
            if (username.isEmpty() || password.isEmpty()) {
                view.showErrorMessage("Please enter a value");
                return;
            }
            model.setUsername(username);
            model.setPassword(password);
            if (model.checkLogin()) {
                name = model.getName();
                specialization = model.getSpecialization();
                role = model.getRole();
                switch (role) {
                    case "admin":
                        view.showAdminPage(username, password, role, name, specialization);
                        view.dispose();
                        break;
                    case "lecturer":
                        view.showLecturerPage(username, password, role, name, specialization);
                        view.dispose();
                        break;
                    case "student":
                        view.showStudentPage(username, password, role, name, specialization);
                        view.dispose();
                        break;
                }
            } else {
                view.showErrorMessage("Invalid username or password");
            }
        }
    }
}
    /**
     * 
     * Class for realLogin as a main for this system
     * 
     */
public class realLogin {

    public static void main(String[] args) {
        LoginView view = new LoginView();
        view.setVisible(true);
        UserModel model = new UserModel(view);
        new LoginController(model, view);
    }
    /**
     * 
     * Constructor for realLogin 
     * 
     */
    public realLogin() {
        LoginView view = new LoginView();
        view.setVisible(true);
        UserModel model = new UserModel(view);
        new LoginController(model, view);
    }
    /**
     * 
     * method for setvisible
     * 
     */
    public void setVisible(boolean b) {
    }
}
