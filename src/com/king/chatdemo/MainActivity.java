package com.king.chatdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.king.chatdemo.Message.ChatMessage;
import com.king.chatdemo.Message.ChatService;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private List<ChatMessage> messages;
    private EditText ed_chat;
    private ListView chatListView;
    private ChatAdapter adapter;
    private ServiceConnection conn;
    private ChatService chatService;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ed_chat = (EditText) findViewById(R.id.chat_edit);
        chatListView = (ListView) findViewById(R.id.chat_list);
        messages = new LinkedList<ChatMessage>();
        adapter = new ChatAdapter(messages, this);
        chatListView.setAdapter(adapter);
        intent = new Intent(this, ChatService.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                chatService = ((ChatService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                chatService = null;
            }
        };
        if (chatService == null) {
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    public void btnSend(View view) {
        String content = ed_chat.getText().toString();
        if (content != null && !content.equals("")) {
            ChatMessage message = new ChatMessage();
            message.setContent(content);
            message.setTo("him");
            message.setFrom("me");
            message.setImgId(R.drawable.ic_launcher);
            messages.add(message);

            message = new ChatMessage();
            message.setContent(chatService.getSend());
            message.setTo("me");
            message.setFrom("him");
            message.setImgId(R.drawable.ic_launcher);
            messages.add(message);
            adapter.notifyDataSetChanged();
            chatListView.setSelectionFromTop(messages.size()-1,0);
            ed_chat.setText("");
        } else {
            Toast.makeText(this, "消息为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddImage(View view){
        String content = ed_chat.getText().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        sb.append("/ic-launcher");
        ed_chat.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
