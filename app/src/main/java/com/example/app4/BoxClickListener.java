package com.example.app4;

import androidx.cardview.widget.CardView;

import com.example.app4.Models.Box;

public interface BoxClickListener {

    void onClick(Box box);
    void onLongClick(Box box, CardView cardView);
}
