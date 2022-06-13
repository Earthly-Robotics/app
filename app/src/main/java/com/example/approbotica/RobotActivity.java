package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;

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
        setBattery();
        //buttons
        activateImageView("buttonDance", new Intent(RobotActivity.this, DanceActivity.class));
        activateImageView("buttonSeed", new Intent(RobotActivity.this, SeedActivity.class));
        activateImageView("buttonCamera", new Intent(RobotActivity.this, CameraActivity.class));
        activateImageView("buttonView", new Intent(RobotActivity.this, ViewActivity.class));
        activateStartStopButton("buttonStop", "MT", "EB");
        //layout
        changeCircleColor("circleConnection", Controller.getInstance().getConnection());
        //This class
        changeText("textStop", Controller.getInstance().getCurrentAction());

        //startUIUpdater();
    }

    public void startUIUpdater()
    {
        Runnable Listen = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().isDaemon();
                while (!stop)
                {
                    if (Controller.getInstance().testConnection())
                    {
                        //TODO when connection ends
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setBattery();
                            changeButtonText("buttonStop", Controller.getInstance().getCurrentAction());
                        }
                    });
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(Listen).start();
    }
}