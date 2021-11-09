package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    Button login_btn, register_btn;
    EditText email_text, password_text;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            goToTimer();
        }

        login_btn = findViewById(R.id.signInButton);
        register_btn = findViewById(R.id.registerButton);

        email_text = findViewById(R.id.emailInput);
        password_text = findViewById(R.id.passwordInput);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_text.getText().toString();
                String password = password_text.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "Correct login", Toast.LENGTH_SHORT).show();
                            goToTimer();
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(AuthActivity.this, errorCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void goToTimer() {
        Intent i = new Intent(AuthActivity.this, TimerActivity.class);
        startActivity(i);
    }
}