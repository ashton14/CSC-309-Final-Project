package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

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
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme. Using fallback." );
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 391, 218);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel loginLabel = new JLabel("User Login");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 16));
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
                if (SqlControlHandler.authenticateUser(username.getText(), password.getText())){
                    displayLoginSucceeded();
                    login();
                } else {
                    displayLoginFailed();
                }
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
        System.out.println(javax.swing.UIManager.getDefaults().getFont("Label.font"));
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
        setVisible(false);
        CoursesPage coursesPage = new CoursesPage();
    }

}

