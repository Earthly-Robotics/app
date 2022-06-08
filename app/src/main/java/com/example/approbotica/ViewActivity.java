package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        //activateBackButton();
        //setBattery();
        //buttons
        //activateStartStopButton("buttonStop", getController().getCurrentAction());
            //this class
        //changeText("textSpeed", getController().getSpeed() + " m/s");
        //changeText("textWeight", getController().getSpeed() + " m/s");
    }

    public void changeText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView textview = (TextView) findViewById(ID);
        textview.setText(text);
    }
}