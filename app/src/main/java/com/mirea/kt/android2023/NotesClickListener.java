package com.mirea.kt.android2023;

import androidx.cardview.widget.CardView;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes , CardView cardView);
}
