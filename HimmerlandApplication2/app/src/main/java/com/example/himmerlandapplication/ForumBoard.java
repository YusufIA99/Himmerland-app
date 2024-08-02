package com.example.himmerlandapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.himmerlandapplication.adapters.ChatAdapter;
import com.example.himmerlandapplication.models.ChatMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ForumBoard extends Fragment {

    EditText inputText;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    String besked;
    FrameLayout sendMessage;
    AppCompatImageView sendImg;
    TextView textname;

    RecyclerView recyclerView;
    ArrayList chatmessageArrayList;
    ChatAdapter chatAdapter;
    FirebaseFirestore db;
    String userid;
    DocumentReference reference;
    String afdeling;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_board, container, false);

        textname = view.findViewById(R.id.textName);

        recyclerView = view.findViewById(R.id.chatRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));

        db = FirebaseFirestore.getInstance();
        chatmessageArrayList = new ArrayList<ChatMessage>();
        chatAdapter = new ChatAdapter(getContext(), chatmessageArrayList);

        recyclerView.setAdapter(chatAdapter);



        inputText = (EditText) view.findViewById(R.id.inputText);
        sendMessage = (FrameLayout) view.findViewById(R.id.layoutSend);
        sendImg = view.findViewById(R.id.sendMessage);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataTilCollection();
                inputText.getText().clear();
            }
        });

        EventChangeListener();

        return view;
    }

    public void sendDataTilCollection(){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());

        besked = inputText.getText().toString();

        db = FirebaseFirestore.getInstance();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = db.collection("users").document(userid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String fornavn = documentSnapshot.getString("Fornavn");
                    String efternavn = documentSnapshot.getString("Efternavn");
                    String afdeling = documentSnapshot.getString("afdeling");
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("message", besked);
                    data.put("dateTime", Date);
                    data.put("fornavn", fornavn);
                    data.put("efternavn", efternavn);
                    data.put("afdeling", afdeling);
                    db.collection("beskeder").document().set(data);
                }
            }
        });
    }

    private void EventChangeListener() {

        db = FirebaseFirestore.getInstance();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = db.collection("users").document(userid);

        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    afdeling = documentSnapshot.getString("afdeling");
                    textname.setText(afdeling);
                    String beskeder = textname.getText().toString();
                    db.collection("beskeder").whereEqualTo("afdeling", beskeder).orderBy("dateTime", Query.Direction.DESCENDING)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                    if (error != null){
                                        Log.e(TAG, "onEvent: Firestore error", error);
                                        return;
                                    }

                                    for (DocumentChange dc : value.getDocumentChanges()){
                                        if (dc.getType() == DocumentChange.Type.ADDED){
                                            chatmessageArrayList.add(dc.getDocument().toObject(ChatMessage.class));
                                        }

                                        chatAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                }
            }
        });
    }
}