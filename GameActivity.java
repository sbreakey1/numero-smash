package com.example.numerosmash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    //Back button
    Button maingame_back;

    //Skip button
    Button maingame_skip;

    //Buttons for the board
    ImageButton oneone;
    ImageButton onetwo;
    ImageButton onethree;
    ImageButton onefour;
    ImageButton onefive;
    ImageButton onesix;

    ImageButton twoone;
    ImageButton twotwo;
    ImageButton twothree;
    ImageButton twofour;
    ImageButton twofive;
    ImageButton twosix;

    ImageButton threeone;
    ImageButton threetwo;
    ImageButton threethree;
    ImageButton threefour;
    ImageButton threefive;
    ImageButton threesix;

    ImageButton fourone;
    ImageButton fourtwo;
    ImageButton fourthree;
    ImageButton fourfour;
    ImageButton fourfive;
    ImageButton foursix;

    ImageButton fiveone;
    ImageButton fivetwo;
    ImageButton fivethree;
    ImageButton fivefour;
    ImageButton fivefive;
    ImageButton fivesix;

    ImageButton sixone;
    ImageButton sixtwo;
    ImageButton sixthree;
    ImageButton sixfour;
    ImageButton sixfive;
    ImageButton sixsix;

    ImageButton sevenone;
    ImageButton seventwo;
    ImageButton seventhree;
    ImageButton sevenfour;
    ImageButton sevenfive;
    ImageButton sevensix;

    ImageButton eightone;
    ImageButton eighttwo;
    ImageButton eightthree;
    ImageButton eightfour;
    ImageButton eightfive;
    ImageButton eightsix;

    View view;

    //Game level
    static TextView game_level_display;

    //Array to hold the current positions on the board
    int[][] positions;
    int[][] checked;

    //Game score variables
    TextView game_highscore;
    
    int current_score = 0;

    //Random number generator
    int randomSymbol;

    int cards_turned;

    String level;

    int target;

    int randomx;
    int randomy;

    int randomx2;
    int randomy2;

    int array_position1;
    int array_position2;

    int card1;
    int card2;

    int result;

    int questions_correct;

    int number_questions;

    int current_question;

    TextView game_questions;

    com.example.numerosmash.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        maingame_back = findViewById(R.id.maingame_back);
        maingame_skip = findViewById(R.id.game_skip);

        oneone = findViewById(R.id.oneone);
        onetwo = findViewById(R.id.onetwo);
        onethree = findViewById(R.id.onethree);
        onefour = findViewById(R.id.onefour);
        onefive = findViewById(R.id.onefive);
        onesix = findViewById(R.id.onesix);

        twoone = findViewById(R.id.twoone);
        twotwo = findViewById(R.id.twotwo);
        twothree = findViewById(R.id.twothree);
        twofour = findViewById(R.id.twofour);
        twofive = findViewById(R.id.twofive);
        twosix = findViewById(R.id.twosix);

        threeone = findViewById(R.id.threeone);
        threetwo = findViewById(R.id.threetwo);
        threethree = findViewById(R.id.threethree);
        threefour = findViewById(R.id.threefour);
        threefive = findViewById(R.id.threefive);
        threesix = findViewById(R.id.threesix);

        fourone = findViewById(R.id.fourone);
        fourtwo = findViewById(R.id.fourtwo);
        fourthree = findViewById(R.id.fourthree);
        fourfour = findViewById(R.id.fourfour);
        fourfive = findViewById(R.id.fourfive);
        foursix = findViewById(R.id.foursix);

        fiveone = findViewById(R.id.fiveone);
        fivetwo = findViewById(R.id.fivetwo);
        fivethree = findViewById(R.id.fivethree);
        fivefour = findViewById(R.id.fivefour);
        fivefive = findViewById(R.id.fivefive);
        fivesix = findViewById(R.id.fivesix);

        sixone = findViewById(R.id.sixone);
        sixtwo = findViewById(R.id.sixtwo);
        sixthree = findViewById(R.id.sixthree);
        sixfour = findViewById(R.id.sixfour);
        sixfive = findViewById(R.id.sixfive);
        sixsix = findViewById(R.id.sixsix);

        sevenone = findViewById(R.id.sevenone);
        seventwo = findViewById(R.id.seventwo);
        seventhree = findViewById(R.id.seventhree);
        sevenfour = findViewById(R.id.sevenfour);
        sevenfive = findViewById(R.id.sevenfive);
        sevensix = findViewById(R.id.sevensix);

        eightone = findViewById(R.id.eightone);
        eighttwo = findViewById(R.id.eighttwo);
        eightthree = findViewById(R.id.eightthree);
        eightfour = findViewById(R.id.eightfour);
        eightfive = findViewById(R.id.eightfive);
        eightsix = findViewById(R.id.eightsix);

        game_level_display = findViewById(R.id.game_level);

        game_questions = findViewById(R.id.game_question);

        mDatabaseHelper = new DatabaseHelper(getApplication());

        //Get the light or dark mode
        Cursor data = mDatabaseHelper.getData();

        boolean found_darkmode=false;
        number_questions=5;

        while(data.moveToNext()){
            if(data.getInt(1)==11){
                found_darkmode=true;
            }
            else if(data.getInt(1)==12){
                number_questions=10;
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
        level = game_level.getStringExtra("level");
        game_level_display.setText("L" + level);

        //Check the level to see what the finishing_score is
        //The level also allows you to get the symbol e.g. add, subtract
        //Multiply, divide, square root etc.

        game_highscore = findViewById(R.id.game_highscore);

        randomSymbol = (int) (Math.random()*4);

        game_highscore.setText("" + current_score);

        positions = new int[6][8];
        checked = new int[6][8];

        for(int i=0; i<=5; i++){
            for(int j=0; j<=7; j++){
                randomSymbol = (int) (Math.random()*9)+1;
                positions[i][j] = randomSymbol;
                checked[i][j]=0;
            }
        }

        cards_turned=0;
        card1=0;
        card2=0;

        questions_correct=0;
        current_question = questions_correct+1;

        game_questions.setText("Q" + current_question);

        //Set the random numbers and target
        randomx = (int) (Math.random()*6);
        randomy = (int) (Math.random()*8);

        array_position1 = positions[randomx][randomy];

        randomx2 = (int) (Math.random()*6);
        randomy2 = (int) (Math.random()*8);

        while((randomx2==randomx) && (randomy2==randomy)){
            randomx2 = (int) (Math.random()*6);
            randomy2 = (int) (Math.random()*8);
        }

        array_position2 = positions[randomx2][randomy2];

        //Level 1 is addition
        if(level.equals("1")){
            target = array_position1 + array_position2;
            game_highscore.setText("? + ? = " + target);
        }

        //Level 2 is subtraction
        else if (level.equals("2")){
            target = array_position1 - array_position2;
            game_highscore.setText("? - ? = " + target);
        }

        //Level 3 is multiplication
        else if(level.equals("3")){
            target = array_position1 * array_position2;
            game_highscore.setText("? x ? = " + target);
        }

        //Level 4 is division
        else if (level.equals("4")){
            while(array_position1==6 || array_position2==6){

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            target = array_position1;
            game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position2 + " = (? - ?)");
        }

        //Level 5 is squared
        else if (level.equals("5")){
            while(array_position1==1 || array_position2==1){

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            target = array_position1;
            game_highscore.setText("(? + ?)² = " +(array_position1 * array_position1));
        }

        //Level 6 is algebra
        else if (level.equals("6")){
            while(array_position1==1 || array_position2==1){

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            target = array_position1;
            game_highscore.setText("" + "(? + ?) + " + array_position2 + " = " + (array_position1 + array_position2));
        }

        //Level 7 is cubed
        else if (level.equals("7")){
            while(array_position1==1 || array_position2==1){

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            target = array_position1;
            game_highscore.setText("(? + ?)³ = " + (array_position1 * array_position1 * array_position1));
        }

        //Level 8 is multiplication with odd numbers
        else if (level.equals("8")){

            //Subract two odd numbers to add up to multiplied value
            //Need to make sure they are both odd
            //Do not need to check they are 6 since they have to be odd
            while((array_position1%2!=0) && (array_position2%2!=0)){

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            if(array_position1%2==0){
                target = array_position1;
                game_highscore.setText("" + array_position2 + " x (? - ?) = " + (array_position1 * array_position2));
            }
            else{
                target = array_position2;
                game_highscore.setText("" + array_position1 + " x (? - ?) = " + (array_position1 * array_position2));
            }

        }

        //Division with even numbers
        else if (level.equals("9")){

            //Add two even numbers to add up to divided value
            //Need to check they are both even
            //No need to check if they are 1 since they both have to be even
            while(((array_position1%2!=0) && (array_position2%2!=0)) || (array_position1<=3 || array_position2<=3)){
                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position1 = positions[randomx][randomy];

                randomx = (int) (Math.random()*6);
                randomy = (int) (Math.random()*8);

                array_position2 = positions[randomx][randomy];
            }
            if(array_position1%2==0){
                target = array_position1;
                game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position2 + " = (? + ?)");
            }
            else{
                target = array_position2;
                game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position1 + " = (? + ?)");
            }

        }

        maingame_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(GameActivity.this, LevelActivity.class);
                startActivity(myIntent);

            }
        });

        maingame_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oneone.setEnabled(false);
                onetwo.setEnabled(false);
                onethree.setEnabled(false);
                onefour.setEnabled(false);
                onefive.setEnabled(false);
                onesix.setEnabled(false);

                twoone.setEnabled(false);
                twotwo.setEnabled(false);
                twothree.setEnabled(false);
                twofour.setEnabled(false);
                twofive.setEnabled(false);
                twosix.setEnabled(false);

                threeone.setEnabled(false);
                threetwo.setEnabled(false);
                threethree.setEnabled(false);
                threefour.setEnabled(false);
                threefive.setEnabled(false);
                threesix.setEnabled(false);

                fourone.setEnabled(false);
                fourtwo.setEnabled(false);
                fourthree.setEnabled(false);
                fourfour.setEnabled(false);
                fourfive.setEnabled(false);
                foursix.setEnabled(false);

                fiveone.setEnabled(false);
                fivetwo.setEnabled(false);
                fivethree.setEnabled(false);
                fivefour.setEnabled(false);
                fivefive.setEnabled(false);
                fivesix.setEnabled(false);

                sixone.setEnabled(false);
                sixtwo.setEnabled(false);
                sixthree.setEnabled(false);
                sixfour.setEnabled(false);
                sixfive.setEnabled(false);
                sixsix.setEnabled(false);

                sevenone.setEnabled(false);
                seventwo.setEnabled(false);
                seventhree.setEnabled(false);
                sevenfour.setEnabled(false);
                sevenfive.setEnabled(false);
                sevensix.setEnabled(false);

                eightone.setEnabled(false);
                eighttwo.setEnabled(false);
                eightthree.setEnabled(false);
                eightfour.setEnabled(false);
                eightfive.setEnabled(false);
                eightsix.setEnabled(false);


                Toast toast = Toast.makeText(getApplicationContext(), "Question skipped", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 200);
                view = toast.getView();
                view.setBackgroundResource(R.drawable.radius);
                view.setPadding(200, 100, 200, 100);
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.parseColor("#000000"));
                text.setTextSize(24);
                text.setGravity(Gravity.CENTER);
                toast.show();
                resetValues();

            }
        });

        oneone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Bug fixing
                if(cards_turned<0){
                    cards_turned=0;
                }

                //Only if the cards turned is less than two then you can turn one over

                if (oneone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][0]=2;
                        }
                        else{
                            checked[0][0] = 1;
                        }


                        cards_turned++;

                        if (positions[0][0] == 1) {
                            oneone.setBackgroundResource(R.drawable.number_one);
                            oneone.setTag("number_one");
                        } else if (positions[0][0] == 2) {
                            oneone.setBackgroundResource(R.drawable.number_two);
                            oneone.setTag("number_two");
                        } else if (positions[0][0] == 3) {
                            oneone.setBackgroundResource(R.drawable.number_three);
                            oneone.setTag("number_three");
                        } else if (positions[0][0] == 4) {
                            oneone.setBackgroundResource(R.drawable.number_four);
                            oneone.setTag("number_four");
                        } else if (positions[0][0] == 5) {
                            oneone.setBackgroundResource(R.drawable.number_five);
                            oneone.setTag("number_five");
                        } else if (positions[0][0] == 6) {
                            oneone.setBackgroundResource(R.drawable.number_six);
                            oneone.setTag("number_six");
                        }
                        else if (positions[0][0] == 7) {
                            oneone.setBackgroundResource(R.drawable.number_seven);
                            oneone.setTag("number_seven");
                        }
                        else if (positions[0][0] == 8) {
                            oneone.setBackgroundResource(R.drawable.number_eight);
                            oneone.setTag("number_eight");
                        }
                        else if (positions[0][0] == 9) {
                            oneone.setBackgroundResource(R.drawable.number_nine);
                            oneone.setTag("number_nine");
                        }
                    }
                }
                else{
                    oneone.setBackgroundResource(R.drawable.face_down_card);
                    oneone.setTag("face_down_card");

                    checked[0][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }


                checkMatches();

            }
        });
        onetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (onetwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][0]=2;
                        }
                        else{
                            checked[1][0] = 1;
                        }

                        cards_turned++;

                        if (positions[1][0] == 1) {
                            onetwo.setBackgroundResource(R.drawable.number_one);
                            onetwo.setTag("number_one");
                        } else if (positions[1][0] == 2) {
                            onetwo.setBackgroundResource(R.drawable.number_two);
                            onetwo.setTag("number_two");
                        } else if (positions[1][0] == 3) {
                            onetwo.setBackgroundResource(R.drawable.number_three);
                            onetwo.setTag("number_three");
                        } else if (positions[1][0] == 4) {
                            onetwo.setBackgroundResource(R.drawable.number_four);
                            onetwo.setTag("number_four");
                        } else if (positions[1][0] == 5) {
                            onetwo.setBackgroundResource(R.drawable.number_five);
                            onetwo.setTag("number_five");
                        } else if (positions[1][0] == 6) {
                            onetwo.setBackgroundResource(R.drawable.number_six);
                            onetwo.setTag("number_six");
                        }
                        else if (positions[1][0] == 7) {
                            onetwo.setBackgroundResource(R.drawable.number_seven);
                            onetwo.setTag("number_seven");
                        }
                        else if (positions[1][0] == 8) {
                            onetwo.setBackgroundResource(R.drawable.number_eight);
                            onetwo.setTag("number_eight");
                        }
                        else if (positions[1][0] == 9) {
                            onetwo.setBackgroundResource(R.drawable.number_nine);
                            onetwo.setTag("number_nine");
                        }
                    }

                }
                else{
                    onetwo.setBackgroundResource(R.drawable.face_down_card);
                    onetwo.setTag("face_down_card");

                    checked[1][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();

            }
        });
        onethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (onethree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][0]=2;
                        }
                        else{
                            checked[2][0] = 1;
                        }

                        cards_turned++;


                        if (positions[2][0] == 1) {
                            onethree.setBackgroundResource(R.drawable.number_one);
                            onethree.setTag("number_one");
                        } else if (positions[2][0] == 2) {
                            onethree.setBackgroundResource(R.drawable.number_two);
                            onethree.setTag("number_two");
                        } else if (positions[2][0] == 3) {
                            onethree.setBackgroundResource(R.drawable.number_three);
                            onethree.setTag("number_three");
                        } else if (positions[2][0] == 4) {
                            onethree.setBackgroundResource(R.drawable.number_four);
                            onethree.setTag("number_four");
                        } else if (positions[2][0] == 5) {
                            onethree.setBackgroundResource(R.drawable.number_five);
                            onethree.setTag("number_five");
                        } else if (positions[2][0] == 6) {
                            onethree.setBackgroundResource(R.drawable.number_six);
                            onethree.setTag("number_six");
                        }
                        else if (positions[2][0] == 7) {
                            onethree.setBackgroundResource(R.drawable.number_seven);
                            onethree.setTag("number_seven");
                        }
                        else if (positions[2][0] == 8) {
                            onethree.setBackgroundResource(R.drawable.number_eight);
                            onethree.setTag("number_eight");
                        }
                        else if (positions[2][0] == 9) {
                            onethree.setBackgroundResource(R.drawable.number_nine);
                            onethree.setTag("number_nine");
                        }
                    }

                }
                else{
                    onethree.setBackgroundResource(R.drawable.face_down_card);
                    onethree.setTag("face_down_card");

                    checked[2][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();


            }
        });
        onefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (onefour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][0]=2;
                        }
                        else{
                            checked[3][0] = 1;
                        }

                        cards_turned++;


                        if (positions[3][0] == 1) {
                            onefour.setBackgroundResource(R.drawable.number_one);
                            onefour.setTag("number_one");
                        } else if (positions[3][0] == 2) {
                            onefour.setBackgroundResource(R.drawable.number_two);
                            onefour.setTag("number_two");
                        } else if (positions[3][0] == 3) {
                            onefour.setBackgroundResource(R.drawable.number_three);
                            onefour.setTag("number_three");
                        } else if (positions[3][0] == 4) {
                            onefour.setBackgroundResource(R.drawable.number_four);
                            onefour.setTag("number_four");
                        } else if (positions[3][0] == 5) {
                            onefour.setBackgroundResource(R.drawable.number_five);
                            onefour.setTag("number_five");
                        } else if (positions[3][0] == 6) {
                            onefour.setBackgroundResource(R.drawable.number_six);
                            onefour.setTag("number_six");
                        }
                        else if (positions[3][0] == 7) {
                            onefour.setBackgroundResource(R.drawable.number_seven);
                            onefour.setTag("number_seven");
                        }
                        else if (positions[3][0] == 8) {
                            onefour.setBackgroundResource(R.drawable.number_eight);
                            onefour.setTag("number_eight");
                        }
                        else if (positions[3][0] == 9) {
                            onefour.setBackgroundResource(R.drawable.number_nine);
                            onefour.setTag("number_nine");
                        }
                    }

                }
                else{
                    onefour.setBackgroundResource(R.drawable.face_down_card);
                    onefour.setTag("face_down_card");

                    checked[3][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();

            }
        });
        onefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (onefive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][0]=2;
                        }
                        else{
                            checked[4][0] = 1;
                        }

                        cards_turned++;


                        if (positions[4][0] == 1) {
                            onefive.setBackgroundResource(R.drawable.number_one);
                            onefive.setTag("number_one");
                        } else if (positions[4][0] == 2) {
                            onefive.setBackgroundResource(R.drawable.number_two);
                            onefive.setTag("number_two");
                        } else if (positions[4][0] == 3) {
                            onefive.setBackgroundResource(R.drawable.number_three);
                            onefive.setTag("number_three");
                        } else if (positions[4][0] == 4) {
                            onefive.setBackgroundResource(R.drawable.number_four);
                            onefive.setTag("number_four");
                        } else if (positions[4][0] == 5) {
                            onefive.setBackgroundResource(R.drawable.number_five);
                            onefive.setTag("number_five");
                        } else if (positions[4][0] == 6) {
                            onefive.setBackgroundResource(R.drawable.number_six);
                            onefive.setTag("number_six");
                        }
                        else if (positions[4][0] == 7) {
                            onefive.setBackgroundResource(R.drawable.number_seven);
                            onefive.setTag("number_seven");
                        }
                        else if (positions[4][0] == 8) {
                            onefive.setBackgroundResource(R.drawable.number_eight);
                            onefive.setTag("number_eight");
                        }
                        else if (positions[4][0] == 9) {
                            onefive.setBackgroundResource(R.drawable.number_nine);
                            onefive.setTag("number_nine");
                        }
                    }

                }
                else{
                    onefive.setBackgroundResource(R.drawable.face_down_card);
                    onefive.setTag("face_down_card");

                    checked[4][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;


                }
                checkMatches();

            }
        });
        onesix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (onesix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][0]=2;
                        }
                        else{
                            checked[5][0] = 1;
                        }

                        cards_turned++;


                        if (positions[5][0] == 1) {
                            onesix.setBackgroundResource(R.drawable.number_one);
                            onesix.setTag("number_one");
                        } else if (positions[5][0] == 2) {
                            onesix.setBackgroundResource(R.drawable.number_two);
                            onesix.setTag("number_two");
                        } else if (positions[5][0] == 3) {
                            onesix.setBackgroundResource(R.drawable.number_three);
                            onesix.setTag("number_three");
                        } else if (positions[5][0] == 4) {
                            onesix.setBackgroundResource(R.drawable.number_four);
                            onesix.setTag("number_four");
                        } else if (positions[5][0] == 5) {
                            onesix.setBackgroundResource(R.drawable.number_five);
                            onesix.setTag("number_five");
                        } else if (positions[5][0] == 6) {
                            onesix.setBackgroundResource(R.drawable.number_six);
                            onesix.setTag("number_six");
                        }
                        else if (positions[5][0] == 7) {
                            onesix.setBackgroundResource(R.drawable.number_seven);
                            onesix.setTag("number_seven");
                        }
                        else if (positions[5][0] == 8) {
                            onesix.setBackgroundResource(R.drawable.number_eight);
                            onesix.setTag("number_eight");
                        }
                        else if (positions[5][0] == 9) {
                            onesix.setBackgroundResource(R.drawable.number_nine);
                            onesix.setTag("number_nine");
                        }
                    }

                }
                else{
                    onesix.setBackgroundResource(R.drawable.face_down_card);
                    onesix.setTag("face_down_card");


                    checked[5][0]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();
            }
        });


        twoone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twoone.getTag().equals("face_down_card")) {
                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {


                        if(cards_turned==1){
                            checked[0][1]=2;
                        }
                        else{
                            checked[0][1] = 1;
                        }

                        cards_turned++;


                        if (positions[0][1] == 1) {
                            twoone.setBackgroundResource(R.drawable.number_one);
                            twoone.setTag("number_one");
                        } else if (positions[0][1] == 2) {
                            twoone.setBackgroundResource(R.drawable.number_two);
                            twoone.setTag("number_two");
                        } else if (positions[0][1] == 3) {
                            twoone.setBackgroundResource(R.drawable.number_three);
                            twoone.setTag("number_three");
                        } else if (positions[0][1] == 4) {
                            twoone.setBackgroundResource(R.drawable.number_four);
                            twoone.setTag("number_four");
                        } else if (positions[0][1] == 5) {
                            twoone.setBackgroundResource(R.drawable.number_five);
                            twoone.setTag("number_five");
                        } else if (positions[0][1] == 6) {
                            twoone.setBackgroundResource(R.drawable.number_six);
                            twoone.setTag("number_six");
                        }
                        else if (positions[0][1] == 7) {
                            twoone.setBackgroundResource(R.drawable.number_seven);
                            twoone.setTag("number_seven");
                        }
                        else if (positions[0][1] == 8) {
                            twoone.setBackgroundResource(R.drawable.number_eight);
                            twoone.setTag("number_eight");
                        }
                        else if (positions[0][1] == 9) {
                            twoone.setBackgroundResource(R.drawable.number_nine);
                            twoone.setTag("number_nine");
                        }

                    }
                }
                else{
                    twoone.setBackgroundResource(R.drawable.face_down_card);
                    twoone.setTag("face_down_card");

                    checked[0][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();

            }
        });
        twotwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twotwo.getTag().equals("face_down_card")) {
                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][1]=2;
                        }
                        else{
                            checked[1][1] = 1;
                        }

                        cards_turned++;


                        if (positions[1][1] == 1) {
                            twotwo.setBackgroundResource(R.drawable.number_one);
                            twotwo.setTag("number_one");
                        } else if (positions[1][1] == 2) {
                            twotwo.setBackgroundResource(R.drawable.number_two);
                            twotwo.setTag("number_two");
                        } else if (positions[1][1] == 3) {
                            twotwo.setBackgroundResource(R.drawable.number_three);
                            twotwo.setTag("number_three");
                        } else if (positions[1][1] == 4) {
                            twotwo.setBackgroundResource(R.drawable.number_four);
                            twotwo.setTag("number_four");
                        } else if (positions[1][1] == 5) {
                            twotwo.setBackgroundResource(R.drawable.number_five);
                            twotwo.setTag("number_five");
                        } else if (positions[1][1] == 6) {
                            twotwo.setBackgroundResource(R.drawable.number_six);
                            twotwo.setTag("number_six");
                        }
                        else if (positions[1][1] == 7) {
                            twotwo.setBackgroundResource(R.drawable.number_seven);
                            twotwo.setTag("number_seven");
                        }
                        else if (positions[1][1] == 8) {
                            twotwo.setBackgroundResource(R.drawable.number_eight);
                            twotwo.setTag("number_eight");
                        }
                        else if (positions[1][1] == 9) {
                            twotwo.setBackgroundResource(R.drawable.number_nine);
                            twotwo.setTag("number_nine");
                        }
                    }
                }
                else{
                    twotwo.setBackgroundResource(R.drawable.face_down_card);
                    twotwo.setTag("face_down_card");

                    checked[1][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();

            }
        });
        twothree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twothree.getTag().equals("face_down_card")) {
                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][1]=2;
                        }
                        else{
                            checked[2][1] = 1;
                        }

                        cards_turned++;


                        if (positions[2][1] == 1) {
                            twothree.setBackgroundResource(R.drawable.number_one);
                            twothree.setTag("number_one");
                        } else if (positions[2][1] == 2) {
                            twothree.setBackgroundResource(R.drawable.number_two);
                            twothree.setTag("number_two");
                        } else if (positions[2][1] == 3) {
                            twothree.setBackgroundResource(R.drawable.number_three);
                            twothree.setTag("number_three");
                        } else if (positions[2][1] == 4) {
                            twothree.setBackgroundResource(R.drawable.number_four);
                            twothree.setTag("number_four");
                        } else if (positions[2][1] == 5) {
                            twothree.setBackgroundResource(R.drawable.number_five);
                            twothree.setTag("number_five");
                        } else if (positions[2][1] == 6) {
                            twothree.setBackgroundResource(R.drawable.number_six);
                            twothree.setTag("number_six");
                        }
                        else if (positions[2][1] == 7) {
                            twothree.setBackgroundResource(R.drawable.number_seven);
                            twothree.setTag("number_seven");
                        }
                        else if (positions[2][1] == 8) {
                            twothree.setBackgroundResource(R.drawable.number_eight);
                            twothree.setTag("number_eight");
                        }
                        else if (positions[2][1] == 9) {
                            twothree.setBackgroundResource(R.drawable.number_nine);
                            twothree.setTag("number_nine");
                        }
                    }
                }
                else{
                    twothree.setBackgroundResource(R.drawable.face_down_card);
                    twothree.setTag("face_down_card");

                    checked[2][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        twofour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twofour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][1]=2;
                        }
                        else{
                            checked[3][1] = 1;
                        }

                        cards_turned++;


                        if (positions[3][1] == 1) {
                            twofour.setBackgroundResource(R.drawable.number_one);
                            twofour.setTag("number_one");
                        } else if (positions[3][1] == 2) {
                            twofour.setBackgroundResource(R.drawable.number_two);
                            twofour.setTag("number_two");
                        } else if (positions[3][1] == 3) {
                            twofour.setBackgroundResource(R.drawable.number_three);
                            twofour.setTag("number_three");
                        } else if (positions[3][1] == 4) {
                            twofour.setBackgroundResource(R.drawable.number_four);
                            twofour.setTag("number_four");
                        } else if (positions[3][1] == 5) {
                            twofour.setBackgroundResource(R.drawable.number_five);
                            twofour.setTag("number_five");
                        } else if (positions[3][1] == 6) {
                            twofour.setBackgroundResource(R.drawable.number_six);
                            twofour.setTag("number_six");
                        }
                        else if (positions[3][1] == 7) {
                            twofour.setBackgroundResource(R.drawable.number_seven);
                            twofour.setTag("number_seven");
                        }
                        else if (positions[3][1] == 8) {
                            twofour.setBackgroundResource(R.drawable.number_eight);
                            twofour.setTag("number_eight");
                        }
                        else if (positions[3][1] == 9) {
                            twofour.setBackgroundResource(R.drawable.number_nine);
                            twofour.setTag("number_nine");
                        }
                    }

                }
                else{
                    twofour.setBackgroundResource(R.drawable.face_down_card);
                    twofour.setTag("face_down_card");

                    checked[3][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        twofive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twofive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][1]=2;
                        }
                        else{
                            checked[4][1] = 1;
                        }

                        cards_turned++;


                        if (positions[4][1] == 1) {
                            twofive.setBackgroundResource(R.drawable.number_one);
                            twofive.setTag("number_one");
                        } else if (positions[4][1] == 2) {
                            twofive.setBackgroundResource(R.drawable.number_two);
                            twofive.setTag("number_two");
                        } else if (positions[4][1] == 3) {
                            twofive.setBackgroundResource(R.drawable.number_three);
                            twofive.setTag("number_three");
                        } else if (positions[4][1] == 4) {
                            twofive.setBackgroundResource(R.drawable.number_four);
                            twofive.setTag("number_four");
                        } else if (positions[4][1] == 5) {
                            twofive.setBackgroundResource(R.drawable.number_five);
                            twofive.setTag("number_five");
                        } else if (positions[4][1] == 6) {
                            twofive.setBackgroundResource(R.drawable.number_six);
                            twofive.setTag("number_six");
                        }
                        else if (positions[4][1] == 7) {
                            twofive.setBackgroundResource(R.drawable.number_seven);
                            twofive.setTag("number_seven");
                        }
                        else if (positions[4][1] == 8) {
                            twofive.setBackgroundResource(R.drawable.number_eight);
                            twofive.setTag("number_eight");
                        }
                        else if (positions[4][1] == 9) {
                            twofive.setBackgroundResource(R.drawable.number_nine);
                            twofive.setTag("number_nine");
                        }

                    }
                }
                else{
                    twofive.setBackgroundResource(R.drawable.face_down_card);
                    twofive.setTag("face_down_card");

                    checked[4][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        twosix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (twosix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][1]=2;
                        }
                        else{
                            checked[5][1] = 1;
                        }

                        cards_turned++;


                        if (positions[5][1] == 1) {
                            twosix.setBackgroundResource(R.drawable.number_one);
                            twosix.setTag("number_one");
                        } else if (positions[5][1] == 2) {
                            twosix.setBackgroundResource(R.drawable.number_two);
                            twosix.setTag("number_two");
                        } else if (positions[5][1] == 3) {
                            twosix.setBackgroundResource(R.drawable.number_three);
                            twosix.setTag("number_three");
                        } else if (positions[5][1] == 4) {
                            twosix.setBackgroundResource(R.drawable.number_four);
                            twosix.setTag("number_four");
                        } else if (positions[5][1] == 5) {
                            twosix.setBackgroundResource(R.drawable.number_five);
                            twosix.setTag("number_five");
                        } else if (positions[5][1] == 6) {
                            twosix.setBackgroundResource(R.drawable.number_six);
                            twosix.setTag("number_six");
                        }
                        else if (positions[5][1] == 7) {
                            twosix.setBackgroundResource(R.drawable.number_seven);
                            twosix.setTag("number_seven");
                        }
                        else if (positions[5][1] == 8) {
                            twosix.setBackgroundResource(R.drawable.number_eight);
                            twosix.setTag("number_eight");
                        }
                        else if (positions[5][1] == 9) {
                            twosix.setBackgroundResource(R.drawable.number_nine);
                            twosix.setTag("number_nine");
                        }
                    }

                }
                else{
                    twosix.setBackgroundResource(R.drawable.face_down_card);
                    twosix.setTag("face_down_card");

                    checked[5][1]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });

        threeone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (threeone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][2]=2;
                        }
                        else{
                            checked[0][2] = 1;
                        }

                        cards_turned++;


                        if (positions[0][2] == 1) {
                            threeone.setBackgroundResource(R.drawable.number_one);
                            threeone.setTag("number_one");
                        } else if (positions[0][2] == 2) {
                            threeone.setBackgroundResource(R.drawable.number_two);
                            threeone.setTag("number_two");
                        } else if (positions[0][2] == 3) {
                            threeone.setBackgroundResource(R.drawable.number_three);
                            threeone.setTag("number_three");
                        } else if (positions[0][2] == 4) {
                            threeone.setBackgroundResource(R.drawable.number_four);
                            threeone.setTag("number_four");
                        } else if (positions[0][2] == 5) {
                            threeone.setBackgroundResource(R.drawable.number_five);
                            threeone.setTag("number_five");
                        } else if (positions[0][2] == 6) {
                            threeone.setBackgroundResource(R.drawable.number_six);
                            threeone.setTag("number_six");
                        }
                        else if (positions[0][2] == 7) {
                            threeone.setBackgroundResource(R.drawable.number_seven);
                            threeone.setTag("number_seven");
                        }
                        else if (positions[0][2] == 8) {
                            threeone.setBackgroundResource(R.drawable.number_eight);
                            threeone.setTag("number_eight");
                        }
                        else if (positions[0][2] == 9) {
                            threeone.setBackgroundResource(R.drawable.number_nine);
                            threeone.setTag("number_nine");
                        }
                    }
                }
                else{
                    threeone.setBackgroundResource(R.drawable.face_down_card);
                    threeone.setTag("face_down_card");

                    checked[0][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        threetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (threetwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][2]=2;
                        }
                        else{
                            checked[1][2] = 1;
                        }

                        cards_turned++;


                        if (positions[1][2] == 1) {
                            threetwo.setBackgroundResource(R.drawable.number_one);
                            threetwo.setTag("number_one");
                        } else if (positions[1][2] == 2) {
                            threetwo.setBackgroundResource(R.drawable.number_two);
                            threetwo.setTag("number_two");
                        } else if (positions[1][2] == 3) {
                            threetwo.setBackgroundResource(R.drawable.number_three);
                            threetwo.setTag("number_three");
                        } else if (positions[1][2] == 4) {
                            threetwo.setBackgroundResource(R.drawable.number_four);
                            threetwo.setTag("number_four");
                        } else if (positions[1][2] == 5) {
                            threetwo.setBackgroundResource(R.drawable.number_five);
                            threetwo.setTag("number_five");
                        } else if (positions[1][2] == 6) {
                            threetwo.setBackgroundResource(R.drawable.number_six);
                            threetwo.setTag("number_six");
                        }
                        else if (positions[1][2] == 7) {
                            threetwo.setBackgroundResource(R.drawable.number_seven);
                            threetwo.setTag("number_seven");
                        }
                        else if (positions[1][2] == 8) {
                            threetwo.setBackgroundResource(R.drawable.number_eight);
                            threetwo.setTag("number_eight");
                        }
                        else if (positions[1][2] == 9) {
                            threetwo.setBackgroundResource(R.drawable.number_nine);
                            threetwo.setTag("number_nine");
                        }

                    }
                }
                else{
                    threetwo.setBackgroundResource(R.drawable.face_down_card);
                    threetwo.setTag("face_down_card");

                    checked[1][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        threethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (threethree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][2]=2;
                        }
                        else{
                            checked[2][2] = 1;
                        }

                        cards_turned++;


                        if (positions[2][2] == 1) {
                            threethree.setBackgroundResource(R.drawable.number_one);
                            threethree.setTag("number_one");
                        } else if (positions[2][2] == 2) {
                            threethree.setBackgroundResource(R.drawable.number_two);
                            threethree.setTag("number_two");
                        } else if (positions[2][2] == 3) {
                            threethree.setBackgroundResource(R.drawable.number_three);
                            threethree.setTag("number_three");
                        } else if (positions[2][2] == 4) {
                            threethree.setBackgroundResource(R.drawable.number_four);
                            threethree.setTag("number_four");
                        } else if (positions[2][2] == 5) {
                            threethree.setBackgroundResource(R.drawable.number_five);
                            threethree.setTag("number_five");
                        } else if (positions[2][2] == 6) {
                            threethree.setBackgroundResource(R.drawable.number_six);
                            threethree.setTag("number_six");
                        }
                        else if (positions[2][2] == 7) {
                            threethree.setBackgroundResource(R.drawable.number_seven);
                            threethree.setTag("number_seven");
                        }
                        else if (positions[2][2] == 8) {
                            threethree.setBackgroundResource(R.drawable.number_eight);
                            threethree.setTag("number_eight");
                        }
                        else if (positions[2][2] == 9) {
                            threethree.setBackgroundResource(R.drawable.number_nine);
                            threethree.setTag("number_nine");
                        }
                    }
                }
                else{
                    threethree.setBackgroundResource(R.drawable.face_down_card);
                    threethree.setTag("face_down_card");

                    checked[2][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        threefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (threefour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][2]=2;
                        }
                        else{
                            checked[3][2] = 1;
                        }

                        cards_turned++;


                        if (positions[3][2] == 1) {
                            threefour.setBackgroundResource(R.drawable.number_one);
                            threefour.setTag("number_one");
                        } else if (positions[3][2] == 2) {
                            threefour.setBackgroundResource(R.drawable.number_two);
                            threefour.setTag("number_two");
                        } else if (positions[3][2] == 3) {
                            threefour.setBackgroundResource(R.drawable.number_three);
                            threefour.setTag("number_three");
                        } else if (positions[3][2] == 4) {
                            threefour.setBackgroundResource(R.drawable.number_four);
                            threefour.setTag("number_four");
                        } else if (positions[3][2] == 5) {
                            threefour.setBackgroundResource(R.drawable.number_five);
                            threefour.setTag("number_five");
                        } else if (positions[3][2] == 6) {
                            threefour.setBackgroundResource(R.drawable.number_six);
                            threefour.setTag("number_six");
                        }
                        else if (positions[3][2] == 7) {
                            threefour.setBackgroundResource(R.drawable.number_seven);
                            threefour.setTag("number_seven");
                        }
                        else if (positions[3][2] == 8) {
                            threefour.setBackgroundResource(R.drawable.number_eight);
                            threefour.setTag("number_eight");
                        }
                        else if (positions[3][2] == 9) {
                            threefour.setBackgroundResource(R.drawable.number_nine);
                            threefour.setTag("number_nine");
                        }
                    }
                }
                else{
                    threefour.setBackgroundResource(R.drawable.face_down_card);
                    threefour.setTag("face_down_card");

                    checked[3][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        threefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (threefive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][2]=2;
                        }
                        else{
                            checked[4][2] = 1;
                        }

                        cards_turned++;


                        if (positions[4][2] == 1) {
                            threefive.setBackgroundResource(R.drawable.number_one);
                            threefive.setTag("number_one");
                        } else if (positions[4][2] == 2) {
                            threefive.setBackgroundResource(R.drawable.number_two);
                            threefive.setTag("number_two");
                        } else if (positions[4][2] == 3) {
                            threefive.setBackgroundResource(R.drawable.number_three);
                            threefive.setTag("number_three");
                        } else if (positions[4][2] == 4) {
                            threefive.setBackgroundResource(R.drawable.number_four);
                            threefive.setTag("number_four");
                        } else if (positions[4][2] == 5) {
                            threefive.setBackgroundResource(R.drawable.number_five);
                            threefive.setTag("number_five");
                        } else if (positions[4][2] == 6) {
                            threefive.setBackgroundResource(R.drawable.number_six);
                            threefive.setTag("number_six");
                        }
                        else if (positions[4][2] == 7) {
                            threefive.setBackgroundResource(R.drawable.number_seven);
                            threefive.setTag("number_seven");
                        }
                        else if (positions[4][2] == 8) {
                            threefive.setBackgroundResource(R.drawable.number_eight);
                            threefive.setTag("number_eight");
                        }
                        else if (positions[4][2] == 9) {
                            threefive.setBackgroundResource(R.drawable.number_nine);
                            threefive.setTag("number_nine");
                        }
                    }
                }
                else{
                    threefive.setBackgroundResource(R.drawable.face_down_card);
                    threefive.setTag("face_down_card");

                    checked[4][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        threesix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (threesix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][2]=2;
                        }
                        else{
                            checked[5][2] = 1;
                        }

                        cards_turned++;


                        if (positions[5][2] == 1) {
                            threesix.setBackgroundResource(R.drawable.number_one);
                            threesix.setTag("number_one");
                        } else if (positions[5][2] == 2) {
                            threesix.setBackgroundResource(R.drawable.number_two);
                            threesix.setTag("number_two");
                        } else if (positions[5][2] == 3) {
                            threesix.setBackgroundResource(R.drawable.number_three);
                            threesix.setTag("number_three");
                        } else if (positions[5][2] == 4) {
                            threesix.setBackgroundResource(R.drawable.number_four);
                            threesix.setTag("number_four");
                        } else if (positions[5][2] == 5) {
                            threesix.setBackgroundResource(R.drawable.number_five);
                            threesix.setTag("number_five");
                        } else if (positions[5][2] == 6) {
                            threesix.setBackgroundResource(R.drawable.number_six);
                            threesix.setTag("number_six");
                        }
                        else if (positions[5][2] == 7) {
                            threesix.setBackgroundResource(R.drawable.number_seven);
                            threesix.setTag("number_seven");
                        }
                        else if (positions[5][2] == 8) {
                            threesix.setBackgroundResource(R.drawable.number_eight);
                            threesix.setTag("number_eight");
                        }
                        else if (positions[5][2] == 9) {
                            threesix.setBackgroundResource(R.drawable.number_nine);
                            threesix.setTag("number_nine");
                        }
                    }
                }
                else{
                    threesix.setBackgroundResource(R.drawable.face_down_card);
                    threesix.setTag("face_down_card");

                    checked[5][2]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });


        fourone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (fourone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][3]=2;
                        }
                        else{
                            checked[0][3] = 1;
                        }

                        cards_turned++;


                        if (positions[0][3] == 1) {
                            fourone.setBackgroundResource(R.drawable.number_one);
                            fourone.setTag("number_one");
                        } else if (positions[0][3] == 2) {
                            fourone.setBackgroundResource(R.drawable.number_two);
                            fourone.setTag("number_two");
                        } else if (positions[0][3] == 3) {
                            fourone.setBackgroundResource(R.drawable.number_three);
                            fourone.setTag("number_three");
                        } else if (positions[0][3] == 4) {
                            fourone.setBackgroundResource(R.drawable.number_four);
                            fourone.setTag("number_four");
                        } else if (positions[0][3] == 5) {
                            fourone.setBackgroundResource(R.drawable.number_five);
                            fourone.setTag("number_five");
                        } else if (positions[0][3] == 6) {
                            fourone.setBackgroundResource(R.drawable.number_six);
                            fourone.setTag("number_six");
                        }
                        else if (positions[0][3] == 7) {
                            fourone.setBackgroundResource(R.drawable.number_seven);
                            fourone.setTag("number_seven");
                        }
                        else if (positions[0][3] == 8) {
                            fourone.setBackgroundResource(R.drawable.number_eight);
                            fourone.setTag("number_eight");
                        }
                        else if (positions[0][3] == 9) {
                            fourone.setBackgroundResource(R.drawable.number_nine);
                            fourone.setTag("number_nine");
                        }
                    }
                }
                else{
                    fourone.setBackgroundResource(R.drawable.face_down_card);
                    fourone.setTag("face_down_card");

                    checked[0][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        fourtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (fourtwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][3]=2;
                        }
                        else{
                            checked[1][3] = 1;
                        }

                        cards_turned++;


                        if (positions[1][3] == 1) {
                            fourtwo.setBackgroundResource(R.drawable.number_one);
                            fourtwo.setTag("number_one");
                        } else if (positions[1][3] == 2) {
                            fourtwo.setBackgroundResource(R.drawable.number_two);
                            fourtwo.setTag("number_two");
                        } else if (positions[1][3] == 3) {
                            fourtwo.setBackgroundResource(R.drawable.number_three);
                            fourtwo.setTag("number_three");
                        } else if (positions[1][3] == 4) {
                            fourtwo.setBackgroundResource(R.drawable.number_four);
                            fourtwo.setTag("number_four");
                        } else if (positions[1][3] == 5) {
                            fourtwo.setBackgroundResource(R.drawable.number_five);
                            fourtwo.setTag("number_five");
                        } else if (positions[1][3] == 6) {
                            fourtwo.setBackgroundResource(R.drawable.number_six);
                            fourtwo.setTag("number_six");
                        }
                        else if (positions[1][3] == 7) {
                            fourtwo.setBackgroundResource(R.drawable.number_seven);
                            fourtwo.setTag("number_seven");
                        }
                        else if (positions[1][3] == 8) {
                            fourtwo.setBackgroundResource(R.drawable.number_eight);
                            fourtwo.setTag("number_eight");
                        }
                        else if (positions[1][3] == 9) {
                            fourtwo.setBackgroundResource(R.drawable.number_nine);
                            fourtwo.setTag("number_nine");
                        }
                    }

                }
                else{
                    fourtwo.setBackgroundResource(R.drawable.face_down_card);
                    fourtwo.setTag("face_down_card");

                    checked[1][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        fourthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (fourthree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][3]=2;
                        }
                        else{
                            checked[2][3] = 1;
                        }

                        cards_turned++;


                        if (positions[2][3] == 1) {
                            fourthree.setBackgroundResource(R.drawable.number_one);
                            fourthree.setTag("number_one");
                        } else if (positions[2][3] == 2) {
                            fourthree.setBackgroundResource(R.drawable.number_two);
                            fourthree.setTag("number_two");
                        } else if (positions[2][3] == 3) {
                            fourthree.setBackgroundResource(R.drawable.number_three);
                            fourthree.setTag("number_three");
                        } else if (positions[2][3] == 4) {
                            fourthree.setBackgroundResource(R.drawable.number_four);
                            fourthree.setTag("number_four");
                        } else if (positions[2][3] == 5) {
                            fourthree.setBackgroundResource(R.drawable.number_five);
                            fourthree.setTag("number_five");
                        } else if (positions[2][3] == 6) {
                            fourthree.setBackgroundResource(R.drawable.number_six);
                            fourthree.setTag("number_six");
                        }
                        else if (positions[2][3] == 7) {
                            fourthree.setBackgroundResource(R.drawable.number_seven);
                            fourthree.setTag("number_seven");
                        }
                        else if (positions[2][3] == 8) {
                            fourthree.setBackgroundResource(R.drawable.number_eight);
                            fourthree.setTag("number_eight");
                        }
                        else if (positions[2][3] == 9) {
                            fourthree.setBackgroundResource(R.drawable.number_nine);
                            fourthree.setTag("number_nine");
                        }
                    }
                }
                else{
                    fourthree.setBackgroundResource(R.drawable.face_down_card);
                    fourthree.setTag("face_down_card");


                    checked[2][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        fourfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }

                if (fourfour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][3]=2;
                        }
                        else{
                            checked[3][3] = 1;
                        }

                        cards_turned++;


                        if (positions[3][3] == 1) {
                            fourfour.setBackgroundResource(R.drawable.number_one);
                            fourfour.setTag("number_one");
                        } else if (positions[3][3] == 2) {
                            fourfour.setBackgroundResource(R.drawable.number_two);
                            fourfour.setTag("number_two");
                        } else if (positions[3][3] == 3) {
                            fourfour.setBackgroundResource(R.drawable.number_three);
                            fourfour.setTag("number_three");
                        } else if (positions[3][3] == 4) {
                            fourfour.setBackgroundResource(R.drawable.number_four);
                            fourfour.setTag("number_four");
                        } else if (positions[3][3] == 5) {
                            fourfour.setBackgroundResource(R.drawable.number_five);
                            fourfour.setTag("number_five");
                        } else if (positions[3][3] == 6) {
                            fourfour.setBackgroundResource(R.drawable.number_six);
                            fourfour.setTag("number_six");
                        }
                        else if (positions[3][3] == 7) {
                            fourfour.setBackgroundResource(R.drawable.number_seven);
                            fourfour.setTag("number_seven");
                        }
                        else if (positions[3][3] == 8) {
                            fourfour.setBackgroundResource(R.drawable.number_eight);
                            fourfour.setTag("number_eight");
                        }
                        else if (positions[3][3] == 9) {
                            fourfour.setBackgroundResource(R.drawable.number_nine);
                            fourfour.setTag("number_nine");
                        }
                    }

                }
                else{
                    fourfour.setBackgroundResource(R.drawable.face_down_card);
                    fourfour.setTag("face_down_card");


                    checked[3][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        fourfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (fourfive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][3]=2;
                        }
                        else{
                            checked[4][3] = 1;
                        }

                        cards_turned++;


                        if (positions[4][3] == 1) {
                            fourfive.setBackgroundResource(R.drawable.number_one);
                            fourfive.setTag("number_one");
                        } else if (positions[4][3] == 2) {
                            fourfive.setBackgroundResource(R.drawable.number_two);
                            fourfive.setTag("number_two");
                        } else if (positions[4][3] == 3) {
                            fourfive.setBackgroundResource(R.drawable.number_three);
                            fourfive.setTag("number_three");
                        } else if (positions[4][3] == 4) {
                            fourfive.setBackgroundResource(R.drawable.number_four);
                            fourfive.setTag("number_four");
                        } else if (positions[4][3] == 5) {
                            fourfive.setBackgroundResource(R.drawable.number_five);
                            fourfive.setTag("number_five");
                        } else if (positions[4][3] == 6) {
                            fourfive.setBackgroundResource(R.drawable.number_six);
                            fourfive.setTag("number_six");
                        }
                        else if (positions[4][3] == 7) {
                            fourfive.setBackgroundResource(R.drawable.number_seven);
                            fourfive.setTag("number_seven");
                        }
                        else if (positions[4][3] == 8) {
                            fourfive.setBackgroundResource(R.drawable.number_eight);
                            fourfive.setTag("number_eight");
                        }
                        else if (positions[4][3] == 9) {
                            fourfive.setBackgroundResource(R.drawable.number_nine);
                            fourfive.setTag("number_nine");
                        }
                    }

                }
                else{
                    fourfive.setBackgroundResource(R.drawable.face_down_card);
                    fourfive.setTag("face_down_card");


                    checked[4][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        foursix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (foursix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][3]=2;
                        }
                        else{
                            checked[5][3] = 1;
                        }

                        cards_turned++;


                        if (positions[5][3] == 1) {
                            foursix.setBackgroundResource(R.drawable.number_one);
                            foursix.setTag("number_one");
                        } else if (positions[5][3] == 2) {
                            foursix.setBackgroundResource(R.drawable.number_two);
                            foursix.setTag("number_two");
                        } else if (positions[5][3] == 3) {
                            foursix.setBackgroundResource(R.drawable.number_three);
                            foursix.setTag("number_three");
                        } else if (positions[5][3] == 4) {
                            foursix.setBackgroundResource(R.drawable.number_four);
                            foursix.setTag("number_four");
                        } else if (positions[5][3] == 5) {
                            foursix.setBackgroundResource(R.drawable.number_five);
                            foursix.setTag("number_five");
                        } else if (positions[5][3] == 6) {
                            foursix.setBackgroundResource(R.drawable.number_six);
                            foursix.setTag("number_six");
                        }
                        else if (positions[5][3] == 7) {
                            foursix.setBackgroundResource(R.drawable.number_seven);
                            foursix.setTag("number_seven");
                        }
                        else if (positions[5][3] == 8) {
                            foursix.setBackgroundResource(R.drawable.number_eight);
                            foursix.setTag("number_eight");
                        }
                        else if (positions[5][3] == 9) {
                            foursix.setBackgroundResource(R.drawable.number_nine);
                            foursix.setTag("number_nine");
                        }
                    }
                }
                else{
                    foursix.setBackgroundResource(R.drawable.face_down_card);
                    foursix.setTag("face_down_card");


                    checked[5][3]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });


        fiveone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (fiveone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][4]=2;
                        }
                        else{
                            checked[0][4] = 1;
                        }

                        cards_turned++;


                        if (positions[0][4] == 1) {
                            fiveone.setBackgroundResource(R.drawable.number_one);
                            fiveone.setTag("number_one");
                        } else if (positions[0][4] == 2) {
                            fiveone.setBackgroundResource(R.drawable.number_two);
                            fiveone.setTag("number_two");
                        } else if (positions[0][4] == 3) {
                            fiveone.setBackgroundResource(R.drawable.number_three);
                            fiveone.setTag("number_three");
                        } else if (positions[0][4] == 4) {
                            fiveone.setBackgroundResource(R.drawable.number_four);
                            fiveone.setTag("number_four");
                        } else if (positions[0][4] == 5) {
                            fiveone.setBackgroundResource(R.drawable.number_five);
                            fiveone.setTag("number_five");
                        } else if (positions[0][4] == 6) {
                            fiveone.setBackgroundResource(R.drawable.number_six);
                            fiveone.setTag("number_six");
                        }
                        else if (positions[0][4] == 7) {
                            fiveone.setBackgroundResource(R.drawable.number_seven);
                            fiveone.setTag("number_seven");
                        }
                        else if (positions[0][4] == 8) {
                            fiveone.setBackgroundResource(R.drawable.number_eight);
                            fiveone.setTag("number_eight");
                        }
                        else if (positions[0][4] == 9) {
                            fiveone.setBackgroundResource(R.drawable.number_nine);
                            fiveone.setTag("number_nine");
                        }

                    }
                }
                else{
                    fiveone.setBackgroundResource(R.drawable.face_down_card);
                    fiveone.setTag("face_down_card");

                    checked[0][4]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        fivetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (fivetwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][4]=2;
                        }
                        else{
                            checked[1][4] = 1;
                        }

                        cards_turned++;


                        if (positions[1][4] == 1) {
                            fivetwo.setBackgroundResource(R.drawable.number_one);
                            fivetwo.setTag("number_one");
                        } else if (positions[1][4] == 2) {
                            fivetwo.setBackgroundResource(R.drawable.number_two);
                            fivetwo.setTag("number_two");
                        } else if (positions[1][4] == 3) {
                            fivetwo.setBackgroundResource(R.drawable.number_three);
                            fivetwo.setTag("number_three");
                        } else if (positions[1][4] == 4) {
                            fivetwo.setBackgroundResource(R.drawable.number_four);
                            fivetwo.setTag("number_four");
                        } else if (positions[1][4] == 5) {
                            fivetwo.setBackgroundResource(R.drawable.number_five);
                            fivetwo.setTag("number_five");
                        } else if (positions[1][4] == 6) {
                            fivetwo.setBackgroundResource(R.drawable.number_six);
                            fivetwo.setTag("number_six");
                        }
                        else if (positions[1][4] == 7) {
                            fivetwo.setBackgroundResource(R.drawable.number_seven);
                            fivetwo.setTag("number_seven");
                        }
                        else if (positions[1][4] == 8) {
                            fivetwo.setBackgroundResource(R.drawable.number_eight);
                            fivetwo.setTag("number_eight");
                        }
                        else if (positions[1][4] == 9) {
                            fivetwo.setBackgroundResource(R.drawable.number_nine);
                            fivetwo.setTag("number_nine");
                        }
                    }

                }
                else{
                    fivetwo.setBackgroundResource(R.drawable.face_down_card);
                    fivetwo.setTag("face_down_card");

                    checked[1][4]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }
                checkMatches();


            }
        });
        fivethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (fivethree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][4]=2;
                        }
                        else{
                            checked[2][4] = 1;
                        }

                        cards_turned++;


                        if (positions[2][4] == 1) {
                            fivethree.setBackgroundResource(R.drawable.number_one);
                            fivethree.setTag("number_one");
                        } else if (positions[2][4] == 2) {
                            fivethree.setBackgroundResource(R.drawable.number_two);
                            fivethree.setTag("number_two");
                        } else if (positions[2][4] == 3) {
                            fivethree.setBackgroundResource(R.drawable.number_three);
                            fivethree.setTag("number_three");
                        } else if (positions[2][4] == 4) {
                            fivethree.setBackgroundResource(R.drawable.number_four);
                            fivethree.setTag("number_four");
                        } else if (positions[2][4] == 5) {
                            fivethree.setBackgroundResource(R.drawable.number_five);
                            fivethree.setTag("number_five");
                        } else if (positions[2][4] == 6) {
                            fivethree.setBackgroundResource(R.drawable.number_six);
                            fivethree.setTag("number_six");
                        }
                        else if (positions[2][4] == 7) {
                            fivethree.setBackgroundResource(R.drawable.number_seven);
                            fivethree.setTag("number_seven");
                        }
                        else if (positions[2][4] == 8) {
                            fivethree.setBackgroundResource(R.drawable.number_eight);
                            fivethree.setTag("number_eight");
                        }
                        else if (positions[2][4] == 9) {
                            fivethree.setBackgroundResource(R.drawable.number_nine);
                            fivethree.setTag("number_nine");
                        }
                    }
                }
                else{
                    fivethree.setBackgroundResource(R.drawable.face_down_card);
                    fivethree.setTag("face_down_card");

                    checked[2][4]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        fivefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (fivefour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][4]=2;
                        }
                        else{
                            checked[3][4] = 1;
                        }

                        cards_turned++;


                        if (positions[3][4] == 1) {
                            fivefour.setBackgroundResource(R.drawable.number_one);
                            fivefour.setTag("number_one");
                        } else if (positions[3][4] == 2) {
                            fivefour.setBackgroundResource(R.drawable.number_two);
                            fivefour.setTag("number_two");
                        } else if (positions[3][4] == 3) {
                            fivefour.setBackgroundResource(R.drawable.number_three);
                            fivefour.setTag("number_three");
                        } else if (positions[3][4] == 4) {
                            fivefour.setBackgroundResource(R.drawable.number_four);
                            fivefour.setTag("number_four");
                        } else if (positions[3][4] == 5) {
                            fivefour.setBackgroundResource(R.drawable.number_five);
                            fivefour.setTag("number_five");
                        } else if (positions[3][4] == 6) {
                            fivefour.setBackgroundResource(R.drawable.number_six);
                            fivefour.setTag("number_six");
                        }
                        else if (positions[3][4] == 7) {
                            fivefour.setBackgroundResource(R.drawable.number_seven);
                            fivefour.setTag("number_seven");
                        }
                        else if (positions[3][4] == 8) {
                            fivefour.setBackgroundResource(R.drawable.number_eight);
                            fivefour.setTag("number_eight");
                        }
                        else if (positions[3][4] == 9) {
                            fivefour.setBackgroundResource(R.drawable.number_nine);
                            fivefour.setTag("number_nine");
                        }
                    }

                }
                else{
                    fivefour.setBackgroundResource(R.drawable.face_down_card);
                    fivefour.setTag("face_down_card");

                    checked[3][4]=0;

                    cards_turned--;
                }
                checkMatches();


            }
        });
        fivefive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (fivefive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][4]=2;
                        }
                        else{
                            checked[4][4] = 1;
                        }

                        cards_turned++;


                        if (positions[4][4] == 1) {
                            fivefive.setBackgroundResource(R.drawable.number_one);
                            fivefive.setTag("number_one");
                        } else if (positions[4][4] == 2) {
                            fivefive.setBackgroundResource(R.drawable.number_two);
                            fivefive.setTag("number_two");
                        } else if (positions[4][4] == 3) {
                            fivefive.setBackgroundResource(R.drawable.number_three);
                            fivefive.setTag("number_three");
                        } else if (positions[4][4] == 4) {
                            fivefive.setBackgroundResource(R.drawable.number_four);
                            fivefive.setTag("number_four");
                        } else if (positions[4][4] == 5) {
                            fivefive.setBackgroundResource(R.drawable.number_five);
                            fivefive.setTag("number_five");
                        } else if (positions[4][4] == 6) {
                            fivefive.setBackgroundResource(R.drawable.number_six);
                            fivefive.setTag("number_six");
                        }
                        else if (positions[4][4] == 7) {
                            fivefive.setBackgroundResource(R.drawable.number_seven);
                            fivefive.setTag("number_seven");
                        }
                        else if (positions[4][4] == 8) {
                            fivefive.setBackgroundResource(R.drawable.number_eight);
                            fivefive.setTag("number_eight");
                        }
                        else if (positions[4][4] == 9) {
                            fivefive.setBackgroundResource(R.drawable.number_nine);
                            fivefive.setTag("number_nine");
                        }
                    }

                }
                else{
                    fivefive.setBackgroundResource(R.drawable.face_down_card);
                    fivefive.setTag("face_down_card");

                    checked[4][4]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();


            }
        });
        fivesix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (fivesix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][4]=2;
                        }
                        else{
                            checked[5][4] = 1;
                        }

                        cards_turned++;


                        if (positions[5][4] == 1) {
                            fivesix.setBackgroundResource(R.drawable.number_one);
                            fivesix.setTag("number_one");
                        } else if (positions[5][4] == 2) {
                            fivesix.setBackgroundResource(R.drawable.number_two);
                            fivesix.setTag("number_two");
                        } else if (positions[5][4] == 3) {
                            fivesix.setBackgroundResource(R.drawable.number_three);
                            fivesix.setTag("number_three");
                        } else if (positions[5][4] == 4) {
                            fivesix.setBackgroundResource(R.drawable.number_four);
                            fivesix.setTag("number_four");
                        } else if (positions[5][4] == 5) {
                            fivesix.setBackgroundResource(R.drawable.number_five);
                            fivesix.setTag("number_five");
                        } else if (positions[5][4] == 6) {
                            fivesix.setBackgroundResource(R.drawable.number_six);
                            fivesix.setTag("number_six");
                        }
                        else if (positions[5][4] == 7) {
                            fivesix.setBackgroundResource(R.drawable.number_seven);
                            fivesix.setTag("number_seven");
                        }
                        else if (positions[5][4] == 8) {
                            fivesix.setBackgroundResource(R.drawable.number_eight);
                            fivesix.setTag("number_eight");
                        }
                        else if (positions[5][4] == 9) {
                            fivesix.setBackgroundResource(R.drawable.number_nine);
                            fivesix.setTag("number_nine");
                        }
                    }

                }
                else{
                    fivesix.setBackgroundResource(R.drawable.face_down_card);
                    fivesix.setTag("face_down_card");

                    checked[5][4]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });


        sixone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (sixone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][5]=2;
                        }
                        else{
                            checked[0][5] = 1;
                        }

                        cards_turned++;


                        if (positions[0][5] == 1) {
                            sixone.setBackgroundResource(R.drawable.number_one);
                            sixone.setTag("number_one");
                        } else if (positions[0][5] == 2) {
                            sixone.setBackgroundResource(R.drawable.number_two);
                            sixone.setTag("number_two");
                        } else if (positions[0][5] == 3) {
                            sixone.setBackgroundResource(R.drawable.number_three);
                            sixone.setTag("number_three");
                        } else if (positions[0][5] == 4) {
                            sixone.setBackgroundResource(R.drawable.number_four);
                            sixone.setTag("number_four");
                        } else if (positions[0][5] == 5) {
                            sixone.setBackgroundResource(R.drawable.number_five);
                            sixone.setTag("number_five");
                        } else if (positions[0][5] == 6) {
                            sixone.setBackgroundResource(R.drawable.number_six);
                            sixone.setTag("number_six");
                        }
                        else if (positions[0][5] == 7) {
                            sixone.setBackgroundResource(R.drawable.number_seven);
                            sixone.setTag("number_seven");
                        }
                        else if (positions[0][5] == 8) {
                            sixone.setBackgroundResource(R.drawable.number_eight);
                            sixone.setTag("number_eight");
                        }
                        else if (positions[0][5] == 9) {
                            sixone.setBackgroundResource(R.drawable.number_nine);
                            sixone.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixone.setBackgroundResource(R.drawable.face_down_card);
                    sixone.setTag("face_down_card");


                    checked[0][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        sixtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sixtwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][5]=2;
                        }
                        else{
                            checked[1][5] = 1;
                        }

                        cards_turned++;


                        if (positions[1][5] == 1) {
                            sixtwo.setBackgroundResource(R.drawable.number_one);
                            sixtwo.setTag("number_one");
                        } else if (positions[1][5] == 2) {
                            sixtwo.setBackgroundResource(R.drawable.number_two);
                            sixtwo.setTag("number_two");
                        } else if (positions[1][5] == 3) {
                            sixtwo.setBackgroundResource(R.drawable.number_three);
                            sixtwo.setTag("number_three");
                        } else if (positions[1][5] == 4) {
                            sixtwo.setBackgroundResource(R.drawable.number_four);
                            sixtwo.setTag("number_four");
                        } else if (positions[1][5] == 5) {
                            sixtwo.setBackgroundResource(R.drawable.number_five);
                            sixtwo.setTag("number_five");
                        } else if (positions[1][5] == 6) {
                            sixtwo.setBackgroundResource(R.drawable.number_six);
                            sixtwo.setTag("number_six");
                        }
                        else if (positions[1][5] == 7) {
                            sixtwo.setBackgroundResource(R.drawable.number_seven);
                            sixtwo.setTag("number_seven");
                        }
                        else if (positions[1][5] == 8) {
                            sixtwo.setBackgroundResource(R.drawable.number_eight);
                            sixtwo.setTag("number_eight");
                        }
                        else if (positions[1][5] == 9) {
                            sixtwo.setBackgroundResource(R.drawable.number_nine);
                            sixtwo.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixtwo.setBackgroundResource(R.drawable.face_down_card);
                    sixtwo.setTag("face_down_card");

                    checked[1][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        sixthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sixthree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][5]=2;
                        }
                        else{
                            checked[2][5] = 1;
                        }

                        cards_turned++;


                        if (positions[2][5] == 1) {
                            sixthree.setBackgroundResource(R.drawable.number_one);
                            sixthree.setTag("number_one");
                        } else if (positions[2][5] == 2) {
                            sixthree.setBackgroundResource(R.drawable.number_two);
                            sixthree.setTag("number_two");
                        } else if (positions[2][5] == 3) {
                            sixthree.setBackgroundResource(R.drawable.number_three);
                            sixthree.setTag("number_three");
                        } else if (positions[2][5] == 4) {
                            sixthree.setBackgroundResource(R.drawable.number_four);
                            sixthree.setTag("number_four");
                        } else if (positions[2][5] == 5) {
                            sixthree.setBackgroundResource(R.drawable.number_five);
                            sixthree.setTag("number_five");
                        } else if (positions[2][5] == 6) {
                            sixthree.setBackgroundResource(R.drawable.number_six);
                            sixthree.setTag("number_six");
                        }
                        else if (positions[2][5] == 7) {
                            sixthree.setBackgroundResource(R.drawable.number_seven);
                            sixthree.setTag("number_seven");
                        }
                        else if (positions[2][5] == 8) {
                            sixthree.setBackgroundResource(R.drawable.number_eight);
                            sixthree.setTag("number_eight");
                        }
                        else if (positions[2][5] == 9) {
                            sixthree.setBackgroundResource(R.drawable.number_nine);
                            sixthree.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixthree.setBackgroundResource(R.drawable.face_down_card);
                    sixthree.setTag("face_down_card");

                    checked[2][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        sixfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sixfour.getTag().equals("face_down_card")) {
                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {


                        if(cards_turned==1){
                            checked[3][5]=2;
                        }
                        else{
                            checked[3][5] = 1;
                        }

                        cards_turned++;


                        if (positions[3][5] == 1) {
                            sixfour.setBackgroundResource(R.drawable.number_one);
                            sixfour.setTag("number_one");
                        } else if (positions[3][5] == 2) {
                            sixfour.setBackgroundResource(R.drawable.number_two);
                            sixfour.setTag("number_two");
                        } else if (positions[3][5] == 3) {
                            sixfour.setBackgroundResource(R.drawable.number_three);
                            sixfour.setTag("number_three");
                        } else if (positions[3][5] == 4) {
                            sixfour.setBackgroundResource(R.drawable.number_four);
                            sixfour.setTag("number_four");
                        } else if (positions[3][5] == 5) {
                            sixfour.setBackgroundResource(R.drawable.number_five);
                            sixfour.setTag("number_five");
                        } else if (positions[3][5] == 6) {
                            sixfour.setBackgroundResource(R.drawable.number_six);
                            sixfour.setTag("number_six");
                        }
                        else if (positions[3][5] == 7) {
                            sixfour.setBackgroundResource(R.drawable.number_seven);
                            sixfour.setTag("number_seven");
                        }
                        else if (positions[3][5] == 8) {
                            sixfour.setBackgroundResource(R.drawable.number_eight);
                            sixfour.setTag("number_eight");
                        }
                        else if (positions[3][5] == 9) {
                            sixfour.setBackgroundResource(R.drawable.number_nine);
                            sixfour.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixfour.setBackgroundResource(R.drawable.face_down_card);
                    sixfour.setTag("face_down_card");

                    checked[3][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        sixfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sixfive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][5]=2;
                        }
                        else{
                            checked[4][5] = 1;
                        }

                        cards_turned++;


                        if (positions[4][5] == 1) {
                            sixfive.setBackgroundResource(R.drawable.number_one);
                            sixfive.setTag("number_one");
                        } else if (positions[4][5] == 2) {
                            sixfive.setBackgroundResource(R.drawable.number_two);
                            sixfive.setTag("number_two");
                        } else if (positions[4][5] == 3) {
                            sixfive.setBackgroundResource(R.drawable.number_three);
                            sixfive.setTag("number_three");
                        } else if (positions[4][5] == 4) {
                            sixfive.setBackgroundResource(R.drawable.number_four);
                            sixfive.setTag("number_four");
                        } else if (positions[4][5] == 5) {
                            sixfive.setBackgroundResource(R.drawable.number_five);
                            sixfive.setTag("number_five");
                        } else if (positions[4][5] == 6) {
                            sixfive.setBackgroundResource(R.drawable.number_six);
                            sixfive.setTag("number_six");
                        }
                        else if (positions[4][5] == 7) {
                            sixfive.setBackgroundResource(R.drawable.number_seven);
                            sixfive.setTag("number_seven");
                        }
                        else if (positions[4][5] == 8) {
                            sixfive.setBackgroundResource(R.drawable.number_eight);
                            sixfive.setTag("number_eight");
                        }
                        else if (positions[4][5] == 9) {
                            sixfive.setBackgroundResource(R.drawable.number_nine);
                            sixfive.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixfive.setBackgroundResource(R.drawable.face_down_card);
                    sixfive.setTag("face_down_card");

                    checked[4][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });
        sixsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sixsix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][5]=2;
                        }
                        else{
                            checked[5][5] = 1;
                        }

                        cards_turned++;


                        if (positions[5][5] == 1) {
                            sixsix.setBackgroundResource(R.drawable.number_one);
                            sixsix.setTag("number_one");
                        } else if (positions[5][5] == 2) {
                            sixsix.setBackgroundResource(R.drawable.number_two);
                            sixsix.setTag("number_two");
                        } else if (positions[5][5] == 3) {
                            sixsix.setBackgroundResource(R.drawable.number_three);
                            sixsix.setTag("number_three");
                        } else if (positions[5][5] == 4) {
                            sixsix.setBackgroundResource(R.drawable.number_four);
                            sixsix.setTag("number_four");
                        } else if (positions[5][5] == 5) {
                            sixsix.setBackgroundResource(R.drawable.number_five);
                            sixsix.setTag("number_five");
                        } else if (positions[5][5] == 6) {
                            sixsix.setBackgroundResource(R.drawable.number_six);
                            sixsix.setTag("number_six");
                        }
                        else if (positions[5][5] == 7) {
                            sixsix.setBackgroundResource(R.drawable.number_seven);
                            sixsix.setTag("number_seven");
                        }
                        else if (positions[5][5] == 8) {
                            sixsix.setBackgroundResource(R.drawable.number_eight);
                            sixsix.setTag("number_eight");
                        }
                        else if (positions[5][5] == 9) {
                            sixsix.setBackgroundResource(R.drawable.number_nine);
                            sixsix.setTag("number_nine");
                        }
                    }
                }
                else{
                    sixsix.setBackgroundResource(R.drawable.face_down_card);
                    sixsix.setTag("face_down_card");

                    checked[5][5]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();


            }
        });



        sevenone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sevenone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][6]=2;
                        }
                        else{
                            checked[0][6] = 1;
                        }

                        cards_turned++;


                        if (positions[0][6] == 1) {
                            sevenone.setBackgroundResource(R.drawable.number_one);
                            sevenone.setTag("number_one");
                        } else if (positions[0][6] == 2) {
                            sevenone.setBackgroundResource(R.drawable.number_two);
                            sevenone.setTag("number_two");
                        } else if (positions[0][6] == 3) {
                            sevenone.setBackgroundResource(R.drawable.number_three);
                            sevenone.setTag("number_three");
                        } else if (positions[0][6] == 4) {
                            sevenone.setBackgroundResource(R.drawable.number_four);
                            sevenone.setTag("number_four");
                        } else if (positions[0][6] == 5) {
                            sevenone.setBackgroundResource(R.drawable.number_five);
                            sevenone.setTag("number_five");
                        } else if (positions[0][6] == 6) {
                            sevenone.setBackgroundResource(R.drawable.number_six);
                            sevenone.setTag("number_six");
                        }
                        else if (positions[0][6] == 7) {
                            sevenone.setBackgroundResource(R.drawable.number_seven);
                            sevenone.setTag("number_seven");
                        }
                        else if (positions[0][6] == 8) {
                            sevenone.setBackgroundResource(R.drawable.number_eight);
                            sevenone.setTag("number_eight");
                        }
                        else if (positions[0][6] == 9) {
                            sevenone.setBackgroundResource(R.drawable.number_nine);
                            sevenone.setTag("number_nine");
                        }
                    }
                }
                else{
                    sevenone.setBackgroundResource(R.drawable.face_down_card);
                    sevenone.setTag("face_down_card");

                    checked[0][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });
        seventwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (seventwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][6]=2;
                        }
                        else{
                            checked[1][6] = 1;
                        }

                        cards_turned++;


                        if (positions[1][6] == 1) {
                            seventwo.setBackgroundResource(R.drawable.number_one);
                            seventwo.setTag("number_one");
                        } else if (positions[1][6] == 2) {
                            seventwo.setBackgroundResource(R.drawable.number_two);
                            seventwo.setTag("number_two");
                        } else if (positions[1][6] == 3) {
                            seventwo.setBackgroundResource(R.drawable.number_three);
                            seventwo.setTag("number_three");
                        } else if (positions[1][6] == 4) {
                            seventwo.setBackgroundResource(R.drawable.number_four);
                            seventwo.setTag("number_four");
                        } else if (positions[1][6] == 5) {
                            seventwo.setBackgroundResource(R.drawable.number_five);
                            seventwo.setTag("number_five");
                        } else if (positions[1][6] == 6) {
                            seventwo.setBackgroundResource(R.drawable.number_six);
                            seventwo.setTag("number_six");
                        }
                        else if (positions[1][6] == 7) {
                            seventwo.setBackgroundResource(R.drawable.number_seven);
                            seventwo.setTag("number_seven");
                        }
                        else if (positions[1][6] == 8) {
                            seventwo.setBackgroundResource(R.drawable.number_eight);
                            seventwo.setTag("number_eight");
                        }
                        else if (positions[1][6] == 9) {
                            seventwo.setBackgroundResource(R.drawable.number_nine);
                            seventwo.setTag("number_nine");
                        }
                    }
                }
                else{
                    seventwo.setBackgroundResource(R.drawable.face_down_card);
                    seventwo.setTag("face_down_card");


                    checked[1][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });
        seventhree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (seventhree.getTag().equals("face_down_card")) {
                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {


                        if(cards_turned==1){
                            checked[2][6]=2;
                        }
                        else{
                            checked[2][6] = 1;
                        }

                        cards_turned++;


                        if (positions[2][6] == 1) {
                            seventhree.setBackgroundResource(R.drawable.number_one);
                            seventhree.setTag("number_one");
                        } else if (positions[2][6] == 2) {
                            seventhree.setBackgroundResource(R.drawable.number_two);
                            seventhree.setTag("number_two");
                        } else if (positions[2][6] == 3) {
                            seventhree.setBackgroundResource(R.drawable.number_three);
                            seventhree.setTag("number_three");
                        } else if (positions[2][6] == 4) {
                            seventhree.setBackgroundResource(R.drawable.number_four);
                            seventhree.setTag("number_four");
                        } else if (positions[2][6] == 5) {
                            seventhree.setBackgroundResource(R.drawable.number_five);
                            seventhree.setTag("number_five");
                        } else if (positions[2][6] == 6) {
                            seventhree.setBackgroundResource(R.drawable.number_six);
                            seventhree.setTag("number_six");
                        }
                        else if (positions[2][6] == 7) {
                            seventhree.setBackgroundResource(R.drawable.number_seven);
                            seventhree.setTag("number_seven");
                        }
                        else if (positions[2][6] == 8) {
                            seventhree.setBackgroundResource(R.drawable.number_eight);
                            seventhree.setTag("number_eight");
                        }
                        else if (positions[2][6] == 9) {
                            seventhree.setBackgroundResource(R.drawable.number_nine);
                            seventhree.setTag("number_nine");
                        }
                    }
                }
                else{
                    seventhree.setBackgroundResource(R.drawable.face_down_card);
                    seventhree.setTag("face_down_card");

                    checked[2][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });
        sevenfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sevenfour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][6]=2;
                        }
                        else{
                            checked[3][6] = 1;
                        }

                        cards_turned++;


                        if (positions[3][6] == 1) {
                            sevenfour.setBackgroundResource(R.drawable.number_one);
                            sevenfour.setTag("number_one");
                        } else if (positions[3][6] == 2) {
                            sevenfour.setBackgroundResource(R.drawable.number_two);
                            sevenfour.setTag("number_two");
                        } else if (positions[3][6] == 3) {
                            sevenfour.setBackgroundResource(R.drawable.number_three);
                            sevenfour.setTag("number_three");
                        } else if (positions[3][6] == 4) {
                            sevenfour.setBackgroundResource(R.drawable.number_four);
                            sevenfour.setTag("number_four");
                        } else if (positions[3][6] == 5) {
                            sevenfour.setBackgroundResource(R.drawable.number_five);
                            sevenfour.setTag("number_five");
                        } else if (positions[3][6] == 6) {
                            sevenfour.setBackgroundResource(R.drawable.number_six);
                            sevenfour.setTag("number_six");
                        }
                        else if (positions[3][6] == 7) {
                            sevenfour.setBackgroundResource(R.drawable.number_seven);
                            sevenfour.setTag("number_seven");
                        }
                        else if (positions[3][6] == 8) {
                            sevenfour.setBackgroundResource(R.drawable.number_eight);
                            sevenfour.setTag("number_eight");
                        }
                        else if (positions[3][6] == 9) {
                            sevenfour.setBackgroundResource(R.drawable.number_nine);
                            sevenfour.setTag("number_nine");
                        }

                    }

                }
                else{
                    sevenfour.setBackgroundResource(R.drawable.face_down_card);
                    sevenfour.setTag("face_down_card");

                    checked[3][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }
                checkMatches();



            }
        });
        sevenfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sevenfive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][6]=2;
                        }
                        else{
                            checked[4][6] = 1;
                        }

                        cards_turned++;


                        if (positions[4][6] == 1) {
                            sevenfive.setBackgroundResource(R.drawable.number_one);
                            sevenfive.setTag("number_one");
                        } else if (positions[4][6] == 2) {
                            sevenfive.setBackgroundResource(R.drawable.number_two);
                            sevenfive.setTag("number_two");
                        } else if (positions[4][6] == 3) {
                            sevenfive.setBackgroundResource(R.drawable.number_three);
                            sevenfive.setTag("number_three");
                        } else if (positions[4][6] == 4) {
                            sevenfive.setBackgroundResource(R.drawable.number_four);
                            sevenfive.setTag("number_four");
                        } else if (positions[4][6] == 5) {
                            sevenfive.setBackgroundResource(R.drawable.number_five);
                            sevenfive.setTag("number_five");
                        } else if (positions[4][6] == 6) {
                            sevenfive.setBackgroundResource(R.drawable.number_six);
                            sevenfive.setTag("number_six");
                        }
                        else if (positions[4][6] == 7) {
                            sevenfive.setBackgroundResource(R.drawable.number_seven);
                            sevenfive.setTag("number_seven");
                        }
                        else if (positions[4][6] == 8) {
                            sevenfive.setBackgroundResource(R.drawable.number_eight);
                            sevenfive.setTag("number_eight");
                        }
                        else if (positions[4][6] == 9) {
                            sevenfive.setBackgroundResource(R.drawable.number_nine);
                            sevenfive.setTag("number_nine");
                        }
                    }
                }
                else{
                    sevenfive.setBackgroundResource(R.drawable.face_down_card);
                    sevenfive.setTag("face_down_card");

                    checked[4][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();


            }
        });
        sevensix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (sevensix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][6]=2;
                        }
                        else{
                            checked[5][6] = 1;
                        }

                        cards_turned++;


                        if (positions[5][6] == 1) {
                            sevensix.setBackgroundResource(R.drawable.number_one);
                            sevensix.setTag("number_one");
                        } else if (positions[5][6] == 2) {
                            sevensix.setBackgroundResource(R.drawable.number_two);
                            sevensix.setTag("number_two");
                        } else if (positions[5][6] == 3) {
                            sevensix.setBackgroundResource(R.drawable.number_three);
                            sevensix.setTag("number_three");
                        } else if (positions[5][6] == 4) {
                            sevensix.setBackgroundResource(R.drawable.number_four);
                            sevensix.setTag("number_four");
                        } else if (positions[5][6] == 5) {
                            sevensix.setBackgroundResource(R.drawable.number_five);
                            sevensix.setTag("number_five");
                        } else if (positions[5][6] == 6) {
                            sevensix.setBackgroundResource(R.drawable.number_six);
                            sevensix.setTag("number_six");
                        }
                        else if (positions[5][6] == 7) {
                            sevensix.setBackgroundResource(R.drawable.number_seven);
                            sevensix.setTag("number_seven");
                        }
                        else if (positions[5][6] == 8) {
                            sevensix.setBackgroundResource(R.drawable.number_eight);
                            sevensix.setTag("number_eight");
                        }
                        else if (positions[5][6] == 9) {
                            sevensix.setBackgroundResource(R.drawable.number_nine);
                            sevensix.setTag("number_nine");
                        }
                    }
                }
                else{
                    sevensix.setBackgroundResource(R.drawable.face_down_card);
                    sevensix.setTag("face_down_card");

                    checked[5][6]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });


        eightone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }


                if (eightone.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[0][7]=2;
                        }
                        else{
                            checked[0][7] = 1;
                        }

                        cards_turned++;


                        if (positions[0][7] == 1) {
                            eightone.setBackgroundResource(R.drawable.number_one);
                            eightone.setTag("number_one");
                        } else if (positions[0][7] == 2) {
                            eightone.setBackgroundResource(R.drawable.number_two);
                            eightone.setTag("number_two");
                        } else if (positions[0][7] == 3) {
                            eightone.setBackgroundResource(R.drawable.number_three);
                            eightone.setTag("number_three");
                        } else if (positions[0][7] == 4) {
                            eightone.setBackgroundResource(R.drawable.number_four);
                            eightone.setTag("number_four");
                        } else if (positions[0][7] == 5) {
                            eightone.setBackgroundResource(R.drawable.number_five);
                            eightone.setTag("number_five");
                        } else if (positions[0][7] == 6) {
                            eightone.setBackgroundResource(R.drawable.number_six);
                            eightone.setTag("number_six");
                        }
                        else if (positions[0][7] == 7) {
                            eightone.setBackgroundResource(R.drawable.number_seven);
                            eightone.setTag("number_seven");
                        }
                        else if (positions[0][7] == 8) {
                            eightone.setBackgroundResource(R.drawable.number_eight);
                            eightone.setTag("number_eight");
                        }
                        else if (positions[0][7] == 9) {
                            eightone.setBackgroundResource(R.drawable.number_nine);
                            eightone.setTag("number_nine");
                        }
                    }
                }
                else{
                    eightone.setBackgroundResource(R.drawable.face_down_card);
                    eightone.setTag("face_down_card");

                    checked[0][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });
        eighttwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (eighttwo.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[1][7]=2;
                        }
                        else{
                            checked[1][7] = 1;
                        }

                        cards_turned++;


                        if (positions[1][7] == 1) {
                            eighttwo.setBackgroundResource(R.drawable.number_one);
                            eighttwo.setTag("number_one");
                        } else if (positions[1][7] == 2) {
                            eighttwo.setBackgroundResource(R.drawable.number_two);
                            eighttwo.setTag("number_two");
                        } else if (positions[1][7] == 3) {
                            eighttwo.setBackgroundResource(R.drawable.number_three);
                            eighttwo.setTag("number_three");
                        } else if (positions[1][7] == 4) {
                            eighttwo.setBackgroundResource(R.drawable.number_four);
                            eighttwo.setTag("number_four");
                        } else if (positions[1][7] == 5) {
                            eighttwo.setBackgroundResource(R.drawable.number_five);
                            eighttwo.setTag("number_five");
                        } else if (positions[1][7] == 6) {
                            eighttwo.setBackgroundResource(R.drawable.number_six);
                            eighttwo.setTag("number_six");
                        }
                        else if (positions[1][7] == 7) {
                            eighttwo.setBackgroundResource(R.drawable.number_seven);
                            eighttwo.setTag("number_seven");
                        }
                        else if (positions[1][7] == 8) {
                            eighttwo.setBackgroundResource(R.drawable.number_eight);
                            eighttwo.setTag("number_eight");
                        }
                        else if (positions[1][7] == 9) {
                            eighttwo.setBackgroundResource(R.drawable.number_nine);
                            eighttwo.setTag("number_nine");
                        }
                    }
                }
                else{
                    eighttwo.setBackgroundResource(R.drawable.face_down_card);
                    eighttwo.setTag("face_down_card");

                    checked[1][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();


            }
        });
        eightthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (eightthree.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[2][7]=2;
                        }
                        else{
                            checked[2][7] = 1;
                        }

                        cards_turned++;


                        if (positions[2][7] == 1) {
                            eightthree.setBackgroundResource(R.drawable.number_one);
                            eightthree.setTag("number_one");
                        } else if (positions[2][7] == 2) {
                            eightthree.setBackgroundResource(R.drawable.number_two);
                            eightthree.setTag("number_two");
                        } else if (positions[2][7] == 3) {
                            eightthree.setBackgroundResource(R.drawable.number_three);
                            eightthree.setTag("number_three");
                        } else if (positions[2][7] == 4) {
                            eightthree.setBackgroundResource(R.drawable.number_four);
                            eightthree.setTag("number_four");
                        } else if (positions[2][7] == 5) {
                            eightthree.setBackgroundResource(R.drawable.number_five);
                            eightthree.setTag("number_five");
                        } else if (positions[2][7] == 6) {
                            eightthree.setBackgroundResource(R.drawable.number_six);
                            eightthree.setTag("number_six");
                        }
                        else if (positions[2][7] == 7) {
                            eightthree.setBackgroundResource(R.drawable.number_seven);
                            eightthree.setTag("number_seven");
                        }
                        else if (positions[2][7] == 8) {
                            eightthree.setBackgroundResource(R.drawable.number_eight);
                            eightthree.setTag("number_eight");
                        }
                        else if (positions[2][7] == 9) {
                            eightthree.setBackgroundResource(R.drawable.number_nine);
                            eightthree.setTag("number_nine");
                        }
                    }
                }
                else{
                    eightthree.setBackgroundResource(R.drawable.face_down_card);
                    eightthree.setTag("face_down_card");

                    checked[2][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();

            }
        });
        eightfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (eightfour.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[3][7]=2;
                        }
                        else{
                            checked[3][7] = 1;
                        }

                        cards_turned++;


                        if (positions[3][7] == 1) {
                            eightfour.setBackgroundResource(R.drawable.number_one);
                            eightfour.setTag("number_one");
                        } else if (positions[3][7] == 2) {
                            eightfour.setBackgroundResource(R.drawable.number_two);
                            eightfour.setTag("number_two");
                        } else if (positions[3][7] == 3) {
                            eightfour.setBackgroundResource(R.drawable.number_three);
                            eightfour.setTag("number_three");
                        } else if (positions[3][7] == 4) {
                            eightfour.setBackgroundResource(R.drawable.number_four);
                            eightfour.setTag("number_four");
                        } else if (positions[3][7] == 5) {
                            eightfour.setBackgroundResource(R.drawable.number_five);
                            eightfour.setTag("number_five");
                        } else if (positions[3][7] == 6) {
                            eightfour.setBackgroundResource(R.drawable.number_six);
                            eightfour.setTag("number_six");
                        }
                        else if (positions[3][7] == 7) {
                            eightfour.setBackgroundResource(R.drawable.number_seven);
                            eightfour.setTag("number_seven");
                        }
                        else if (positions[3][7] == 8) {
                            eightfour.setBackgroundResource(R.drawable.number_eight);
                            eightfour.setTag("number_eight");
                        }
                        else if (positions[3][7] == 9) {
                            eightfour.setBackgroundResource(R.drawable.number_nine);
                            eightfour.setTag("number_nine");
                        }
                    }
                }
                else{
                    eightfour.setBackgroundResource(R.drawable.face_down_card);
                    eightfour.setTag("face_down_card");

                    checked[3][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();

            }
        });
        eightfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (eightfive.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[4][7]=2;
                        }
                        else{
                            checked[4][7] = 1;
                        }

                        cards_turned++;


                        if (positions[4][7] == 1) {
                            eightfive.setBackgroundResource(R.drawable.number_one);
                            eightfive.setTag("number_one");
                        } else if (positions[4][7] == 2) {
                            eightfive.setBackgroundResource(R.drawable.number_two);
                            eightfive.setTag("number_two");
                        } else if (positions[4][7] == 3) {
                            eightfive.setBackgroundResource(R.drawable.number_three);
                            eightfive.setTag("number_three");
                        } else if (positions[4][7] == 4) {
                            eightfive.setBackgroundResource(R.drawable.number_four);
                            eightfive.setTag("number_four");
                        } else if (positions[4][7] == 5) {
                            eightfive.setBackgroundResource(R.drawable.number_five);
                            eightfive.setTag("number_five");
                        } else if (positions[4][7] == 6) {
                            eightfive.setBackgroundResource(R.drawable.number_six);
                            eightfive.setTag("number_six");
                        }
                        else if (positions[4][7] == 7) {
                            eightfive.setBackgroundResource(R.drawable.number_seven);
                            eightfive.setTag("number_seven");
                        }
                        else if (positions[4][7] == 8) {
                            eightfive.setBackgroundResource(R.drawable.number_eight);
                            eightfive.setTag("number_eight");
                        }
                        else if (positions[4][7] == 9) {
                            eightfive.setBackgroundResource(R.drawable.number_nine);
                            eightfive.setTag("number_nine");
                        }
                    }
                }
                else{
                    eightfive.setBackgroundResource(R.drawable.face_down_card);
                    eightfive.setTag("face_down_card");

                    checked[4][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;
                }

                checkMatches();

            }
        });
        eightsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cards_turned<0){
                    cards_turned=0;
                }



                if (eightsix.getTag().equals("face_down_card")) {

                    //Only if the cards turned is less than two then you can turn one over
                    if(cards_turned<2) {

                        if(cards_turned==1){
                            checked[5][7]=2;
                        }
                        else{
                            checked[5][7] = 1;
                        }

                        cards_turned++;


                        if (positions[5][7] == 1) {
                            eightsix.setBackgroundResource(R.drawable.number_one);
                            eightsix.setTag("number_one");
                        } else if (positions[5][7] == 2) {
                            eightsix.setBackgroundResource(R.drawable.number_two);
                            eightsix.setTag("number_two");
                        } else if (positions[5][7] == 3) {
                            eightsix.setBackgroundResource(R.drawable.number_three);
                            eightsix.setTag("number_three");
                        } else if (positions[5][7] == 4) {
                            eightsix.setBackgroundResource(R.drawable.number_four);
                            eightsix.setTag("number_four");
                        } else if (positions[5][7] == 5) {
                            eightsix.setBackgroundResource(R.drawable.number_five);
                            eightsix.setTag("number_five");
                        } else if (positions[5][7] == 6) {
                            eightsix.setBackgroundResource(R.drawable.number_six);
                            eightsix.setTag("number_six");
                        }
                        else if (positions[5][7] == 7) {
                            eightsix.setBackgroundResource(R.drawable.number_seven);
                            eightsix.setTag("number_seven");
                        }
                        else if (positions[5][7] == 8) {
                            eightsix.setBackgroundResource(R.drawable.number_eight);
                            eightsix.setTag("number_eight");
                        }
                        else if (positions[5][7] == 9) {
                            eightsix.setBackgroundResource(R.drawable.number_nine);
                            eightsix.setTag("number_nine");
                        }
                    }
                }
                else{
                    eightsix.setBackgroundResource(R.drawable.face_down_card);
                    eightsix.setTag("face_down_card");

                    checked[5][7]=0;

                    //Set the second card turned to be first
                    for(int i=0; i<=5; i++){
                        for(int j=0; j<=7; j++){
                            if(checked[i][j]==2){
                                checked[i][j]=1;
                            }
                        }
                    }

                    cards_turned--;

                }

                checkMatches();

            }
        });



    }

    //A function to check if cards turned for player are a match
    public void checkMatches(){

        boolean found=false;
        boolean notifications_off = false;

        Cursor notifications_data = mDatabaseHelper.getData();

        while(notifications_data.moveToNext()){
            //If the notifications are turned on
            if (notifications_data.getInt(1)==10){
                notifications_off=true;
            }
        }

        //Check that the two cards turned over are a match to the target
        if(cards_turned==2){

            card1=0;
            card2=0;

            //Loop through the 2d array and get the checked values
            for(int i=0; i<=5; i++){
                for(int j=0; j<=7; j++){
                    if (checked[i][j]==2){
                        card2=positions[i][j];
                    }
                    else if(checked[i][j]==1){
                        card1=positions[i][j];
                    }
                }
            }

            if(level.equals("1")){
                result = card1 + card2;
            }
            else if(level.equals("2")){
                result = card1 - card2;
            }
            else if(level.equals("3")){
                result = card1 * card2;
            }
            else if(level.equals("4")){
                result = card1 - card2;
            }
            else if(level.equals("5")){
                result = card1 + card2;
            }
            else if (level.equals("6")){
                result = card1 + card2;
            }
            else if (level.equals("7")){
                result = card1 + card2;
            }
            else if (level.equals("8")){

                result=-1;

                //Have to check they are both odd
                if(card1%2!=0 && card2%2!=0){
                    result = card1 - card2;
                }
            }
            else if (level.equals("9")){

                result=-1;

                //Have to check they are both even
                if(card1%2==0 && card2%2==0){
                    result = card1 + card2;
                }

            }

            if(target==result) {

                questions_correct++;

                //Set the number of questions here
                if(questions_correct==number_questions){

                    //If it is the first time the user has completed the activity
                    //Then display congratulations notifications

                    if(level.equals("1")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==1){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(1);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 1 complete")
                                        .setContentText("Congratulations on completing the first level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());
                            }
                        }
                    }
                    else if (level.equals("2")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==2){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(2);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 2 complete")
                                        .setContentText("Congratulations on completing the second level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }

                    }
                    else if (level.equals("3")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==3){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(3);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 3 complete")
                                        .setContentText("Congratulations on completing the third level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    else if (level.equals("4")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==4){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(4);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 4 complete")
                                        .setContentText("Congratulations on completing the fourth level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    else if (level.equals("5")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==5){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(5);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 5 complete")
                                        .setContentText("Congratulations on completing the fifth level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    else if (level.equals("6")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==6){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(6);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 6 complete")
                                        .setContentText("Congratulations on completing the sixth level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    else if (level.equals("7")){

                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==7){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(7);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 7 complete")
                                        .setContentText("Congratulations on completing the seventh level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }

                    }

                    else if(level.equals("8")){
                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==8){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(8);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 8 complete")
                                        .setContentText("Congratulations on completing the eighth level.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    else if (level.equals("9")){
                        //Check if the level has already been completed
                        Cursor data = mDatabaseHelper.getData();

                        while(data.moveToNext()){
                            if(data.getInt(1)==9){
                                found=true;
                            }
                        }

                        if(found==false) {
                            mDatabaseHelper.addData(9);

                            if(notifications_off==false) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                PendingIntent pendingIntent = PendingIntent.getActivity(GameActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                                NotificationCompat.Builder notification_builder;
                                NotificationManager notification_manager = (NotificationManager) this
                                        .getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    String chanel_id = "3000";
                                    CharSequence name = "Channel Name";
                                    String description = "Chanel Description";
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
                                    mChannel.setDescription(description);
                                    mChannel.enableLights(true);
                                    mChannel.setLightColor(Color.BLUE);
                                    notification_manager.createNotificationChannel(mChannel);
                                    notification_builder = new NotificationCompat.Builder(this, chanel_id);
                                } else {
                                    notification_builder = new NotificationCompat.Builder(this);
                                }
                                notification_builder.setSmallIcon(R.drawable.ic_baseline_check_circle_24)
                                        .setContentTitle("Level 9 complete")
                                        .setContentText("Congratulations on completing the ninth level - you have completed the game.")
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent);
                                notification_manager.notify(0, notification_builder.build());

                            }
                        }
                    }

                    final Handler handler = new Handler();
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    Intent myIntent = new Intent(GameActivity.this, LevelActivity.class);
                                    startActivity(myIntent);
                                }

                            });


                        }
                    };
                    timer.schedule(task, 2250);

                    Toast toast = Toast.makeText(getApplicationContext(), "Level " + level + " complete", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 200);
                    view = toast.getView();
                    view.setBackgroundResource(R.drawable.radius);
                    view.setPadding(200, 100, 200, 100);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#000000"));
                    text.setTextSize(24);
                    text.setGravity(Gravity.CENTER);
                    toast.show();

                }
                else{
                    //Display a success message for the user
                    Toast toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 200);
                    view = toast.getView();
                    view.setBackgroundResource(R.drawable.radius);
                    view.setPadding(200, 100, 200, 100);
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.parseColor("#000000"));
                    text.setTextSize(24);
                    text.setGravity(Gravity.CENTER);
                    toast.show();

                    resetValues();
                }

                oneone.setEnabled(false);
                onetwo.setEnabled(false);
                onethree.setEnabled(false);
                onefour.setEnabled(false);
                onefive.setEnabled(false);
                onesix.setEnabled(false);

                twoone.setEnabled(false);
                twotwo.setEnabled(false);
                twothree.setEnabled(false);
                twofour.setEnabled(false);
                twofive.setEnabled(false);
                twosix.setEnabled(false);

                threeone.setEnabled(false);
                threetwo.setEnabled(false);
                threethree.setEnabled(false);
                threefour.setEnabled(false);
                threefive.setEnabled(false);
                threesix.setEnabled(false);

                fourone.setEnabled(false);
                fourtwo.setEnabled(false);
                fourthree.setEnabled(false);
                fourfour.setEnabled(false);
                fourfive.setEnabled(false);
                foursix.setEnabled(false);

                fiveone.setEnabled(false);
                fivetwo.setEnabled(false);
                fivethree.setEnabled(false);
                fivefour.setEnabled(false);
                fivefive.setEnabled(false);
                fivesix.setEnabled(false);

                sixone.setEnabled(false);
                sixtwo.setEnabled(false);
                sixthree.setEnabled(false);
                sixfour.setEnabled(false);
                sixfive.setEnabled(false);
                sixsix.setEnabled(false);

                sevenone.setEnabled(false);
                seventwo.setEnabled(false);
                seventhree.setEnabled(false);
                sevenfour.setEnabled(false);
                sevenfive.setEnabled(false);
                sevensix.setEnabled(false);

                eightone.setEnabled(false);
                eighttwo.setEnabled(false);
                eightthree.setEnabled(false);
                eightfour.setEnabled(false);
                eightfive.setEnabled(false);
                eightsix.setEnabled(false);

            }

        }


    }

    public void resetValues(){

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        //Reset the array values for positions and checked
                        for(int i=0; i<=5; i++){
                            for(int j=0; j<=7; j++){
                                randomSymbol = (int) (Math.random()*9)+1;
                                positions[i][j] = randomSymbol;
                                checked[i][j]=0;
                            }
                        }

                        //Turn the cards back over

                        oneone.setBackgroundResource(R.drawable.face_down_card);
                        oneone.setTag("face_down_card");
                        onetwo.setBackgroundResource(R.drawable.face_down_card);
                        onetwo.setTag("face_down_card");
                        onethree.setBackgroundResource(R.drawable.face_down_card);
                        onethree.setTag("face_down_card");
                        onefour.setBackgroundResource(R.drawable.face_down_card);
                        onefour.setTag("face_down_card");
                        onefive.setBackgroundResource(R.drawable.face_down_card);
                        onefive.setTag("face_down_card");
                        onesix.setBackgroundResource(R.drawable.face_down_card);
                        onesix.setTag("face_down_card");

                        twoone.setBackgroundResource(R.drawable.face_down_card);
                        twoone.setTag("face_down_card");
                        twotwo.setBackgroundResource(R.drawable.face_down_card);
                        twotwo.setTag("face_down_card");
                        twothree.setBackgroundResource(R.drawable.face_down_card);
                        twothree.setTag("face_down_card");
                        twofour.setBackgroundResource(R.drawable.face_down_card);
                        twofour.setTag("face_down_card");
                        twofive.setBackgroundResource(R.drawable.face_down_card);
                        twofive.setTag("face_down_card");
                        twosix.setBackgroundResource(R.drawable.face_down_card);
                        twosix.setTag("face_down_card");

                        threeone.setBackgroundResource(R.drawable.face_down_card);
                        threeone.setTag("face_down_card");
                        threetwo.setBackgroundResource(R.drawable.face_down_card);
                        threetwo.setTag("face_down_card");
                        threethree.setBackgroundResource(R.drawable.face_down_card);
                        threethree.setTag("face_down_card");
                        threefour.setBackgroundResource(R.drawable.face_down_card);
                        threefour.setTag("face_down_card");
                        threefive.setBackgroundResource(R.drawable.face_down_card);
                        threefive.setTag("face_down_card");
                        threesix.setBackgroundResource(R.drawable.face_down_card);
                        threesix.setTag("face_down_card");

                        fourone.setBackgroundResource(R.drawable.face_down_card);
                        fourone.setTag("face_down_card");
                        fourtwo.setBackgroundResource(R.drawable.face_down_card);
                        fourtwo.setTag("face_down_card");
                        fourthree.setBackgroundResource(R.drawable.face_down_card);
                        fourthree.setTag("face_down_cardd");
                        fourfour.setBackgroundResource(R.drawable.face_down_card);
                        fourfour.setTag("face_down_card");
                        fourfive.setBackgroundResource(R.drawable.face_down_card);
                        fourfive.setTag("face_down_card");
                        foursix.setBackgroundResource(R.drawable.face_down_card);
                        foursix.setTag("face_down_card");

                        fiveone.setBackgroundResource(R.drawable.face_down_card);
                        fiveone.setTag("face_down_card");
                        fivetwo.setBackgroundResource(R.drawable.face_down_card);
                        fivetwo.setTag("face_down_card");
                        fivethree.setBackgroundResource(R.drawable.face_down_card);
                        fivethree.setTag("face_down_card");
                        fivefour.setBackgroundResource(R.drawable.face_down_card);
                        fivefour.setTag("face_down_card");
                        fivefive.setBackgroundResource(R.drawable.face_down_card);
                        fivefive.setTag("face_down_card");
                        fivesix.setBackgroundResource(R.drawable.face_down_card);
                        fivesix.setTag("face_down_card");

                        sixone.setBackgroundResource(R.drawable.face_down_card);
                        sixone.setTag("face_down_card");
                        sixtwo.setBackgroundResource(R.drawable.face_down_card);
                        sixtwo.setTag("face_down_card");
                        sixthree.setBackgroundResource(R.drawable.face_down_card);
                        sixthree.setTag("face_down_card");
                        sixfour.setBackgroundResource(R.drawable.face_down_card);
                        sixfour.setTag("face_down_card");
                        sixfive.setBackgroundResource(R.drawable.face_down_card);
                        sixfive.setTag("face_down_card");
                        sixsix.setBackgroundResource(R.drawable.face_down_card);
                        sixsix.setTag("face_down_card");

                        sevenone.setBackgroundResource(R.drawable.face_down_card);
                        sevenone.setTag("face_down_card");
                        seventwo.setBackgroundResource(R.drawable.face_down_card);
                        seventwo.setTag("face_down_card");
                        seventhree.setBackgroundResource(R.drawable.face_down_card);
                        seventhree.setTag("face_down_card");
                        sevenfour.setBackgroundResource(R.drawable.face_down_card);
                        sevenfour.setTag("face_down_card");
                        sevenfive.setBackgroundResource(R.drawable.face_down_card);
                        sevenfive.setTag("face_down_card");
                        sevensix.setBackgroundResource(R.drawable.face_down_card);
                        sevensix.setTag("face_down_card");

                        eightone.setBackgroundResource(R.drawable.face_down_card);
                        eightone.setTag("face_down_card");
                        eighttwo.setBackgroundResource(R.drawable.face_down_card);
                        eighttwo.setTag("face_down_card");
                        eightthree.setBackgroundResource(R.drawable.face_down_card);
                        eightthree.setTag("face_down_card");
                        eightfour.setBackgroundResource(R.drawable.face_down_card);
                        eightfour.setTag("face_down_card");
                        eightfive.setBackgroundResource(R.drawable.face_down_card);
                        eightfive.setTag("face_down_card");
                        eightsix.setBackgroundResource(R.drawable.face_down_card);
                        eightsix.setTag("face_down_card");

                        //Reset the value of cards turned
                        cards_turned=0;

                        //Display the new question
                        current_question = questions_correct + 1;
                        game_questions.setText("Q" + current_question);

                        //Set a new target
                        //Set the random numbers and target
                        randomx = (int) (Math.random()*6);
                        randomy = (int) (Math.random()*8);

                        array_position1 = positions[randomx][randomy];

                        randomx2 = (int) (Math.random()*6);
                        randomy2 = (int) (Math.random()*8);

                        while((randomx2==randomx) && (randomy2==randomy)){
                            randomx2 = (int) (Math.random()*6);
                            randomy2 = (int) (Math.random()*8);
                        }

                        array_position2 = positions[randomx2][randomy2];

                        if(level.equals("1")) {
                            while(target==(array_position1+array_position2)){
                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1 + array_position2;
                            game_highscore.setText("? + ? = " + target);
                        }
                        else if (level.equals("2")){
                            while(target==(array_position1-array_position2)){
                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1 - array_position2;
                            game_highscore.setText("? - ? = " + target);
                        }
                        else if (level.equals("3")){
                            while(target==(array_position1*array_position2)){
                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1 * array_position2;
                            game_highscore.setText("? x ? = " + target);
                        }
                        else if (level.equals("4")){

                            while((array_position1==6 || array_position2==6) || (target==array_position1 || target==array_position2)){

                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1;
                            game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position2 + " = (? - ?)");

                        }
                        else if (level.equals("5")){

                            while((array_position1==1 || array_position2==1) || (target==array_position1 || target==array_position2)){

                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position2;
                            game_highscore.setText("(? + ?)² = " +(array_position2 * array_position2));

                        }
                        else if (level.equals("6")){

                            while((array_position1==1 || array_position2==1) || (target==array_position1 || target==array_position2)){

                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1;
                            game_highscore.setText("" + "(? + ?) + " + array_position2 + " = " + (array_position1 + array_position2));

                        }
                        else if (level.equals("7")){

                            while((array_position1==1 || array_position2==1) || (target==array_position1 || target==array_position2)){

                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            target = array_position1;
                            game_highscore.setText("(? + ?)³ = " + (array_position1 * array_position1 * array_position1));

                        }

                        else if (level.equals("8")){

                            //Subract two odd numbers to add up to multiplied value
                            //Need to make sure they are both odd
                            //Do not need to check they are 6 since they have to be odd
                            while((array_position1%2!=0) && (array_position2%2!=0) || (target==array_position1 || target==array_position2)){
                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }
                            if(array_position1%2==0){
                                target = array_position1;
                                game_highscore.setText("" + array_position2 + " x (? - ?) = " + (array_position1 * array_position2));
                            }
                            else{
                                target = array_position2;
                                game_highscore.setText("" + array_position1 + " x (? - ?) = " + (array_position1 * array_position2));
                            }
                        }

                        else if(level.equals("9")){

                            //Add two even numbers to add up to divided value
                            //Need to check they are both even
                            //No need to check if they are 1 since they both have to be even
                            while(((array_position1%2!=0) && (array_position2%2!=0)) || (array_position1<=3 || array_position2<=3) || (target==array_position1 || target==array_position2)){

                                randomx = (int) (Math.random()*6);
                                randomy = (int) (Math.random()*8);

                                array_position1 = positions[randomx][randomy];

                                randomx2 = (int) (Math.random()*6);
                                randomy2 = (int) (Math.random()*8);

                                while((randomx2==randomx) && (randomy2==randomy)){
                                    randomx2 = (int) (Math.random()*6);
                                    randomy2 = (int) (Math.random()*8);
                                }

                                array_position2 = positions[randomx2][randomy2];
                            }

                            if(array_position2%2!=0){
                                target = array_position1;
                                game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position2 + " = (? + ?)");
                            }

                            else{
                                target = array_position2;
                                game_highscore.setText("" + (array_position1 * array_position2) + " ÷ " + array_position1 + " = (? + ?)");
                            }

                       }

                      //Re enable the buttons
                        oneone.setEnabled(true);
                        onetwo.setEnabled(true);
                        onethree.setEnabled(true);
                        onefour.setEnabled(true);
                        onefive.setEnabled(true);
                        onesix.setEnabled(true);

                        twoone.setEnabled(true);
                        twotwo.setEnabled(true);
                        twothree.setEnabled(true);
                        twofour.setEnabled(true);
                        twofive.setEnabled(true);
                        twosix.setEnabled(true);

                        threeone.setEnabled(true);
                        threetwo.setEnabled(true);
                        threethree.setEnabled(true);
                        threefour.setEnabled(true);
                        threefive.setEnabled(true);
                        threesix.setEnabled(true);

                        fourone.setEnabled(true);
                        fourtwo.setEnabled(true);
                        fourthree.setEnabled(true);
                        fourfour.setEnabled(true);
                        fourfive.setEnabled(true);
                        foursix.setEnabled(true);

                        fiveone.setEnabled(true);
                        fivetwo.setEnabled(true);
                        fivethree.setEnabled(true);
                        fivefour.setEnabled(true);
                        fivefive.setEnabled(true);
                        fivesix.setEnabled(true);

                        sixone.setEnabled(true);
                        sixtwo.setEnabled(true);
                        sixthree.setEnabled(true);
                        sixfour.setEnabled(true);
                        sixfive.setEnabled(true);
                        sixsix.setEnabled(true);

                        sevenone.setEnabled(true);
                        seventwo.setEnabled(true);
                        seventhree.setEnabled(true);
                        sevenfour.setEnabled(true);
                        sevenfive.setEnabled(true);
                        sevensix.setEnabled(true);

                        eightone.setEnabled(true);
                        eighttwo.setEnabled(true);
                        eightthree.setEnabled(true);
                        eightfour.setEnabled(true);
                        eightfive.setEnabled(true);
                        eightsix.setEnabled(true);

                    }

                });


            }
        };
        timer.schedule(task, 4000);

    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

}