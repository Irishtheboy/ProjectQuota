package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

public class MainDashboard extends JFrame implements ActionListener {

    JPanel pnlNorth, pnlCenter, pnlWest, pnlEast, pnlSouth;
    JLabel lblTitle;
    JButton btnManageClients, btnCreateQuotation, btnViewSales, btnLogout;

    public MainDashboard() {
        super("Quota Live - Main Dashboard");
        // Load the frame logo
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
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
        btnViewSales.addActionListener(this);
        btnManageClients.addActionListener(this);
        btnLogout.addActionListener(this);
        

        // Set up the GUI
        setGUI();
    }

    public void setGUI() {
       setExtendedState(JFrame.MAXIMIZED_BOTH);  
        //setUndecorated(true);  
        //setSize(900, 900);
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCreateQuotation) {
            dispose();
            new QuoteForm();  
        } else if (e.getSource() == btnLogout) {
            System.exit(0);
        } else if (e.getSource() == btnViewSales) {
            JOptionPane.showMessageDialog(null,"Well to be honest i dunno what this is even for lol...");
        } else if(e.getSource() == btnManageClients){
            dispose();
            new ClientsHistory();
        }
    }

}
