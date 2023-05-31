package src.main;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlControlHandler {

        private static final String url = "jdbc:mysql://localhost:3306/tutorapp_schema_1";
        private static final String username = "root";
        private static final String password = "Csc3092023";

    /**
     * Method to handle the user authentication, queries the db to check if user info is there
     */
    public static boolean authenticateUser(String username, String password) {

        // Connect to the MySQL database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorapp_schema_1",
                    "root", "Csc3092023");

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
     */
    public static boolean registerUser(String userType, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorapp_schema_1",
                    "root", "Csc3092023");
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()){
                conn.close();
                return false;
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
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

        public static boolean uploadFlowchart(String filename) {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO Flowcharts (name, file_data) VALUES (?, ?)";
                File file = new File(filename);

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

    public static void downloadFlowchart(String filename) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT file_data FROM Flowcharts WHERE name = ?";
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

    public static List<String> getFlowchartFileNames() {
        List<String> fileNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT name FROM Flowcharts";

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
