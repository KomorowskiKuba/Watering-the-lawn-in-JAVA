package com.company;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.company.Global.*;

public class Graphics {
    private final int amount_of_sprinklers = 1;
    private final int[][] t_values = new int[Y_REAL_SIZE][X_REAL_SIZE];

    Graphics() {
    }

    public void Generate_jpg(int[][] tab, String jpgName, boolean saveEnabled) throws IOException {

        int r = 0;
        int g = 0;
        int b = 0;
        int p = 0;

        BufferedImage img = new BufferedImage(X_SIZE_END - X_SIZE_START, Y_SIZE_END - Y_SIZE_START, BufferedImage.TYPE_INT_RGB);

        for (int i = Y_SIZE_START; i < Y_SIZE_END; i++) {
            for (int j = X_SIZE_START; j < X_SIZE_END; j++) {
                if (tab[i][j] == 0) {
                    r = 255;
                    g = 0;
                    b = 0;
                } else if (tab[i][j] > 0) {
                    r = 0;
                    if (255 - 5 * tab[i][j] > 30) {
                        g = 255 - 5 * tab[i][j];
                        b = 0;
                    } else {
                        g = 0;
                        b = 220;
                    }
                } else if (tab[i][j] < 0) {
                    r = 0;
                    g = 0;
                    b = 0;
                }

                p = (r << 16) | (g << 8) | b;

                img.setRGB(j - Y_SIZE_START, i - X_SIZE_START, p);
            }
        }
        if (saveEnabled == true) {
            try {
                File f = new File(jpgName + ".jpg");
                ImageIO.setUseCache(false);
                ImageIO.write(img, "jpg", f);
            } catch (IOException e1) {
                System.out.println("Error: " + e1);
            }
        }
        WritableImage wr = null;
        wr = new WritableImage(img.getWidth(), img.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                pw.setArgb(x, y, img.getRGB(x, y));
            }
        }
        ImageView img_v = new ImageView(wr);
        Controller.ImageViewListAdd(img_v);
    }

    public void takeValues(int[][] tab) {
        for (int i = Y_SIZE_START; i < Y_SIZE_END; i++) {
            for (int j = X_SIZE_START; j < X_SIZE_END; j++) {
                t_values[i][j] = tab[i][j];
            }
        }
    }

    public void addWateringIteration(int[][] tab) {
        for (int i = Y_SIZE_START; i < Y_SIZE_END; i++) {
            for (int j = X_SIZE_START; j < X_SIZE_END; j++) {
                tab[i][j] += t_values[i][j];
            }
        }
    }
}
