package com.arasvitkus.chicagobearsquizzler

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    //Member variables for app
    var mTrueButton: Button? = null
    var mFalseButton: Button? = null
    var mQuestionTextView: TextView? = null
    var mScoreTextView: TextView? = null
    var mIndex = 0
    var mQuestion = 0
    var mScore = 0
    var mProgressBar: ProgressBar? = null

    //Use an array to create the question bank
    private val mQuestionBank = arrayOf(
            TrueFalse(R.string.question_1, true),
            TrueFalse(R.string.question_2, true),
            TrueFalse(R.string.question_3, true),
            TrueFalse(R.string.question_4, true),
            TrueFalse(R.string.question_5, false),
            TrueFalse(R.string.question_6, false),
            TrueFalse(R.string.question_7, true),
            TrueFalse(R.string.question_8, true),
            TrueFalse(R.string.question_9, false),
            TrueFalse(R.string.question_10, false),
            TrueFalse(R.string.question_11, false),
            TrueFalse(R.string.question_12, false),
            TrueFalse(R.string.question_13, true),
            TrueFalse(R.string.question_14, false),
            TrueFalse(R.string.question_15, false),
            TrueFalse(R.string.question_16, true),
            TrueFalse(R.string.question_17, false),
            TrueFalse(R.string.question_18, false),
            TrueFalse(R.string.question_19, false),
            TrueFalse(R.string.question_20, true),
            TrueFalse(R.string.question_21, false),
            TrueFalse(R.string.question_22, false),
            TrueFalse(R.string.question_23, false),
            TrueFalse(R.string.question_24, true),
            TrueFalse(R.string.question_25, true),
            TrueFalse(R.string.question_26, false),
            TrueFalse(R.string.question_27, false),
            TrueFalse(R.string.question_28, true),
            TrueFalse(R.string.question_29, false),
            TrueFalse(R.string.question_30, true),
            TrueFalse(R.string.question_31, false),
            TrueFalse(R.string.question_32, false),
            TrueFalse(R.string.question_33, true),
            TrueFalse(R.string.question_34, false),
            TrueFalse(R.string.question_35, true),
            TrueFalse(R.string.question_36, false),
            TrueFalse(R.string.question_37, true),
            TrueFalse(R.string.question_38, true),
            TrueFalse(R.string.question_39, false),
            TrueFalse(R.string.question_40, false),
            TrueFalse(R.string.question_41, false),
            TrueFalse(R.string.question_42, true),
            TrueFalse(R.string.question_43, false),
            TrueFalse(R.string.question_44, false),
            TrueFalse(R.string.question_45, true),
            TrueFalse(R.string.question_46, false),
            TrueFalse(R.string.question_47, false),
            TrueFalse(R.string.question_48, false),
            TrueFalse(R.string.question_49, false),
            TrueFalse(R.string.question_50, false)
    )

    //Progress bar constant, had to move here for the code to work properly, due to use of mQuestionBank.
    //final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);
    val NUMBER_OF_QUESTIONS = 50 //New way to update progress bar,
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey")
            mIndex = savedInstanceState.getInt("IndexKey")
        } else {
            mScore = 0
            mIndex = 0
        }
        mTrueButton = findViewById(R.id.true_button)
        mFalseButton = findViewById(R.id.false_button)
        mQuestionTextView = findViewById(R.id.question_text_view)
        mScoreTextView = findViewById(R.id.score)
        mProgressBar = findViewById(R.id.progress_bar)
        mProgressBar?.max = NUMBER_OF_QUESTIONS //Number of questions, maximum size, previously setMax in Java
        mQuestion = mQuestionBank[mIndex].questionID
        mQuestionTextView?.setText(mQuestion)
        mScoreTextView?.text = "Score " + mScore + "/" + mQuestionBank.size // Need to update strings.xml


        //True and False button listeners with methods to run on each click
        mTrueButton?.setOnClickListener(View.OnClickListener {
            checkAnswer(true)
            updateQuestion()
        })
        mFalseButton?.setOnClickListener(View.OnClickListener {
            checkAnswer(false)
            updateQuestion()
        })
    }

    //Method to update next question or show final message of score and close application
    private fun updateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.size
        if (mIndex == 0) {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Game Over")
            alert.setCancelable(false)
            alert.setMessage("You scored $mScore points!")
            alert.setPositiveButton("Close Application") { dialog, which -> finish() }
            alert.show()
        }
        mQuestion = mQuestionBank[mIndex].questionID
        mQuestionTextView!!.setText(mQuestion)
        //mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mProgressBar!!.progress = mIndex + 1 //New way for progress bar to update by question
        mScoreTextView!!.text = "Score " + mScore + "/" + mQuestionBank.size
    }

    //Method that checks the answer and display a toast message of the result
    private fun checkAnswer(userSelection: Boolean) {
        val correctAnswer = mQuestionBank[mIndex].isAnswer
        if (userSelection == correctAnswer) {
            Toast.makeText(applicationContext, R.string.correct_toast, Toast.LENGTH_SHORT).show()
            mScore = mScore + 1
        } else {
            Toast.makeText(applicationContext, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }

    //Save instance to store the score and question index, invoked when the activity may be temporarily destroyed, save the instance state here
    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("ScoreKey", mScore)
        outState.putInt("IndexKey", mIndex)
    }
}