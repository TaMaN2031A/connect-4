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
    Helper helper = new Helper();
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int BUTTON_SIZE = 80;

    private byte[][] board;
    private byte currentPlayer;

    public Gui(Controller controller) {
        super("Connect 4");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));
        boardPanel.setBackground(Color.blue);
        boardButtons = new RoundButton[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boardButtons[row][col] = new RoundButton();
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
        board = new byte[ROWS][COLS];
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
                currentPlayer = (byte) (3 - currentPlayer);
                updateCurrentPlayerLabel();
                enableButtonsInBottomRow();
                byte[][] new_state = new byte[6][7];
                for(int l = 0; l < 6; l++){
                    for(int j = 0; j < 7; j++){
                        new_state[l][j] = board[l][j];
                    }
                }
                int ai_chosen_column = controller.make_good_move((new_state));

                if(ai_chosen_column == -1){
                    long x = helper.calculateScore(new_state, 2);
                    long y = helper.calculateScore(new_state, 1);
                    currentPlayerLabel.setText("Final Score: " + "Computer "+x + "  User  " + y);

                }

                int ai_chosen_row = getEmptyRow(ai_chosen_column);

                board[ai_chosen_row][ai_chosen_column] = currentPlayer;
                updateButton(ai_chosen_row, ai_chosen_column);
                currentPlayer = (byte) (3 - currentPlayer);
                updateCurrentPlayerLabel();
                enableButtonsInBottomRow();
                int cnt = 0;
                for (int r = 0; r < ROWS; r++) {
                    for (int col = 0; col < COLS; col++) {
                        if (board[r][col] != 0)
                            cnt++;
//                        System.out.print(board[r][col] + " ");
                    }
//                    System.out.println();
                }
                if(cnt == 42)
                {
                    long x = helper.calculateScore(board, 2);
                    long y = helper.calculateScore(board, 1);
                    currentPlayerLabel.setText("Final Score: " + "Computer "+x + "  User  " + y);
                }
            }
        }
        /*** TO DO **/
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
class RoundButton extends JButton {

    public RoundButton() {
        setOpaque(false); // Make the button transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do not paint the default button border
    }
}