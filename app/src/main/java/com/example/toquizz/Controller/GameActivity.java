package com.example.toquizz.Controller;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toquizz.Model.Question;
import com.example.toquizz.Model.QuestionBank;
import com.example.toquizz.R;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private QuestionBank mQuestionBank;
    private TextView mQuestionText;
    private Button mAnswerBtn1;
    private Button mAnswerBtn2;
    private Button mAnswerBtn3;
    private Button mAnswerBtn4;
    private Question mCurrentQuestion;
    private int mNumberOfQuestions;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents;
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("onCreate");
        mNumberOfQuestions =4;

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        mQuestionText = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerBtn2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerBtn3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerBtn4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        mAnswerBtn1.setTag(0);
        mAnswerBtn2.setTag(1);
        mAnswerBtn3.setTag(2);
        mAnswerBtn4.setTag(3);

        mAnswerBtn1.setOnClickListener(this);
        mAnswerBtn2.setOnClickListener(this);
        mAnswerBtn3.setOnClickListener(this);
        mAnswerBtn4.setOnClickListener(this);
        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        System.out.println("cliqueclique");
        int responseIndex = (int) v.getTag();
        if(responseIndex == mCurrentQuestion.getAnswerIndex() ) {
            Toast.makeText(this, "EXACTEMENT !", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Mais nonnnnnnn !", Toast.LENGTH_SHORT).show();
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;


                if (--mNumberOfQuestions == 0) {

                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000);
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Quelle est la voiture de Sylvain?",
                Arrays.asList("Twingo", "Ferrari", "Barbie", "Alpha Romeo"),
                0);

        Question question2 = new Question("Quel type de danse Sofiane exerce?",
                Arrays.asList("Country", "BreakDance", "Polka", "Tecktonik"),
                3);

        Question question3 = new Question("Valentin est il normal ?",
                Arrays.asList("Non", "Oui", "Ah bon?", "Qui ça ?"),
                0);

        Question question4 = new Question("Pourquoi Maurane était absente ce matin ?",
                Arrays.asList("Pour manger des sushis", "Pour avoir un stage à responsabilités", "Pour s'enfiler des spaghettis", "Pour se teindre les cheveux en vert"),
                1);


        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4));


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }


    private void displayQuestion(final Question question) {
        mQuestionText.setText(question.getQuestion());
        mAnswerBtn1.setText(question.getChoiceList().get(0));
        mAnswerBtn2.setText(question.getChoiceList().get(1));
        mAnswerBtn3.setText(question.getChoiceList().get(2));
        mAnswerBtn4.setText(question.getChoiceList().get(3));
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bravo !")
                .setMessage("Ton score est maintenant de " + mScore + "points !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }

}
