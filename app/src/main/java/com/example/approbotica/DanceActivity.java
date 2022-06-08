package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DanceActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance);
        getSupportActionBar().hide();

            //Activations class
        //data
        setController();
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonLineDance", "linedance");
        activateStartStopButton("buttonSoloDance", "solodance");
        String currentAction = getController().getCurrentAction();
        if (currentAction == "linedance")
        {
            changeCircleColor("circleConnectionDance", true);
            changeCircleColor("circleLineDance", true);
            changeButtonText("circleLineDance", "Start");
        }
        else if (currentAction == "solodance")
        {
            changeCircleColor("circleConnectionDance", true);
            changeCircleColor("circleSoloDance", true);
            changeButtonText("circleSoloDance", "Start");
        }

    }
}