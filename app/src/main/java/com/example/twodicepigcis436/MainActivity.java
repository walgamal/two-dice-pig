package com.example.twodicepigcis436;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public final class MainActivity extends AppCompatActivity {
    //All views in main activity
    ImageView iv_dice1, iv_dice2;
    TextView tv_p1, tv_p2, tv_curr_player, tv_turn_total;
    Button b_roll, b_hold, b_reset;

    //ints to keep track of points and total turns
    int p1_points = 0, p2_points = 0, turn_total = 0;

    //random num
    Random r;
    boolean is_p1 = true;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        iv_dice1 = findViewById(R.id.dice1);
        iv_dice2 = findViewById(R.id.dice2);

        tv_p1 = findViewById(R.id.p1_score);
        tv_p2 = findViewById(R.id.p2_score);
        tv_curr_player = findViewById(R.id.curr_player);
        tv_turn_total = findViewById((R.id.turn_total));

        b_roll = findViewById(R.id.roll_button);
        b_hold = findViewById(R.id.hold_button);
        b_reset = findViewById(R.id.reset_button);

        r = new Random();

        //function to roll dice when 'roll' button is clicked
        b_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_hold.setEnabled(true);

                //creating random numbers 1-6 for each die
                int dice1_throw = r.nextInt(6)+1;
                int dice2_throw = r.nextInt(6)+1;

                rotateDice();
                setDiceImage(dice1_throw, dice2_throw);

                if(dice1_throw == 1 && dice2_throw == 1){
                    if(is_p1){
                        p1_points = 0;
                        tv_p1.setText("Player 1 Total: 0");
                    }
                    else{
                        p2_points = 0;
                        tv_p2.setText("Player 2 Total: 0");
                    }

                    switchPlayer();
                }
                else if(dice1_throw == 1 || dice2_throw == 1){
                    switchPlayer();
                }
                else if(dice1_throw == dice2_throw){
                    b_hold.setEnabled(false);
                    turn_total += dice1_throw + dice2_throw;
                    tv_turn_total.setText("Turn Total: " + turn_total);
                }
                else {
                    turn_total += dice1_throw + dice2_throw;
                    tv_turn_total.setText("Turn Total: " + turn_total);
                }

            }
        });

        b_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_p1){
                    p1_points += turn_total;
                    tv_p1.setText("Player 1 Total: " + p1_points);
                }
                else{
                    p2_points += turn_total;
                    tv_p2.setText("Player 2 Total: " + p2_points);
                }

                checkForWinner();
                switchPlayer();
            }
        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    public void setDiceImage(int thrownNum1, int thrownNum2){
        switch(thrownNum1){
            case 1:
                iv_dice1.setImageResource(R.drawable.dice1);
                break;
            case 2:
                iv_dice1.setImageResource(R.drawable.dice2);
                break;
            case 3:
                iv_dice1.setImageResource(R.drawable.dice3);
                break;
            case 4:
                iv_dice1.setImageResource(R.drawable.dice4);
                break;
            case 5:
                iv_dice1.setImageResource(R.drawable.dice5);
                break;
            case 6:
                iv_dice1.setImageResource(R.drawable.dice6);
                break;
        }

        switch(thrownNum2){
            case 1:
                iv_dice2.setImageResource(R.drawable.dice1);
                break;
            case 2:
                iv_dice2.setImageResource(R.drawable.dice2);
                break;
            case 3:
                iv_dice2.setImageResource(R.drawable.dice3);
                break;
            case 4:
                iv_dice2.setImageResource(R.drawable.dice4);
                break;
            case 5:
                iv_dice2.setImageResource(R.drawable.dice5);
                break;
            case 6:
                iv_dice2.setImageResource(R.drawable.dice6);
                break;
        }
    }

    public void switchPlayer(){
        tv_turn_total.setText("Turn Total: 0");
        turn_total = 0;

        if(is_p1){
            //set curr player text view to p1
            tv_curr_player.setText("Current Player: P2");
        }
        else{
            //set curr player text view to p2
            tv_curr_player.setText("Current Player: P1");
        }

        is_p1 = !is_p1;
    }

    public void rotateDice(){
        ObjectAnimator rotate_dice1 = ObjectAnimator.ofFloat(iv_dice1, "rotation", 0f, 360f);
        ObjectAnimator rotate_dice2 = ObjectAnimator.ofFloat(iv_dice2, "rotation", 0f, 360f);
        rotate_dice1.setDuration(1000);
        rotate_dice2.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotate_dice1, rotate_dice2);
        animatorSet.start();
    }

    public void checkForWinner(){
        if(p1_points >= 50){
            tv_p1.setTextColor(0xFF00FF00);

            b_hold.setEnabled(false);
            b_roll.setEnabled(false);
        }
        else if(p2_points >=50){
            tv_p2.setTextColor(0xFF00FF00);

            b_hold.setEnabled(false);
            b_roll.setEnabled(false);
        }
    }

    public void reset(){
        b_hold.setEnabled(true);
        b_roll.setEnabled(true);

        is_p1 = true;

        p1_points = 0;
        p2_points = 0;
        turn_total = 0;

        tv_turn_total.setText("Turn Total: 0");
        tv_curr_player.setText("Current Player: P1");

        tv_p1.setTextColor(Color.parseColor("#808080"));
        tv_p2.setTextColor(Color.parseColor("#737373"));
        tv_p1.setText("Player 1 Total: 0");
        tv_p2.setText("Player 2 Total: 0");

        iv_dice1.setImageResource(R.drawable.dice1);
        iv_dice2.setImageResource(R.drawable.dice1);
    }
}