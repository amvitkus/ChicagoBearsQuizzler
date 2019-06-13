package com.arasvitkus.chicagobearsquizzler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Member variables for app
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    int mIndex;
    int mQuestion;
    int mScore;
    ProgressBar mProgressBar;

    //Use an array to create the question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, false),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, true),
            new TrueFalse(R.string.question_9, false),
            new TrueFalse(R.string.question_10, false),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true),
            new TrueFalse(R.string.question_14, false),
            new TrueFalse(R.string.question_15, false),
            new TrueFalse(R.string.question_16, true),
            new TrueFalse(R.string.question_17, false),
            new TrueFalse(R.string.question_18, false),
            new TrueFalse(R.string.question_19, false),
            new TrueFalse(R.string.question_20, true),
            new TrueFalse(R.string.question_21, false),
            new TrueFalse(R.string.question_22, false),
            new TrueFalse(R.string.question_23, false),
            new TrueFalse(R.string.question_24, true),
            new TrueFalse(R.string.question_25, true),
            new TrueFalse(R.string.question_26, false),
            new TrueFalse(R.string.question_27, false),
            new TrueFalse(R.string.question_28, true),
            new TrueFalse(R.string.question_29, false),
            new TrueFalse(R.string.question_30, true),
            new TrueFalse(R.string.question_31, false),
            new TrueFalse(R.string.question_32, false),
            new TrueFalse(R.string.question_33, true),
            new TrueFalse(R.string.question_34, false),
            new TrueFalse(R.string.question_35, true),
            new TrueFalse(R.string.question_36, false),
            new TrueFalse(R.string.question_37, true),
            new TrueFalse(R.string.question_38, true),
            new TrueFalse(R.string.question_39, false),
            new TrueFalse(R.string.question_40, false),
            new TrueFalse(R.string.question_41, false),
            new TrueFalse(R.string.question_42, true),
            new TrueFalse(R.string.question_43, false),
            new TrueFalse(R.string.question_44, false),
            new TrueFalse(R.string.question_45, true),
            new TrueFalse(R.string.question_46, false),
            new TrueFalse(R.string.question_47, false),
            new TrueFalse(R.string.question_48, false)

    };

    //Progress bar constant, had to move here for the code to work properly, due to use of mQuestionBank.
    //final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);
    final int NUMBER_OF_QUESTIONS = 48; //New way to update progress bar,



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setMax(NUMBER_OF_QUESTIONS);  //Number of questions, maximum size,

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);


        //True and False button listeners with methods to run on each click
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });

    }

    //Method to update next question or show final message of score and close application
    private void updateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length;

        if(mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        //mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mProgressBar.setProgress(mIndex + 1); //New way for progress bar to update by question
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    //Method that checks the answer and display a toast message of the result
    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore + 1;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    //Save instance to store the score and question index
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }

}
