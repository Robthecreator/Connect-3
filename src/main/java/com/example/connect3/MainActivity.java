package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red
    int activePlayer = 0;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameIsActive = true;

    public void dropIn(View view) {

        ImageView token = (ImageView) view;
        TextView winMessage = (TextView) findViewById(R.id.winMessage);
        Button restart = (Button) findViewById(R.id.restart);
        int tappedToken = Integer.parseInt(token.getTag().toString());

        if (gameState[tappedToken] == 2 && gameIsActive) {

            gameState[tappedToken] = activePlayer;
            token.setTranslationY(-1000f);

            if (activePlayer == 0) {
                token.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                token.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            token.animate().translationYBy(1000f).rotationBy(360).setDuration(500);

        }

        for (int[] winningPosition : winningPositions) {

            if (gameState[winningPosition[0]] != 2 && gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]) {
                gameIsActive = false;

                // Checks which user won (Yellow or Red)
                if(gameState[winningPosition[2]] == 0) {
                    winMessage.setText("Yellow won");
                    winMessage.setTextColor(Color.YELLOW);
                } else if (gameState[winningPosition[2]] == 1) {
                    winMessage.setText("Red won");
                    winMessage.setTextColor(Color.RED);
                }

                restart.setVisibility(View.VISIBLE);

            } else {

                boolean gameIsOver = true;

                for(int state : gameState) {
                    if (state == 2) {
                        gameIsOver = false;
                    }
                }

                if (gameIsOver) {
                    winMessage.setText("It's a tie");
                    winMessage.setTextColor(Color.BLACK);
                    gameIsActive = false;
                    restart.setVisibility(View.VISIBLE);
                }

            }

        }

    }

    public void refresh(View view) {

        TextView winMessage = (TextView) findViewById(R.id.winMessage);
        Button restart = (Button) findViewById(R.id.restart);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        activePlayer = 0;
        gameState = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2};
        gameIsActive = true;

        winMessage.setText("");
        winMessage.setTextColor(Color.WHITE);
        restart.setVisibility(View.INVISIBLE);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}