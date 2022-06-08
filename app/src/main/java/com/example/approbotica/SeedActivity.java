package com.example.approbotica;

import android.os.Bundle;

public class SeedActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        getSupportActionBar().hide();

            //Activations class
        //data
        setController();
        //menu
        activateBackButton();
        activateRefreshButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonPlantSeeds", "plantseeds");
        if (getController().getCurrentAction() == "plantseeds")
        {
            changeCircleColor("circleConnectionSeed", true);
            changeCircleColor("circlePlantSeeds", true);
            changeButtonText("circlePlantSeeds", "Start");
        }

    }
}