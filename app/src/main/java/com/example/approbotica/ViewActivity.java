package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends Activations {

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
        changeText("textSpeed", Controller.getInstance().getSpeed() + " m/s");
        changeText("textWeight", Controller.getInstance().getWeight() + " g");

        activateCameraButton();
    }

    public void changeText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView textview = (TextView) findViewById(ID);
        textview.setText(text);
    }
}