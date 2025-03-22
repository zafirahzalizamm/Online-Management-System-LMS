//AUTHOR RAFIE.LUQMAN

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

class AdminViewProject extends JFrame {
  JFrame frame;
  JFrame addcommentFrame;

  String userValue;
  String passwordValue;
  String role;
  String account_name;
  String specialization;



  private class AddPrjButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      frame.dispose();
      AdminAddProject2 addproject = new AdminAddProject2(userValue, passwordValue, role, account_name, specialization);
      dispose();
      addproject.setVisible(true);
    }
  }

  private class BacktoPageListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      frame.dispose();
      AdminPage userpage = new AdminPage(userValue, passwordValue, role, account_name, specialization);
      dispose();
      userpage.setVisible(true);
    }
  }

  public void refresh() {
    frame.removeAll();
    frame.revalidate();
    frame.repaint();
  }


  // The above code is creating an instance of the JFrame class, which creates a window for the application.
// It is then reading the "projects.csv" file and adding the first two values from each line to an ArrayList called "projects".
// This ArrayList is then used to initialize a JList, which is a list view for displaying items.
// A ListSelectionListener is then added to the JList, which listens for changes in the selected item in the list.
// When a selection is made, the code reads the "projects.csv" file again, but this time it looks for the selected index in the JList and retrieves the corresponding line in the CSV file.
// The values in that line are then used to set the text in various JLabels, which will display the project's details in the GUI.

  public AdminViewProject(String userValue, String passwordValue, String role, String account_name,
      String specialization) {

    this.userValue = userValue;
    this.passwordValue = passwordValue;
    this.role = role;
    this.account_name = account_name;
    this.specialization = specialization;

    JButton addProjectButton;
    JButton deleteProjectButton;
    JButton commentProjectButton;
    JButton backButton;

    JLabel project_ID = new JLabel("project ID");
    project_ID.setForeground(Color.RED);
    JLabel project_ID2 = new JLabel();

    JLabel project_Name = new JLabel("project Name");
    project_Name.setForeground(Color.RED);
    JLabel project_Name2 = new JLabel();

    JLabel Project_Specialization = new JLabel("Project Specialization");
    Project_Specialization.setForeground(Color.RED);
    JLabel Project_Specialization2 = new JLabel();

    JLabel Project_Content = new JLabel(" Project Content");
    Project_Content.setForeground(Color.RED);
    JLabel Project_Content2 = new JLabel();

    JLabel Project_Lecturer = new JLabel("Project Lecturer");
    Project_Lecturer.setForeground(Color.RED);
    JLabel Project_Lecturer2 = new JLabel();

    JLabel Project_Comment = new JLabel("Project Comment");
    Project_Comment.setForeground(Color.RED);
    JLabel Project_Comment2 = new JLabel();

    JLabel Project_Student = new JLabel("Project Student");
    Project_Student.setForeground(Color.RED);
    JLabel Project_Student2 = new JLabel();

    JLabel Project_Status = new JLabel("Project Status ");
    Project_Status.setForeground(Color.RED);
    JLabel Project_Status2 = new JLabel();

    frame = new JFrame();

    ArrayList<String> projects = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split("//,");
        if (values.length < 2) {
          System.out.println("Line " + line + " does not contain enough values, skipping...");
          continue;
        }

        // Add the first two values to the ArrayList
        projects.add(values[0] + " (" + values[1] + ")");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Initialize list view and buttons
    JList<String> projecList = new JList<>(projects.toArray(new String[0]));

    ListSelectionListener listener = new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          int selectedindex = projecList.getSelectedIndex();

          int chosenline = selectedindex;
          try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {

            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {

              if (currentLine == chosenline) {
                String[] values = line.split("//,");
                project_ID2.setText(values[0]);
                project_Name2.setText(values[1]);
                Project_Specialization2.setText(values[2]);
                Project_Content2.setText(values[3]);
                Project_Lecturer2.setText(values[4]);
                Project_Comment2.setText(values[5]);
                Project_Student2.setText(values[6]);
                Project_Status2.setText(values[7]);
              }
              currentLine++;
            }
            br.close();
          } catch (IOException d) {
            d.printStackTrace();
          }
        }
      }

    };

    // add the listener to the JList
    projecList.addListSelectionListener(listener);

    addProjectButton = new JButton("Add Project");
    addProjectButton.addActionListener(new AddPrjButtonListener());

    deleteProjectButton = new JButton("Delete Project");
    commentProjectButton = new JButton("Comment Project");

    backButton = new JButton("Back To Main Page");
    backButton.addActionListener(new BacktoPageListener());

    commentProjectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addcommentFrame = new JFrame("Add Project's Comment");
        JLabel addcomment = new JLabel("Comment");
        JTextField commentToAdd = new JTextField();
        JButton addCommentBtn = new JButton("Add Comment");
        JButton backBtn = new JButton("Back");

        JPanel CommentPanel = new JPanel(new GridLayout(0, 1, 0, 2));
        int projectToComment = projecList.getSelectedIndex() + 1;

        try (BufferedReader br = new BufferedReader(new FileReader("projects.csv"))) {
          String line;
          while ((line = br.readLine()) != null) {
            String[] values = line.split("//,");
            if (values.length < 2) {
              System.out.println("Line " + line + " does not contain enough values, skipping...");
              continue;
            }

            commentToAdd.setText(values[5]);
          }
        } catch (IOException eb) {
          eb.printStackTrace();
        }

        addCommentBtn.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              String filePath = "projects.csv";
              String newcomment = commentToAdd.getText();

              File inputFile = new File(filePath);
              File tempFile = new File("project-temp.csv");

              BufferedReader reader = new BufferedReader(new FileReader(inputFile));
              BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

              String currentLine;
              int currentLineNumber = 0;

              while ((currentLine = reader.readLine()) != null) {
                currentLineNumber++;
                String[] values = currentLine.split("//,");
                if (currentLineNumber == projectToComment) {
                  values[5] = newcomment;
                  currentLine = String.join("//,", values);
                }
                writer.write(currentLine + System.getProperty("line.separator"));
              }

              writer.close();
              reader.close();
              inputFile.delete();
              tempFile.renameTo(inputFile);

            } catch (Exception ev) {
              ev.printStackTrace();
            }
            addcommentFrame.dispose();
            AdminViewProject viewproject = new AdminViewProject(userValue, passwordValue, role, account_name,
                specialization);
            frame.dispose();
            viewproject.setVisible(true);
            dispose();
          }

        });

        CommentPanel.add(addcomment);
        CommentPanel.add(commentToAdd);
        CommentPanel.add(addCommentBtn);
        CommentPanel.add(backBtn);

        addcommentFrame.add(CommentPanel, BorderLayout.CENTER);

        addcommentFrame.setSize(400, 350);
        addcommentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addcommentFrame.setLocationRelativeTo(null);
        addcommentFrame.setVisible(true);

      }
    });
    deleteProjectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        String fileName = "projects.csv";
        String tempFileName = "project-temp.csv";

        int projectToDelete = projecList.getSelectedIndex() + 1;
        String selectedProject = projecList.getSelectedValue();

        int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete " + selectedProject + " ?",
            "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
          try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            FileWriter fw = new FileWriter(tempFileName);
            int currentIndex = 0;
            String line;
            while ((line = br.readLine()) != null) {
              currentIndex++;
              if (currentIndex != projectToDelete) {
                fw.write(line + System.getProperty("line.separator"));
              }
            }
            fw.close();
            br.close();
            File tempFile = new File(tempFileName);
            File originalFile = new File(fileName);
            originalFile.delete();
            tempFile.renameTo(originalFile);

          } catch (IOException ex) {
            System.out.println("An error occurred while trying to remove the record from the CSV file.");
            ex.printStackTrace();
          }
          frame.dispose();

          AdminViewProject viewproject = new AdminViewProject(userValue, passwordValue, role, account_name,
              specialization);
          viewproject.setVisible(true);

        }
      }
    });

    // Add the JList to a scroll pane
    JScrollPane scrollPane = new JScrollPane(projecList);
    scrollPane.setSize(500, 300);

    // Create panel to hold the buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addProjectButton);
    buttonPanel.add(deleteProjectButton);
    buttonPanel.add(commentProjectButton);
    buttonPanel.add(backButton);

    Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    JPanel FullPjDetailPanel = new JPanel(new GridLayout(0, 1));

    FullPjDetailPanel.add(project_ID);
    FullPjDetailPanel.add(project_ID2);
    project_ID2.setBorder(border);
    project_ID2.setPreferredSize(new Dimension(100, 10));

    FullPjDetailPanel.add(project_Name);
    FullPjDetailPanel.add(project_Name2);
    project_Name2.setBorder(border);
    project_Name2.setPreferredSize(new Dimension(100, 10));

    FullPjDetailPanel.add(Project_Specialization);
    FullPjDetailPanel.add(Project_Specialization2);
    Project_Specialization2.setBorder(border);
    Project_Specialization2.setPreferredSize(new Dimension(100, 10));

    FullPjDetailPanel.add(Project_Content);
    FullPjDetailPanel.add(Project_Content2);
    Project_Content2.setBorder(border);
    Project_Content2.setPreferredSize(new Dimension(100, 20));

    FullPjDetailPanel.add(Project_Lecturer);
    FullPjDetailPanel.add(Project_Lecturer2);
    Project_Lecturer2.setBorder(border);
    Project_Lecturer2.setPreferredSize(new Dimension(100, 10));

    FullPjDetailPanel.add(Project_Comment);
    FullPjDetailPanel.add(Project_Comment2);
    Project_Comment2.setBorder(border);
    Project_Comment2.setPreferredSize(new Dimension(200, 20));

    FullPjDetailPanel.add(Project_Student);
    FullPjDetailPanel.add(Project_Student2);
    Project_Student2.setBorder(border);
    Project_Student2.setPreferredSize(new Dimension(100, 10));

    FullPjDetailPanel.add(Project_Status);
    FullPjDetailPanel.add(Project_Status2);
    Project_Status2.setBorder(border);
    Project_Status2.setPreferredSize(new Dimension(100, 10));

    // Add list view and buttons to the frame
    frame.add(scrollPane, BorderLayout.WEST);
    frame.add(buttonPanel, BorderLayout.SOUTH);
    frame.add(FullPjDetailPanel, BorderLayout.CENTER);

    // Set frame properties
    frame.setTitle("View Project");
    frame.setSize(700, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}
