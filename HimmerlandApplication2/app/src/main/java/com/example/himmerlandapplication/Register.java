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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            finish();
            return;
        }

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBruger();
            }
        });

        TextView tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skiftTilLogin();
            }
        });
    }

    private void registerBruger(){
        EditText editFornavn = findViewById(R.id.editFornavn);
        EditText editEfternavn = findViewById(R.id.editEfternavn);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editKodeord = findViewById(R.id.editKodeord);
        EditText editKodeordGentag = findViewById(R.id.editKodeordGentag);

        String fornavn = editFornavn.getText().toString();
        String efternavn = editEfternavn.getText().toString();
        String email = editEmail.getText().toString();
        String kodeord = editKodeord.getText().toString();
        String kodeordGentag = editKodeordGentag.getText().toString();

        if(fornavn.isEmpty() || efternavn.isEmpty() || email.isEmpty() || kodeord.isEmpty() || kodeordGentag.isEmpty())
        {
            Toast.makeText(this, "Udfyld venligst alle felter", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!kodeord.equals(kodeordGentag))
        {
            Toast.makeText(this, "Kodeord matcher ikke", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, kodeord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Bruger bruger = new Bruger(fornavn, efternavn, email);
                            FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("Fornavn", fornavn);
                            data.put("Efternavn", efternavn);
                            data.put("Email", email);
                            firebaseDB.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(data)
                                    .addOnSuccessListener(documentReference ->  {
                                        Toast.makeText(getApplicationContext(), "Bruger registeret", Toast.LENGTH_SHORT).show();
                                        showMainActivity();
                                    })
                                    .addOnFailureListener(exception -> {
                                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });

                            //FirebaseDatabase.getInstance("https://himmerlandbolig-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users")
                              //      .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                //    .setValue(bruger).addOnCompleteListener(new OnCompleteListener<Void>() {
                               // @Override
                               // public void onComplete(@NonNull Task<Void> task) {
                                //    showMainActivity();
                               // }
                           // });
                        //} else {
                         //   Toast.makeText(Register.this, "Oprettelse fejlede", Toast.LENGTH_SHORT).show();
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

    private void skiftTilLogin()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}