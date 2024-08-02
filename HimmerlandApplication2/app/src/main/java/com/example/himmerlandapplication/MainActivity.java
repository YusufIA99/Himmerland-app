package com.example.himmerlandapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    BottomNavigationView bottomNavigationView;

    MinSide minSide = new MinSide();
    ForumBoard forumBoard = new ForumBoard();
    Kontakt kontakt = new Kontakt();
    HimmerlandBeskeder himmerlandBeskeder = new HimmerlandBeskeder();

    Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textInputLayout = findViewById(R.id.afdelingMenu);
        autoCompleteTextView = findViewById(R.id.afdelingDrop);

        bottomNavigationView = findViewById(R.id.navigationsBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, minSide).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.minside:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, minSide).commit();
                        return true;

                    case R.id.forum:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, forumBoard).commit();
                        return true;

                    case R.id.himmerland:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, himmerlandBeskeder).commit();
                        return true;

                    case R.id.kontakt:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, kontakt).commit();
                        return true;

                }
                return false;
            }
        });

    }

}