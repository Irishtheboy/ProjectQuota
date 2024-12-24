package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame implements ActionListener {

    // Declare panels and components
    JPanel pnlNorth, pnlCenter, pnlSouth;
    JLabel lblTitle;
    JButton btnManageClients, btnCreateQuotation, btnViewSales, btnLogout;

    public MainDashboard() {
        super("Quota Live - Main Dashboard");

        // Set frame icon
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
        setIconImage(frameIcon.getImage());

        // Initialize panels
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();

        // Initialize components
        lblTitle = new JLabel("Welcome to Quota Live!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        btnManageClients = new JButton("Manage Clients");
        btnCreateQuotation = new JButton("Create Quotation");
        btnViewSales = new JButton("View Sales Reports");
        btnLogout = new JButton("Logout");

        // Add action listeners
        btnManageClients.addActionListener(this);
        btnCreateQuotation.addActionListener(this);
        btnViewSales.addActionListener(this);
        btnLogout.addActionListener(this);

        // Set up the GUI
        setGUI();
    }

    public void setGUI() {
        // Set frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel: Title
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        // Center Panel: Main Buttons
        pnlCenter.setLayout(new GridLayout(3, 1, 10, 10));
        pnlCenter.add(btnManageClients);
        pnlCenter.add(btnCreateQuotation);
        pnlCenter.add(btnViewSales);
        add(pnlCenter, BorderLayout.CENTER);

        // South Panel: Logout Button
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.add(btnLogout);
        add(pnlSouth, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCreateQuotation) {
            dispose();
            new QuoteForm();  // Launch QuoteForm for creating quotations
        } else if (e.getSource() == btnManageClients) {
            dispose();
            new ClientsHistory();  // Launch ClientsHistory for managing clients
        } else if (e.getSource() == btnViewSales) {
            JOptionPane.showMessageDialog(this, "Sales Reports are under development.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == btnLogout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();  // Close the dashboard
                new loginGUI();  // Redirect to login screen
            }
        }
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}
