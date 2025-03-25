package ui;

import service.BudgetService;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private BudgetService budgetService;

    public MainView() {
        budgetService = new BudgetService();

        setTitle("Personal Budget Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        TransactionPanel transactionPanel = new TransactionPanel(budgetService);
        add(transactionPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }
}