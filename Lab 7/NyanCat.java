import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class NyanCat extends Thread {
    int x;
    int y;

    private int speedX;
    private int speedY;
    private int width;

    final int sizeX = 90;
    final int sizeY = 53;

    private JLabel nyanCat;
    private GamePanel panel;

    NyanCat(int newWidth, int newHeight, GamePanel newPanel) {
        super();

        width = newWidth;
        int height = newHeight - newPanel.height * 5 / 12;
        panel = newPanel;

        Random random = new Random();
        int duckType = Math.abs(random.nextInt()) % 2;

        nyanCat = new JLabel(new ImageIcon(getClass().getResource((duckType == 0) ? "LR.png" : "RL.png")));

        nyanCat.setSize(new Dimension(sizeX, sizeY));

        speedX = Math.abs(random.nextInt(3)) + 1;
        speedY = -Math.abs(random.nextInt(2)) - 1;
        if (duckType == 1) speedX = -speedX;

        y = height;
        int quarterWidth = width / 4;
        x = width / 2 - quarterWidth / 2 + Math.abs(random.nextInt()) % quarterWidth - 2 * sizeX;
    }

    @Override
    public void run() {
        panel.add(nyanCat);

        boolean flag = true;

        while (!isInterrupted() && flag) {
            int nx = x + speedX;
            int ny = y + speedY;

            if (speedX > 0 && nx > width) flag = false;
            if (speedX < 0 && nx < -sizeX) flag = false;
            if (ny < -sizeY) flag = false;

            x = nx;
            y = ny;
            nyanCat.setLocation(x, y);

            try {
                sleep(20);
            } catch (InterruptedException e) {
                interrupt();
            }
        }

        if (flag) panel.changedCoins(1);
        else panel.changedCoins(-1);

        panel.remove(nyanCat);
        panel.repaint();
        panel.nyanCats.remove(this);
        new Splash(panel, x, y).start();
    }
}
