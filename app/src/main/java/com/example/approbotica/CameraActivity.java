package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends Activations {

    boolean startStream = false;

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
        setBattery();
        //buttons
        activateStartStopButton("buttonBlueBlock", "MT", "BLUE_BLOCK");

        activateCameraButton();
        if (Controller.getInstance().getCurrentAction() == "Recognize Blue Block")
            blueblock(true);
    }

    public void blueblock(boolean on)
    {
        changeCircleColor("circleConnectionCamera", on);
        changeCircleColor("circleBlueBlock", on);
        if (on)
            changeButtonText("buttonBlueBlock", "Stop");
        else
            changeButtonText("buttonBlueBlock", "Start");


    }

    public void activateCameraButton(){
        Button button = (Button) findViewById(R.id.buttonCamera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(button.INVISIBLE);
                startStream = true;
            }
        });
    }

    public void startUIUpdaterView()
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
                            if (Controller.getInstance().getCurrentAction() == "Recognize Blue Block")
                                blueblock(true);
                            else
                                blueblock(false);

                            if (startStream)
                            {
                                //TODO setup camera stream
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