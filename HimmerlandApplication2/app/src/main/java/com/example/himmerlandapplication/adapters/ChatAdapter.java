package com.example.himmerlandapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himmerlandapplication.R;
import com.example.himmerlandapplication.models.ChatMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    Context context;
    ArrayList<ChatMessage> chatMessageArrayList;
    FirebaseFirestore db;
    DocumentReference reference;
    String userid;

    public ChatAdapter(Context context, ArrayList<ChatMessage> chatMessageArrayList) {
        this.context = context;
        this.chatMessageArrayList = chatMessageArrayList;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_container_sent_message, parent, false);




        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        ChatMessage chatMessage = chatMessageArrayList.get(position);
        String navn = chatMessage.fornavn + " " + chatMessage.efternavn;

        holder.textMessage.setText(chatMessage.message);
        holder.textDateTime.setText(chatMessage.dateTime);
        holder.textFornavnEfternavn.setText(navn);

    }

    @Override
    public int getItemCount() {
        return chatMessageArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textMessage;
        TextView textDateTime;
        TextView textFornavnEfternavn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            textFornavnEfternavn = itemView.findViewById(R.id.textFornavnEfternavn);


        }
    }
}



