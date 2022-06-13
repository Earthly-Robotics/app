package com.example.approbotica;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Dictionary;

public class Activations extends AppCompatActivity {

    volatile boolean stop = false;

    public void refresh(){
        finish();
        startActivity(getIntent());
    }

    public void activateBackButton(){
        ImageView button = (ImageView) findViewById(R.id.buttonBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setBattery(){
        int battery = Controller.getInstance().getBattery();
        TextView textview = (TextView) findViewById(R.id.batteryText);
        ImageView imageview = (ImageView) findViewById(R.id.batteryImage);
        textview.setText(battery + "%");
        int imageBattery = battery / 15;
        imageBattery = imageBattery * 15 + 10;
        int ID = getResources().getIdentifier("battery" + imageBattery, "drawable", getPackageName());
        imageview.setImageResource(ID);
    }

    public void activateImageView(String id, Intent activity){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        ImageView imageview = (ImageView) findViewById(ID);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(activity);
            }
        });
    }

    public void activateStartStopButton(String id, String A, String B){
        String currentAction = Controller.getInstance().getCurrentAction();
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        Button button = (Button) findViewById(ID);
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    button.setText("Hallo");
                if (!hasFocus)
                    button.setText("Stop");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String [] a = {A};
                String [] b = {B};
                String [] c = {"UB"};
                if (button.getText() == "Stop")
                    Controller.getInstance().sendMessage(a,c);
                else
                    Controller.getInstance().sendMessage(a,b);
            }
        });
    }

    public void changeText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView textview = (TextView) findViewById(ID);
        textview.setText(text);
    }

    public void changeButtonText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        Button button = (Button) findViewById(ID);
        button.setText(text);
    }

    public void changeCircleColor(String id, boolean colorGreen){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView circle = (TextView) findViewById(ID);
        if (colorGreen)
            circle.setBackgroundResource(R.drawable.circlegreen);
        else
            circle.setBackgroundResource(R.drawable.circlered);
    }

    @Override
    public void onDestroy()
    {
        stop = true;
        super.onDestroy();
    }
}
