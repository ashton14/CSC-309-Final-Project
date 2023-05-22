package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * @author Alex Banham
 * frame with simple login user interface
 * connects with mySQL DB to authenticate users
 */
public class LoginPage extends JFrame{

    private JPanel contentPane;
    private JTextField username;
    private Connection conn = null;
    private JFormattedTextField password;

    /**
     * Constructor to initialize basic layout of login form
     */
    public LoginPage() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 391, 218);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel loginLabel = new JLabel("User Login");
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        loginLabel.setBounds(37, 22, 102, 28);
        contentPane.add(loginLabel);

        username = new JTextField();
        username.setBounds(110, 87, 141, 23);
        contentPane.add(username);
        username.setColumns(10);

        password = new JFormattedTextField();
        password.setBounds(110, 125, 141, 23);
        contentPane.add(password);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(37, 90, 78, 14);
        contentPane.add(userLabel);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(37, 129, 78, 14);
        contentPane.add(passLabel);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        loginButton.setBounds(276, 86, 89, 23);
        contentPane.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Student", "Professor"};
                int optionSelected = JOptionPane.showOptionDialog(null, "Are you a student or a professor?", "User Type",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (optionSelected == 0) {
                    registerUser("Student");
                } else if (optionSelected == 1) {
                    registerUser("Professor");
                }
            }
        });
        registerButton.setBounds(276, 125, 89, 23);
        contentPane.add(registerButton);
    }

    /**
     * main method
     * @param args
     */
    public static void main(String [] args){
        LoginPage page = new LoginPage();
        page.setVisible(true);
        page.login();
    }


    /**
     * Method to handle the user authentication, queries the db to check if user info is there
     */
    private void authenticateUser() {

        // Connect to the MySQL database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorapp_schema_1",
                    "root", "Csc3092023");

            // Execute the SELECT statement to check if the user exists
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username.getText());
            statement.setString(2, password.getText());
            ResultSet result = statement.executeQuery();

            // Check the result set
            if (result.next()) {
                JOptionPane.showMessageDialog(this, "Login successful");
                login();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

            // Close the database connection
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Method to handle user registration, queries the db to check if user exists before adding a new user
     * @param userType the type of user, either professor or student
     */
    private void registerUser(String userType) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorapp_schema_1",
                    "root", "Csc3092023");
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username.getText());
            statement.setString(2, password.getText());
            ResultSet result = statement.executeQuery();

            if (result.next()){
                JOptionPane.showMessageDialog(this, "Error Registering: User Already Exists");
                conn.close();
            } else {
                String sql2 = "INSERT INTO users (username, password, usertype) VALUES (?, ?, ?)";
                PreparedStatement statement2 = conn.prepareStatement(sql2);
                statement2.setString(1, username.getText());
                statement2.setString(2, password.getText());
                statement2.setString(3, userType);
                Integer rowsUpdated = statement2.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful. Please log in with your username and password.");
                }
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Method to handle the completion of the login process
     * Hides the login form and renders the main app
     */
    private void login() {
        setVisible(false);
        CoursesPage coursesPage = new CoursesPage();
    }

}


