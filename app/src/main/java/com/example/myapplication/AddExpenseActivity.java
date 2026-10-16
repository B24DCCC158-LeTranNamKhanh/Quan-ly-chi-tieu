package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtTitle;
    private EditText edtAmount;
    private Spinner spCategory;
    private DatePicker dpDate;
    private EditText edtNote;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        spCategory = findViewById(R.id.spCategory);
        dpDate = findViewById(R.id.dpDate);
        edtNote = findViewById(R.id.edtNote);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        String title = edtTitle.getText().toString().trim();
        String amountStr = edtAmount.getText().toString().trim();
        String category = spCategory.getSelectedItem().toString();
        String note = edtNote.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            edtTitle.setError(getString(R.string.error_empty_title));
            edtTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            edtAmount.setError(getString(R.string.error_invalid_amount));
            edtAmount.requestFocus();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                edtAmount.setError(getString(R.string.error_invalid_amount));
                edtAmount.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            edtAmount.setError(getString(R.string.error_invalid_amount));
            edtAmount.requestFocus();
            return;
        }

        int day = dpDate.getDayOfMonth();
        int month = dpDate.getMonth() + 1;
        int year = dpDate.getYear();
        String dateStr = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year);

        Expense newExpense = new Expense(
                UUID.randomUUID().toString(),
                title,
                amount,
                category,
                dateStr,
                note
        );

        ExpenseManager.getInstance().addExpense(newExpense);
        Toast.makeText(this, "Đã lưu khoản chi", Toast.LENGTH_SHORT).show();
        finish();
    }
}
