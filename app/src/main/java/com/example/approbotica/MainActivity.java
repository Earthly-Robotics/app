package com.example.approbotica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText textBox1 = findViewById(R.id.textbox1);
                String str1 = textBox1.getText().toString();
                EditText textBox2 = findViewById(R.id.textbox2);
                String str2 = textBox2.getText().toString();
                TextView text = findViewById(R.id.textView);
                String combined = str1 + str2;
                text.setText(combined);
            }
        });
    }
}