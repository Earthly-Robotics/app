package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.Control;

public class RobotActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(RobotActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        else{
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"BATTERY"});
            startUIUpdater();
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
    }

    public void startUIUpdater()
    {
        Runnable Listen = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().isDaemon();
                while (!stop)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeCircleColor("circleConnection", Controller.getInstance().getConnection());
                            setBattery();
                            changeText("textStop", Controller.getInstance().getCurrentAction());
                        }
                    });
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"BATTERY"});
                Controller.getInstance().setActive(false);
            }
        };
        new Thread(Listen).start();
    }
}