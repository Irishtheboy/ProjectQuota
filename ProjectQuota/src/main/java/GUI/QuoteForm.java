package GUI;

import DatabaseConnection.DBConnection;
import Queries.QuoteQuery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;

public class QuoteForm extends JFrame implements ActionListener {

    // Panels
    JPanel pnlNorth, pnlCenter, pnlSouth, pnlLeft;

    // Labels
    JLabel lblTitle, lblClientName, lblPartName, lblQuantity, lblPricePerUnit, lblTotalAmount, lblEmail, lblContactNumber;

    // Text Fields
    JTextField txtClientName, txtPartName, txtQuantity, txtPricePerUnit, txtEmail, txtContactNumber;

    // Buttons
    JButton btnCalculate, btnReturn, btnClear, btnAddJob, btnPrint;

    // Labels
    JLabel lblResponsiblePerson;

// Text Fields
    JTextField txtResponsiblePerson;

    // Table
    JTable jobTable;
    DefaultTableModel tableModel;

    public QuoteForm() {
        super("Quota Live - Create Quotation");
        // Set custom frame icon
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
        setIconImage(frameIcon.getImage());

        // Initialize Panels
        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();
        pnlLeft = new JPanel();

        // Initialize the responsible person field
        lblResponsiblePerson = new JLabel("Responsible Person:");
        txtResponsiblePerson = new JTextField(15);

        // Initialize Labels
        lblTitle = new JLabel("Create New Quotation", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        lblClientName = new JLabel("Client Name:");
        lblPartName = new JLabel("Part Name:");
        lblQuantity = new JLabel("Quantity:");
        lblPricePerUnit = new JLabel("Price per Unit:");
        lblTotalAmount = new JLabel("Total Amount: R 0.00");
        lblEmail = new JLabel("Client Email:");
        lblContactNumber = new JLabel("Client Contact Number:");

        // Initialize Text Fields
        txtClientName = new JTextField(15);
        txtPartName = new JTextField(15);
        txtQuantity = new JTextField(10);
        txtPricePerUnit = new JTextField(10);
        txtEmail = new JTextField(15);
        txtContactNumber = new JTextField(15);

        ImageIcon iconAdd = new ImageIcon("resources/Logo-cut.png");
        ImageIcon iconPrint = new ImageIcon("resources/print_icon.png");
        // Initialize Buttons
        btnCalculate = new JButton("LogOut");
        btnReturn = new JButton("Return");
        btnClear = new JButton("Clear");
        btnAddJob = new JButton("Add To Table",iconAdd);
        btnPrint = new JButton("Print Quote");
        
        

        // Initialize Table
        String[] columnNames = {"Part Name", "Quantity", "Price per Unit", "Total", "Responsible Person"};

        tableModel = new DefaultTableModel(columnNames, 0);
        jobTable = new JTable(tableModel);

        // Add Listeners
        btnCalculate.addActionListener(this);
        btnReturn.addActionListener(this);
        btnClear.addActionListener(this);
        btnAddJob.addActionListener(this);
        btnPrint.addActionListener(this);

        setGUI();
    }

    public void setGUI() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);  
        //setUndecorated(true);  
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel
        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        // Left Panel
        pnlLeft.setLayout(new GridLayout(9, 5, 10, 10));
        pnlLeft.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        pnlLeft.add(lblClientName);
        pnlLeft.add(txtClientName);
        pnlLeft.add(lblContactNumber);
        pnlLeft.add(txtContactNumber);
        pnlLeft.add(lblEmail);
        pnlLeft.add(txtEmail);
        pnlLeft.add(lblPartName);
        pnlLeft.add(txtPartName);
        pnlLeft.add(lblQuantity);
        pnlLeft.add(txtQuantity);
        pnlLeft.add(lblPricePerUnit);
        pnlLeft.add(txtPricePerUnit);
        pnlLeft.add(lblResponsiblePerson);
        pnlLeft.add(txtResponsiblePerson);
        pnlLeft.add(lblTotalAmount);

        add(pnlLeft, BorderLayout.WEST);

        // South Panel
        pnlSouth.setLayout(new GridLayout(1, 5, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        pnlSouth.add(btnAddJob);
        pnlSouth.add(btnClear);
        pnlSouth.add(btnPrint);
        pnlSouth.add(btnReturn);
        pnlSouth.add(btnCalculate);
        
        
        
        add(pnlSouth, BorderLayout.SOUTH);

        // Center Table
        add(new JScrollPane(jobTable), BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAddJob) {
            addJobToTable();
        } else if (e.getSource() == btnCalculate) {
            System.exit(0);
        } else if (e.getSource() == btnReturn) {
            saveQuote();
        } else if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnPrint) {
            printStyledQuote();
            printStyledQuotepdf();
        }
    }

    private void addJobToTable() {
        try {
            String partName = txtPartName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double pricePerUnit = Double.parseDouble(txtPricePerUnit.getText());
            String responsiblePerson = txtResponsiblePerson.getText();  // Get responsible person's name
            double total = quantity * pricePerUnit;

            // Add row to the table with responsible person
            tableModel.addRow(new Object[]{partName, quantity, pricePerUnit, total, responsiblePerson});
            calculateTotalAmount();
            clearJobFields();

            // Disable client fields if they are already filled
            if (!txtClientName.getText().isEmpty()) {
                txtClientName.setEnabled(false);
            }
            if (!txtContactNumber.getText().isEmpty()) {
                txtContactNumber.setEnabled(false);
            }
            if (!txtEmail.getText().isEmpty()) {
                txtEmail.setEnabled(false);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid data.");
        }
    }

    private void calculateTotalAmount() {
        double totalAmount = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalAmount += (double) tableModel.getValueAt(i, 3);
        }
        lblTotalAmount.setText("Total Amount: R " + String.format("%.2f", totalAmount));
    }

    private void clearFields() {
        txtClientName.setText("");
        txtPartName.setText("");
        txtQuantity.setText("");
        txtPricePerUnit.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
        tableModel.setRowCount(0);
        lblTotalAmount.setText("Total Amount: R 0.00");

        // Re-enable the client fields when clearing
        txtClientName.setEnabled(true);
        txtContactNumber.setEnabled(true);
        txtEmail.setEnabled(true);
    }

    private void clearJobFields() {
        txtPartName.setText("");
        txtQuantity.setText("");
        txtPricePerUnit.setText("");
    }

    private void saveQuote() {
        dispose();
        new MainDashboard();
    }

    private void printStyledQuote() {
        String clientName = txtClientName.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();

        if (clientName.isEmpty() || contactNumber.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter client details.");
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        double grandTotal = 0;
        Object[][] quoteItems = new Object[tableModel.getRowCount()][4];

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            quoteItems[i][0] = tableModel.getValueAt(i, 0);
            quoteItems[i][1] = tableModel.getValueAt(i, 1);
            quoteItems[i][2] = tableModel.getValueAt(i, 2);
            quoteItems[i][3] = tableModel.getValueAt(i, 3);
            grandTotal += (double) tableModel.getValueAt(i, 3);
        }

        try {
            int quoteId = QuoteQuery.insertQuote(clientName, contactNumber, email, currentDate, grandTotal);
            QuoteQuery.insertQuoteItems(quoteId, quoteItems);
            JOptionPane.showMessageDialog(this, "Quote saved and printed.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving quote: " + ex.getMessage());
        }
    }
    // Print the quote in styled HTML format

   private void printStyledQuotepdf() {
    // Get client name and ensure it's filled in
    String clientName = txtClientName.getText();
    if (clientName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the client name before printing.");
        return;
    }

    // Get the current date for printing
    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    // Read the username of the person printing the document
    String username = getLoggedInUser();
    if (username == null) {
        JOptionPane.showMessageDialog(this, "Error: No logged-in user found.");
        return;
    }

    // Build the HTML content for the quotation
    StringBuilder htmlQuote = new StringBuilder();
    htmlQuote.append("<html><body style='font-family:Arial, sans-serif; line-height:1.6;'>");

    // Header Section with logo and title
htmlQuote.append("<div style='text-align:center; padding:5px; border:1px solid #ccc; border-radius:5px; background-color:#f9f9f9; margin-bottom:5px;'>")
        .append("<img src='file://resources/Logo-cut.png' alt='Company Logo' style='width:80px; height:auto; margin-bottom:5px;'>")
        .append("<h2 style='font-size:16px; color:#333; margin:0;'>Quotation Invoice</h2>")
        .append("<hr style='border:0; border-top:1px solid #ddd; margin:5px 0;'>")
        .append("<table style='width:100%; font-size:10px; color:#444; border-collapse:collapse;'>")
        .append("<tr>")
        .append("<td style='padding:5px;'><strong>Date:</strong></td>")
        .append("<td style='padding:5px;'>").append(currentDate).append("</td>")
        .append("</tr>")
        .append("<tr>")
        .append("<td style='padding:5px;'><strong>Client Name:</strong></td>")
        .append("<td style='padding:5px;'>").append(clientName).append("</td>")
        .append("</tr>")
        .append("<tr>")
        .append("<td style='padding:5px;'><strong>Responsible Biller:</strong></td>")
        .append("<td style='padding:5px;'>").append(username).append("</td>")
        .append("</tr>")
        .append("<tr>")
        .append("<td style='padding:5px;'><strong>Company Name:</strong></td>")
        .append("<td style='padding:5px;'>YourCompanyName</td>")
        .append("</tr>")
        .append("</table>")
        .append("</div>");





    // Table Section with improved styles for rows and columns
    htmlQuote.append("<table style='width:100%; border-collapse:collapse; font-size:6px; margin-top:10px;'>")
            .append("<thead>")
            .append("<tr style='background-color:#007BFF; color:white; text-align:left;'>")
            .append("<th style='padding:10px; border:1px solid #ccc;'>Part Name</th>")
            .append("<th style='padding:10px; border:1px solid #ccc;'>Quantity</th>")
            .append("<th style='padding:10px; border:1px solid #ccc;'>Price per Unit</th>")
            .append("<th style='padding:10px; border:1px solid #ccc;'>Total</th>")
            .append("<th style='padding:10px; border:1px solid #ccc;'>Responsible Person</th>")
            .append("</tr>")
            .append("</thead>")
            .append("<tbody>");

    // Loop through the rows in the table and add them to the HTML table
    double grandTotal = 0;
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String partName = (String) tableModel.getValueAt(i, 0);
        int quantity = (int) tableModel.getValueAt(i, 1);
        double pricePerUnit = (double) tableModel.getValueAt(i, 2);
        double total = (double) tableModel.getValueAt(i, 3);
        String responsiblePerson = (String) tableModel.getValueAt(i, 4);  // Get responsible person's name

        // Add each row to the HTML table with alternating row colors
        htmlQuote.append("<tr style='background-color:").append(i % 2 == 0 ? "#ffffff" : "#f8f9fa").append("; border:1px solid #ddd;'>")
                .append("<td style='padding:10px; border:1px solid #ddd;'>").append(partName).append("</td>")
                .append("<td style='padding:10px; border:1px solid #ddd;'>").append(quantity).append("</td>")
                .append("<td style='padding:10px; border:1px solid #ddd;'>").append(String.format("%.2f", pricePerUnit)).append("</td>")
                .append("<td style='padding:10px; border:1px solid #ddd;'>").append(String.format("%.2f", total)).append("</td>")
                .append("<td style='padding:10px; border:1px solid #ddd;'>").append(responsiblePerson).append("</td>")
                .append("</tr>");

        // Accumulate the grand total
        grandTotal += total;
    }

    // Close the table and add the total amount at the bottom
    htmlQuote.append("</tbody></table>");
    htmlQuote.append("<h3 style='text-align:right; margin-top:20px; color:#333;'>")
            .append("Total Amount: <span style='color:#007BFF;'>R ").append(String.format("%.2f", grandTotal)).append("</span></h3>");
    htmlQuote.append("</body></html>");

    // Create a JEditorPane to display the HTML content and print it
    JEditorPane editorPane = new JEditorPane("text/html", htmlQuote.toString());
    editorPane.setEditable(false);  // Disable editing

    try {
        // Print the content of the editor pane (HTML-formatted)
        editorPane.print();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error while printing: " + ex.getMessage());
    }
}


// Helper method to get the logged-in user's username from the file
    private String getLoggedInUser() {
        File file = new File("logged_in_user.txt");
        if (!file.exists()) {
            return null; // No user logged in
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine(); // Read the username from the file
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new QuoteForm();
    }
}
