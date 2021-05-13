import java.awt.*;
import javax.swing.*;

public class MainApp
{

    public MainApp()
    {
        JFrame window = new JFrame();
        window.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        Board board = new Board(Settings.INITIAL_SIZE);
        c.weightx = 0.80;
        window.add(board, c);

        Menu menu = new Menu(board);
        c.weightx = 0.20;
        window.add(menu, c);

        window.setVisible(true);
        window.setResizable(true);
        window.setTitle("Queens Problem");
        window.setSize(Settings.WIDTH, Settings.HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args)
    {
        new MainApp();
    }
}
