package com.example.assignment1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.assignment1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class HomeFragment extends Fragment {

    public TextView totalValue;
    public TextInputEditText kWhValue, percentageValue;
    public TextInputLayout kWhInputLayout, percentageInputLayout;
    public Button btnCalculate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        totalValue = root.findViewById(R.id.totalValue);
        kWhValue = root.findViewById(R.id.kWhValue);
        percentageValue = root.findViewById(R.id.percentageValue);
        kWhInputLayout = root.findViewById(R.id.kWhInputLayout);
        percentageInputLayout = root.findViewById(R.id.percentageInputLayout);
        btnCalculate = root.findViewById(R.id.btnCalculate);

        // Set button click listener
        btnCalculate.setOnClickListener(v -> {
            String unitsText = kWhValue.getText() != null ? kWhValue.getText().toString() : "";
            String rebateText = percentageValue.getText() != null ? percentageValue.getText().toString() : "";

            // Reset previous error messages
            kWhInputLayout.setError(null);
            percentageInputLayout.setError(null);

            // Validate inputs
            if (unitsText.isEmpty()) {
                kWhInputLayout.setError("Please enter the electricity units (kWh).");
                return;
            }
            if (rebateText.isEmpty()) {
                percentageInputLayout.setError("Please enter the rebate percentage.");
                return;
            }

            try {
                double kWhValueInput = Double.parseDouble(unitsText);
                double rebatePercentage = Double.parseDouble(rebateText);

                if (rebatePercentage < 0 || rebatePercentage > 5) {
                    percentageInputLayout.setError("Rebate must be between 0% and 5%.");
                    return;
                }

                double totalCharges = calculateCharges(kWhValueInput);
                double finalCost = totalCharges - (totalCharges * rebatePercentage / 100);

                totalValue.setText("Total Bill: RM " + String.format("%.2f", finalCost));

            } catch (NumberFormatException e) {
                if (!isNumeric(unitsText)) {
                    kWhInputLayout.setError("Enter a valid numeric value for kWh.");
                }
                if (!isNumeric(rebateText)) {
                    percentageInputLayout.setError("Enter a valid numeric value for rebate.");
                }
            }
        });

        return root;
    }

    private double calculateCharges(double units) {
        double total = 0;

        if (units <= 200) {
            total = units * 0.218;
        } else if (units <= 300) {
            total = (200 * 0.218) + ((units - 200) * 0.334);
        } else if (units <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516);
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546);
        }

        return total;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
