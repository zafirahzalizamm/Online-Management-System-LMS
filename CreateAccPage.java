// Author Luqman & Rafie
//import required classes and packages  
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * CreateAccModel is a class that represents the data model for a administrator.
 * 
 * It contains fields for username, password, role, account name, and
 * specialization
 * 
 * and methods to access these fields.
 */
class CreateAccModel {
    private String username;
    private String password;
    private String role;
    private String account_name;
    private String specialization;

    private String[] values = { "admin", "lecturer", "student" };
    private String[] special = { "Software Engineering", "Data Science", "Game Development", "Cyber Security",
            "Information System", "N/A" };
    
    /**
     * 
     * Constructor for creating a new instance of CreateAccModel
     * 
     * @param username       the username of the new user
     * @param password       the password of the new user
     * @param role           the role of the new user
     * @param account_name   the account name of the new user
     * @param specialization the specialization of the new user
     */
    public CreateAccModel(String username, String password, String role, String account_name, String specialization) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.account_name = account_name;
        this.specialization = specialization;
    }

    /**
     * 
     * @return  the username of the new user
     */
    public String getUsername() {
        return username;
    }

    
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getAccountName() {
        return account_name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String[] getValues() {
        return values;
    }

    public String[] getSpecial() {
        return special;
    }

}

class CreateAccView extends JFrame {
    private JLabel empty, instruction, userLabel, passwordLabel, userNameLabel, userTypeLabel, userSpecialLabel;
    private JTextField newUserText, newPasswordText, newAccNameText;
    private JButton addAccButton, backButton;
    private JComboBox dropDown, newUserSpecial;
    private JPanel jp, jp2, jp3, jp4, jp5, jp6, jp7;

    public CreateAccView(CreateAccModel model) {

        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("MMU Online Management System"); // set title

        empty = new JLabel("");
        instruction = new JLabel("Please enter all the details needed:- ");
        userLabel = new JLabel("Username"); // Usename label
        newUserText = new JTextField(15); // set length of tesxt
        passwordLabel = new JLabel("Password"); // Password label
        newPasswordText = new JTextField(15); // set length of text
        userNameLabel = new JLabel("Name"); // name
        newAccNameText = new JTextField(15); // set length of text
        userTypeLabel = new JLabel("Type of account:"); // User Type layout
        dropDown = new JComboBox<>(model.getValues()); // drop down
        userSpecialLabel = new JLabel("Specialization"); // name
        newUserSpecial = new JComboBox<>(model.getSpecial()); // set length of text
        addAccButton = new JButton("Add Account "); // add acc button
        backButton = new JButton("Back "); // add acc button

        // create panel to put form elements
        jp = new JPanel(new GridLayout(10, 1, 1, 1));

        add(jp);
        add(jp, BorderLayout.CENTER); // set border to panel

        jp.add(empty);
        jp.add(empty);
        jp.add(empty);
        jp.add(empty);
        jp.add(instruction);
        jp.add(empty);

        jp2 = new JPanel(new FlowLayout());
        jp.add(jp2);
        jp2.add(userLabel); // set username label to panel
        jp2.add(newUserText); // set new username text field to panel

        jp3 = new JPanel(new FlowLayout());
        jp.add(jp3);
        jp3.add(passwordLabel); // set password label to panel
        jp3.add(newPasswordText); // set new password text field to panel

        jp4 = new JPanel(new FlowLayout());
        jp.add(jp4);
        jp4.add(userNameLabel); // name
        jp4.add(newAccNameText); // set new name text field to panel

        jp5 = new JPanel(new FlowLayout());
        jp.add(jp5);
        jp5.add(userSpecialLabel); // specialization
        jp5.add(newUserSpecial); // set new specialization text field to panel

        jp6 = new JPanel(new FlowLayout());
        jp.add(jp6);
        jp6.add(userTypeLabel); // user type
        jp6.add(dropDown); // drop down
        jp.add(empty);
        jp.add(empty);

        jp7 = new JPanel(new FlowLayout());
        jp.add(jp7);
        jp7.add(addAccButton); // set button to panel
        jp7.add(backButton);

        setVisible(true);
    }

    public String getNewUsername() {
        return newUserText.getText();
    }

    public String getNewPassword() {
        return newPasswordText.getText();
    }

    public String getNewAccName() {
        return newAccNameText.getText();
    }

    public String getNewSpecialization() {
        return (String) newUserSpecial.getSelectedItem();
    }

    public String getNewType() {
        return (String) dropDown.getSelectedItem();
    }

    public void setAddAccButtonActionListener(ActionListener listener) {
        addAccButton.addActionListener(listener);
    }

    public void setBackButtonActionListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearpage() {
        dispose();
    }
}

class CreateAccController {
    CreateAccModel model;
    CreateAccView view;
    private String username, password, role, account_name, specialization;
    private String newUserType, newUserValue, newPasswordValue, newNameValue, newSpecialValue;

    public CreateAccController(CreateAccModel model, CreateAccView view) {
        this.model = model;
        this.view = view;

        view.setBackButtonActionListener(new BackButtonActionListener());
        view.setAddAccButtonActionListener(new SubmitBtnActionListener());

        this.username = model.getUsername();
        this.password = model.getPassword();
        this.role = model.getRole();
        this.account_name = model.getAccountName();
        this.specialization = model.getSpecialization();
    }

    private class BackButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AdminPage userpage = new AdminPage(username, password, role, account_name, specialization); // pass data to
                                                                                                        // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.dispose();
        }
    }

    private class SubmitBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Get the selected item from the drop-down list
            newUserType = view.getNewType();
            newUserValue = view.getNewUsername(); // get user entered username from the textField1
            newPasswordValue = view.getNewPassword();
            newNameValue = view.getNewAccName();
            newSpecialValue = view.getNewSpecialization();

            try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
                String line;
                int valid = 1;
                try {
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(",");
                        String usermame = values[0];

                        if (usermame.equals(newUserValue)) {
                            valid = 0;

                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if (newUserValue.isEmpty() || newPasswordValue.isEmpty()) {
                    view.showErrorMessage("Please enter a value for the username and password");
                    return;
                }

                if (valid == 0) {
                    view.showErrorMessage("Username duplicated. Please enter another username");
                    // CreateAccPage.newUserText.setText("");
                    // return;
                } else { // valid == 1
                    String user = newUserValue + "," + newPasswordValue + "," + newUserType + "," + newNameValue + ","
                            + newSpecialValue;

                    JOptionPane.showMessageDialog(null, user);
                    // append data in users.csv
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));
                        writer.write(user);
                        writer.newLine();
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (HeadlessException | IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

class CreateAccPage {
    public CreateAccPage(String username, String password, String role, String account_name, String specialization) {
        CreateAccModel model = new CreateAccModel(username, password, role, account_name, specialization);
        CreateAccView view = new CreateAccView(model);
        new CreateAccController(model, view);

    }

    public void setVisible(boolean b) {
    }
}