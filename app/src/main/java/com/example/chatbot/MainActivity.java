package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    private final String bot_key = "bot";
    private final String user_key = "user";
    private ArrayList<ChatsModel> chatsModelArrayList;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        editText = findViewById(R.id.edittext);
        floatingActionButton = findViewById(R.id.buttonAction);
        chatsModelArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatsModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter the message first", Toast.LENGTH_LONG).show();
                    return;
                }
                getResponse(editText.getText().toString());
                editText.setText("");
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void getResponse(String message){
        chatsModelArrayList.add(new ChatsModel(message,user_key));
        chatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=173538&key=epcNxyEr6VzIcmyM&uid=[uid]&msg="+message;
        AndroidNetworking.initialize(getApplicationContext());
                AndroidNetworking.get(url)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener(){
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RES",response.toString());
                                try {
                                    String res = response.getString("cnt");
                                    chatsModelArrayList.add(new ChatsModel(res,bot_key));
                                    chatAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                              anError.printStackTrace();
                              Log.d("ERROR",anError.toString());
                            }
                        });
    }
}