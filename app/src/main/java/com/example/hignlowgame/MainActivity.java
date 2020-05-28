package com.example.hignlowgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private EditText num_input;
    private Button guess;
    private TextView feedback;
    private Button playAgain;
    private TextView count;
    private int number;
    private int attempts = 0;
    private int maxAttempts = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        num_input = findViewById(R.id.num_input);
        guess = findViewById(R.id.button);
        feedback = findViewById(R.id.feedback);
        playAgain = findViewById(R.id.button2);
        number = (int)(Math.random()*(1000)+0);
        count = findViewById(R.id.textView);
        count.setText("Attempts: 0 | Remaining : 20");
        count.setTextColor(Color.BLUE);
        num_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback.setText("");
            }
        });
        this.guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = num_input.getText().toString();
                int result = 0;
                Log.d(""+number,num_input.getText().toString());
                if(!allDigits(info) || info.length()==0){
                    feedback.setText(R.string.feedback1);
                    feedback.setTextColor(Color.RED);
                }else{
                    result = Integer.parseInt(info);
                    switch(Compare(number,result)){
                        case 0:
                            feedback.setText(R.string.feedback_too_large);
                            feedback.setTextColor(Color.RED);
                            break;
                        case 1:
                            feedback.setText(R.string.feedback_too_small);
                            feedback.setTextColor(Color.RED);
                            break;
                        case 2:
                            feedback.setText(R.string.feedback_close_small);
                            feedback.setTextColor(Color.RED);
                            break;
                        case 3:
                            feedback.setText(R.string.feedback_close_large);
                            feedback.setTextColor(Color.RED);
                            break;
                        case 4:
                            feedback.setText(R.string.feedback_match);
                            feedback.setTextColor(Color.GREEN);
                            guess.setEnabled(false);
                            break;
                        default:
                            feedback.setText("");
                            break;
                    }
                    attempts += 1;
                    maxAttempts -= 1;
                    if(attempts == 20){
                        guess.setEnabled(false);
                        count.setText(R.string.feedback_over);
                        count.setTextColor(Color.RED);
                        return;
                    }
                    count.setText(String.format("Attempts: %d | Remaining: %d",attempts,maxAttempts));


                }

            }
        });
        this.playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = (int)(Math.random()*(1000)+0);
                attempts = 0;
                maxAttempts = 20;
                guess.setEnabled(true);
                count.setText(String.format("Attempts: %d | Remaining: %d",attempts,maxAttempts));
                count.setTextColor(Color.BLUE);

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    public boolean allDigits(String string){
        for(int i=0;i<string.length();i++){
            if(!Character.isDigit(string.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public int Compare(int number, int guess){
        if(guess - number >= 30){
            return 0; // 0 represent tooLarge;
        }else if(number - guess >= 30){
            return 1; // number > guess // 1 represent tooSmall;
        }else if(number > guess && (Math.abs(number - guess))<30){
            return 2; // number > guess. close if abs < 30;
        }else if(number < guess && (Math.abs(guess-number))<30){
            return 3; // number < guess . close
        }else{
            return 4; // number = guess;
        }
    }
}
