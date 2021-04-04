package com.example.numerosmash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView info_display;
    TextView info_heading;
    Button info_back;
    String level;
    com.example.numerosmash.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        info_display = findViewById(R.id.info_display);
        info_heading = findViewById(R.id.info_heading);
        info_back = findViewById(R.id.info_back);

        mDatabaseHelper = new DatabaseHelper(getApplication());

        //Get the light or dark mode
        Cursor data = mDatabaseHelper.getData();
        boolean found_darkmode=false;

        while(data.moveToNext()){
            if(data.getInt(1)==11){
                found_darkmode=true;
            }
        }

        if(found_darkmode==true){
            setActivityBackgroundColor(0xff696966);
        }
        else{
            setActivityBackgroundColor(0xffedddca);
        }

        //Get the level
        Intent game_level = getIntent();
        level = game_level.getStringExtra("level_info");
        info_heading.setText("Level " + level);

        if(level.equals("1")){
            info_display.setText("This level is for addition.\n\nYou must find any two values on the board that add together to reach the target value.");
        }
        else if (level.equals("2")){
            info_display.setText("This level is for subtraction.\n\nYou must find any two values on the board that subtract to reach the target value.");
        }
        else if (level.equals("3")){
            info_display.setText("This level is for multiplication.\n\nYou must find any two values on the board that multiply to reach the target value.");
        }
        else if (level.equals("4")){
            info_display.setText("This round is for division.\n\nYou must find any two values on the board that subtract to reach the target value.");
        }
        else if (level.equals("5")){
            info_display.setText("This round is for squared values.\n\nYou must find any two values on the board that add up to reach a value that when squared is the target value.");
        }
        else if (level.equals("6")){
            info_display.setText("This round is for algebra.\n\nYou must find any two values on the board that add up to reach the missing value.");
        }
        else if (level.equals("7")){
            info_display.setText("This round is for cubed values.\n\nYou must find any two values on the board that add up to reach a value that when cubed is the target value.");
        }
        else if (level.equals("8")){
            info_display.setText("This round is for multiplication and subtraction with odd values.\n\nYou must find two odd values on the board that when subtracted reach the target value.");
        }
        else if (level.equals("9")){
            info_display.setText("This round is for division and addition with even values.\n\nYou must find two even values on the board that add up to reach the target value.");
        }

        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(InfoActivity.this, LevelActivity.class);
                startActivity(myIntent);

            }
        });

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}