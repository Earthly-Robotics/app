package com.example.approbotica;

import android.content.Intent;
import android.os.Bundle;

public class DanceActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(DanceActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        else {
            startUIUpdater();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonLineDance", "MT", "LINE_DANCE");
        activateStartStopButton("buttonSoloDance", "MT", "SOLO_DANCE");
        if (Controller.getInstance().getCurrentAction() == "Line Dance")
            linedance(true);
        else if (Controller.getInstance().getCurrentAction() == "Solo Dance")
            solodance(true);
    }

    public void linedance(boolean on)
    {
        changeCircleColor("circleConnectionDance", on);
        changeCircleColor("circleLineDance", on);
        changeButtonColor("buttonLineDance", !on);
        if (on)
            changeButtonText("buttonLineDance", "Stop");
        else
            changeButtonText("buttonLineDance", "Start");
    }

    public void solodance(boolean on)
    {
        changeCircleColor("circleConnectionDance", on);
        changeCircleColor("circleSoloDance", on);
        changeButtonColor("buttonSoloDance", !on);
        if (on)
            changeButtonText("buttonSoloDance", "Stop");
        else
            changeButtonText("buttonSoloDance", "Start");
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
                            if (Controller.getInstance().getCurrentAction() == "Line Dance"){
                                linedance(true);
                                solodance(false);
                            }
                            else if (Controller.getInstance().getCurrentAction() == "Solo Dance"){
                                linedance(false);
                                solodance(true);
                            }
                            else{
                                linedance(false);
                                solodance(false);
                            }
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