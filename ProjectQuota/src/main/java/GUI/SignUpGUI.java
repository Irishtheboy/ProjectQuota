package GUI;

import DatabaseConnection.DBConnection;
import Queries.EmployeeQueries;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpGUI extends JFrame implements ActionListener {

    // Panels for layout
    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter;

    // Labels for user input fields
    JLabel lblUsername, lblPassword, lblConfirmPassword, lblName, lblRole, lblEmail, lblTitle;

    // Text fields for user input
    JTextField usernameField, nameField, emailField;
    JPasswordField passwordField, confirmPasswordField;

    // Buttons for actions
    JButton signUpBtn, cancelBtn;

    public SignUpGUI() {
        super("Quota Live - Sign Up");

        // Set custom frame icon
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
        setIconImage(frameIcon.getImage());

        // Initialize Panels
        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlEast = new JPanel();
        pnlWest = new JPanel();
        pnlCenter = new JPanel();

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lblTitle = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);

        // Labels for inputs
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");
        lblConfirmPassword = new JLabel("Confirm Password:");
        lblName = new JLabel("Name:");
        lblRole = new JLabel("Role:");
        lblEmail = new JLabel("Email:");

        // Input fields for the user to fill out
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        nameField = new JTextField(15);
        emailField = new JTextField(15);

        // Buttons for actions
        signUpBtn = new JButton("Sign Up");
        cancelBtn = new JButton("Cancel");

        // Add action listeners for buttons
        signUpBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Set up the GUI
        setGUI();
    }

    public void setGUI() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel - Title
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        // Center Panel - Form
        pnlCenter.setLayout(new GridLayout(7, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        pnlCenter.add(lblUsername);
        pnlCenter.add(usernameField);
        pnlCenter.add(lblPassword);
        pnlCenter.add(passwordField);
        pnlCenter.add(lblConfirmPassword);
        pnlCenter.add(confirmPasswordField);
        pnlCenter.add(lblName);
        pnlCenter.add(nameField);
        pnlCenter.add(lblRole);
        pnlCenter.add(new JComboBox<>(new String[]{"Admin", "Employee", "Manager"})); // Example role choices
        pnlCenter.add(lblEmail);
        pnlCenter.add(emailField);
        add(pnlCenter, BorderLayout.CENTER);

        // South Panel - Buttons
        pnlSouth.setLayout(new GridLayout(1, 2, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        pnlSouth.add(signUpBtn);
        pnlSouth.add(cancelBtn);
        add(pnlSouth, BorderLayout.SOUTH);

        setVisible(true);
    }

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == signUpBtn) {
        // Get user input from the fields
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String fullName = nameField.getText(); // Full name entered by the user
        String email = emailField.getText();
        String role = ((JComboBox) pnlCenter.getComponent(9)).getSelectedItem().toString();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Split full name into first and last name
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0]; // First part is the first name
        String lastName = nameParts.length > 1 ? nameParts[1] : ""; // Handle cases with no last name

        // Add new employee to the database
        boolean isAdded = EmployeeQueries.addEmployee(firstName, lastName, username, password, role, email);

        // Provide feedback
        if (isAdded) {
            JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new loginGUI();  // Redirect to the login screen
            dispose();  // Close the sign-up screen
        } else {
            JOptionPane.showMessageDialog(this, "Error creating account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else if (e.getSource() == cancelBtn) {
        dispose();  // Close the sign-up window
    }
}


    public static void main(String[] args) {
        // Start the sign-up GUI
        new SignUpGUI();
    }
}
