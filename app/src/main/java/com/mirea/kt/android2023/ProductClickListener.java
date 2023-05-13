package com.mirea.kt.android2023;

import androidx.cardview.widget.CardView;

public interface ProductClickListener {

    void onClick (Products products);
    void onLongClick (Products products, CardView cardView);
}
