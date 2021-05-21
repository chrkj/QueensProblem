import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Square extends JPanel {
    private Token token;
    private Image queenScaled;
    private Image crossScaled;
    private final int queenImageHeight;
    private final int queenImageWidth;
    private final int crossImageHeight;
    private final int crossImageWidth;

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
        // TODO: Optimize this
        crossImageHeight = crossScaled.getHeight(this);
        crossImageWidth = crossScaled.getWidth(this);
        queenImageHeight = queenScaled.getHeight(this);
        queenImageWidth = queenScaled.getWidth(this);
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
            renderImage(g, queenScaled, queenImageHeight, queenImageWidth);
        if (token == Token.CROSS)
            renderImage(g, crossScaled, crossImageHeight, crossImageWidth);
    }

    private void renderImage(Graphics g, Image image, int height, int width)
    {
        int squareHeight = getHeight();
        int x = (squareHeight - height) / 2;
        int squareWidth = getWidth();
        int y = (squareWidth - width) / 2;
        g.drawImage(image, x, y, this);
    }

    public Image getScaledImage(BufferedImage image)
    {
        return image.getScaledInstance((int) ((Settings.WIDTH * 0.8 / Settings.INITIAL_SIZE) * 0.7),
                (int) ((Settings.HEIGHT / Settings.INITIAL_SIZE) * 0.7),
                Image.SCALE_SMOOTH
        );
    }

}
