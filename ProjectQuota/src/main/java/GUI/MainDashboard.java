package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame implements ActionListener {

    JPanel pnlNorth, pnlCenter, pnlWest, pnlEast, pnlSouth;
    JLabel lblTitle;
    JButton btnManageClients, btnCreateQuotation, btnViewSales, btnLogout;

    public MainDashboard() {
        super("Main Dashboard");
        ImageIcon frameIcon = new ImageIcon("Logo-cut.png");
        setIconImage(frameIcon.getImage());

        // Initialize Panels
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlWest = new JPanel();
        pnlEast = new JPanel();
        pnlSouth = new JPanel();

        // Initialize Components
        lblTitle = new JLabel("Welcome to Quota Live!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        btnManageClients = new JButton("Manage Clients");
        btnCreateQuotation = new JButton("Create Quotation");
        btnViewSales = new JButton("View Sales Reports");
        btnLogout = new JButton("Logout");

        btnCreateQuotation.addActionListener(this);

        // Set up the GUI
        setGUI();
    }

    public void setGUI() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        // Center Panel
        pnlCenter.setLayout(new GridLayout(3, 1, 10, 10));
        pnlCenter.add(btnManageClients);
        pnlCenter.add(btnCreateQuotation);
        pnlCenter.add(btnViewSales);
        add(pnlCenter, BorderLayout.CENTER);

        // South Panel
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.add(btnLogout);
        add(pnlSouth, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnCreateQuotation){
            dispose();
            new QuoteForm();
        } else if (e.getSource() == btnLogout) {
            System.exit(0);

        }

    }
}
