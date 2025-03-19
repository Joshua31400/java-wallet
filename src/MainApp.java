package budgetmanager;

import budgetmanager.Income;
import budgetmanager.Expense;
import service.BudgetService;

import java.time.LocalDate;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        BudgetService budgetService = new BudgetService();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Personal Budget Manager ===");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Current Balance");
            System.out.println("4. Show Transactions");
            System.out.println("5. Delete Transaction");
            System.out.println("6. Quit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Income ID: ");
                    String incomeId = scanner.nextLine();
                    System.out.print("Income Amount: ");
                    double incomeAmount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Date (YYYY-MM-DD): ");
                    LocalDate incomeDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Category: ");
                    String incomeCategory = scanner.nextLine();
                    System.out.print("Description: ");
                    String incomeDescription = scanner.nextLine();

                    budgetService.addTransaction(new Income(incomeId, incomeAmount, incomeDate, incomeCategory, incomeDescription));
                    System.out.println("Income added successfully!");
                    break;

                case 2:
                    System.out.print("Expense ID: ");
                    String expenseId = scanner.nextLine();
                    System.out.print("Expense Amount: ");
                    double expenseAmount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Date (YYYY-MM-DD): ");
                    LocalDate expenseDate = LocalDate.parse(scanner.nextLine());
                    System.out.print("Category: ");
                    String expenseCategory = scanner.nextLine();
                    System.out.print("Description: ");
                    String expenseDescription = scanner.nextLine();

                    budgetService.addTransaction(new Expense(expenseId, expenseAmount, expenseDate, expenseCategory, expenseDescription));
                    System.out.println("Expense added successfully!");
                    break;

                case 3:
                    System.out.println("Current Balance: " + budgetService.calculateBalance() + "€");
                    break;

                case 4:
                    System.out.println("\nList of Transactions:");
                    if (budgetService.getTransactions().isEmpty()) {
                        System.out.println("No transactions recorded.");
                    } else {
                        for (var transaction : budgetService.getTransactions()) {
                            System.out.println(transaction);
                        }
                    }
                    break;

                case 5:
                    System.out.print("ID of the transaction to delete: ");
                    String deleteId = scanner.nextLine();
                    budgetService.removeTransaction(deleteId);
                    System.out.println("Transaction deleted!");
                    break;

                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }
}