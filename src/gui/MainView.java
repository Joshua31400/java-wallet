package ui;

import service.BudgetService;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private BudgetService budgetService;

    public MainView() {
        setTitle("Personal Budget Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        showLoginPanel();
        setLocationRelativeTo(null);
    }

    private void showLoginPanel() {
        ui.LoginPanel loginPanel = new ui.LoginPanel(this);
        setContentPane(loginPanel);
        revalidate();
    }

    public void showTransactionPanel(String username, String password) {
        budgetService = new BudgetService(username, password);
        ui.TransactionPanel transactionPanel = new ui.TransactionPanel(budgetService, username, password);
        setContentPane(transactionPanel);
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}