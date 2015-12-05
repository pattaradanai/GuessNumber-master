package com.example.guessnumber;

import android.content.Intent;
import android.util.Log;

import java.util.Random;


public class Game {

    private static final String TAG = "Game";

    protected enum CompareResult {
        TOO_SMALL, EQUAL, TOO_BIG
    }
    private int mAnswer;
    private int mTotalGuesses;
    private Random random = new Random();

    public Game(int which) {
        mTotalGuesses = 0;

        if(which==0) {
            mAnswer = random.nextInt(10);
        }else if(which==1) {
            mAnswer = random.nextInt(100);
        }
        Log.d(TAG, "The answer is " + mAnswer);
    }

    public CompareResult submitGuess(int guessNumber) {

        mTotalGuesses++;

        if (guessNumber == mAnswer) {
            return CompareResult.EQUAL;
        } else if (guessNumber < mAnswer) {
            return CompareResult.TOO_SMALL;
        } else if (guessNumber > mAnswer) {
            return CompareResult.TOO_BIG;
        }

        return null;
    }

    public int getTotalGuesses() {

        return mTotalGuesses;
    }
    public void setmTotalGuesses(int mTotalGuesses) {
        this.mTotalGuesses = mTotalGuesses;
    }
}
