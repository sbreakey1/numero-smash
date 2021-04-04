package com.example.numerosmash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelActivity extends AppCompatActivity {

    Button level_back;

    ImageButton level1;
    ImageButton level2;
    ImageButton level3;
    ImageButton level4;
    ImageButton level5;
    ImageButton level6;
    ImageButton level7;
    ImageButton level8;
    ImageButton level9;

    ImageButton level1_info;
    ImageButton level2_info;
    ImageButton level3_info;
    ImageButton level4_info;
    ImageButton level5_info;
    ImageButton level6_info;
    ImageButton level7_info;
    ImageButton level8_info;
    ImageButton level9_info;

    com.example.numerosmash.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        level_back = findViewById(R.id.level_back);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        level6 = findViewById(R.id.level6);
        level7 = findViewById(R.id.level7);
        level8 = findViewById(R.id.level8);
        level9 = findViewById(R.id.level9);

        level1_info = findViewById(R.id.level1_info);
        level2_info = findViewById(R.id.level2_info);
        level3_info = findViewById(R.id.level3_info);
        level4_info = findViewById(R.id.level4_info);
        level5_info = findViewById(R.id.level5_info);
        level6_info = findViewById(R.id.level6_info);
        level7_info = findViewById(R.id.level7_info);
        level8_info = findViewById(R.id.level8_info);
        level9_info = findViewById(R.id.level9_info);


        mDatabaseHelper = new DatabaseHelper(getApplication());

        Cursor data = mDatabaseHelper.getData();

        //Get the light or dark mode
        boolean found_darkmode=false;

        while(data.moveToNext()) {

            if (data.getInt(1) == 11) {
                found_darkmode = true;
            }


            //Set the levels to be unlocked depending on the db vales
            if(data.getInt(1)==1){
                level1.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level1.setTag("ic_baseline_check_circle_outline_24");
                level2.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level2.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==2){
                level2.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level2.setTag("ic_baseline_check_circle_outline_24");
                level3.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level3.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==3){
                level3.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level3.setTag("ic_baseline_check_circle_outline_24");
                level4.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level4.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==4){
                level4.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level4.setTag("ic_baseline_check_circle_outline_24");
                level5.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level5.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==5){
                level5.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level5.setTag("ic_baseline_check_circle_outline_24");
                level6.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level6.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==6){
                level6.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level6.setTag("ic_baseline_check_circle_outline_24");
                level7.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level7.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==7){
                level7.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level7.setTag("ic_baseline_check_circle_outline_24");
                level8.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level8.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==8){
                level8.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level8.setTag("ic_baseline_check_circle_outline_24");
                level9.setBackgroundResource(R.drawable.ic_baseline_lock_open_24);
                level9.setTag("ic_baseline_lock_open_24");
            }
            else if (data.getInt(1)==9){
                level9.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24);
                level9.setTag("ic_baseline_check_circle_outline_24");
            }
        }

        if(found_darkmode==true){
            setActivityBackgroundColor(0xff696966);
        }
        else{
            setActivityBackgroundColor(0xffedddca);
        }

        level_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                myIntent.putExtra("level", "1");
                startActivity(myIntent);

            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level2.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "2");
                    startActivity(myIntent);
                }

            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level3.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "3");
                    startActivity(myIntent);
                }

            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level4.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "4");
                    startActivity(myIntent);
                }

            }
        });

        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level5.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "5");
                    startActivity(myIntent);
                }



            }
        });

        level6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level6.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "6");
                    startActivity(myIntent);
                }



            }
        });

        level7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Check if you can click on the element or if it is locked
                if(!(level7.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "7");
                    startActivity(myIntent);
                }

            }
        });

        level8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level8.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "8");
                    startActivity(myIntent);
                }

            }
        });

        level9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check if you can click on the element or if it is locked
                if(!(level9.getTag().equals("ic_baseline_lock_24"))){
                    Intent myIntent = new Intent(LevelActivity.this, GameActivity.class);
                    myIntent.putExtra("level", "9");
                    startActivity(myIntent);
                }

            }
        });

        level1_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "1");
                startActivity(myIntent);


            }
        });

        level2_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "2");
                startActivity(myIntent);

            }
        });

        level3_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "3");
                startActivity(myIntent);

            }
        });

        level4_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "4");
                startActivity(myIntent);

            }
        });

        level5_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "5");
                startActivity(myIntent);

            }
        });

        level6_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "6");
                startActivity(myIntent);

            }
        });

        level7_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "7");
                startActivity(myIntent);

            }
        });

        level8_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "8");
                startActivity(myIntent);

            }
        });

        level9_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LevelActivity.this, InfoActivity.class);
                myIntent.putExtra("level_info", "9");
                startActivity(myIntent);

            }
        });

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}