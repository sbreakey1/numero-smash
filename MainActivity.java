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
import android.widget.TextView;

import com.example.numerosmash.DatabaseHelper;
import com.example.numerosmash.LevelActivity;
import com.example.numerosmash.OptionsActivity;
import com.example.numerosmash.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    com.example.numerosmash.DatabaseHelper mDatabaseHelper;
    Button main_game;
    Button main_options;
    Button main_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_game = findViewById(R.id.main_game);
        main_options = findViewById(R.id.main_options);
        main_quit = findViewById(R.id.main_quit);
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

        main_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, LevelActivity.class);
                startActivity(myIntent);

            }
        });

        main_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, OptionsActivity.class);
                startActivity(myIntent);

            }
        });

        main_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishAffinity();
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



            }
        });


    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}