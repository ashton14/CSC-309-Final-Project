package src.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to handle all database queries and the results
 * @author Alex Banham
 */

public class SqlControlHandler {

    private static final String url = "jdbc:mysql://sql9.freesqldatabase.com:3306/sql9625644";
    private static final String connectionUser = "sql9625644";
    private static final String connectionPass = "KjBEiLBFrn";

    /**
     * Method to handle the user authentication, queries the db to check if user info is there
     * @param username The user's username to be validated
     * @param password The user's password to be validated
     */
    public static boolean authenticateUser(String username, String password) {
        // Connect to the MySQL database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, connectionUser, connectionPass);
            // Execute the SELECT statement to check if the user exists
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            // Check the result set
            if (result.next()) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * Method to handle user registration, queries the db to check if user exists before adding a new user
     * @param userType the type of user, either professor or student
     * @param username The user's username to be registered
     * @param password The user's password to be registered
     */
    public static boolean registerUser(String userType, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, connectionUser, connectionPass);
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                conn.close();
            } else {
                String sql2 = "INSERT INTO users (username, password, usertype) VALUES (?, ?, ?)";
                PreparedStatement statement2 = conn.prepareStatement(sql2);
                statement2.setString(1, username);
                statement2.setString(2, password);
                statement2.setString(3, userType);
                Integer rowsUpdated = statement2.executeUpdate();
                if (rowsUpdated > 0) {
                    conn.close();
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Method to handle uploading a flowchart to the db
     * @param filename the filename of the flowchart to upload
     */
    public static boolean uploadFlowchart(String filename) {
        try (Connection connection = DriverManager.getConnection(url, connectionUser, connectionPass)) {
            String sql = "INSERT INTO flowcharts (name, file_data) VALUES (?, ?)";
            File file = new File(filename + ".diag");

            try (FileInputStream fis = new FileInputStream(file);
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, file.getName());
                statement.setBinaryStream(2, fis);
                statement.executeUpdate();
                System.out.println("Flowchart uploaded successfully.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to handle downloading a flowchart from the db
     * @param filename the filename of the flowchart to upload
     */
    public static void downloadFlowchart(String filename) {
        try (Connection connection = DriverManager.getConnection(url, connectionUser, connectionPass)) {
            String sql = "SELECT file_data FROM flowcharts WHERE name = ?";
            File outputFile = new File(filename);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, filename);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        InputStream inputStream = resultSet.getBinaryStream("file_data");

                        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }

                            System.out.println("Flowchart downloaded successfully.");
                        }
                    } else {
                        System.out.println("Flowchart not found.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that returns a list of the names of all the flowcharts in the db
     * @return List of flowchart file names
     */
    public static List<String> getFlowchartFileNames() {
        List<String> fileNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, connectionUser, connectionPass)) {
            String sql = "SELECT name FROM flowcharts";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String fileName = resultSet.getString("name");
                    fileNames.add(fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }


}