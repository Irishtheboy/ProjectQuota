package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGUI extends JFrame implements ActionListener {
    JPanel pnlSouth, pnlNorth, pnlEast, pnlWest, pnlCenter;
    JLabel lblPassword, lblUsername, lblTitle;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, signupBtn, forgotBtn;

    public loginGUI() {
        super("Quota Live - Login");

        // Set custom frame icon
        ImageIcon frameIcon = new ImageIcon("Logo-cut.png");
        setIconImage(frameIcon.getImage());

        // Initialize Panels
        pnlSouth = new JPanel();
        pnlNorth = new JPanel();
        pnlEast = new JPanel();
        pnlWest = new JPanel();
        pnlCenter = new JPanel();

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon("Logo-cut.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        lblTitle = new JLabel(new ImageIcon(scaledImage), SwingConstants.CENTER);

        lblPassword = new JLabel("Password:");
        lblUsername = new JLabel("Username:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        loginBtn = new JButton("Login");
        signupBtn = new JButton("Sign Up");
        forgotBtn = new JButton("Forgot Password?");

        // Add Action Listeners
        loginBtn.addActionListener(this);

        // Set up the GUI
        setGUI();
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
        pnlSouth.setLayout(new GridLayout(1, 3, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        pnlSouth.add(loginBtn);
        pnlSouth.add(signupBtn);
        pnlSouth.add(forgotBtn);
        add(pnlSouth, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            new MainDashboard();
            dispose();
        }
    }


}
