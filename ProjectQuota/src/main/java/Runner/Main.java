package Runner;

import DatabaseConnection.SupabaseConnection;
import GUI.loginGUI;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    loginGUI run = new loginGUI();
    run.setGUI();

    //tried testing supabase but dololo luck on the db

//        Connection conn = SupabaseConnection.connectToSupabase();

    }
}