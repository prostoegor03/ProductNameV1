package com.mirea.kt.android2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("NotesProduct","Erros :(");
        Button btnStart = findViewById(R.id.btnStart);
        EditText inpupLog = findViewById(R.id.inputLog);
        EditText inputPswd = findViewById(R.id.inputPass);

        btnStart.setOnClickListener(v ->{
            String login = inpupLog.getText().toString();
            String pswd = inputPswd.getText().toString();
/*
            if(!login.isEmpty() && !pswd.isEmpty()){
                String server = "https://android-for-students.ru";
                String serverPath = "/coursework/login.php";
                HashMap<String,String> map = new HashMap();
                map.put("lgn", login);
                map.put("pwd", pswd);
                map.put("g", "RIBO-02-21");
                HTTPRunnable hTTPRunnable = new HTTPRunnable(server + serverPath, map);
                Thread th = new Thread(hTTPRunnable);
                th.start();

                try{
                    th.join();
                }catch(InterruptedException ex){
                    Log.d("FalseMain","Ошибка при ожидании потока",ex);
                }
                finally {

                    try {
                        JSONObject jSONObject = new JSONObject(hTTPRunnable.getResponseBody());
                        result = jSONObject.getInt("result_code");
                    } catch (JSONException e) {
                        Log.d("FalseMain","Ошибка при обработке JSON",e);
                    }finally {
                        if(result == 1){
                            Intent actIntent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(actIntent);
                        }else {
                            Toast.makeText(getApplicationContext(),"Введенные данные некоректны",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }else {
                Toast.makeText(getApplicationContext(),"Пожалуйста введите данные для авторизации",Toast.LENGTH_LONG).show();
           }
           */
            Intent actIntent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(actIntent);
        });
    }
}