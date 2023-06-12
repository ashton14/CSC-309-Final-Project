package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alex Banham, Connor Hickey
 * JPanel with simple login user interface
 * connects with mySQL DB to authenticate users
 */
public class LoginPage extends JPanel implements AppPage {

    private JTextField username;
    private JFormattedTextField password;
    private TeachingApp app;

    private boolean isTesting;

    /**
     * Constructor to initialize basic layout of login form
     */
    public LoginPage(boolean isTesting, TeachingApp app) {
        this.isTesting = isTesting;
        this.app = app;
        setBounds(100, 100, 391, 218);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        username = new JTextField();
        username.setBounds(110, 87, 141, 23);
        add(username);
        username.setColumns(10);

        password = new JFormattedTextField();
        password.setBounds(110, 125, 141, 23);
        add(password);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(37, 90, 78, 14);
        add(userLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(37, 129, 78, 14);
        add(passLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isTesting) {
                    if (SqlControlHandler.authenticateUser(username.getText(), password.getText())){
                        displayLoginSucceeded();
                        login();
                    } else {
                        displayLoginFailed();
                    }
                } else {
                    login();
                }
            }
        });
        loginButton.setBounds(276, 86, 89, 23);
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Student", "Professor"};
                int optionSelected = JOptionPane.showOptionDialog(null, "Are you a student or a professor?", "User Type",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (optionSelected == 0) {
                    if (SqlControlHandler.registerUser("Student", username.getText(), password.getText())) {
                        displayRegistrationSucceeded();
                    } else {
                        displayRegistrationFailed();
                    }

                } else if (optionSelected == 1) {
                    if (SqlControlHandler.registerUser("Professor", username.getText(), password.getText())) {
                        displayRegistrationSucceeded();
                    } else {
                        displayRegistrationFailed();
                    }
                }
            }
        });
        registerButton.setBounds(276, 125, 89, 23);
        add(registerButton);
    }

    @Override
    public String getHeaderInfo() {
        return "User Login";
    }


    private void displayLoginFailed() {
        JOptionPane.showMessageDialog(this, "Invalid username or password");
    }

    private void displayLoginSucceeded() {
        JOptionPane.showMessageDialog(this, "Login Successful");
    }

    private void displayRegistrationFailed() {
        JOptionPane.showMessageDialog(this, "Error Registering: User Already Exists");
    }

    private void displayRegistrationSucceeded() {
        JOptionPane.showMessageDialog(this, "Registration successful. Please log in with your username and password.");
    }

    /**
     * Method to handle the completion of the login process
     * Hides the login form and renders the main app
     */
    private void login() {
        //CoursesPage coursesPage = new CoursesPage();

        CourseView courseView = new CourseView(this.app);

        if (isTesting) {
            String[] courseNames = {
                "Introduction to Algorithms",
                "Software Engineering Principles",
                "Computer Networks",
                "Artificial Intelligence: Theory and Practice"
            };
            String[] teachers = {
                "Connor",
                "Alex",
                "Patrick",
                "Aaron"
            };

            String[] descriptions = {
                "In 'Introduction to Algorithms', students explore the foundations of computer science. This course introduces core algorithmic techniques and proofs, and encourages students to design, analyze, and implement algorithms in a hands-on manner.",
                "'Software Engineering Principles' teaches the basics of software development. Students will learn about different software development methodologies, project management, and quality assurance. The course also includes a group project where students will design and implement their own software application.",
                "The 'Computer Networks' course provides an overview of how data is transmitted over networks. Key topics include network architecture, data transmission methods, network protocols, and network security. Practical labs will allow students to set up and troubleshoot simple networks.",
                "'Artificial Intelligence: Theory and Practice' exposes students to the principles and techniques used in the field of artificial intelligence. The curriculum includes machine learning, natural language processing, robotics, and expert systems. The final project involves developing a simple AI program."
            };

            List<String> assignments = Arrays.asList("Assignment 1", "Assignment 2", "Assignment 3");

            List<Course> courses = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                courses.add(new Course(courseNames[i], teachers[i], descriptions[i], assignments));
            }
            courseView.setCourses(courses);
        }
        app.pushPage(courseView);
    }

    @Override
    public void showContents() {
        setVisible(true);
    }
}
