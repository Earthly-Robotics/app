package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CameraActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

            //Activations class
        //data
        setController();
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonBlueBlock", "blueblock");
        activateStartStopButton("buttonCorner", "corner");
        String currentAction = getController().getCurrentAction();
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