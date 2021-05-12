import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Square extends JPanel {

    private boolean hasQueen;
    private BufferedImage image;

    public Square(Color color)
    {
        hasQueen = false;
        setBackground(color);
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("queen-icon.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void placeQueen()
    {
        this.hasQueen = true;
    }

    public boolean hasQueen()
    {
        return hasQueen;
    }

    public void removeQueen()
    {
        this.hasQueen = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hasQueen) {
            Graphics2D g2D = (Graphics2D) g.create();
            try {
                Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2D.addRenderingHints(renderingHints);

                Image imageToRender = image.getScaledInstance((int) (getWidth() * 0.8), (int) (getHeight() * 0.8), Image.SCALE_DEFAULT);

                int imageHeight = imageToRender.getHeight(this);
                int squareHeight = getHeight();
                int x = (squareHeight - imageHeight) / 2;

                int imageWidth = imageToRender.getWidth(this);
                int squareWidth = getWidth();
                int y = (squareWidth - imageWidth) / 2;

                g.drawImage(imageToRender, x, y, this);
            } finally {
                g2D.dispose();
            }
        }
    }

}
