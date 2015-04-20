package com.king.chatdemo.Message;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * User: king
 * Date: 2015/4/16
 */
public class ChatService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public ChatService getService() {

            return ChatService.this;
        }
    }

    public String getSend() {
        Random random = new Random();
        int x = random.nextInt(1000);
        return x + "";
    }
}
