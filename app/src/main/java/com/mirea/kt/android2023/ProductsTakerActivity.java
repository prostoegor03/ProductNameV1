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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductsTakerActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    Button addbutton;
    ProductsDBHelper dataBase;

    DBHelper conteinerDataBase;

    List<Products> products = new ArrayList<>();

    EditText create_product_list_title;

    ProductAdapter productAdapter;

    String IDConteiner;

    Products selectedList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_taker);

        Toolbar tb = findViewById(R.id.toolbar_CreateProductNotes);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("Home ");
            ab.setHomeButtonEnabled(true);//Поддержка конопки назад
            ab.setDisplayHomeAsUpEnabled(true);//Включение
        }

        recyclerView = findViewById(R.id.recyclerProductsTaker);
        create_product_list_title = findViewById(R.id.create_product_list_title);
        addbutton = findViewById(R.id.taker_add_product_card);
        dataBase = new ProductsDBHelper(this);
        conteinerDataBase = new DBHelper(this);
        IDConteiner = getIntent().getStringExtra("BaseID");
        products = dataBase.getListBuyID(IDConteiner);
        updateRecyclerView(products);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productTitle = create_product_list_title.getText().toString();

                Intent intent = new Intent(ProductsTakerActivity.this, ProductsCardTakerActivity.class);
                startActivityForResult(intent,201);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 201){
                String title = data.getStringExtra("title");
                String quantity = data.getStringExtra("quantity");
               Log.i("ValueCheck","Прилетело из intent " + title + " " + quantity);

                Products new_products = new Products();
                new_products.setTitle(title);
                new_products.setQuantitu(quantity);
                new_products.setConteinerID(IDConteiner);
                new_products.setIsBuy("0");

               Log.i("ValueCheck","лежит в классе " + new_products.getTitle() + " "
                       + new_products.getQuantitu() + "Айдишник " + IDConteiner);

                dataBase.addOne(new_products);
                products.clear();
                products.addAll(dataBase.getListBuyID(IDConteiner));

                Log.i("ValueCheck ","Размер вернувшейся базы " + products.size());
                for (int i = 0 ; i < products.size();i++){
                    Log.i("ValueCheck ","То что прилетело из базы "
                            + products.get(i).getTitle()+" "
                            + products.get(i).getQuantitu());
                }

                productAdapter.notifyDataSetChanged();
            }
            if (requestCode == 202){
                String title = data.getStringExtra("title");
                String quantity = data.getStringExtra("quantity");
                String cardID = data.getStringExtra("cardID");
                Log.i("IntentCheck","То что достали из intentd \n"
                        + title + "\n"
                        + quantity + "\n"
                        + cardID + "\n");

                Products request_products = new Products();
                request_products.setQuantitu(quantity);
                request_products.setTitle(title);
                request_products.setConteinerID(IDConteiner);

                Log.i("IntentCheck","То что лежит в классе \n"
                        + request_products.getTitle() + "\n"
                        + request_products.getQuantitu() + "\n"
                        + request_products.getID() + "\n");

                dataBase.updateOne(cardID,request_products);
                products.clear();
                products.addAll(dataBase.getListBuyID(IDConteiner));
                productAdapter.notifyDataSetChanged();
            }

    }

    private void updateRecyclerView(List<Products> products) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        productAdapter = new ProductAdapter(ProductsTakerActivity.this,products,productClickListener);
        recyclerView.setAdapter(productAdapter);

    }
    private final ProductClickListener productClickListener = new ProductClickListener() {
        @Override
        public void onClick(Products products) {
            Intent intent = new Intent(ProductsTakerActivity.this,ProductsCardTakerActivity.class);
            intent.putExtra("title",products.getTitle());
            intent.putExtra("quantity",products.getQuantitu());
            intent.putExtra("cardID",products.getID());
            startActivityForResult(intent,202);
        }

        @Override
        public void onLongClick(Products products, CardView cardView) {
            selectedList = new Products();
            selectedList = products;
            ShowPopUpProductTaker(cardView);
        }
    };

    private void ShowPopUpProductTaker(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.long_clicl_prosuct_card_popup);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.isBuy) {
            Log.i("IsBuy","IsBuy до изменений: " + selectedList.getIsBuy());
            if (selectedList.getIsBuy().equals("0")) {
                selectedList.setIsBuy("1");
                dataBase.updateOne(selectedList.getID(), selectedList);
                productAdapter.notifyDataSetChanged();
                Log.i("IsBuy","(улетел в isBuy == 0)IsBuy после изменений: " + selectedList.getIsBuy());
                return true;
            } else {
                selectedList.setIsBuy("0");
                dataBase.updateOne(selectedList.getID(), selectedList);
                productAdapter.notifyDataSetChanged();
                Log.i("IsBuy","(улетел в isBuy == 1)IsBuy после изменений: " + selectedList.getIsBuy());
                return true;
            }
        } else if (itemID == R.id.deleateCard) {
            dataBase.removeOne(selectedList.getID());
            products.remove(selectedList);
            productAdapter.notifyDataSetChanged();
            Toast.makeText(ProductsTakerActivity.this,"Товар удален",Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }
}