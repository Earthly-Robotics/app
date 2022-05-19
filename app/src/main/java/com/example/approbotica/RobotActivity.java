package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RobotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        getSupportActionBar().hide();
        configureBackButton();
        configureButton1();
        configureButton2();
        configureButton3();
        configureButton4();
    }

    private void configureBackButton(){
        ImageView ImageButton = (ImageView) findViewById(R.id.BackButton);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureButton1(){
        ImageView ImageButton = (ImageView) findViewById(R.id.ButtonImage1);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RobotActivity.this, DanceActivity.class));
            }
        });
    }

    private void configureButton2(){
        ImageView ImageButton = (ImageView) findViewById(R.id.ButtonImage2);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RobotActivity.this, SeedActivity.class));
            }
        });
    }

    private void configureButton3(){
        ImageView ImageButton = (ImageView) findViewById(R.id.ButtonImage3);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RobotActivity.this, CameraActivity.class));
            }
        });
    }

    private void configureButton4(){
        ImageView ImageButton = (ImageView) findViewById(R.id.ButtonImage4);
        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RobotActivity.this, ViewActivity.class));
            }
        });
    }
}