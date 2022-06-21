package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.Network.Client;
import com.example.Network.Message;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Controller.getInstance().getNoMessage())
            getSupportActionBar().hide();

            //Activations in this class
        ActivateButton();
        ActivateButtonIron();
        addMargin();
    }

    /**
     * Responsiveness for the MainActivity page.
     */
    private void addMargin(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LinearLayout scrollbar = (LinearLayout) findViewById(R.id.scrollBar);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scrollbar.getLayoutParams();
        layoutParams.topMargin = displayMetrics.heightPixels - 130;
        scrollbar.setLayoutParams(layoutParams);
    }

    /**
     * Creates onClick method for connection button of the Flower Golem.
     * Goes to RobotActivity page.
     */
    private void ActivateButton(){
        Button buttonconnectflowergolem = (Button) findViewById(R.id.buttonConnectFlowerGolem);
        buttonconnectflowergolem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConnectActivity.class));
            }
        });
    }

    /**
     * Meme
     */
    private void ActivateButtonIron(){
        Button buttonconnectflowergolem = (Button) findViewById(R.id.buttonConnectIronGolem);
        buttonconnectflowergolem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/dQw4w9WgXcQ"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            }
        });
    }
}