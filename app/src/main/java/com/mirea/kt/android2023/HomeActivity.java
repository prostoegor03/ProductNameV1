package com.mirea.kt.android2023;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    Button addbutton;
    NotesAdapter notesAdapter;
    DBHelper dataBase;

    Notes selectedNote;

    List<Notes> notes = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar tb = findViewById(R.id.toolbar_home);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("Home ");
            ab.setHomeButtonEnabled(true);//Поддержка конопки назад
            ab.setDisplayHomeAsUpEnabled(true);//Включение
        }


        recyclerView = findViewById(R.id.recycler_home);
        addbutton = findViewById(R.id.home_add_button);
        dataBase = new DBHelper(this);
        notes= dataBase.getAll();
        NotesAdapter notesAdapter;

        updateRececlerView(notes);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUpMenuButton(v);
            }
        });
    }

    private void showPopUpMenuButton(View v) {
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.create_notes_popup);//привязываем макет к меню
        popupMenu.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            Log.i("Create","прилетел код" + requestCode);
                String inTitle = data.getStringExtra("title");
                String inContent = data.getStringExtra("content");
                Log.i("ExternalValue","То что прилетело в HomeActivity" + inTitle + " " + inContent);

                Notes createNotes = new Notes();
                createNotes.setTitle(inTitle);
                createNotes.setContent(inContent);

                Log.d("ExternalValue", "Работа addOne");

                dataBase.addOne(createNotes);
                notes.clear();
                notes.addAll(dataBase.getAll());

                Log.i("ExternalValue","То что вернулось из базы" + notes);
                notesAdapter.notifyDataSetChanged();

        }
        if(requestCode == 102){
            Log.i("Create","прилетел код" + requestCode);
            String inId = data.getStringExtra("id");
            String inTitle = data.getStringExtra("title");
            String inContent = data.getStringExtra("content");
            Log.i("ExternalValue","То что прилетело в HomeActivity при обнове" + inId + " "+ inTitle + " " + inContent);

            Notes createNotes = new Notes();

            createNotes.setTitle(inTitle);
            createNotes.setContent(inContent);

            Log.d("ExternalValue", "Работа addOne");

            dataBase.updateOne(inId,createNotes);
            notes.clear();
            notes.addAll(dataBase.getAll());

            Log.i("ExternalValue","То что вернулось из базы" + notes);
            notesAdapter.notifyDataSetChanged();

        }

    }

    private void updateRececlerView(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        //Задали recyclerView с Linear Manager 2 столбца вертикальная ориентация

        notesAdapter = new NotesAdapter(HomeActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(HomeActivity.this , NotesTakerActivity.class);
            intent.putExtra("get_id",notes.getID());
            intent.putExtra("get_title",notes.getTitle());
            intent.putExtra("get_content",notes.getContent());
            startActivityForResult(intent,102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopUp(cardView);

        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.long_click_notes_popup);//привязываем макет к меню
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
//Через switch не работает просит константы

//для меню при удаление заметки
            int itemId = item.getItemId();

            if (itemId == R.id.delete){
                dataBase.removeOne(selectedNote.getID());
                notes.remove(selectedNote);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(HomeActivity.this,"Заметка удалена",Toast.LENGTH_SHORT).show();
                return true;
            }
            //Если при добавлении пользователь решил создать заметку
            else if(itemId == R.id.popUpCreateNotes) {
                Intent intent = new Intent(HomeActivity.this , NotesTakerActivity.class);
                startActivityForResult(intent,101);

                return true;
            }
            //Если при добавлении пользователь решил создать продуктовую корзину;
            else if(itemId == R.id.popUpCreateList){
                String BaseConteiner = dataBase.createConteiner();
                Intent intent = new Intent(HomeActivity.this,ProductsTakerActivity.class);
                intent.putExtra("BaseID",BaseConteiner);
                startActivity(intent);
                return true;
            }
            else {
                return false;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }
}