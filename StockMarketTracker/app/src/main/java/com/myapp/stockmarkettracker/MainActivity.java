package com.myapp.stockmarkettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // Conss for Other Activitites
    //int LAUNCH_STOCK_ACTIVITY = 1;
    int LAUNCH_NEW_STOCK_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        addDividers();

        // Floating Button from Adding new book
//        FloatingActionButton addStockFloatingButton = findViewById(R.id.fab);
//        addStockFloatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAddStockClick();
//            }
//        });

    }

    /**
     *  Adding gray colored dividers between rows of book
     *
     */
    private void addDividers() {
        // Divider in portrait mode
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );

        // Divider in landscape mode
        Drawable horizontalDivider = ContextCompat.getDrawable(
                MainActivity.this,
                R.drawable.horizontal_divider
        );
        horizontalDecoration.setDrawable(horizontalDivider);

        // Add divider to Recycler view
        recyclerView.addItemDecoration(horizontalDecoration);
    }

    public void onAddStockClick() {
        Intent intent = new Intent(this, NewStockActivity.class);
        startActivityForResult(intent, LAUNCH_NEW_STOCK_ACTIVITY);
    }
}
