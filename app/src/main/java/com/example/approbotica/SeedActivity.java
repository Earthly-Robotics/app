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
        else
            startUIUpdater();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonPlantSeeds", "MT", "PLANT");
        if (Controller.getInstance().getCurrentAction() == "Plant Seeds")
            plantseeds(true);

    }

    public void plantseeds(boolean on)
    {
        changeCircleColor("circleConnectionSeed", on);
        changeCircleColor("circlePlantSeeds", on);
        changeButtonColor("buttonPlantSeeds", on);
        if (on)
            changeButtonText("buttonPlantSeeds", "Stop");
        else
            changeButtonText("buttonPlantSeeds", "Start");
    }

    public void startUIUpdater()
    {
        Runnable Listen = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().isDaemon();
                while (!stop)
                {
                    if (!Controller.getInstance().getConnection())
                        finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setBattery();
                            if (Controller.getInstance().getCurrentAction() == "Plant Seeds")
                                plantseeds(true);
                            else
                                plantseeds(false);
                        }
                    });
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(Listen).start();
    }
}