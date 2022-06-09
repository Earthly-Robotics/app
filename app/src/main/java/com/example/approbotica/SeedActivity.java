package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;

public class SeedActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(SeedActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonPlantSeeds", "MT", "PS");
        if (Controller.getInstance().getCurrentAction() == "plantseeds")
        {
            changeCircleColor("circleConnectionSeed", true);
            changeCircleColor("circlePlantSeeds", true);
            changeButtonText("circlePlantSeeds", "Start");
        }

    }
}