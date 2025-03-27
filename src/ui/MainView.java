package ui;

import service.BudgetService;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private BudgetService budgetService;

    public MainView() {
        budgetService = new BudgetService();
        String username = "Joshua31400"; // Remplacez par le nom d'utilisateur approprié

        setTitle("Personal Budget Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        TransactionPanel transactionPanel = new TransactionPanel(budgetService, username);
        add(transactionPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}