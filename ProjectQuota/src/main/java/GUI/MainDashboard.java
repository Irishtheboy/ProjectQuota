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
        super("Main Dashboard");
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCreateQuotation) {
            dispose();
            new QuoteForm();  // Assuming you have a QuoteForm class for creating quotations
        } else if (e.getSource() == btnLogout) {
            System.exit(0);
        } else if (e.getSource() == btnViewSales) {
//            // Sample Sales Data
//            List<Sale> sales = Arrays.asList(
//                    new Sale(1, "Client A", LocalDate.of(2024, 12, 10), 150.0),
//                    new Sale(2, "Client B", LocalDate.of(2024, 12, 11), 250.0)
//            );
//
//            // Create and display sales report
//            ViewSalesReport report = new ViewSalesReport(1, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 31), sales);
//            JFrame reportFrame = new JFrame("Sales Report");
//            reportFrame.setLayout(new BorderLayout());
//            JTextArea reportArea = new JTextArea();
//            reportArea.setText(report.toString());
//            reportArea.setEditable(false);
//            reportFrame.add(new JScrollPane(reportArea), BorderLayout.CENTER);
//            reportFrame.setSize(400, 300);
//            reportFrame.setVisible(true);
        }
    }


}
