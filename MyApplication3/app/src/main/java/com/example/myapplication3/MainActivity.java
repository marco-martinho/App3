package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView label = (TextView) findViewById(R.id.label);
        label.setTextColor(Color.RED);

        Button clickButton = (Button) findViewById(R.id.button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            // action bei click
            @Override
            public void onClick(View view) {
                count ++;
                TextView label = (TextView) findViewById(R.id.label);
                label.setText( "" + count);
                label.setTextColor(Color.BLUE);

            }
        });


    }
}
