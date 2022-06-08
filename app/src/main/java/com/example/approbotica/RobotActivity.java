package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RobotActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(RobotActivity.this, ViewActivity.class);
            activity.putExtra("CONTROLLER", getController());
            startActivity(activity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        getSupportActionBar().hide();

            //Activations class
        //data
        setController();
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateImageView("buttonDance", new Intent(RobotActivity.this, DanceActivity.class));
        activateImageView("buttonSeed", new Intent(RobotActivity.this, SeedActivity.class));
        activateImageView("buttonCamera", new Intent(RobotActivity.this, CameraActivity.class));
        activateImageView("buttonView", new Intent(RobotActivity.this, ViewActivity.class));
        activateStartStopButton("buttonStop", getController().getCurrentAction());
        //layout
        changeCircleColor("circleConnection", getController().getConnection());

            //This class
        changeText(getController().getCurrentAction());
    }

    public void changeText(String text){
        TextView textview = (TextView) findViewById(R.id.textStop);
        textview.setText(text);
    }
}