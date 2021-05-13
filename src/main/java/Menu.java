import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    public Menu(Board board)
    {
        Button resetButton = new Button("Reset");
        resetButton.addActionListener( event ->
        {
            board.reset();
            System.err.println("reset");
        });
        add(resetButton);
    }
}
