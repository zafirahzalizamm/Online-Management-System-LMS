//Author Luqman

//import required classes and packages  
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This code defines a class named "AddProjectModel" which contains fields for storing user-related information 
 * such as userValue, passwordValue, role, account_name, specialization, and projectidValue. 
 * It also contains an ArrayList field named "lecturers_list" to store the list of lecturers.
 * 
 * The class has a constructor that takes in these values as parameters and assigns them to their corresponding fields.
 * It also calls two methods "readLecturersFromCsv" and "readProjectFromCsv" to read the list of lecturers and project IDs 
 * from the CSV files respectively.
 * 
 * The class has various getter methods to access the values of the fields and the ArrayList.
 * The "readProjectFromCsv" method reads the projects.csv file, it generates a random project ID, checks if it already exists in the file,
 * if not it assigns it to the projectidValue field.
 * The "readLecturersFromCsv" method reads the users.csv file and filters the lecturers by checking if the role of the user is equal to "lecturer"
 * then it adds the name of the user to the "lecturers_list"
 */

class AddProjectModel{

  String userValue, passwordValue, role, account_name, specialization, projectidValue;
  ArrayList<String> lecturers_list;

  public AddProjectModel(String userValue, String passwordValue, String role, String account_name, String specialization){
        this.userValue = userValue;
        this.passwordValue = passwordValue;
        this.role = role;
        this.account_name = account_name;
        this.specialization = specialization;

        readLecturersFromCsv();
        readProjectFromCsv();

  }
  public String getUsername() {
    return userValue;
}

public String getPassword() {
    return passwordValue;
}

public String getRole() {
    return role;
}

public String getName() {
    return account_name;
}

public String getSpecialization() {
    return specialization;
}

public String getProjectID(){
  return projectidValue;
}

public ArrayList<String> getLecturersList(){
  return lecturers_list;
}
      // method that reads the list of users from the csv file
      private String readProjectFromCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
          String line;
          while ((line = br.readLine()) != null) {
            String[] values = line.split("//,");
            
            int min = 1000;
            int max = 9999;
            int range = max - min + 1;
            int randomNum = (int)(Math.random() * range) + min;
            projectidValue = "P" + Integer.toString(randomNum);

            if (projectidValue == values[0]){
                continue;
            }
            if (values.length < 2) {
              System.out.println("Line " + line + " does not contain enough values, skipping...");
              continue;}
          }
        } 
        catch (IOException e) {
          e.printStackTrace();
        }
        return projectidValue;
    }
  
    // method that reads the list of users from the csv file
    private ArrayList<String> readLecturersFromCsv() {
      lecturers_list = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
          String[] values = line.split(",");
          
          if (values[2].equals("lecturer")){
              lecturers_list.add(values[3]);

          }
        }
      } 
      catch (IOException e) {
        e.printStackTrace();
      }
      return lecturers_list;
  }
}

class AddProjectView extends JFrame{
  AddProjectModel model;

  JLabel empty, projectidLabel, projectidLabel2, projectnameLabel, newlecturerLabel, projectcommentLabel;
  JButton addPrjBtn, bckBtn;
  JTextField projectnameText, projectcomment;
  JComboBox<String> dropDown; 
  JFrame frame;
  JComboBox<String>projectlecturerDD;

  public AddProjectView(AddProjectModel model){
    this.model = model;

    empty = new JLabel(" ");
    projectidLabel = new JLabel("Project ID"); 
    projectidLabel2 = new JLabel(model.getProjectID());         
        
    projectnameLabel = new JLabel("Project Name :");          
    projectnameText = new JTextField(15);

    newlecturerLabel = new JLabel("Lecturer In Charge");
    dropDown = new JComboBox<>();
    for (String item : model.getLecturersList()) {
      dropDown.addItem(item);
    }               
    projectcommentLabel = new JLabel("Project's Comment");
    projectcomment = new JTextField(20); 


    addPrjBtn = new JButton("Add Project");
    bckBtn = new JButton("Back");


    frame = new JFrame("Add Project");
    //create panel to put form elements  
    JPanel jp = new JPanel(new GridLayout(7, 1));
    JPanel jp2 = new JPanel(new FlowLayout());
    JPanel jp3 = new JPanel(new FlowLayout()); 
    JPanel jp4 = new JPanel(new FlowLayout()); 
    JPanel jp5 = new JPanel(new FlowLayout()); 
    JPanel jp6 = new JPanel(new FlowLayout()); 
    JPanel jp7 = new JPanel(new FlowLayout());
    JPanel jp8 = new JPanel(new FlowLayout()); 
    
    
    setTitle("MMU Online Management System");     //set title  
    frame.add(jp);                                      //set border to panel  
    
    jp2.add(projectidLabel);
    jp2.add(projectidLabel2);

    jp3.add(projectnameLabel);
    jp3.add(projectnameText);

    jp4.add(newlecturerLabel);
    jp4.add(dropDown);

    jp5.add(projectcommentLabel);
    jp8.add(projectcomment);

    jp6.add(addPrjBtn);
    jp7.add(bckBtn);

    jp.add(jp2);
    jp.add(jp3);
    jp.add(jp4);
    jp.add(jp5);
    jp.add(jp8);
    jp.add(jp6);
    jp.add(jp7);
    
    frame.setSize(700,400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public String getProjectName(){
    return projectnameText.getText();
  }

  public String getProjectComment(){
    return projectcomment.getText();
  }

  public String getDropdown(){
    return (String) dropDown.getSelectedItem();
  }

  public void setAddPrjBtnActionListener(ActionListener listener){
    addPrjBtn.addActionListener(listener);
  }
  public void setMenuButtonActionListener(ActionListener listener) {
    bckBtn.addActionListener(listener);
  }
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    frame.dispose();
  }

  public void displaySuccessMessage(String successMessage) {
    JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    frame.dispose();
  }
  public void refresh(){
    frame.removeAll();
    frame.revalidate();
    frame.repaint();        
  }
  public void clearPage(){
    dispose();
  }
  public void frameClearPage(){
    dispose();
  }
}
class AddProjectController{

  AddProjectView view;
  AddProjectModel model;

  String userValue, passwordValue, role, account_name, specialization;
  String projectidValue, projectnameValue, newlecturerValue, projectcommentValue;

  public AddProjectController(AddProjectView view, AddProjectModel model){
    this.view = view;
    this.model = model;

    this.userValue = model.getUsername();
    this.passwordValue = model.getPassword();
    this.role = model.getRole();
    this.account_name = model.getName();
    this.specialization = model.getSpecialization();

    view.setAddPrjBtnActionListener(new AddPrjBtnActionListener());
    view.setMenuButtonActionListener(new bckBtnActionListener());

  }

  private class AddPrjBtnActionListener implements ActionListener{

    @Override
        public void actionPerformed(ActionEvent e) {

        newlecturerValue = view.getDropdown();
        projectnameValue = view.getProjectName(); 
        projectcommentValue = view.getProjectComment();

        if (projectnameValue.isEmpty()) {
          view.displayErrorMessage("Please enter Project Name");
          return;
        }

        String newprojectaddString = projectidValue + "//," + projectnameValue + "//," 
                                    + "-" + "//," + "-" + "//," + newlecturerValue 
                                    + "//,"+ projectcommentValue + "//," + "-"
                                    +"//,"+ "Not active";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("projects.csv", true));
            writer.write(newprojectaddString);
            writer.newLine();
            writer.close();
        } 
        catch (IOException ex) {
        ex.printStackTrace();
        }
        view.displaySuccessMessage("Project Added");
        AdminViewProject viewproject = new AdminViewProject(userValue,passwordValue,role,account_name,specialization);
        viewproject.setVisible(true);  
        view.clearPage();
        view.refresh();
        }
}
  // Set the ActionListener to navigate to the AdminPage
  private class bckBtnActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      view.frameClearPage();
      AdminViewProject viewproject = new AdminViewProject(userValue,passwordValue,role,account_name,specialization);
      viewproject.setVisible(true);  
      view.clearPage();
      view.refresh();
  }
  }
}


//create NewPage class to create a new page on which user will navigate  
class AdminAddProject2{ 

    public AdminAddProject2(String userValue, String passwordValue, String role, String account_name, String specialization) {
      AddProjectModel model = new AddProjectModel(userValue, passwordValue, role, account_name, specialization);
      AddProjectView view = new AddProjectView(model);
      new AddProjectController(view, model);
    }

    public void setVisible(boolean b) {
    }
  }

