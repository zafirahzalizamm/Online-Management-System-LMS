import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class PublishPage extends JFrame {
    String userValue, passwordValue, account_name, role, specialization, studentTOassign;
    String[] studentList = { "Select Student" };
    List<String> studentHasProject = new ArrayList<String>();
    List<String[]> projectList = new ArrayList<>();
    JComboBox<String> Lstudent = new JComboBox<>();

    JLabel project_name = new JLabel(" Project?? ");

    private class BmenuBtnActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

            LecturerPage userpage = new LecturerPage(userValue, passwordValue, role, account_name, specialization); // pass
                                                                                                                    // data
                                                                                                                    // to
                                                                                                                    // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            dispose();
        }
    }

    
    // This constructor creates a new PublishPage object with the specified userValue, passwordValue, role, account_name, and specialization.
    // It sets the default close operation, title, size, and location of the window.
    // It also creates a JPanel called frame and sets its layout.
    // Additionally, it creates a labelPanel and sets its layout.
    // It adds two JLabels to the labelPanel with specific text and adds the labelPanel to the top of the frame.
    // It then creates a listPanel and sets its layout.
    // It reads a file called "projects.csv" and adds the projects of the current lecturer to the projectList.
    // It also adds students who have a project to the studentHasProject list.
    public PublishPage(String userValue, String passwordValue, String role, String account_name,
            String specialization) {
        this.userValue = userValue;
        this.passwordValue = passwordValue;
        this.account_name = account_name;

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Publish to Student");
        setSize(1100, 500);
        setLocationRelativeTo(null);

        JPanel frame = new JPanel();
        add(frame);

        frame.setLayout(new BorderLayout());

        // create the panel for the labels and add it to the top of the frame
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2, 2));

        JLabel label1 = new JLabel("All project of Lecturer " + account_name);
        labelPanel.add(label1);

        JLabel label2 = new JLabel("Please choose project to publish :");
        labelPanel.add(label2);

        frame.add(labelPanel, BorderLayout.PAGE_START);

        // create the panel for the lists and add it to the center of the frame
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 1));

        try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
            String line;

            // Read each line of the file
            while ((line = br.readLine()) != null) {
                // Split the line on the comma character (//,) to get an array of values
                String[] values = line.split("//,");

                // choose current lecturer only
                if ((values[4].toString()).equals(account_name.toString()) && !(values[2].toString()).equals("-")) {
                    // Add the array of values to the list
                    projectList.add(values);
                }
                if (!values[6].toString().equals("-")) {
                    studentHasProject.add(values[6].toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Choose element
        String[] publishList = projectList.stream().map(a -> (a[1]) + " --- " + a[2] + " --- " + a[6] + " --- " + a[7]).toArray(String[]::new);

        JList<String> list1 = new JList<>(publishList);

        // add a selection listener to the list
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // get the selected value from the list
                String selectedValue = list1.getSelectedValue();

                // update the label's text
                project_name.setText(selectedValue);
            }
        });

        JScrollPane scrollPane = new JScrollPane(list1);
        listPanel.add(scrollPane);
        frame.add(listPanel, BorderLayout.CENTER);

        // set the size of the frame and panel
        frame.setSize(600, 400);
        listPanel.setPreferredSize(new Dimension(400, 300));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel choose_project = new JLabel("Choose project :  ");
        buttonPanel.add(choose_project);

        // add border
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

        project_name.setBorder(border);
        project_name.setPreferredSize(new Dimension(200, 20));
        buttonPanel.add(project_name);

        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;

            // Read each line of the file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String[] first_four_values = Arrays.copyOfRange(values, 0, 5); // get the first three data
                String roleA = first_four_values[2];
                String name = first_four_values[3];
                String studentspec = first_four_values[4];

                if (roleA.equals("student") && !studentHasProject.contains(name)) {
                    ArrayList<String> student = new ArrayList<>(Arrays.asList(studentList));
                    student.add(name + " --- " + studentspec);
                    studentList = student.toArray(new String[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JComboBox<String> Lstudent = new JComboBox<>(studentList);
        // default display
        Lstudent.setSelectedItem("Select Student");
        buttonPanel.add(Lstudent);

        JButton Bassign = new JButton("Assign");
        Bassign.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(Bassign);

    // This is an ActionListener for the Bassign button.
    // When the button is clicked, it gets the selected student from Lstudent and the selected project from project_name.
    // It then checks if a student and project have been selected, and if not, displays an error message.
    // If both a student and project have been selected, it checks if the student is specialized for that project.
    // If not, it displays an error message.
    // If the student is specialized for the project, it reads the "projects.csv" file, assigns the project to the student,
    // writes the changes back to the file, displays a success message, and then closes and re-opens the PublishPage.

        Bassign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                studentTOassign = (String) Lstudent.getSelectedItem();

                String[] split = project_name.getText().split(" --- ");
                String[] split2 = studentTOassign.split(" --- ");

                if ((studentTOassign.equals("Select Student")) || (split[0].equals(" Project?? "))) {
                    JOptionPane.showMessageDialog(null, "Please choose project and student", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!split[1].equals(split2[1])) {

                        JOptionPane.showMessageDialog(null, "Student are not specialized for that project", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    else {
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
                                if (values[1].equals(split[0])) {
                                    values[6] = split2[0];
                                    JOptionPane.showMessageDialog(null,
                                            "Project " + split[0] + " has assign to " + split2[0]);
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

                        dispose();
                        PublishPage userpage = new PublishPage(userValue, passwordValue, role, account_name,
                                specialization); // pass data to Userpage.java
                        // make page visible to the user
                        userpage.setVisible(true);
                    }
                }
            }
        });
        
        JButton Bunassign = new JButton("Un-assign");
        Bunassign.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(Bunassign);

    // This is an ActionListener for the Bunassign button.
    // When the button is clicked, it gets the selected project from project_name.
    // It then checks if a project has been selected, and if not, displays an error message.
    // If a project has been selected, it checks if the project is already not assigned.
    // If it is not assigned, it displays an error message.
    // If it is assigned, it reads the "projects.csv" file, unassigns the project to the student,
    // writes the changes back to the file, displays a success message, and then closes and re-opens the PublishPage.

        Bunassign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentTOassign = (String) Lstudent.getSelectedItem();

                String[] split = project_name.getText().split(" --- ");

                if ((split[0].equals(" Project?? "))) {
                    JOptionPane.showMessageDialog(null, "Please choose project", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if ((split[1].equals("-"))) {
                    JOptionPane.showMessageDialog(null, "Project " + split[0] + " already not assign", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {

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

                            if (values[1].equals(split[0])) {
                                values[6] = "-";
                                JOptionPane.showMessageDialog(null,
                                        "Project " + split[0] + " has no assign student");
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

                    dispose();
                    PublishPage userpage = new PublishPage(userValue, passwordValue, role, account_name,
                            specialization); // pass data to Userpage.java
                    // make page visible to the user
                    userpage.setVisible(true);
                }
            }
        });

        JButton Bpublish = new JButton("Publish/Un-publish");
        Bpublish.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(Bpublish);
        
    // This is an ActionListener for the Bpublish button.
    // When the button is clicked, it gets the selected project from project_name.
    // It then checks if a project has been selected, and if not, displays an error message.
    // If a project has been selected, it checks if the project is already active or not.
    // If it is not active, it checks if the project is assigned to a student.
    // If it is not assigned, it displays an error message.
    // If it is assigned, it reads the "projects.csv" file, makes the project active,
    // writes the changes back to the file, displays a success message, and then closes and re-opens the PublishPage.
    // If it is active, it reads the "projects.csv" file, makes the project not active,
    // writes the changes back to the file, displays a success message, and then closes and re-opens the PublishPage.
        Bpublish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                studentTOassign = (String) Lstudent.getSelectedItem();

                String[] split = project_name.getText().split(" --- ");

                if ((split[0].equals(" Project?? "))) {
                    JOptionPane.showMessageDialog(null, "Please choose project", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if ((split[3].equals("Not active"))) {
                    
                    if (split[2].equals("-")) {
                        JOptionPane.showMessageDialog(null, "Please assign project to student first", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } 
                    else {
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
                                if (values[1].equals(split[0])) {
                                    values[7] = "Active";
                                    JOptionPane.showMessageDialog(null,
                                            "Project " + split[0] + " has been Publish");
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

                        dispose();
                        PublishPage userpage = new PublishPage(userValue, passwordValue, role, account_name,
                                specialization); // pass data to Userpage.java
                        // make page visible to the user
                        userpage.setVisible(true);
                    }
                } else {

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
                            if (values[1].equals(split[0])) {
                                values[7] = "Not active";
                                JOptionPane.showMessageDialog(null,
                                        "Project " + split[0] + " has become Un-publish");
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

                    dispose();
                    PublishPage userpage = new PublishPage(userValue, passwordValue, role, account_name,
                            specialization); // pass data to Userpage.java
                    // make page visible to the user
                    userpage.setVisible(true);
                }
            }
        });


        JButton Bmenu = new JButton("Main Menu");
        Bmenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(Bmenu);
        Bmenu.addActionListener(new BmenuBtnActionListener());
        frame.add(buttonPanel, BorderLayout.PAGE_END);

        // show the frame
        frame.setVisible(true);

    }

}
