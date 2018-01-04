package com.example.jaska.soccerscore;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int goalA = 0;
    int goalB = 0;
    int cornerA = 0;
    int cornerB = 0;
    int foulA = 0;
    int foulB = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // PASTE CODE YOU WANT TO TEST HERE
    }
    public void teamACorner(View view){
        cornerA++;
        displayA(view);
    }
    public void teamBCorner(View view){
        cornerB++;
        displayB(view);
    }
    public void teamAGoal(View view){
        goalA++;
        displayA(view);
    }
    public void teamBGoal(View view){
        goalB++;
        displayB(view);
    }
    //incrementing fouls but if a team does a foul three times goal is decremented by one
    public void teamAFoul(View view){
        foulA++;
        if(foulA > 0 && foulA % 3 == 0)goalA--;
        displayA(view);
    }
    public void teamBFoul(View view){
        foulB++;
        if(foulB > 0 && foulB % 3 == 0)goalB--;
        displayB(view);
    }
    public void setTimer(final View view){
        resetScore(view);
        new CountDownTimer(59000, 1000) {
            TextView mTextField = (TextView)findViewById(R.id.timer);
            public void onTick(long millisUntilFinished) {
                mTextField.setText("00:" + String.format("%02d",millisUntilFinished / 1000));
            }

            public void onFinish() {
                mTextField.setText("0:00");
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                if(goalA > goalB){
                    alertDialog.setTitle("TeamA Wins");
                    String ans = "Goals: " + goalA + "\n"+"Corners: " + cornerA + "\n";
                    ans += "Fouls: " + foulA;
                    alertDialog.setMessage(ans);
                }
                else
                if(goalA == goalB)
                {
                    alertDialog.setTitle("Match Draws");

                }
                else
                {
                    alertDialog.setTitle("TeamB Wins");
                    String ans = "Goals: " + goalB + "\n"+"Corners: " + cornerB + "\n";
                    ans += "Fouls: " + foulB;
                    alertDialog.setMessage(ans);
                }
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                resetScore(view);
            }
        }.start();
    }
    public void resetScore(View view){
        goalA = 0;
        goalB = 0;
        cornerA = 0;
        cornerB = 0;
        foulA = 0;
        foulB = 0;
        displayA(view);
        displayB(view);

    }
    private void displayA(View v) {
        TextView t = (TextView)findViewById(R.id.team_a_goal);
        t.setText(""+goalA);
        t = (TextView)findViewById(R.id.team_a_corner);
        t.setText(""+cornerA);
        t = (TextView)findViewById(R.id.team_a_foul);
        t.setText(""+foulA);
    }
    private void displayB(View v) {
        TextView t = (TextView)findViewById(R.id.team_b_goal);
        t.setText(""+goalB);
        t = (TextView)findViewById(R.id.team_b_corner);
        t.setText(""+cornerB);
        t = (TextView)findViewById(R.id.team_b_foul);
        t.setText(""+foulB);
    }
}