import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * LecturerModel is a class that represents the data model for a lecturer.
 * 
 * It contains fields for username, password, role, account name, and
 * specialization
 * 
 * and methods to access these fields.
 */
class LecturerModel {

    private String username;
    private String password;
    private String role;
    private String account_name;
    private String specialization;

    /**
     * 
     * Constructor for creating a new instance of LecturerModel
     * 
     * @param username       the username of the lecturer
     * @param password       the password of the lecturer
     * @param role           the role of the lecturer
     * @param account_name   the account name of the lecturer
     * @param specialization the specialization of the lecturer
     */
    public LecturerModel(String username, String password, String role, String account_name, String specialization) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.account_name = account_name;
        this.specialization = specialization;
    }

    /**
     * 
     * @return the username of the lecturer
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @return the password of the lecturer
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return the role of the lecturer
     */
    public String getRole() {
        return role;
    }

    /**
     * 
     * @return the account name of the lecturer
     */
    public String getAccountName() {
        return account_name;
    }

    /**
     * 
     * @return the specialization of the lecturer
     */
    public String getSpecialization() {
        return specialization;
    }
}

/**
 * 
 * LecturerView is a class that represents the user interface for a lecturer.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
class LecturerView extends JFrame {
    LecturerModel model;
    private JLabel label;
    private JButton uploadButton, publishButton, modifyButton, outButton;
    private JPanel jp, jp2, jp3, jp4, jp5;

    /**
     * 
     * Constructor for creating a new instance of LecturerView
     * 
     * @param model the data model for the lecturer
     */
    public LecturerView(LecturerModel model) {
        this.model = model;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lecturer Page");
        setSize(400, 230);
        setLocationRelativeTo(null);

        jp = new JPanel(new FlowLayout());
        jp2 = new JPanel(new FlowLayout());
        jp3 = new JPanel(new FlowLayout());
        jp4 = new JPanel(new FlowLayout());
        jp5 = new JPanel(new FlowLayout());
        label = new JLabel(" Hi lecturer, " + model.getAccountName() + " "); // label

        add(jp);
        jp.add(label); // add label
        jp.add(jp2);
        jp.add(jp3);
        jp.add(jp4);
        jp.add(jp5);

        uploadButton = new JButton("Set specialization and content of Project");
        jp2.add(uploadButton);

        publishButton = new JButton("Assign/Publish Project");
        jp3.add(publishButton);

        modifyButton = new JButton("Modify project content");
        jp4.add(modifyButton);

        outButton = new JButton("Log out");
        jp5.add(outButton);

        publishButton.setPreferredSize(uploadButton.getPreferredSize()); // set size button follow other button
        modifyButton.setPreferredSize(uploadButton.getPreferredSize()); // set size button follow other button
        outButton.setPreferredSize(uploadButton.getPreferredSize()); // set size button follow other button

        setVisible(true);
    }

    /**
     * Method for setting an action listener for the upload button
     * 
     * @param listener the action listener to be set
     */
    public void setUploadButtonActionListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }

    /**
     * Method for setting an action listener for the publish button
     * 
     * @param listener the action listener to be set
     */
    public void setPublishButtonActionListener(ActionListener listener) {
        publishButton.addActionListener(listener);
    }

    /**
     * Method for setting an action listener for the modify button
     * 
     * @param listener the action listener to be set
     */
    public void setModifyButtonActionListenener(ActionListener listener) {
        modifyButton.addActionListener(listener);
    }

    /**
     * Method for setting an action listener for the log out button
     * 
     * @param listener the action listener to be set
     */
    public void setMenuButtonActionListenener(ActionListener listener) {
        outButton.addActionListener(listener);
    }

    /**
     * Method for clearing the page
     */
    public void clearpage() {
        dispose();
    }
}

/**
 * 
 * LecturerController is a class that acts as a link between the model and the
 * view.
 * 
 * It receives user input from the view and updates the model accordingly.
 * 
 * It also sets action listeners for the buttons in the view to respond to user
 * actions.
 */
class LecturerController {
    private LecturerView view;

    String username, password, role, account_name, specialization;

    /**
     * 
     * Constructor for creating a new instance of LecturerController
     * 
     * @param model the data model for the lecturer
     * 
     * @param view  the user interface for the lecturer
     */
    public LecturerController(LecturerModel model, LecturerView view) {
        this.view = view;
        view.setUploadButtonActionListener(new UploadBtnActionListener());
        view.setPublishButtonActionListener(new PublishBtnActionListener());
        view.setModifyButtonActionListenener(new ModifyBtnActionListener());
        view.setMenuButtonActionListenener(new MenuBtnActionListener());

        this.username = model.getUsername();
        this.password = model.getPassword();
        this.role = model.getRole();
        this.account_name = model.getAccountName();
        this.specialization = model.getSpecialization();
    }

    private class UploadBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setContent userpage = new setContent(username, password, role, account_name, specialization); // pass
                                                                                                          // data
                                                                                                          // to
                                                                                                          // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.clearpage();
        }
    }

    private class PublishBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PublishPage userpage = new PublishPage(username, password, role, account_name, specialization); // pass data
                                                                                                            // to
                                                                                                            // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.clearpage();
        }
    }

    private class ModifyBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ModifyContent userpage = new ModifyContent(username, password, role, account_name, specialization); // pass
                                                                                                                // data
                                                                                                                // to
                                                                                                                // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.clearpage();
        }
    }

    private class MenuBtnActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            realLogin userpage = new realLogin(); // pass
                                                  // data
                                                  // to
                                                  // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.clearpage();
        }

    }
}

class LecturerPage {
    public LecturerPage(String username, String password, String role, String account_name, String specialization) {
        LecturerModel model = new LecturerModel(username, password, role, account_name, specialization);
        LecturerView view = new LecturerView(model);
        new LecturerController(model, view);
    }

    public void setVisible(boolean b) {
    }
}