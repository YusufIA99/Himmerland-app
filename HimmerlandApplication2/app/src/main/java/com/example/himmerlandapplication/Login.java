package com.example.himmerlandapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        Button loginBtn = findViewById(R.id.loginBtn);
        TextView register = findViewById(R.id.register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bekraftBruger();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skiftTilOpret();
            }
        });

    }

    private void bekraftBruger()
    {
        EditText emailLogin = findViewById(R.id.emailLogin);
        EditText passwordLogin = findViewById(R.id.passwordLogin);

        String emailTxt = emailLogin.getText().toString();
        String passwordTxt = passwordLogin.getText().toString();

        if (emailTxt.isEmpty() || passwordTxt.isEmpty())
        {
            Toast.makeText(Login.this, "Indtast venligst Email og Kodeord", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showMainActivity();
                        } else {
                            Toast.makeText(Login.this, "Login mislykket", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void showMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void skiftTilOpret()
    {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
}