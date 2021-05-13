import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Square extends JPanel {

    private Token token;
    private BufferedImage queenImage;
    private BufferedImage crossImage;

    private enum Token {NONE, QUEEN, CROSS}

    public Square(Color color)
    {
        setBackground(color);
        try
        {
            queenImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("queen-icon.png")));
            crossImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("cross-icon.png")));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        token = Token.NONE;
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
            renderScaledImage(g, queenImage);
        if (token == Token.CROSS)
            renderScaledImage(g, crossImage);
    }

    private void renderScaledImage(Graphics g, BufferedImage image)
    {
        Image imageToRender = getScaledImage(image);

        int imageHeight = imageToRender.getHeight(this);
        int squareHeight = getHeight();
        int x = (squareHeight - imageHeight) / 2;

        int imageWidth = imageToRender.getWidth(this);
        int squareWidth = getWidth();
        int y = (squareWidth - imageWidth) / 2;

        g.drawImage(imageToRender, x, y, this);
    }

    public Image getScaledImage(BufferedImage image)
    {
        return image.getScaledInstance((int) (getWidth() * 0.8), (int) (getHeight() * 0.8), Image.SCALE_SMOOTH);
    }

}
