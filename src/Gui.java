import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.util.Random;

public class Gui extends JFrame {
    Controller controller;
    private JButton[][] boardButtons;
    private JLabel currentPlayerLabel;

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int BUTTON_SIZE = 80;

    private int[][] board;
    private int currentPlayer;

    public Gui(Controller controller) {
        super("Connect 4");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        boardButtons = new JButton[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardButtons[row][col] = new JButton();
                boardButtons[row][col].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                boardButtons[row][col].setBackground(Color.WHITE);
                boardButtons[row][col].setEnabled(false);
                boardButtons[row][col].addActionListener(new ButtonListener(col));
                boardPanel.add(boardButtons[row][col]);
            }
        }

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        add(boardPanel, BorderLayout.CENTER);
        add(currentPlayerLabel, BorderLayout.NORTH);

        currentPlayer = 1;
        board = new int[ROWS][COLS];
        updateCurrentPlayerLabel();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        startGame();
    }
    private void updateCurrentPlayerLabel() {
        if(currentPlayer == 1)
            currentPlayerLabel.setText("Current Player: user" );
        else
            currentPlayerLabel.setText("Current Player: AI model");
    }
    private void startGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = 0;
            }
        }
        enableButtonsInBottomRow();
        updateCurrentPlayerLabel();
    }
    private void enableButtonsInBottomRow() {
        for (int col = 0; col < COLS; col++) {
            if (board[0][col] == 0) {
                boardButtons[0][col].setEnabled(true);
            }
        }
    }
    private class ButtonListener implements ActionListener {
        private int col;
        public ButtonListener(int col) {
            this.col = col;
        }
        public void actionPerformed(ActionEvent e) {
            int row = getEmptyRow(col);
            if (row != -1) {
                board[row][col] = currentPlayer;
                updateButton(row, col);
                currentPlayer = 3 - currentPlayer;
                updateCurrentPlayerLabel();
                enableButtonsInBottomRow();
                int ai_chosen_column = controller.make_good_move(create_state(board));
                System.out.println("Chosen column is " + ai_chosen_column);
                int ai_chosen_row = getEmptyRow(ai_chosen_column);
                System.out.println("Chosen row is " + ai_chosen_row);

                board[ai_chosen_row][ai_chosen_column] = currentPlayer;
                updateButton(ai_chosen_row, ai_chosen_column);
                currentPlayer = 3 - currentPlayer;
                updateCurrentPlayerLabel();
                enableButtonsInBottomRow();
            }
        }
        /*** TO DO **/
        State create_state(int[][] board){
            return new State();
        }
    }
    private void updateButton(int row, int col) {
        if (board[row][col] == 1) {
            boardButtons[row][col].setBackground(Color.RED);
        } else if (board[row][col] == 2) {
            boardButtons[row][col].setBackground(Color.YELLOW);
        }
        boardButtons[row][col].setEnabled(false);
    }
    private int getEmptyRow(int col) {
        for (int row = ROWS-1; row > -1; row--) {
            if (board[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }
}