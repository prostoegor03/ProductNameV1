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
import android.os.Message;
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

public class HomeActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    Button addbutton;
    NotesAdapter notesAdapter;
    DBHelper dataBase;

    ProductsDBHelper productsDataBase;

    Notes selectedNote;

    List<Notes> notes = new ArrayList<>();
    List<Products> products = new ArrayList<>();

    String BaseConteiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar tb = findViewById(R.id.toolbar_home);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();



        recyclerView = findViewById(R.id.recycler_home);
        addbutton = findViewById(R.id.home_add_button);
        dataBase = new DBHelper(this);
        productsDataBase = new ProductsDBHelper(this);
        notes = dataBase.getAll();
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
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.create_notes_popup);//привязываем макет к меню
        popupMenu.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("123", "requestCode " + requestCode);
        //Cоздание заметки
        if (requestCode == 101) {
            try {
                Log.i("Create", "прилетел код" + requestCode);
                String inTitle = data.getStringExtra("title");
                String inContent = data.getStringExtra("content");
                Log.i("ExternalValue", "То что прилетело в HomeActivity" + inTitle + " \n" + inContent);


                Notes createNotes = new Notes();
                createNotes.setTitle(inTitle);
                createNotes.setContent(inContent);
                createNotes.setType("Notes");

                Log.d("ExternalValue", "Работа addOne");

                dataBase.addOne(createNotes);
                notes.clear();
                notes.addAll(dataBase.getAll());

                Log.i("ExternalValue", "То что вернулось из базы" + notes);
                notesAdapter.notifyDataSetChanged();
            } catch (NullPointerException ex) {
                Toast.makeText(getApplicationContext(), "Создание заметки отменено", Toast.LENGTH_SHORT);
            }

        }
        //Редачим заметку
        else if (requestCode == 102) {
            Log.i("Create", "прилетел код" + requestCode);
            String inId = data.getStringExtra("id");
            String inTitle = data.getStringExtra("title");
            String inContent = data.getStringExtra("content");
            Log.i("ExternalValue", "То что прилетело в HomeActivity при обнове" + inId + " " + inTitle + " " + inContent);

            Notes createNotes = new Notes();

            createNotes.setTitle(inTitle);
            createNotes.setContent(inContent);
            createNotes.setType("Notes");
            Log.d("ExternalValue", "Работа addOne");

            dataBase.updateOne(inId, createNotes);
            notes.clear();
            notes.addAll(dataBase.getAll());

            Log.i("ExternalValue", "То что вернулось из базы" + notes);
            notesAdapter.notifyDataSetChanged();
            //Cоздание спика
        } else if (requestCode == 301) {

            try{
                String newTitle = data.getStringExtra("newTitle");
                String IDConteiner = data.getStringExtra("IDConteiner");
                Log.i("123", "Достали в HomeActivity " + newTitle + " " + IDConteiner);
                Notes createNotes = new Notes();
                createNotes.setTitle(newTitle);
                createNotes.setType("ProductList");

                Log.i("request302", "То что улетело в класс \n" + createNotes.getTitle() + "\n" + createNotes.getType() + "\n");
                dataBase.updateOne(IDConteiner, createNotes);
                notes.clear();
                notes.addAll(dataBase.getAll());
                updateRececlerView(notes);
            }catch (NullPointerException npe){
                dataBase.removeOne(BaseConteiner);
            }
        }

    }

    private void updateRececlerView(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        //Задали recyclerView с Linear Manager 2 столбца вертикальная ориентация

        notesAdapter = new NotesAdapter(HomeActivity.this, notes,notesClickListener);
        recyclerView.setAdapter(notesAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            String NotesType = notes.getType();
            if (NotesType.equals("Notes")) {
                Intent intent = new Intent(HomeActivity.this, NotesTakerActivity.class);
                intent.putExtra("get_id", notes.getID());
                intent.putExtra("get_title", notes.getTitle());
                intent.putExtra("get_content", notes.getContent());
                startActivityForResult(intent, 102);
            } else if (NotesType.equals("ProductList")) {
                Intent intent = new Intent(HomeActivity.this, ProductsTakerActivity.class);
                intent.putExtra("BaseID", notes.getID());
                intent.putExtra("get_title", notes.getTitle());
                startActivityForResult(intent, 301);
            }
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopUp(cardView);

        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.long_click_notes_popup);//привязываем макет к меню
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
//Через switch не работает просит константы

//для меню при удаление заметки
        int itemId = item.getItemId();

        if (itemId == R.id.delete) {
            if (selectedNote.getType().equals("Notes")) {
                dataBase.removeOne(selectedNote.getID());
                notes.remove(selectedNote);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(HomeActivity.this, "Заметка удалена", Toast.LENGTH_SHORT).show();
                return true;
            } else if (selectedNote.getType().equals("ProductList")) {
                productsDataBase.removeListBuyID(selectedNote.getID());
                dataBase.removeOne(selectedNote.getID());
                notes.remove(selectedNote);
                notesAdapter.notifyDataSetChanged();
                Toast.makeText(HomeActivity.this, "Список был удален", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                return false;
            }
        } else if (itemId == R.id.share) {
            if (selectedNote.getType().equals("Notes")){
                String message = selectedNote.getContent();
                Intent sendIntent = new Intent();
                sendIntent.setAction(sendIntent.ACTION_SEND);
                sendIntent.putExtra(sendIntent.EXTRA_TEXT,selectedNote.getTitle() +"\n" + message);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Отправить Сообщение"));
                return true;
            }
            else {
                String IDConteiner = selectedNote.getID();
                products.addAll(productsDataBase.getListBuyID(IDConteiner));
                int sizeOfList = products.size();
                Log.i("ShareCheck","Размер прилетефшего списка" + sizeOfList);
                String shareMessage = selectedNote.getTitle() + "\n";

                for (int i = 0 ; i < sizeOfList ; i++){
                    Log.i("ShareCheck","Вытащили " + "i=" + i + " " + products.get(i).toString());
                   shareMessage = shareMessage.concat(products.get(i).toString());
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(sendIntent.ACTION_SEND);
                sendIntent.putExtra(sendIntent.EXTRA_TEXT,shareMessage);

                Log.i("ShareCheck","Положили в Intent \n" + shareMessage);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Отправить Список покупок"));
                return true;
            }
        }


        //Если при добавлении пользователь решил создать заметку
        else if (itemId == R.id.popUpCreateNotes) {
            Intent intent = new Intent(HomeActivity.this, NotesTakerActivity.class);
            startActivityForResult(intent, 101);

            return true;
        }
        //Если при добавлении пользователь решил создать продуктовую корзину;
        else if (itemId == R.id.popUpCreateList) {
            BaseConteiner = dataBase.createConteiner();
            Intent intent = new Intent(HomeActivity.this, ProductsTakerActivity.class);
            intent.putExtra("BaseID", BaseConteiner);
            startActivityForResult(intent, 301);
            return true;
        } else {
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