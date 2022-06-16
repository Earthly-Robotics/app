package com.example.approbotica;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CameraActivity extends Activations {

    boolean startStream = false;
    boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == 2)
        {
            Intent activity = new Intent(CameraActivity.this, ViewActivity.class);
            startActivity(activity);
        }
        else
            startUIUpdaterView();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().hide();

            //Activations class
        //menu
        activateBackButton();
        setBattery();
        //buttons
        activateStartStopButton("buttonBlueBlock", "MT", "BLUE_BLOCK");
        changeEditText("textLowerArea", Controller.getInstance().getLowerArea() + "");
        changeEditText("textUpperArea", Controller.getInstance().getUpperArea() + "");
        changeEditText("textLowerShape", Controller.getInstance().getLowerShape() + "");
        changeEditText("textUpperShape", Controller.getInstance().getUpperShape() + "");

        activateCameraButton();
        activateEditText("textLowerArea");
        activateEditText("textUpperArea");
        activateEditText("textLowerShape");
        activateEditText("textUpperShape");
        if (Controller.getInstance().getCurrentAction() == "Recognize Blue Block")
            blueblock(true);
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
                        if (Controller.getInstance().getCurrentAction() == "Recognize Blue Block")
                        {
                            Controller.getInstance().setLowerArea(getText("textLowerArea"));
                            Controller.getInstance().setUpperArea(getText("textUpperArea"));
                            Controller.getInstance().setLowerShape(getText("textLowerShape"));
                            Controller.getInstance().setUpperShape(getText("textUpperShape"));
                            String [] a = {"MT","Lower_Area","Upper_Area","Lower_Shape","Upper_Shape"};
                            String [] b = {"BLUE_BLOCK_VALUES",Controller.getInstance().getLowerArea() + "", Controller.getInstance().getUpperArea() + "", Controller.getInstance().getLowerShape() + "", Controller.getInstance().getUpperShape() + ""};
                            Controller.getInstance().sendMessage(a,b);
                        }
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

    public void blueblock(boolean on)
    {
        changeCircleColor("circleConnectionCamera", on);
        changeCircleColor("circleBlueBlock", on);
        changeButtonColor("buttonBlueBlock", on);
        if (on){
            changeButtonText("buttonBlueBlock", "Stop");
        }
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
                    if (!Controller.getInstance().getConnection())
                        finish();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setBattery();
                            if (changed) {
                                changeEditText("textLowerArea", Controller.getInstance().getLowerArea() + "");
                                changeEditText("textUpperArea", Controller.getInstance().getUpperArea() + "");
                                changeEditText("textLowerShape", Controller.getInstance().getLowerShape() + "");
                                changeEditText("textUpperShape", Controller.getInstance().getUpperShape() + "");
                                changed = false;
                            }
                            if (Controller.getInstance().getCurrentAction() == "Recognize Blue Block")
                                blueblock(true);
                            else
                                blueblock(false);
                            if (startStream)
                            {
                                LinearLayout linearlayout = (LinearLayout) findViewById(R.id.imageCamera);
                                linearlayout.setBackground( new BitmapDrawable(getResources(), Controller.getInstance().getCameraDebug()));
                            }

                        }
                    });
                    try {
                        Thread.currentThread().sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(Listen).start();
    }
}