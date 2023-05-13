package com.mirea.kt.android2023;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

public class NotesTakerActivity extends AppCompatActivity{

    EditText create_notes_title , create_notes_content;
    ImageView ic_save_create_notise;

    Notes notes;

    boolean isOldNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        ic_save_create_notise = findViewById(R.id.ic_save_create_notise);
        create_notes_title = findViewById(R.id.create_notes_title);
        create_notes_content = findViewById(R.id.create_notes_content);

            try {
                String id = getIntent().getStringExtra("get_id");
                String title = getIntent().getStringExtra("get_title");
                String content = getIntent().getStringExtra("get_content");
                Log.i("Create","То что прилитело из intent " + id + " " + title + " " + content);

                if (!id.isEmpty() && !title.isEmpty() && !content.isEmpty()){
                create_notes_title.setText(title);
                create_notes_content.setText(content);
                isOldNote = true;
                Log.i("Create","try отработал полностью, isOldNote принял значение " + isOldNote);
                }
            }catch (Exception e){
                Log.i("ExternalValue","Ошибка в установке значений при редактировании");
                Log.i("Create","try отлетел в catch " + isOldNote);
                e.printStackTrace();

            }
        ic_save_create_notise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = create_notes_title.getText().toString();
                String content = create_notes_content.getText().toString();


                if (!isOldNote) {
                    Log.i("Create","Отрабатывает создание заметок isOldNote принял значение " + isOldNote);
                    if (!title.isEmpty() && !title.isEmpty()) {
                        Intent intent = new Intent();
                        Log.i("ExternalValue", "То что прилитело из NotesTakerActivity" + title + " " + content);
                        intent.putExtra("title", title);
                        intent.putExtra("content", content);
                        setResult(101, intent);
                        Log.i("Create","resultCode 101");

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Поэалуйста заполните поля", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    String id = getIntent().getStringExtra("get_id");
                    Log.i("Idcheck","Айдишник который прилетел " + " " + id);
                    Log.i("Create","Отрабатывает редактор заметок isOldNote принял значение " + isOldNote);
                    if (!title.isEmpty() && !title.isEmpty()) {

                        Intent intent = new Intent();
                        Log.i("ExternalValue", "То что прилитело из NotesTakerActivity при обновлении" + title + " " + content);
                        intent.putExtra("id", id);
                        intent.putExtra("title", title);
                        intent.putExtra("content", content);
                        setResult(102, intent);
                        Log.i("Create","resultCode 102");
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}