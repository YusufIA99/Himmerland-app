package com.example.himmerlandapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class MinSide extends Fragment {


    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    TextView firstname;
    TextView lastname;
    TextView email;
    Button addAfdeling;
    Button logoutBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DocumentReference reference = db.collection("users").document(userid);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_min_side, container, false);

        logoutBtn = view.findViewById(R.id.logBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (view.getContext(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        textInputLayout = view.findViewById(R.id.afdelingMenu);
        autoCompleteTextView = view.findViewById(R.id.afdelingDrop);

        if (userid.equals("V0bmUhWSgVhRgimSPxhVcXvS43A2")){
            String [] items ={"Afdeling a", "Afdeling b", "Afdeling c", "admin"};
            ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.afdeling_liste , items);
            autoCompleteTextView.setAdapter(itemAdapter);
        }else {
            String [] items ={"Afdeling a", "Afdeling b", "Afdeling c"};
            ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.afdeling_liste , items);
            autoCompleteTextView.setAdapter(itemAdapter);
        }



        addAfdeling = view.findViewById(R.id.addAfdeling);
        addAfdeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String afdeling = autoCompleteTextView.getText().toString();
                HashMap<String, Object> data = new HashMap<>();
                        data.put("afdeling", afdeling);
                reference.update(data);
                autoCompleteTextView.getText().clear();

            }
        });

        firstname = view.findViewById(R.id.tvFirstName);
        email = view.findViewById(R.id.emailTv);


        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String fornavn = documentSnapshot.getString("Fornavn");
                    String efternavn = documentSnapshot.getString("Efternavn");
                    String mail = documentSnapshot.getString("Email");
                    firstname.setText("" + fornavn + " " + efternavn);
                    email.setText("" + mail);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}