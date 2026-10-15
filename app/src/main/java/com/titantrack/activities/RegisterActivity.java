package com.titantrack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titantrack.R;
import com.titantrack.utils.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private TextInputLayout tilName;
    private TextInputLayout tilEmailReg;
    private TextInputLayout tilPasswordReg;
    private TextInputLayout tilConfirmPassword;
    private TextInputEditText etName;
    private TextInputEditText etEmailReg;
    private TextInputEditText etPasswordReg;
    private TextInputEditText etConfirmPassword;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);

        tilName = findViewById(R.id.tilName);
        tilEmailReg = findViewById(R.id.tilEmailReg);
        tilPasswordReg = findViewById(R.id.tilPasswordReg);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etName = findViewById(R.id.etName);
        etEmailReg = findViewById(R.id.etEmailReg);
        etPasswordReg = findViewById(R.id.etPasswordReg);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvGoLogin = findViewById(R.id.tvGoLogin);

        btnRegister.setOnClickListener(v -> attemptRegister());

        tvGoLogin.setOnClickListener(v -> finish());
    }

    private void attemptRegister() {
        String name = textOf(etName).trim();
        String email = textOf(etEmailReg).trim();
        String password = textOf(etPasswordReg);
        String confirm = textOf(etConfirmPassword);

        // & (y no &&) para evaluar todas las validaciones y mostrar todos los errores a la vez
        boolean valid = validateName(name)
                & validateEmail(email)
                & validatePassword(password)
                & validateConfirm(password, confirm);
        if (!valid) {
            return;
        }

        sessionManager.saveSession(email);

        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean validateName(String name) {
        if (name.isEmpty()) {
            tilName.setError(getString(R.string.error_field_required));
            return false;
        }
        tilName.setError(null);
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            tilEmailReg.setError(getString(R.string.error_field_required));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmailReg.setError(getString(R.string.error_email_invalid));
            return false;
        }
        tilEmailReg.setError(null);
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            tilPasswordReg.setError(getString(R.string.error_field_required));
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            tilPasswordReg.setError(getString(R.string.error_password_short));
            return false;
        }
        tilPasswordReg.setError(null);
        return true;
    }

    private boolean validateConfirm(String password, String confirm) {
        if (confirm.isEmpty()) {
            tilConfirmPassword.setError(getString(R.string.error_field_required));
            return false;
        }
        if (!confirm.equals(password)) {
            tilConfirmPassword.setError(getString(R.string.error_password_mismatch));
            return false;
        }
        tilConfirmPassword.setError(null);
        return true;
    }

    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString();
    }
}