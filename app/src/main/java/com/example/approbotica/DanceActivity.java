package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DanceActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(DanceActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonLineDance", "MT", "LD");
        activateStartStopButton("buttonSoloDance", "MT", "SD");
        String currentAction = Controller.getInstance().getCurrentAction();
        if (currentAction == "linedance")
        {
            changeCircleColor("circleConnectionDance", true);
            changeCircleColor("circleLineDance", true);
            changeButtonText("buttonLineDance", "Start");
        }
        else if (currentAction == "solodance")
        {
            changeCircleColor("circleConnectionDance", true);
            changeCircleColor("circleSoloDance", true);
            changeButtonText("buttonSoloDance", "Start");
        }

    }
}