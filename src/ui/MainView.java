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
    }

    private void showLoginPanel() {
        LoginPanel loginPanel = new LoginPanel(this);
        setContentPane(loginPanel);
        revalidate();
    }

    public void showTransactionPanel(String username) {
        budgetService = new BudgetService(username);
        TransactionPanel transactionPanel = new TransactionPanel(budgetService, username);
        setContentPane(transactionPanel);
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}