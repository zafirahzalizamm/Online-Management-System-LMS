//Author Zafirah
//import required classes and packages 
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;  

/**
 * 
 * StudentPage is a class that represents the user interface for a student.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
class StudentPage extends JFrame {
    
    JButton logoutButton;

    JLabel topLabel;
    JLabel bottomLabel;
    JLabel IntroLabel;

    JPanel SPpanel;
    JPanel STpanel;
    JPanel button;

    JTextArea SPcontent;
    JTextArea STcontent;

    String userValue;
    String passwordValue;
    String role;
    String account_name;
    String specialization;

    /**
     * Method for setting an action listener for the logout button
     */
    private class LogOutBtnActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            realLogin logoutpage = new realLogin();  // pass data to Userpage.java
            //make page visible to the user  
            logoutpage.setVisible(true);  
            dispose();
        }

    }

    /**
     * 
     * Constructor for creating a new instance of StudentPage
     * 
     * @param userValue       the username of the student
     * @param passwordValue   the password of the student
     * @param role            the role of the student
     * @param account_name    the account name of the student
     * @param specialization  the specialization of the student
     */
    public StudentPage(String userValue, String passwordValue, String role, String account_name, String specialization) {
        
        this.userValue = userValue;
        this.passwordValue = passwordValue;
        this.role = role;
        this.account_name = account_name;
        this.specialization =specialization;

        setTitle("Student Page");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6,0,1,1));

        ArrayList<String> projectSP = new ArrayList<>();
        ArrayList<String> projectST = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
          String line;
          while ((line = br.readLine()) != null) {
            String[] values = line.split("//,");
            
            if (values[2].equals(specialization)){// && values[7].equals("Active")) {
                projectSP.add(values[0] + " (" + values[1]+ ")");
            }

            if (values[6].equals(account_name) && values[7].equals("Active")){
                projectST.add(values[0] + " (" + values[1]+ ")");
            }
        }
        }
        catch (IOException e) {
          e.printStackTrace();
        }


        // Initialize list view and buttons
        JList<String> projectSPshow = new JList<>(projectSP.toArray(new String[0]));
        JList<String> projectSTshow = new JList<>(projectST.toArray(new String[0]));

        ListSelectionListener listenerST = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
                      
                      String line;
                      String takePrjID = projectSTshow.getSelectedValue().substring(0, 5);
                      while ((line = br.readLine()) != null) {
                        String[] values = line.split("//,");
                        if(takePrjID.equals(values[0])) {
                          STcontent.setText(values[3]);        
                      }
                      }
                      br.close();
                    } catch (IOException d) {
                      d.printStackTrace();
                    }
                }
            }
            
    
        };

        ListSelectionListener listenerSP = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
                      
                      String line;
                      String takePrjID = projectSPshow.getSelectedValue().substring(0, 5);
                      while ((line = br.readLine()) != null) {
                        String[] values = line.split("//,");
                        if(takePrjID.equals(values[0])) {
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
        projectSTshow.addListSelectionListener(listenerST);

        logoutButton = new JButton("Logout");
        logoutButton.setSize(50,50);
        logoutButton.addActionListener(new LogOutBtnActionListener());
        
        button = new JPanel();
        button.add(logoutButton);
        
        IntroLabel = new JLabel("Hi, " + account_name +" !");
        topLabel = new JLabel("List Projects For " + specialization);
        bottomLabel = new JLabel("Project Assigned");

        
        add(IntroLabel);
        add(topLabel);
        
        SPpanel = new JPanel(new GridLayout(0,2));
        SPcontent = new JTextArea();
        SPcontent.setEditable(false);
        SPpanel.add(new JScrollPane(projectSPshow));
        SPpanel.add(SPcontent);
        add(SPpanel);
        
        add(bottomLabel);
        
        STpanel = new JPanel(new GridLayout(0,2));
        STcontent = new JTextArea();
        STcontent.setEditable(false);
        STpanel.add(new JScrollPane(projectSTshow));
        STpanel.add(STcontent);
        
        add(STpanel);

        
        add(button);
        
        setSize(700,550);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

