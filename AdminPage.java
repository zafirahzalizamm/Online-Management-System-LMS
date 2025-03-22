// Author Luqman and Rafie
//import required classes and packages  
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;  

/**
 * 
 * AdminModel is a class that represents the data model for a lecturer.
 * 
 * It contains fields for username, password, role, account name, and
 * specialization
 * 
 * and methods to access these fields.
 */
class AdminModel{
    
    private String username;
    private String password;
    private String role;
    private String account_name;
    private String specialization;

    /**
     * 
     * @param username      the username of the admin
     * @param password      the password of the admin
     * @param role          the role of the admin
     * @param account_name  the account name of the admin
     * @param specialization the specialization of the admin
     */
    public AdminModel(String username, String password, String role, String account_name, String specialization){
        this.username = username;
        this.password = password;
        this.role = role;
        this.account_name = account_name;
        this.specialization = specialization;
    }
    /**
     * 
     * @return the username of the admin
     */
    public String getUsername(){
        return username;
    }

    /**
     * 
     * @return the password of the admin
     */
    public String getPassword(){
        return password;
    }

    /**
     * 
     * @return the role of the admin
     */
    public String getRole() {
        return role;
    }

    /**
     * 
     * @return the account name of the admin
     */
    public String getAccountName() {
        return account_name;
    }

    /**
     * 
     * @return the specialization of the admin
     */
    public String getSpecialization() {
        return specialization;
    }
}
/**
 * 
 * AdminView is a class that represents the user interface for a admin.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
class AdminView extends JFrame{
    AdminModel model;
    private JLabel label;
    private JButton createAccButton, projectButton, reportButton, outButton;
    private JPanel jp, jp2, jp3, jp4, jp5;

    /**
     * Constructor for creating a new instance of AdminView
     * 
     * @param model the data model for the admin
     */
    public AdminView(AdminModel model){
        this.model = model;
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);  
        setTitle("Admin Page");  
        setSize(300, 230);  
        setLocationRelativeTo(null);

        jp = new JPanel(new FlowLayout());
        jp2 = new JPanel(new FlowLayout());
        jp3 = new JPanel(new FlowLayout());
        jp4 = new JPanel(new FlowLayout());
        jp5 = new JPanel(new FlowLayout());
        label = new JLabel("   Hi admin, " + model.getAccountName() + "   ");       // label

        
        add(jp);
        jp.add(label);       //add label
        jp.add(jp2);
        jp.add(jp3);
        jp.add(jp4);
        jp.add(jp5);
    
        createAccButton = new JButton("Create New Account");
        jp2.add(createAccButton);
    
        projectButton = new JButton("View Projects Menu");
        jp3.add(projectButton);

        reportButton = new JButton("Generate Report");
        jp4.add(reportButton);

        outButton = new JButton("Log out");
        jp5.add(outButton);

        projectButton.setPreferredSize(createAccButton.getPreferredSize()); //set size button follow other button
        reportButton.setPreferredSize(createAccButton.getPreferredSize());  //set size button follow other button
        outButton.setPreferredSize(createAccButton.getPreferredSize()); // set size button follow other button

        setVisible(true);
    }
    
    /**
     * 
     * Method for setting an action listener for the create account button
     * 
     * @param listener the action listener to be set
     */
    public void setCreateAccButtonActionListener(ActionListener listener) {
        createAccButton.addActionListener(listener);
    }

    /**
     * 
     * Method for setting an action listener for the set project button
     * 
     * @param listener the action listener to be set
     */
    public void setProjectButtonActionListener(ActionListener listener) {
        projectButton.addActionListener(listener);
    }

    /**
     * Method for setting an action listener for the set report button
     * 
     * @param listener the action listener to be set
     */
    public void setReportButtonActionListenener(ActionListener listener) {
        reportButton.addActionListener(listener);
    }

    /**
     * 
     * Method for setting an action listener for the set menu button
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
 * AdminController is a class that acts as a link between the model and the
 * view.
 * 
 * It receives user input from the view and updates the model accordingly.
 * 
 * It also sets action listeners for the buttons in the view to respond to user
 * actions.
 */
class AdminController{
    private AdminView view;

    String username, password, role, account_name, specialization;

    /**
     * 
     * Constructor for creating a new instance of AdminController
     * 
     * @param model the data model for the admin
     * @param view the user interface for the admin
     */
    public AdminController(AdminModel model, AdminView view) {
        this.view = view;
        view.setCreateAccButtonActionListener(new CreateAccButtonActionListener());
        view.setProjectButtonActionListener(new ProjectButtonActionListener());
        view.setReportButtonActionListenener(new ReportButtonnActionListener());
        view.setMenuButtonActionListenener(new MenuBtnActionListener());

        this.username = model.getUsername();
        this.password = model.getPassword();
        this.role = model.getRole();
        this.account_name = model.getAccountName();
        this.specialization = model.getSpecialization();
    }

    private class CreateAccButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            CreateAccPage userpage = new CreateAccPage(username, password, role, account_name, specialization);  // pass data to Userpage.java
            //make page visible to the user  
            userpage.setVisible(true);  
            view.clearpage();
        }
    }

    private class ProjectButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            AdminViewProject userpage = new AdminViewProject(username, password, role, account_name, specialization);  // pass data to Userpage.java
            //make page visible to the user  
            userpage.setVisible(true);
            view.clearpage();
        }
    }

    private class ReportButtonnActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            ReportPage userpage;
            try {
                userpage = new ReportPage(username, password, role, account_name, specialization);
                userpage.setVisible(true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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

//create NewPage class to create a new page on which user will navigate  
class AdminPage{
    public AdminPage(String username, String password, String role, String account_name, String specialization){
        AdminModel model = new AdminModel(username, password, role, account_name, specialization);
        AdminView view = new AdminView(model);
        new AdminController(model, view);
    }
    public void setVisible(boolean b) {
    }
}