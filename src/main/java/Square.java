import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Square extends JPanel {
    private Token token;
    private Image queenScaled;
    private Image crossScaled;

    private enum Token { NONE, QUEEN, CROSS }

    public Square(Color color)
    {
        token = Token.NONE;
        setBackground(color);
        try
        {
            BufferedImage queenImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("queen-icon.png")));
            queenScaled = getScaledImage(queenImage);
            BufferedImage crossImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("cross-icon.png")));
            crossScaled = getScaledImage(crossImage);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setQueen()
    {
        token = Token.QUEEN;
    }

    public void setCross()
    {
        token = Token.CROSS;
    }

    public void setEmpty()
    {
        token = Token.NONE;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paintComponent(g);
        if (token == Token.QUEEN)
            renderImage(g, queenScaled);
        if (token == Token.CROSS)
            renderImage(g, crossScaled);
    }

    private void renderImage(Graphics g, Image image)
    {
        int imageHeight = image.getHeight(this);
        int squareHeight = getHeight();
        int x = (squareHeight - imageHeight) / 2;

        int imageWidth = image.getWidth(this);
        int squareWidth = getWidth();
        int y = (squareWidth - imageWidth) / 2;

        g.drawImage(image, x, y, this);
    }

    public Image getScaledImage(BufferedImage image)
    {
        return image.getScaledInstance((int) ((Settings.WIDTH * 0.8 / Settings.INITIAL_SIZE) * 0.8),
                (int) ((Settings.HEIGHT / Settings.INITIAL_SIZE) * 0.8),
                Image.SCALE_SMOOTH
        );
    }

}
