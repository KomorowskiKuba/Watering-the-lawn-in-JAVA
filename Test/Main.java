package Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random r = new Random();
        char[][] tab = new char[40][80];
        int amount_of_shapes = 13;
        int max_shape_size = 12;
        String file_name = "Map5.txt";
        PrintWriter out = null;

        try {
            out = new PrintWriter(file_name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 80; i++)
            tab[0][i] = '*';

        for (int i = 1; i < 39; i++) {
            tab[i][0] = '*';
            for (int j = 1; j < 79; j++) {
                tab[i][j] = '#';
            }
            tab[i][79] = '*';
        }

        for (int i = 0; i < 80; i++)
            tab[39][i] = '*';

        for (int i = 0; i < amount_of_shapes; i++) {
            boolean type = r.nextBoolean();
            if (type == false) // square
            {
                int size = r.nextInt(5) + 1;
                int x = r.nextInt(78) + 1;
                int y = r.nextInt(38) + 1;
                for (int j = y; j < (y + size) && j < 39; j++) {
                    for (int k = x; k < (x + size) && k < 79; k++)
                        tab[j][k] = '*';
                }
            } else // rectangle
            {
                int x_size = r.nextInt(max_shape_size) + 1;
                int y_size = r.nextInt(max_shape_size) + 1;
                int x = r.nextInt(78) + 1;
                int y = r.nextInt(38) + 1;
                for (int j = y; j < (y + y_size) && j < 39; j++) {
                    for (int k = x; k < (x + x_size) && k < 79; k++)
                        tab[j][k] = '*';
                }
            }
        }

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 80; j++) {
                out.print(tab[i][j]);
            }
            out.print("\n");
        }
        out.close();
    }
}
