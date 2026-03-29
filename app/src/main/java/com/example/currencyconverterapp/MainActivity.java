package com.example.currencyconverterapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button themeBtn = findViewById(R.id.themeBtn);

        themeBtn.setOnClickListener(v -> {
            int currentMode = getResources().getConfiguration().uiMode
                    & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

            if (currentMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        Spinner fromCurrency = findViewById(R.id.fromCurrency);
        Spinner toCurrency = findViewById(R.id.toCurrency);

        String[] currencies = {"INR", "USD", "EUR", "JPY"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                currencies
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);

        Button convertBtn = findViewById(R.id.convertBtn);
        EditText amountInput = findViewById(R.id.amountInput);
        TextView resultText = findViewById(R.id.resultText);

        convertBtn.setOnClickListener(v -> {
            String amountStr = amountInput.getText().toString();

            if (amountStr.isEmpty()) {
                resultText.setText("Enter amount first");
                return;
            }

            double amount = Double.parseDouble(amountStr);

            String from = fromCurrency.getSelectedItem().toString();
            String to = toCurrency.getSelectedItem().toString();

            double result = amount;

            if (from.equals(to)) {
                result = amount;
            } else if (from.equals("INR") && to.equals("USD")) result = amount * 0.012;
            else if (from.equals("USD") && to.equals("INR")) result = amount * 83;
            else if (from.equals("INR") && to.equals("EUR")) result = amount * 0.011;
            else if (from.equals("EUR") && to.equals("INR")) result = amount * 90;
            else if (from.equals("INR") && to.equals("JPY")) result = amount * 1.8;
            else if (from.equals("JPY") && to.equals("INR")) result = amount * 0.55;

            resultText.setText("Converted: " + result);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}