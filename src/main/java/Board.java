import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Board extends JPanel
{
    private final int size;
    private Solver solver;
    private final Square[][] boardState;

    public Board(int size)
    {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new GridLayout(size, size));
        boardState = new Square[size][size];
        solver = new Solver();
        solver.initializeBoard(size);
        this.size = size;
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent event)
            {
                System.err.format("Clicked at: %d, %d%n", event.getX(), event.getY());
                int col = (event.getY() - 1) / (getHeight() / size);
                int row = (event.getX() - 1) / (getWidth() / size);
                System.err.format("Col: %d, Row: %d%n", col, row);
                System.err.println(col + " " + row);
                solver.insertQueen(col, row);
            }
        });
        setupBoardState();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        removeAll();
        reCalculate();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                add(boardState[i][j]);
        validate();
        repaint();
    }

    public void setupBoardState()
    {
        int[][] board = solver.getBoard();
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if(i % 2 == 0)
                {
                    Square square;
                    if (j % 2 == 0)
                        square = new Square(Settings.boardColor1);
                    else
                        square = new Square(Settings.boardColor2);

                    if (board[i][j] == -1)
                        square.setCross();
                    if (board[i][j] == 1)
                        square.setQueen();
                    boardState[i][j] = square;
                }
                else
                {
                    Square square;
                    if (j % 2 == 0)
                        square = new Square(Settings.boardColor2);
                    else
                        square = new Square(Settings.boardColor1);

                    if (board[i][j] == -1)
                        square.setCross();
                    if (board[i][j] == 1)
                        square.setQueen();
                    boardState[i][j] = square;
                }
            }
        }
    }

    public void reCalculate()
    {
        int[][] board = solver.getBoard();
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (board[i][j] == -1)
                    boardState[i][j].setCross();
                if (board[i][j] == 1)
                    boardState[i][j].setQueen();
                if (board[i][j] == 0)
                    boardState[i][j].setEmpty();
            }
        }
    }

    public void reset()
    {
        solver = new Solver();
        solver.initializeBoard(size);
        repaint();
    }

}
