package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivateButton();
        addMargin();
        getSupportActionBar().hide();
    }

    private void addMargin(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LinearLayout scrollbar = (LinearLayout) findViewById(R.id.scrollBar);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollbar.getLayoutParams();
        layoutParams.topMargin = displayMetrics.heightPixels - 130;
        scrollbar.setLayoutParams(layoutParams);
    }

    private void ActivateButton(){
        Button buttonconnectflowergolem = (Button) findViewById(R.id.buttonConnectFlowerGolem);
        buttonconnectflowergolem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConnectActivity.class));
            }
        });
    }
}