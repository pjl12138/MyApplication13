package com.example.administrator.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
     private TextView textView;
     private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.tv);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x110){
                    textView.setText(msg.obj.toString());
                }
            }
        };
    }
    public void change(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                        url = new URL("https://www.baidu.com");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpURLConnection connection = null;
                    try {
                            connection = (HttpURLConnection) url.openConnection();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            String result = "";
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                result += line;
                                reader.close();
                                Message message = new Message();
                                message.what = 0x110;
                                if (result != null && result != "") {
                                    message.obj = result;
                                } else {
                                    message.obj = "error";
                                }
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        }).start();

    }
}
