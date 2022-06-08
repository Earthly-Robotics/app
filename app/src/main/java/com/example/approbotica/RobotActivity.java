package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Network.Client;
import com.example.Network.Message;

import java.io.IOException;
import java.net.UnknownHostException;

public class RobotActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(RobotActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateImageView("buttonDance", new Intent(RobotActivity.this, DanceActivity.class));
        activateImageView("buttonSeed", new Intent(RobotActivity.this, SeedActivity.class));
        activateImageView("buttonCamera", new Intent(RobotActivity.this, CameraActivity.class));
        activateImageViewView("buttonView", new Intent(RobotActivity.this, ViewActivity.class));
        activateStartStopButton("buttonStop", "MT", "LD");
        //layout
        changeCircleColor("circleConnection", Controller.getInstance().getConnection());
            //This class
        changeText(Controller.getInstance().getCurrentAction());
    }

    public void activateImageViewView(String id, Intent activity){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        ImageView imageview = (ImageView) findViewById(ID);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(activity);
            }
        });
    }

    public void changeText(String text){
        TextView textview = (TextView) findViewById(R.id.textStop);
        textview.setText(text);
    }
}