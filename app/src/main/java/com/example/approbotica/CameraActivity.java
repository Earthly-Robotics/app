package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CameraActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(CameraActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonBlueBlock", "MT", "BR");
        activateStartStopButton("buttonCorner", "MT", "CR");

        activateCameraButton();

        String currentAction = Controller.getInstance().getCurrentAction();
        if (currentAction == "blueblock")
        {
            changeCircleColor("circleConnectionCamera", true);
            changeCircleColor("circleBlueBlock", true);
            changeButtonText("buttonBlueBlock", "Start");
        }
        else if (currentAction == "corner")
        {
            changeCircleColor("circleConnectionCamera", true);
            changeCircleColor("circleCorner", true);
            changeButtonText("buttonCorner", "Start");
        }
    }
}