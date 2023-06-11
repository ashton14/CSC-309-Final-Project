package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * The MessagesPage class represents the view for sending messages.
 * It extends JPanel and implements the AppPage interface.
 * It provides methods to show the contents of the messages page and retrieve the header information.
 *
 * The messages page allows the user to select a course from a dropdown menu and send a message to the selected course.
 * The message text area initially displays a placeholder text specific to the selected course.
 * When the user clicks on the text area, the placeholder text is cleared and the user can enter their message.
 * The user can send the message by clicking the send button.
 *
 * The messages page uses a BorderLayout to arrange the components.
 * The course selection dropdown is placed in the north, the message text area with a scroll pane is placed in the center,
 * and the send button is placed in the south.
 *
 * The messages page is associated with the TeachingApp.
 *
 * @author Connor Hickey
 */
public class MessagesPage extends JPanel implements AppPage {

    private JTextArea messageTextArea;
    private JButton sendButton;
    private JComboBox<Course> coursesComboBox;
    private List<Course> courses;
    private String placeholderText;

    /**
     * Constructs a MessagesPage object with the list of courses.
     *
     * @param courses the list of courses.
     */
    public MessagesPage(List<Course> courses) {
        this.courses = courses;
        setLayout(new BorderLayout());
    }

    /**
     * Shows the contents of the messages page.
     * It creates and adds the course selection dropdown, the message text area, and the send button.
     * It initializes the placeholder text based on the selected course.
     */
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

    /**
     * Retrieves the header information of the messages page.
     *
     * @return the header information.
     */
    @Override
    public String getHeaderInfo() {
        return "Message";
    }

}
