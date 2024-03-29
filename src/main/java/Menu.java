import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel
{
    private final Board board;
    public static JLabel pathCount;

    public Menu(Board board)
    {
        this.board = board;
        setLayout(new GridLayout(2, 8));
        Button resetButton = new Button("Reset");
        resetButton.addActionListener(event ->
        {
            board.resetBoard();
            System.err.println("reset");
            updatePathCount();
        });
        pathCount = new JLabel("Path count: " + board.solver.getPathCount(), JLabel.CENTER);
        add(pathCount);
        add(resetButton);
    }

    private void updatePathCount()
    {
        pathCount.setText("Path count: " + board.solver.getPathCount());
    }

}
