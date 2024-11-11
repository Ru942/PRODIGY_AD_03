package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends ComponentActivity {

    private Button[] buttons = new Button[9];
    private boolean isPlayerXTurn = true;
    private int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0 = empty, 1 = X, -1 = O
    private int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < 9; i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            final int index = i;
            buttons[i].setOnClickListener(view -> onGridButtonClick(index));
        }

        // Reset Button
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(view -> resetGame());
    }

    private void onGridButtonClick(int index) {
        if (gameState[index] != 0) return;

        // Set player symbol and update game state
        gameState[index] = isPlayerXTurn ? 1 : -1;
        buttons[index].setText(isPlayerXTurn ? "X" : "O");

        if (checkForWin()) {
            Toast.makeText(this, (isPlayerXTurn ? "X" : "O") + " Wins!", Toast.LENGTH_LONG).show();
            disableButtons();
        } else if (isDraw()) {
            Toast.makeText(this, "It's a Draw!", Toast.LENGTH_LONG).show();
        }

        // Switch turn
        isPlayerXTurn = !isPlayerXTurn;
    }

    private boolean checkForWin() {
        for (int[] condition : winConditions) {
            int a = condition[0], b = condition[1], c = condition[2];
            if (gameState[a] != 0 && gameState[a] == gameState[b] && gameState[a] == gameState[c]) {
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (int cell : gameState) {
            if (cell == 0) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        isPlayerXTurn = true;
        for (int i = 0; i < 9; i++) {
            gameState[i] = 0;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
    }
}
