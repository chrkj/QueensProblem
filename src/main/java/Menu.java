import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel
{
    public static JLabel pathCount;

    public Menu(Board board)
    {
        setLayout(new GridLayout(2, 8));
        Button resetButton = new Button("Reset");
        resetButton.addActionListener(event ->
        {
            board.reset();
            System.err.println("reset");
        });
        pathCount = new JLabel("Path count: " + board.solver.getPathCount(), JLabel.CENTER);
        add(pathCount);
        add(resetButton);
    }

}
