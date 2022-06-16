package com.example.approbotica;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewActivity extends Activations {

    boolean startStream = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 1)
            finish();
        else{
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"WEIGHT"});
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"VELOCITY"});
            startUIUpdaterView();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().hide();


        //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonStop", "MT", "EMERGENCY_BUTTON");
            //this class
        changeText("textVelocity", Controller.getInstance().getVelocity());
        changeText("textWeight", Controller.getInstance().getWeight());
        changeButtonText("buttonStop", Controller.getInstance().getCurrentAction());

        activateCameraButton();
    }

    public void activateCameraButton(){
        Button button = (Button) findViewById(R.id.buttonCamera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(button.INVISIBLE);
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"CAMERA"});
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
                    if (!Controller.getInstance().getConnection())
                        finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setBattery();
                            changeText("textVelocity", Controller.getInstance().getVelocity());
                            changeText("textWeight", Controller.getInstance().getWeight());
                            changeButtonText("buttonStop", Controller.getInstance().getCurrentAction());

                            if (startStream)
                            {
                                ImageView linearlayout = (ImageView) findViewById(R.id.imageCamera);
                                linearlayout.setBackground( new BitmapDrawable(getResources(), Controller.getInstance().getCamera()));
                            }
                        }
                    });
                    try {
                        Thread.currentThread().sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (startStream)
                    Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"CAMERA"});
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"WEIGHT"});
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"VELOCITY"});
            }
        };
        new Thread(Listen).start();
    }


}