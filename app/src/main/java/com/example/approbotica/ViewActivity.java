package com.example.approbotica;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewActivity extends Activations {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 1)
            finish();
        else{
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"WEIGHT"});
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"VELOCITY"});
            Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"CAMERA"});
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
            //this class
        changeText("textVelocity", Controller.getInstance().getVelocity());
        changeText("textWeight", Controller.getInstance().getWeight());
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

                            ImageView linearlayout = (ImageView) findViewById(R.id.imageCamera);
                            linearlayout.setBackground( new BitmapDrawable(getResources(), Controller.getInstance().getCamera()));
                        }
                    });
                    try {
                        Thread.currentThread().sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"CAMERA"});
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"WEIGHT"});
                Controller.getInstance().sendMessage(new String[]{"MT"}, new String[]{"VELOCITY"});
            }
        };
        new Thread(Listen).start();
    }


}