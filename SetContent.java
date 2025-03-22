//import required classes and packages  
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 

/**
 * 
 * ContentModel is a class that represents the data model for a SetContent.
 * 
 * It contains fields for username, password, role, account name, and
 * specialization
 * 
 * and methods to access these fields.
 */
class ContentModel{
    
    private String account_name;
    private String[] specializationList = { "Select Specialization", "Software Engineering", "Game Development", "Data Science", "Cyber Security", "Information System" };
    private List<String[]> projectList = new ArrayList<>();
    private static final String PROJECTS_CSV_FILE = "projects.csv";

    /**
     * 
     * Constructor for creating a new instance of LecturerModel
     * 
     * @param account_name   the account name of the lecturer
     * @param specialization the specialization of the lecturer
     * @param projectList    the list of the project
     */
    public ContentModel(String account_name){
        this.account_name = account_name;
        readUsersFromCsv(account_name, projectList);
    }

    /**
     * 
     * @return the username of the lecturer
     */
    public String getAccountName() {
        return account_name;
    }

    /**
     * 
     * @return the specialization of the lecturer
     */
    public String[] getSpecializationList(){
        return specializationList;
    }
    
    /**
     * 
     * @return the list of the project
     */
    public List<String[]> getProjectList(){
        return projectList;
    }

    // method that reads the list of users from the csv file
    private List<String[]> readUsersFromCsv(String account_name, List<String[]> projectList) {
        try (BufferedReader br = new BufferedReader(new FileReader(PROJECTS_CSV_FILE))) {
            String line;
    
            // Read each line of the file
            while ((line = br.readLine()) != null) {
                // Split the line on the comma character (,) to get an array of values
                String[] values = line.split("//,");
    
                // choose current lecturer only 
                if ((values[4].toString()).equals(account_name) && ((values[3].toString()).equals("-"))) {
                    // Add the array of values to the list
                    projectList.add(values);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projectList;
    }
}

/**
 * 
 * ContentView is a class that represents the user interface for a SetContent.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
class ContentView extends JFrame{
    
    private ContentModel model;

    private JTextArea textArea;
    private JComboBox<String> specialComboBox;
    private JButton saveButton, menuButton;
    private JScrollPane scrollPane1, scrollPane2;
    private JPanel labelPanel, buttonPanel, frame, listPanel;
    private JLabel label1, label2, choose_project, project_name;
    private JList<String> list1;
    private Border border;
    private String account_name;
    private String[] uploadList;
    private List<String[]> projectList;

    /**
     * 
     * Constructor for creating a new instance of ContentView
     * 
     * @param model the data model for the SetContent
     */
    public ContentView(ContentModel model){

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Upload Content");
        setSize(900, 500);
        setLocationRelativeTo(null);

        this.model = model;
        account_name = model.getAccountName();
        projectList = model.getProjectList();

        // Choose element
        uploadList = projectList.stream().map(a -> (a[1])).toArray(String[]::new);

        frame = new JPanel();
        add(frame);

        frame.setLayout(new BorderLayout());

        // create the panel for the labels and add it to the top of the frame
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(2, 2));

        label1 = new JLabel("All project of Lecturer " + account_name);
        labelPanel.add(label1);

        label2 = new JLabel("Please choose project :");
        labelPanel.add(label2);

        frame.add(labelPanel, BorderLayout.PAGE_START);

        // create the panel for the lists and add it to the center of the frame
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 1));

        

        list1 = new JList<>(uploadList);

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

        scrollPane2 = new JScrollPane(list1);
        listPanel.add(scrollPane2);
        frame.add(listPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        choose_project = new JLabel("Choose project :  ");
        buttonPanel.add(choose_project);

        // set the size of the frame and panel
        frame.setSize(600, 400);
        listPanel.setPreferredSize(new Dimension(400, 300));

        textArea = new JTextArea(5, 20); // create a new text area with 5 rows and 20 columns

        textArea.setLineWrap(true); // enable line wrapping
        textArea.setWrapStyleWord(true); // wrap by word instead of by character
        textArea.setEditable(true); // set the text area to be editable
        textArea.setText(" --- Set Content Here --- ");

        scrollPane1 = new JScrollPane(textArea);

        add(scrollPane1, BorderLayout.SOUTH);

        specialComboBox = new JComboBox<>(model.getSpecializationList());
        // default display
        specialComboBox.setSelectedItem("Select");
        buttonPanel.add(specialComboBox);


        // add border
        border = BorderFactory.createLineBorder(Color.BLACK, 1);
        project_name = new JLabel(" Projects");
        project_name.setBorder(border);
        project_name.setPreferredSize(new Dimension(200, 20));
        buttonPanel.add(project_name);

        saveButton = new JButton("Save");
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(saveButton);

        menuButton = new JButton("Main Menu");
        menuButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(menuButton); 

        frame.add(buttonPanel, BorderLayout.PAGE_END);

        // show the frame
        setVisible(true);
    }

    
    public String getContent(){
        return textArea.getText();
    }

    public String getSpecialValue(){
        return (String)specialComboBox.getSelectedItem();
    }

    public String[] imnotsure() {
        return project_name.getText().split(" --- ");
    }
    
    public void clearPage(){
        dispose();
    }
    
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void displaySuccessMessage(String successMessage) {
        JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method for setting an action listener for the save button
     * 
     * @param listener the action listener to be set
     */
    public void setSaveButtonActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    /**
     * Method for setting an action listener for the log out button
     * 
     * @param listener the action listener to be set
     */
    public void setMenuButtonActionListener(ActionListener listener) {
        menuButton.addActionListener(listener);
    }
}

/**
 * 
 * ContentController is a class that acts as a link between the model and the
 * view.
 * 
 * It receives user input from the view and updates the model accordingly.
 * 
 * It also sets action listeners for the buttons in the view to respond to user
 * actions.
 */
class ContentController{
    ContentModel model;
    ContentView view;

    private String username, password, role, account_name, specialization;

    /**
     * 
     * Constructor for creating a new instance of ContentController
     * 
     * @param model the data model for the lecturer
     * 
     * @param view  the user interface for the lecturer
     */
    public ContentController(ContentModel model, ContentView view){
        this.model = model;
        this.view = view;

        this.account_name = model.getAccountName();

        view.setSaveButtonActionListener(new saveButtonActionListener());
        view.setMenuButtonActionListener(new menuButtonActionListener());
    }

    private class menuButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

            LecturerPage userpage = new LecturerPage(username, password, role, account_name, specialization); // pass
                                                                                                                    // data
                                                                                                                    // to
                                                                                                                    // Userpage.java
            // make page visible to the user
            userpage.setVisible(true);
            view.dispose();
        }
    }
    private class saveButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // get the selected item from the specialBox
            String specialAssign = view.getSpecialValue();
            // get the text from the textArea
            String content = view.getContent();

            // split the text from the project_name by " --- " and assign to variable split
            String[] split = view.imnotsure();
            System.out.println(specialAssign);
            System.out.println(split[0]);

            // check if specialAssign, split[0], or content is empty and display error message if true
            if ((specialAssign.equals("Select Specialization")) || (split[0].equals(" Projects")) || content.equals("")) {
                JOptionPane.showMessageDialog(null, "Please choose project and specialization. Don't leave empty content", "Error",
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
                            values[2] = specialAssign;
                            values[3] = content;
                            JOptionPane.showMessageDialog(null,
                                    "Project " + split[0] + " has assign to " + specialAssign);
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
                view.clearPage();
                setContent userpage = new setContent(username, password, role, account_name, specialization); // pass data to Userpage.java
                // make page visible to the user
                userpage.setVisible(true);
            }
        }
    }
}

//create NewPage class to create a new page on which user will navigate  
class setContent{  
    public setContent(String userValue, String passwordValue, String role, String account_name, String specialization){

        ContentModel model = new ContentModel(account_name);
        ContentView view = new ContentView(model);
        new ContentController(model, view);
    }

    public void setVisible(boolean b) {
    }
}