package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ChatsModel> chatsModelArrayList;

    public ChatAdapter(Context context, ArrayList<ChatsModel> chatsModelArrayList) {
        this.context = context;
        this.chatsModelArrayList = chatsModelArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case 0:
               view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg,parent,false);
               return new UserViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         ChatsModel chatsModel = chatsModelArrayList.get(position);
         switch (chatsModel.getSender()){
             case "user":
                 ((UserViewHolder)holder).usertextView.setText(chatsModel.getMessage());
             break;
             case "bot":
                 ((BotViewHolder)holder).bottextView.setText(chatsModel.getMessage());
             break;
         }
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsModelArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModelArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView usertextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usertextView = itemView.findViewById(R.id.textviewUser);

        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
     TextView bottextView;
        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            bottextView = itemView.findViewById(R.id.textviewBot);

        }
    }
}
