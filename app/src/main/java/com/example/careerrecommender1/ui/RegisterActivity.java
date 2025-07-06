package com.example.careerrecommender1.ui;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.careerrecommender1.R;
import com.example.careerrecommender1.api.ApiService;
import com.example.careerrecommender1.api.RetrofitClient;
import com.example.careerrecommender1.model.ApiResponse;
import com.example.careerrecommender1.model.User;
import com.example.careerrecommender1.util.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;
public class RegisterActivity  extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput, confirmPassword;
    private Button registerBtn;

    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = findViewById(R.id.etFullName);
        emailInput = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);

        apiService = RetrofitClient.getApiService();
        sessionManager = new SessionManager(this);

        registerBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String conPassword = confirmPassword.getText().toString().trim();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            registerUser(name, email, password);
        });
    }

    private void registerUser(String name, String email, String password) {
        User user = new User(name, email, password);

        apiService.registerUser(user).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse res = response.body();
                    if(res.isSuccess()) {
                        Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        sessionManager.saveUserSession(name, email);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
