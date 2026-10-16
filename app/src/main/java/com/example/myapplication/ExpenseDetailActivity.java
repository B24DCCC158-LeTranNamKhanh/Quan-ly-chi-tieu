package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvTitleDetail;
    private TextView tvAmountDetail;
    private TextView tvCategoryDetail;
    private TextView tvDateDetail;
    private TextView tvNoteDetail;
    private Button btnBack;
    private Button btnDelete;

    private Expense currentExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvTitleDetail = findViewById(R.id.tvTitleDetail);
        tvAmountDetail = findViewById(R.id.tvAmountDetail);
        tvCategoryDetail = findViewById(R.id.tvCategoryDetail);
        tvDateDetail = findViewById(R.id.tvDateDetail);
        tvNoteDetail = findViewById(R.id.tvNoteDetail);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        if (getIntent() != null && getIntent().hasExtra("expense")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                currentExpense = getIntent().getSerializableExtra("expense", Expense.class);
            } else {
                currentExpense = (Expense) getIntent().getSerializableExtra("expense");
            }
        }

        if (currentExpense != null) {
            tvTitleDetail.setText(currentExpense.getTitle());
            tvAmountDetail.setText(ExpenseManager.getInstance().formatCurrency(currentExpense.getAmount()));
            tvCategoryDetail.setText(currentExpense.getCategory());
            tvDateDetail.setText(currentExpense.getDate());
            tvNoteDetail.setText(currentExpense.getNote().isEmpty() ? "Không có" : currentExpense.getNote());
        }

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            if (currentExpense != null) {
                ExpenseManager.getInstance().removeExpenseById(currentExpense.getId());
                Toast.makeText(this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
