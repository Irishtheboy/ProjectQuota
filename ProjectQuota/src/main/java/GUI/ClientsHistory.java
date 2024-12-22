package GUI;

import Queries.ClientQueries;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientsHistory extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JButton returnButton;
    private JButton logoutButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClientsHistory() {
        // Set the window title and layout
        setTitle("Quota Live - Clients History");
        // Set custom frame icon
        ImageIcon frameIcon = new ImageIcon(getClass().getResource("/Logo-cut.png"));
        setIconImage(frameIcon.getImage());
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create search bar with search button
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JLabel searchLabel = new JLabel("Search by Client Name:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Create the table and add it to a scroll pane
        tableModel = new DefaultTableModel(new String[]{"Quote ID", "Client Name", "Quote Date", "Part Name", "Quantity", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Create buttons panel for "Return" and "Log Out"
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        returnButton = new JButton("Return to Previous Window");
        logoutButton = new JButton("Log Out");

        buttonPanel.add(returnButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);

        // Add search button action listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the table
                tableModel.setRowCount(0);
                
                String searchQuery = searchField.getText().trim();
                try {
                    // Get the sold items from the database based on the search query
                    ArrayList<String[]> soldItems = ClientQueries.getSoldItemsHistory(searchQuery);
                    for (String[] item : soldItems) {
                        tableModel.addRow(item);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error retrieving data: " + ex.getMessage());
                }
            }
        });

        // Return button action listener
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                dispose();  
                new MainDashboard();
                // You can instantiate the previous window here (e.g., new MainMenu().setVisible(true);)
            }
        });

        // Log out button action listener
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset session (if applicable) and return to the login screen
                JOptionPane.showMessageDialog(null, "You have been logged out.");
                dispose();  // Close the current window

                // Assuming you have a LoginWindow class, you can re-open it
                // new LoginWindow().setVisible(true);
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientsHistory();
            }
        });
    }
}
