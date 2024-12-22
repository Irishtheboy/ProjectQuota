package com.mycompany.projectquota;

import GUI.loginGUI;
import DatabaseConnection.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProjectQuota {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,"Starting...");
//        loginGUI run = new loginGUI();
//        run.setGUI();
        try {
            // Establish the database connection
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
               JOptionPane.showMessageDialog(null,"Database Starting up... Complete!");
            }
            
            // Launch the login GUI
            loginGUI run = new loginGUI();
            run.setGUI();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the database.");
           JOptionPane.showMessageDialog(null, "Failed due to DB");

        }
    }
}
