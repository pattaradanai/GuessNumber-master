package com.example.guessnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView mGuessNumberTextView;
    private TextView mResultTextView;
    private EditText mInput;
    private Button mGuessButton;
    private Game mGame;
    private int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGuessNumberTextView = (TextView) findViewById(R.id.guess_number_text_view);
        mResultTextView = (TextView) findViewById(R.id.result_text_view);
        mInput = (EditText) findViewById(R.id.input);
        mGuessButton = (Button) findViewById(R.id.guess_button);

        Intent i = getIntent();
        level = i.getIntExtra("level",0);

        mGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInput.getText().length() == 0) {
                    Toast.makeText(
                            GameActivity.this, "กรุณาพิมพ์ตัวเลข", Toast.LENGTH_LONG).show();
                    return;
                }

                int guessNumber = Integer.valueOf(mInput.getText().toString());
                mGuessNumberTextView.setText(String.valueOf(guessNumber));

                checkAnswer(guessNumber);
            }
        });

        newGame();
    }

    private void newGame() {
        mGame = new Game(getLevel());
        mGuessNumberTextView.setText("X");
        mResultTextView.setText(null);
        mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);
        mGame.setmTotalGuesses(0);
    }

    private void checkAnswer(int guessNumber) {
        Game.CompareResult result = mGame.submitGuess(guessNumber);

        if (result == Game.CompareResult.EQUAL) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.applause);
            mp.start();

            mResultTextView.setText("ถูกต้องนะครับ");
            mGuessNumberTextView.setBackgroundResource(R.color.correct_guess);

            String msg = String.format("จำนวนครั้งที่ทาย: %d",mGame.getTotalGuesses());

            new AlertDialog.Builder(this)
                    .setTitle("สรุปผล")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("เริ่มเกมใหม่", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newGame();
                        }
                    })
                    .setNegativeButton("กลับหน้าหลัก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
        else if (result == Game.CompareResult.TOO_BIG) {
            mResultTextView.setText(guessNumber + " มากไป");
            mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);
        }
        else if (result == Game.CompareResult.TOO_SMALL) {
            mResultTextView.setText(guessNumber + " น้อยไป");
            mGuessNumberTextView.setBackgroundResource(R.color.incorrect_guess);
        }
    }
        public int getLevel() {
            return level;
        }
}
