//Author Afif and Zafri

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

class ModifyContent extends JFrame {

  JButton logoutButton, saveButton;
  JLabel topLabel, IntroLabel;
  JPanel topPanel, SPpanel, button;
  JTextArea SPcontent;
  String userValue, passwordValue, role, account_name, specialization;

  /**
   * This code creates an inner class named "LogOutBtnActionListener" that implements the ActionListener interface.
   * When the actionPerformed method is triggered, it creates a new object of the "LecturerPage" class and pass data 
   * to it, the data passed are userValue, passwordValue, role, account_name, specialization. Then it makes the newly created 
   * page visible to the user and disposes of the current page.
   */
  private class LogOutBtnActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      LecturerPage userpage = new LecturerPage(userValue, passwordValue, role, account_name, specialization); // pass
                                                                                                              // data
                                                                                                              // to
                                                                                                              // Userpage.java
      // make page visible to the user
      userpage.setVisible(true);
      dispose();
    }
  }

  public ModifyContent(String userValue, String passwordValue, String role, String account_name, String specialization) {

    this.userValue = userValue;            //the username of the lecturer
    this.passwordValue = passwordValue;    //the password of the lecturer
    this.role = role;                      //the role of the lecturer
    this.account_name = account_name;      //the account_name of the lecturer
    this.specialization = specialization;  //the specialization of the lecturer

    //set the GUI of the page
    setTitle("Modify Project Content ");
    setSize(700, 550);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(3, 0, 1, 1));

    /**
     * This code creates an ArrayList object named "projectSP" and a JList object named "projectSPshow". 
     * It initializes the JList object with the contents of the ArrayList object by using the toArray method 
     * and passing in a new String array of size 0.
     */
    ArrayList<String> projectSP = new ArrayList<>();
    JList<String> projectSPshow = new JList<>(projectSP.toArray(new String[0]));

    /**
     * This code uses a try-with-resources statement to open a BufferedReader and read from a file named "projects.csv". 
     * For each line read, it splits the line by "//," and assigns the resulting array of strings to the variable "values". 
     * It then checks if the 5th element in the array is equal to a variable named "account_name" and if the 3rd element in the array is not equal to "-". 
     * If these conditions are met, it adds a string composed of the first element in the array, 
     * followed by " (" and the second element in the array, followed by ")" to a JList object named "projectSP". 
     * If an IOException is thrown, it is caught and the stack trace is printed.
     */
    try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split("//,");

        if (values[4].equals(account_name) && !values[2].equals("-")) {
          projectSP.add(values[0] + " (" + values[1] + ")");
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    /**
     * This code creates a ListSelectionListener object named "listenerSP" that, when triggered, 
     * reads from a file named "projects.csv" and searches for a line that matches a specified project ID.
     * The project ID is obtained by getting the selected value from a JList object named "projectSPshow" and 
     * taking the first 5 characters of the selected value. 
     * When the matching line is found, the code sets the text of a JTextField object named "SPcontent" to the 4th value of the array.
     * If an IOException is thrown, it is caught and the stack trace is printed.
     */
    ListSelectionListener listenerSP = new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {

            String line;
            String takePrjID = projectSPshow.getSelectedValue().substring(0, 5);
            while ((line = br.readLine()) != null) {
              String[] values = line.split("//,");

              if (takePrjID.equals(values[0])) {
                SPcontent.setText(values[3]);
              }
            }
            br.close();
          } catch (IOException d) {
            d.printStackTrace();
          }
        }
      }

    };
    projectSPshow.addListSelectionListener(listenerSP);

    /**
     * This code adds an action listener to a "saveButton" that, when clicked, 
     * reads a CSV file named "projects.csv" and stores each line as an array of strings in a list. 
     * The code then loops through each array and checks if the first value in the array matches 
     * a specified value (projectSPshow.getSelectedValue().substring(0, 5)). 
     * If a match is found, the third value in the array is updated to the text in a text field named "SPcontent". 
     * The modified list of arrays is then written back to the "projects.csv" file. 
     * A message dialog is then displayed to confirm that the content of the project has been updated.
     */
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          // read the CSV file
          BufferedReader reader = new BufferedReader(new FileReader("projects.csv"));
          List<String[]> lines = new ArrayList<>();
          String line;
          while ((line = reader.readLine()) != null) {
            String[] values = line.split("//,");
            lines.add(values);
          }
          reader.close();

          // edit the specified cell
          for (String[] values : lines) {

            if (values[0].equals(projectSPshow.getSelectedValue().substring(0, 5))) {
              values[3] = SPcontent.getText();
              break;
            }
          }
          // write the changes back to the file
          BufferedWriter writer = new BufferedWriter(new FileWriter("projects.csv"));
          for (String[] values : lines) {
            writer.write(String.join("//,", values));
            writer.newLine();
          }
          writer.close();
        } catch (Exception f) {
          f.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Content of Project " + projectSPshow.getSelectedValue().substring(0, 5) + " has been update");
      }
    });


    //Set the buttons of the page
    saveButton = new JButton("Save");
    saveButton.setSize(50, 50);

    logoutButton = new JButton("Main Menu");
    logoutButton.setSize(50, 50);
    logoutButton.addActionListener(new LogOutBtnActionListener());

    button = new JPanel();
    button.add(saveButton);
    button.add(logoutButton);
    add(button);

    //Set the labels of the page
    IntroLabel = new JLabel("Hi Lecturer, " + account_name);
    topLabel = new JLabel("List Projects :");

    //Set the panels of the page
    topPanel = new JPanel(new GridLayout(2, 0));
    topPanel.add(IntroLabel);
    topPanel.add(topLabel);
    add(topPanel);

    SPpanel = new JPanel(new GridLayout(0, 2));
    SPcontent = new JTextArea();
    SPcontent.setEditable(true);
    SPpanel.add(new JScrollPane(projectSPshow));
    SPpanel.add(SPcontent);
    add(SPpanel);

    setLocationRelativeTo(null);
    setVisible(true);
  }
}
