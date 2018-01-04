package com.example.jaska.thinknext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int Scores = 0;
    ArrayList<Integer> answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFlipper mFlipper = (ViewFlipper)findViewById(R.id.simple_flipper);
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    public void submitAnswers(View view) {
        Scores = 0;
        answers = new ArrayList<Integer>();

        checkQuestion1(view);
        checkQuestion2(view);
        checkQuestion3(view);
        checkQuestion4(view);
        checkQuestion5(view);
        submit();
    }

    public void nextQues(View v){
        ViewFlipper fp = (ViewFlipper)findViewById(R.id.simple_flipper);
        int displayedChild = fp.getDisplayedChild();
        int childCount = fp.getChildCount();

        if (displayedChild != childCount - 1) {
            fp.showNext();
        }
        else
            Toast.makeText(this, "You are at the last question!", Toast.LENGTH_LONG).show();

    }
    public void prevQues(View v){
        ViewFlipper flipper = (ViewFlipper)findViewById(R.id.simple_flipper);
        int displayedChild = flipper.getDisplayedChild();

        if (displayedChild != 0) {
            flipper.showPrevious();
        }
        else
            Toast.makeText(this, "You are at first question!", Toast.LENGTH_LONG).show();
    }
    private void submit() {
        StringBuffer str = new StringBuffer("Your Score is " + Scores);
        if(answers.size()==0)
        {
            str.append("\nCongratulations! You are a champ");
        }
        else {

            str.append("\n Please check your answers for the following questions:\n");

            for (int i = 0; i < answers.size(); i++) {
                str.append(answers.get(i) + " ");
            }
            str.append("\nBest of Luck for the next try :)");

        }

        Toast.makeText(this, str.toString(), Toast.LENGTH_LONG).show();
    }

    private void checkQuestion5(View view) {
        EditText et = (EditText)findViewById(R.id.q5a1);
        String myAns = et.getText().toString().toLowerCase();
        if(myAns.matches("elon musk") || myAns.matches("elon") || myAns.matches("musk")){
            Scores++;
        }
        else{
            answers.add(5);
        }

    }


    private void checkQuestion4(View view) {
        RadioGroup rg = (RadioGroup)findViewById(R.id.question4);
        int selectedIdq1 = rg.getCheckedRadioButtonId();
        if(selectedIdq1 == -1){
            answers.add(4);
            return;
        }
        RadioButton selectedRadioButton = (RadioButton)findViewById(selectedIdq1);
        String ans1 = selectedRadioButton.getText().toString();
        if(ans1.matches("1998")){
            Scores++;
        }
        else
        {
            answers.add(4);
        }
    }
    private void checkQuestion3(View view) {
        EditText et = (EditText)findViewById(R.id.q3a1);
        String myAns = et.getText().toString().toLowerCase();
        if(myAns.matches("world wide web")){
            Scores++;
        }
        else
        {
            answers.add(3);
        }

    }
    private void checkQuestion2(View view) {
        CheckBox ans1 = (CheckBox)findViewById(R.id.q2a1);
        CheckBox ans2 = (CheckBox)findViewById(R.id.q2a2);
        CheckBox ans3 = (CheckBox)findViewById(R.id.q2a3);
        CheckBox ans4 = (CheckBox)findViewById(R.id.q2a4);
        if(ans1.isChecked() && ans2.isChecked() && ans3.isChecked() && !ans4.isChecked())
        {
            Scores++;
        }
        else
        {
            answers.add(2);
        }
    }


    private void checkQuestion1(View view) {
        RadioGroup rg = (RadioGroup)findViewById(R.id.question1);
        int selectedIdq1 = rg.getCheckedRadioButtonId();
        if(selectedIdq1 == -1){
            answers.add(1);
            return;
        }

        RadioButton selectedRadioButton = (RadioButton)findViewById(selectedIdq1);
        String ans1 = selectedRadioButton.getText().toString();
        if(ans1.matches("Ada Lovelace")){
            Scores++;
        }
        else
        {
            answers.add(1);
        }
    }


}

