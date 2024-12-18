package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuoteForm extends JFrame implements ActionListener {

    JPanel pnlNorth, pnlCenter, pnlSouth;
    JLabel lblTitle, lblClientName, lblPartName, lblQuantity, lblPricePerUnit, lblTotalAmount;
    JTextField txtClientName, txtPartName, txtQuantity, txtPricePerUnit;
    JButton btnCalculate, btnSave, btnClear, btnAddJob, btnPrint;
    JTable jobTable;
    DefaultTableModel tableModel;

    public QuoteForm() {
        super("Create Quotation");

        pnlNorth = new JPanel();
        pnlCenter = new JPanel();
        pnlSouth = new JPanel();

        lblTitle = new JLabel("Create New Quotation", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        lblClientName = new JLabel("Client Name:");
        lblPartName = new JLabel("Part Name:");
        lblQuantity = new JLabel("Quantity:");
        lblPricePerUnit = new JLabel("Price per Unit:");
        lblTotalAmount = new JLabel("Total Amount: R 0.00");

        txtClientName = new JTextField(15);
        txtPartName = new JTextField(15);
        txtQuantity = new JTextField(10);
        txtPricePerUnit = new JTextField(10);

        btnCalculate = new JButton("Calculate");
        btnSave = new JButton("Save Quote");
        btnClear = new JButton("Clear");
        btnAddJob = new JButton("Add Job");
        btnPrint = new JButton("Print Quote");

        String[] columnNames = {"Part Name", "Quantity", "Price per Unit", "Total"};
        tableModel = new DefaultTableModel(columnNames, 0);
        jobTable = new JTable(tableModel);

        btnCalculate.addActionListener(this);
        btnSave.addActionListener(this);
        btnClear.addActionListener(this);
        btnAddJob.addActionListener(this);
        btnPrint.addActionListener(this);

        setGUI();
    }

    public void setGUI() {
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlNorth.setLayout(new FlowLayout());
        pnlNorth.add(lblTitle);
        add(pnlNorth, BorderLayout.NORTH);

        pnlCenter.setLayout(new GridLayout(5, 2, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        pnlCenter.add(lblClientName);
        pnlCenter.add(txtClientName);
        pnlCenter.add(lblPartName);
        pnlCenter.add(txtPartName);
        pnlCenter.add(lblQuantity);
        pnlCenter.add(txtQuantity);
        pnlCenter.add(lblPricePerUnit);
        pnlCenter.add(txtPricePerUnit);
        pnlCenter.add(lblTotalAmount);
        add(pnlCenter, BorderLayout.CENTER);

        pnlSouth.setLayout(new GridLayout(1, 5, 10, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        pnlSouth.add(btnAddJob);
        pnlSouth.add(btnCalculate);
        pnlSouth.add(btnSave);
        pnlSouth.add(btnClear);
        pnlSouth.add(btnPrint);
        add(pnlSouth, BorderLayout.SOUTH);

        add(new JScrollPane(jobTable), BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCalculate) {
            calculateTableTotal();
        } else if (e.getSource() == btnSave) {
            JOptionPane.showMessageDialog(this, "Quotation saved successfully!");
        } else if (e.getSource() == btnClear) {
            clearFields();
        } else if (e.getSource() == btnAddJob) {
            addJob();
        } else if (e.getSource() == btnPrint) {
            printStyledQuote();
        }
    }

    private void calculateTableTotal() {
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
        lblTotalAmount.setText("Total Amount: R 0.00");
        tableModel.setRowCount(0);
    }

    private void addJob() {
        try {
            String partName = txtPartName.getText();
            int quantity = Integer.parseInt(txtQuantity.getText());
            double pricePerUnit = Double.parseDouble(txtPricePerUnit.getText());
            double total = quantity * pricePerUnit;
            tableModel.addRow(new Object[]{partName, quantity, pricePerUnit, total});
            calculateTableTotal();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid data for all fields.");
        }
    }

    private void printStyledQuote() {
        StringBuilder htmlQuote = new StringBuilder();
        htmlQuote.append("<html><body style='font-family:Arial,sans-serif;'>");
        htmlQuote.append("<h2 style='text-align:center;'>Quotation</h2>");
        htmlQuote.append("<p><b>Client Name:</b> ").append(txtClientName.getText()).append("</p>");
        htmlQuote.append("<table border='1' cellspacing='0' cellpadding='5'>");
        htmlQuote.append("<tr style='background-color:lightgray;'>");
        htmlQuote.append("<th>Part Name</th><th>Quantity</th><th>Price per Unit</th><th>Total</th></tr>");

        double grandTotal = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            htmlQuote.append("<tr>")
                    .append("<td>").append(tableModel.getValueAt(i, 0)).append("</td>")
                    .append("<td>").append(tableModel.getValueAt(i, 1)).append("</td>")
                    .append("<td>").append(tableModel.getValueAt(i, 2)).append("</td>")
                    .append("<td>").append(tableModel.getValueAt(i, 3)).append("</td>")
                    .append("</tr>");
            grandTotal += (double) tableModel.getValueAt(i, 3);
        }
        htmlQuote.append("</table>");
        htmlQuote.append("<h3>Total Amount: R " + String.format("%.2f", grandTotal) + "</h3>");
        htmlQuote.append("</body></html>");

        JEditorPane editorPane = new JEditorPane("text/html", htmlQuote.toString());
        editorPane.setEditable(false);
        try {
            editorPane.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while printing: " + ex.getMessage());
        }
    }
}
