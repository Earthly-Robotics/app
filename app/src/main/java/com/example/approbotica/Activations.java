package com.example.approbotica;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Objects;

public class Activations extends AppCompatActivity {

    volatile boolean stop = false; //Stop for
    public Vibrator vibrator;

    /**
     * Finds view with id and adds an on click listener to it.
     *
     * finish(); stops current activity.
     */
    public void activateBackButton(){
        ImageView button = (ImageView) findViewById(R.id.buttonBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Gets battery from controller class.
     * Finds view with id and sets correct image and text.
     *
     * Battery image names are formatted with batteryXX.
     * XX stands for percentage starting at 10 with increasing by 15 until 100.
     */
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

    /**
     * Finds view with id and adds an on click listener to it.
     *
     * Intent is an android variable where you can put different actions inside.
     * Here they are used to navigate to other activities.
     *
     * @param id variable id for multiple activations.
     * @param activity changeable intent for multiple activations.
     */
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

    /**
     * Finds view with id and adds an on click listener to it.
     * if the current action is Manual or button text is either "Emergency Button" or "Stop".
     *      onClick it send a message to the client which is a json string of A and B.
     * else
     *      it does nothing :D.
     *
     *
     * @param id variable id for multiple activations.
     * @param A an string array to make an json string. Names.
     * @param B an string array to make an json string. Variables.
     */
    public void activateStartStopButton(String id, String A, String B){
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        String currentAction = Controller.getInstance().getCurrentAction();
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        Button button = (Button) findViewById(ID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String i = (String) button.getText();
                if (Controller.getInstance().getCurrentAction() == "Manual" || Objects.equals(i, "Emergency Button") || Objects.equals(i, "Stop")) {
                    String [] a = {A};
                    String [] b = {B};
                    Controller.getInstance().sendMessage(a,b);
                }
                else
                    return;
                vibrator.vibrate(100);
            }
        });
    }

    /**
     * Change the text of a textview.
     * @param id variable id for multiple activations.
     * @param text
     */
    public void changeText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView textview = (TextView) findViewById(ID);
        textview.setText(text);
    }

    /**
     * Change the text of a button.
     * @param id variable id for multiple activations.
     * @param text
     */
    public void changeButtonText(String id, String text){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        Button button = (Button) findViewById(ID);
        button.setText(text);
    }

    /**
     * Change the color of a connection circle.
     * @param id variable id for multiple activations.
     * @param colorGreen true for the color green, false for the color red.
     */
    public void changeCircleColor(String id, boolean colorGreen){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        TextView circle = (TextView) findViewById(ID);
        if (colorGreen)
            circle.setBackgroundResource(R.drawable.circlegreen);
        else
            circle.setBackgroundResource(R.drawable.circlered);
    }

    /**
     * Change the color of a start/stop button.
     * @param id variable id for multiple activations.
     * @param colorBlue true for the color blue, false for the color red.
     */
    public void changeButtonColor(String id, boolean colorBlue){
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        Button button = (Button) findViewById(ID);
        if (colorBlue)
            button.setBackgroundResource(R.drawable.buttonstyleblue);
        else
            button.setBackgroundResource(R.drawable.buttonstylered);
    }

    /**
     *
     */
    @Override
    public void onDestroy()
    {
        stop = true;
        super.onDestroy();
    }
}
