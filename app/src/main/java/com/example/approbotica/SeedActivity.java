package com.example.approbotica;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SeedActivity extends Activations {

    public static boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(SeedActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        else
            startUIUpdater();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonPlantSeeds", "MT", "PLANT");
        changeEditText("textRowAmount", Controller.getInstance().getRowAmount() + "");
        changeEditText("textDistanceRow", Controller.getInstance().getDistanceRow() + "");
        changeEditText("textSeedAmount", Controller.getInstance().getSeedAmount() + "");
        changeEditText("textDistanceSeed", Controller.getInstance().getDistanceSeed() + "");

        activateEditText("textRowAmount");
        activateEditText("textDistanceRow");
        activateEditText("textSeedAmount");
        activateEditText("textDistanceSeed");
        if (Controller.getInstance().getCurrentAction() == "Plant Seeds")
            plantseeds(true);

    }

    public void changeEditText(String id, String text)
    {
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        EditText edittext = (EditText) findViewById(ID);
        edittext.setText(text);
    }

    public void activateEditText(String id)
    {
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        EditText edittext = (EditText) findViewById(ID);
        edittext.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN){
                        edittext.clearFocus();
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        Controller.getInstance().setRowAmount(getText("textRowAmount"));
                        Controller.getInstance().setDistanceRow(getText("textDistanceRow"));
                        Controller.getInstance().setSeedAmount(getText("textSeedAmount"));
                        Controller.getInstance().setDistanceSeed(getText("textDistanceSeed"));
                        String [] a = {"MT","Row_Amount","Distance_Row","Seed_Amount","Distance_Seed"};
                        String [] b = {"values of seed",Controller.getInstance().getRowAmount() + "", Controller.getInstance().getDistanceRow() + "", Controller.getInstance().getSeedAmount() + "", Controller.getInstance().getDistanceSeed() + ""};
                        Controller.getInstance().sendMessage(a,b);
                        changed = true;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public String getText(String id)
    {
        int ID = getResources().getIdentifier(id, "id", getPackageName());
        EditText edittext = (EditText) findViewById(ID);
        return edittext.getText().toString();
    }

    public void plantseeds(boolean on)
    {
        changeCircleColor("circleConnectionSeed", on);
        changeCircleColor("circlePlantSeeds", on);
        changeButtonColor("buttonPlantSeeds", !on);
        if (on)
            changeButtonText("buttonPlantSeeds", "Stop");
        else
            changeButtonText("buttonPlantSeeds", "Start");
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
                            if (changed) {
                                changeEditText("textRowAmount", Controller.getInstance().getRowAmount() + "");
                                changeEditText("textDistanceRow", Controller.getInstance().getDistanceRow() + "");
                                changeEditText("textSeedAmount", Controller.getInstance().getSeedAmount() + "");
                                changeEditText("textDistanceSeed", Controller.getInstance().getDistanceSeed() + "");
                                changed = false;
                            }
                            if (Controller.getInstance().getCurrentAction() == "Plant Seeds")
                                plantseeds(true);
                            else
                                plantseeds(false);
                        }
                    });
                    try {
                        Thread.currentThread().sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(Listen).start();
    }
}