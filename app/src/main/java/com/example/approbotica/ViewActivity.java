package com.example.approbotica;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewActivity extends Activations {

    boolean startStream = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 1)
            finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().hide();

        //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonStop", "MT", "EB");
            //this class
        changeText("textVelocity", Controller.getInstance().getVelocity());
        changeText("textWeight", Controller.getInstance().getWeight());
        changeButtonText("buttonStop", Controller.getInstance().getCurrentAction());

        activateCameraButton();
        startUIUpdaterView();
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
                            changeText("textVelocity", Controller.getInstance().getVelocity());
                            changeText("textWeight", Controller.getInstance().getWeight());
                            changeButtonText("buttonStop", Controller.getInstance().getCurrentAction());

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