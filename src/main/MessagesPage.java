package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class MessagesPage extends JPanel implements AppPage {

    private JTextArea messageTextArea;
    private JButton sendButton;
    private JComboBox<Course> coursesComboBox;
    private List<Course> courses;
    private String placeholderText;

    public MessagesPage(List<Course> courses) {
        this.courses = courses;
        setLayout(new BorderLayout());
    }

    @Override
    public void showContents() {
        if (coursesComboBox == null) {
            coursesComboBox = new JComboBox<>(courses.toArray(new Course[0]));
            coursesComboBox.setRenderer(new ListCellRenderer<Course>() {
                @Override
                public Component getListCellRendererComponent(JList<? extends Course> list, Course value, int index, boolean isSelected, boolean cellHasFocus) {
                    return new JLabel(value.getTeacher() + "\t(" + value.getCourseName() + ")");
                }
            });
            coursesComboBox.addActionListener(e -> {
                Course selectedCourse = (Course) coursesComboBox.getSelectedItem();
                placeholderText = "Message " + selectedCourse.getTeacher() + "...";
                messageTextArea.setText(placeholderText);
            });
            add(coursesComboBox, BorderLayout.NORTH);
        }

        if (messageTextArea == null) {
            messageTextArea = new JTextArea();
            messageTextArea.setForeground(Color.GRAY);
            messageTextArea.setText(placeholderText);
            messageTextArea.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (messageTextArea.getText().equals(placeholderText)) {
                        messageTextArea.setText("");
                        messageTextArea.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (messageTextArea.getText().isEmpty()) {
                        messageTextArea.setForeground(Color.GRAY);
                        messageTextArea.setText(placeholderText);
                    }
                }
            });
            add(new JScrollPane(messageTextArea), BorderLayout.CENTER);
        }

        if (sendButton == null) {
            sendButton = new JButton("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = messageTextArea.getText();
                    if (!message.equals(placeholderText)) {
                        // Handle the message sending logic here
                        System.out.println("Message sent: " + message);
                        messageTextArea.setText(placeholderText);
                        messageTextArea.setForeground(Color.GRAY);
                    }
                }
            });
            add(sendButton, BorderLayout.SOUTH);
        }

        // Initialize the placeholder text
        Course selectedCourse = (Course) coursesComboBox.getSelectedItem();
        placeholderText = "Message " + selectedCourse.getTeacher() + "...";
        messageTextArea.setText(placeholderText);
        messageTextArea.setForeground(Color.GRAY);
    }

    @Override
    public String getHeaderInfo() {
        return "Message";
    }

}
