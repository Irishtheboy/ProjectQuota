package GUI;

import DatabaseConnection.DBConnection;
import Queries.EmployeeQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

public class loginGUI extends JFrame implements ActionListener {

    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter;
    JLabel lblPassword, lblUsername, lblTitle, alchemLL, llempty1;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, signupBtn, forgotBtn, databaseBtn;

    public loginGUI() {
        super("Quota Live - Login");

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

        lblPassword = new JLabel("Password:");
        lblUsername = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        loginBtn = new JButton("Login");
        signupBtn = new JButton("Sign Up");
        forgotBtn = new JButton("Forgot Password?");
        databaseBtn = new JButton("Start Database");

        alchemLL = new JLabel("Developed by Alchemy Studio @Franco Lukhele with JAVA");
        alchemLL.setFont(new Font("Arial", Font.ITALIC, 12)); // Font size is adjustable
        alchemLL.setForeground(Color.GRAY);

        llempty1 = new JLabel(" ");

        // Add Action Listeners
        loginBtn.addActionListener(this);
        signupBtn.addActionListener(this);
        databaseBtn.addActionListener(this);

        // Set up the GUI
        setGUI();

        // Add a window listener to delete the file on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                deleteFileOnClose();
            }
        });
    }

    public void deleteFileOnClose() {
        File file = new File("logged_in_user.txt");
        if (file.exists() && file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }

    public void setGUI() {
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel - Title
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        // Center Panel - Login Form
        pnlCenter.setLayout(new GridLayout(4, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        pnlCenter.add(lblUsername);
        pnlCenter.add(usernameField);
        pnlCenter.add(lblPassword);
        pnlCenter.add(passwordField);
        add(pnlCenter, BorderLayout.CENTER);

        // South Panel - Buttons
        pnlSouth.setLayout(new GridLayout(2, 3, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        pnlSouth.add(loginBtn);
        pnlSouth.add(signupBtn);
        pnlSouth.add(forgotBtn);
        pnlSouth.add(llempty1);
        pnlSouth.add(alchemLL);
        add(pnlSouth, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean isValid = EmployeeQueries.verifyLogin(username, password);

            if (isValid) {
                writeToFile(username);
                new MainDashboard(); // Assuming MainDashboard is another JFrame
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == signupBtn) {
            dispose();
            new SignUpGUI();
        } else if (e.getSource() == databaseBtn) {
            try {
                Connection conn = DBConnection.getConnection();

                if (conn != null) {
                    JOptionPane.showMessageDialog(null, "Database and server started successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Failed to start the server or connect to the database: " + ex.getMessage());
            }
        }
    }

    private void writeToFile(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logged_in_user.txt"))) {
            writer.write(username);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new loginGUI();
    }
}
