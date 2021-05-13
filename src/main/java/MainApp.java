import javax.swing.*;
import java.awt.*;

public class MainApp {

    private final int WIDTH = 800;
    private final int HEIGHT = (int) (WIDTH * 0.8);

    public MainApp()
    {
        JFrame window = new JFrame();
        window.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.80;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        Board board = new Board(Settings.INITIAL_SIZE);
        window.add(board, c);

        Menu menu = new Menu(board);
        c.weightx = 0.20;
        window.add(menu, c);

        window.setVisible(true);
        window.setResizable(false);
        window.setSize(WIDTH, HEIGHT);
        window.setTitle("Queens Problem");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args)
    {
        new MainApp();
    }
}
