package com.example.numerosmash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.numerosmash.DatabaseHelper;
import com.example.numerosmash.MainActivity;
import com.example.numerosmash.R;

public class OptionsActivity extends AppCompatActivity {

    Button options_back;
    com.example.numerosmash.DatabaseHelper mDatabaseHelper;
    Button options_reset;
    CheckBox options_notifications;
    CheckBox options_darkmode;
    ToggleButton options_questions;

    boolean found_notifications;
    boolean found_darkmode;
    boolean found_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        options_back = findViewById(R.id.options_back);
        options_reset = findViewById(R.id.options_reset);
        options_notifications = findViewById(R.id.options_notifications);
        options_darkmode = findViewById(R.id.options_darkmode);
        options_questions = findViewById(R.id.options_questions);

        found_notifications=false;
        found_darkmode=false;
        found_questions=false;

        mDatabaseHelper = new DatabaseHelper(getApplication());

        Cursor data = mDatabaseHelper.getData();

        while(data.moveToNext()){
            if(data.getInt(1)==10){
                found_notifications=true;
            }
            if(data.getInt(1)==11){
                found_darkmode=true;
            }
            if(data.getInt(1)==12){
                found_questions=true;
            }
        }

        if(found_notifications==false) {
            options_notifications.setChecked(true);
        }

        if(found_darkmode==true){
            options_darkmode.setChecked(true);
            setActivityBackgroundColor(0xff696966);
        }
        else{
            setActivityBackgroundColor(0xffedddca);
        }
        if(found_questions==true){
            options_questions.setChecked(true);
        }


        options_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                builder.setMessage("Are you sure you want to reset? Please note all game progress will be lost")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDatabaseHelper.clearData();

                                //Reset both the checkboxes too if they have not been reset
                                options_notifications.setChecked(true);
                                options_darkmode.setChecked(false);
                                options_questions.setChecked(false);

                                setActivityBackgroundColor(0xffedddca);

                                Toast toast = Toast.makeText(getApplicationContext(), "Reset to factory", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();



                /*AlertDialog alertDialog = builder.create();
                alertDialog.show();

                mDatabaseHelper.clearData();

                //Reset both the checkboxes too if they have not been reset
                options_notifications.setChecked(true);
                options_darkmode.setChecked(false);

                setActivityBackgroundColor(0xffedddca);

                Toast toast = Toast.makeText(getApplicationContext(), "Reset to factory", Toast.LENGTH_LONG);
                toast.show();*/

            }
        });

        options_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });

        options_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(options_notifications.isChecked()){
                    options_notifications.setChecked(true);
                    mDatabaseHelper.deleteData(10);

                }
                else{
                    options_notifications.setChecked(false);
                    mDatabaseHelper.addData(10);
                }

            }
        });

        options_darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(options_darkmode.isChecked()){
                    options_darkmode.setChecked(true);
                    mDatabaseHelper.addData(11);
                    setActivityBackgroundColor(0xff696966);
                }
                else{
                    options_darkmode.setChecked(false);
                    mDatabaseHelper.deleteData(11);
                    setActivityBackgroundColor(0xffedddca);
                }

            }
        });

        options_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(options_questions.isChecked()){
                    options_questions.setChecked(true);
                    mDatabaseHelper.addData(12);
                }
                else{
                    options_questions.setChecked(false);
                    mDatabaseHelper.deleteData(12);
                }

            }
        });

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}