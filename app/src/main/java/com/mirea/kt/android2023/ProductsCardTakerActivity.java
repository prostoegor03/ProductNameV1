package com.mirea.kt.android2023;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ProductsCardTakerActivity extends AppCompatActivity {
    EditText ProductCardTaker_etCardTitle,ProductCardTaker_etCardQuantity;
    ImageView ic_save_create_product_card;

    boolean isOldCard = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_card_taker);

        ProductCardTaker_etCardTitle = findViewById(R.id.ProductCardTaker_etCardTitle);
        ProductCardTaker_etCardQuantity = findViewById(R.id.ProductCardTaker_etCardQuantity);
        ic_save_create_product_card = findViewById(R.id.ic_save_create_product_card);


        Products products = new Products();
            try {
                String title = getIntent().getStringExtra("title");
                String quantity = getIntent().getStringExtra("quantity");
                String cardID = getIntent().getStringExtra("cardID");
                if(!title.isEmpty() && !cardID.isEmpty()){
                    ProductCardTaker_etCardTitle.setText(title);
                    ProductCardTaker_etCardQuantity.setText(quantity);
                    isOldCard = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        ic_save_create_product_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ProductCardTaker_etCardTitle.getText().toString();
                String quantity = ProductCardTaker_etCardQuantity.getText().toString();
                Log.i("ValueCheck", "Прилетело в title + quantity " + title + " " + quantity);

                if (!isOldCard) {
                    if (!title.isEmpty()) {
                        Intent intent = new Intent();
                        intent.putExtra("title", title);
                        intent.putExtra("quantity", quantity);
                        setResult(201, intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Пожалуйста введите название продукта", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String cardID = getIntent().getStringExtra("cardID");
                    if (!title.isEmpty()) {
                        Intent intent = new Intent();
                        intent.putExtra("title",title);
                        intent.putExtra("quantity",quantity);
                        intent.putExtra("cardID",cardID);
                        Log.i("IntentCheck","То что прилетело из et при редактировании \n"
                        + title + "\n"
                        + quantity + "\n"
                        + cardID + "\n");

                        setResult(202,intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Поле с наименование", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}