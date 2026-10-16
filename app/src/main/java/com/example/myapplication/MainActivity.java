package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private RecyclerView rvExpenses;
    private Button btnAddExpense;
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        rvExpenses = findViewById(R.id.rvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));

        List<Expense> expenseList = ExpenseManager.getInstance().getExpenses();
        adapter = new ExpenseAdapter(expenseList, expense -> {
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("expense", expense);
            startActivity(intent);
        });
        rvExpenses.setAdapter(adapter);

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        List<Expense> expenseList = ExpenseManager.getInstance().getExpenses();
        adapter.updateData(expenseList);
        double total = ExpenseManager.getInstance().getTotalAmount();
        tvTotalAmount.setText(ExpenseManager.getInstance().formatCurrency(total));
    }
}
