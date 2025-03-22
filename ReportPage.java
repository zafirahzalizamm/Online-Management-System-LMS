// Author Luqman and Rafie
//import required classes and packages
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * ReportPage is a class that represents the user interface for a administrator.
 * 
 * It displays a window with labels and buttons for the user to interact with.
 * 
 * It also has methods to set action listeners for the buttons to respond to
 * user actions.
 */
public class ReportPage extends JFrame{

    JPanel frame,topPanel;
    JLabel label1,label2,label3,label4,Lblank;
    JComboBox<String> dropdown;
    JScrollPane scrollPane;
    //int total=3;

    /**
     * Method for setting an action listener for the comboboxaction
     * 
     * Show the list of the report that can generated
     * 
     */
    private class ComboBoxActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String selectedValue = (String) dropdown.getSelectedItem();

            if (selectedValue.equals("")){
                refresh();
                frame.setVisible(false);
            }
            else if (selectedValue.equals("Projects entered into the system")) {
                refresh();
                show_project();
            }
            else if (selectedValue.equals("Projects according to specialisation")) {
                refresh();
                show_project_specialization();
            }
            else if (selectedValue.equals("Projects according to lecturers")){
                refresh();
                show_project_lecturer();
            }
            else if (selectedValue.equals("Inactive projects")){
                refresh();
                show_project_inactive();
            }
            else if (selectedValue.equals("Active projects")){
                refresh();
                show_project_active();
            }
            else if (selectedValue.equals("Projects assigned to students")){
                refresh();
                show_project_assigned();
            }
            else if (selectedValue.equals("Project un-assign to students")){
                refresh();
                show_project_unassigned();
            }
            else if (selectedValue.equals("Projects with comments")){
                refresh();
                show_project_comment();
            }
        }

    }

    /**
     * method to clear the screen
     */
    public void refresh(){
        frame.removeAll();
        frame.revalidate();
        frame.repaint();        
    }

    /**
     * method to show the list of the project and project name
     * 
     * Retrieve the data from the projects.csv file
     */
    public void show_project(){
        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects","Project Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // read csv
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);
                // add data to table
                if(projects.length>0 && !line.equals(""))
                    model.addRow(new Object[]{projects[0],projects[1]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);

    }

    /**
     * Method to show the list of the project and its specialization
     */
    public void show_project_specialization(){
        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Specialization"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // read csv
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);
                // add data to table
                if(projects.length>0 && !line.equals(""))
                    model.addRow(new Object[]{projects[0],projects[1], projects[2]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);

    }

    /**
     * Method to show the list of the project and its lecturer
     */
    public void show_project_lecturer(){
        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Lecturer Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // read csv
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                // add data to table
                if(projects.length>0 && !line.equals(""))
                    model.addRow(new Object[]{projects[0],projects[1],projects[4]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);

    }

    /**
     * Method to show the list of the inactive project
     */
    public void show_project_inactive(){
        
        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,"; 
        // read csv
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                if(projects.length>7 && !line.equals("")) {
                    if (projects[7].equals("Not active")) {
                        // add data to table
                        model.addRow(new Object[]{projects[0],projects[1],projects[7]});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path and try again.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the CSV file. Please check the file format and try again.");
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);
    }

    /**
     * Method to show the list of the active project
     */
    public void show_project_active(){
        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,"; 
        // read csv
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                if(projects.length>7 && !line.equals("")) {
                    if (projects[7].equals("Active")) {
                        // add data to table
                        model.addRow(new Object[]{projects[0],projects[1],projects[7]});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path and try again.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the CSV file. Please check the file format and try again.");
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);
    }

    /**
     * Method to show the list of the assigned project with the student name
     */
    public void show_project_assigned(){
        // create pane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Student assigned"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,"; 
        // read csv
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                if(projects.length>2 && !line.equals("-")) {
                    if (!projects[6].equals("-")) {
                        // add data to table
                        model.addRow(new Object[]{projects[0],projects[1],projects[6]});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path and try again.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the CSV file. Please check the file format and try again.");
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);
    }
        
    /**
     * Method to show the list of the unassigned project
     */
    public void show_project_unassigned(){
        // create pane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,"; 
        // read csv
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                if(projects.length>2 && !line.equals("-")) {
                    if (projects[6].equals("-")) {
                        // add data to table
                        model.addRow(new Object[]{projects[0],projects[1],"unassigned"});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path and try again.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the CSV file. Please check the file format and try again.");
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);
    }

    /**
     * Method to show the list of the project and the comment given
     */
    public void show_project_comment(){
        // create pane
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // create table header
        String[] columnNames = {"Projects ID","Project Name","Comment"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        String csvFile = "projects.csv";
        String line = "";
        String cvsSplitBy = "//,"; 
        // read csv
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] projects = line.split(cvsSplitBy);

                if(projects.length>2 && !line.equals("-")) {
                    if (!projects[5].equals("-")) {
                        // add data to table
                        model.addRow(new Object[]{projects[0],projects[1],projects[5]});
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path and try again.");
        } catch (IOException e) {
            System.out.println("Error: An error occurred while reading the CSV file. Please check the file format and try again.");
        }

        // add panel to frame
        frame.add(panel);

        // display frame
        frame.setVisible(true);
    }

    /**
     * 
     * @param userValue         the username of the admin
     * @param passwordValue     the password of the admin
     * @param role              the role of the admin
     * @param account_name      the account name of the admin
     * @param specialization    the specialization of the admin
     * @throws IOException
     */
    public ReportPage(String userValue, String passwordValue, String role, String account_name,String specialization) throws IOException {
        
        String[] listReport = {"-","Projects entered into the system", "Projects according to specialisation", "Projects according to lecturers",
                                "Inactive projects", "Active projects", "Projects assigned to students","Project un-assign to students",
                                "Projects with comments"};

        frame = new JPanel();
        topPanel = new JPanel();

        label1 = new JLabel("Report");
        label2 = new JLabel("List of : ");

        Lblank = new JLabel("   ");
        dropdown = new JComboBox<>(listReport); 
        dropdown.addActionListener((ActionListener) new ComboBoxActionListener());     //add action listener to dropdown

        topPanel.setLayout(new GridLayout(10, 1));
        frame.setLayout(new BorderLayout());
        add(frame);

        topPanel.add(label1);
        topPanel.add(Lblank);
        topPanel.add(label2);
        topPanel.add(dropdown);

        // topPanel.add(Lblank);
        // topPanel.add(Lblank);
        // topPanel.add(Lblank);




        frame.add(topPanel, BorderLayout.PAGE_START);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generate Report");
        setSize(900, 500);
        setLocationRelativeTo(null);

        
            
  }
}


