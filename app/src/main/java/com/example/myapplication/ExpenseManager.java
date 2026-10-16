package com.example.myapplication;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ExpenseManager {
    private static ExpenseManager instance;
    private final List<Expense> expenses;

    private ExpenseManager() {
        expenses = new ArrayList<>();
        initSampleData();
    }

    public static synchronized ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    private void initSampleData() {
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        expenses.add(new Expense(UUID.randomUUID().toString(), "Ăn sáng", 30000, "Ăn uống", today, "Bún bò"));
        expenses.add(new Expense(UUID.randomUUID().toString(), "Đổ xăng", 50000, "Đi lại", today, "Xăng A95"));
        expenses.add(new Expense(UUID.randomUUID().toString(), "Mua sách", 120000, "Học tập", today, "Sách Lập trình Android"));
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(0, expense);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    public void removeExpenseById(String id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId().equals(id)) {
                expenses.remove(i);
                break;
            }
        }
    }

    public double getTotalAmount() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public String formatCurrency(double amount) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        return formatter.format(amount) + " đ";
    }
}
