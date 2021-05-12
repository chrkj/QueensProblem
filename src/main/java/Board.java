import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    public Board(int size)
    {
        this.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i % 2 == 0) {
                    if (j % 2 == 0) add(new Square(Settings.boardColor1));
                    else            add(new Square(Settings.boardColor2));
                } else {
                    if (j % 2 == 0) add(new Square(Settings.boardColor2));
                    else            add(new Square(Settings.boardColor1));
                }
            }
        }
    }
}
