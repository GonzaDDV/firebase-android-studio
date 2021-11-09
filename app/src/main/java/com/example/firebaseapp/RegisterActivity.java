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

public class RegisterActivity extends AppCompatActivity {
    Button login_btn, register_btn;
    EditText email_text, password_text;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        login_btn = findViewById(R.id.signInButton);
        register_btn = findViewById(R.id.registerButton);

        email_text = findViewById(R.id.emailInput);
        password_text = findViewById(R.id.passwordInput);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, AuthActivity.class);
                startActivity(i);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_text.getText().toString();
                String password = password_text.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(RegisterActivity.this, errorCode, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}