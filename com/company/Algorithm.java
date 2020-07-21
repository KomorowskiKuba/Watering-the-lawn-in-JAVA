package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Algorithm extends Global {

    int[][] entryArray = new int[49][89];
    private int x, y;

    Algorithm(int[][] small_array, int[][] t, File f) throws FileNotFoundException {
        fill_borders(small_array, t);
        fileReader(f);
        fill(t);
        fill_interior(t);
    }

    private void fill_borders(int[][] small_array, int[][] t) {
        int[] counter = new int[10];
        int condition = 0;
        int i, j, k;
        for (i = 0; i < 40; i++) {
            for (j = 0; j < 80; j++) {
                if (small_array[i][j] == -9) {
                    if (i > 1 && small_array[i - 1][j] == 0 && small_array[i - 2][j] == 0)
                        counter[0] += 1;
                    else
                        counter[0] = 0;
                    if (i < 38 && small_array[i + 1][j] == 0 && small_array[i + 2][j] == 0)
                        counter[2] += 1;
                    else
                        counter[2] = 0;
                }
                if (counter[0] == 8) {
                    for (k = 0; (i - 1 - k >= 0 && j - 8 >= 0 && small_array[i - 1 - k][j - 8] < 0 && small_array[i - 1 - k][j - 7] >= 0); k++) {
                        if (((i - k >= 0 && j - 8 >= 0 && j + 1 < 80) && (small_array[i - k][j - 8] < 0 || small_array[i - k][j + 1] < 0)) || ((i + k < 40 && j - 8 >= 0 && j + 1 < 80) && (small_array[i + k][j - 8] < 0 || small_array[i + k][j + 1] < 0)))
                            condition++;
                    }
                    if (condition >= 5)
                        counter[0] = 5;
                    condition = 0;
                    if (counter[0] == 8) {
                        if (j - 3 > 0) {
                            x = j - 3;
                            y = i;
                            Sprinkler s = new Sprinkler(t, x * 100, y * 100 - 1, 180, 0);
                            sprinklerList.add(s);
                        }
                        x = 0;
                        y = 0;
                        counter[0] = 0;
                    }
                }
                if (counter[2] == 8) {
                    for (k = 0; (i + 1 + k < 40 && j - 8 >= 0 && small_array[i + 1 + k][j - 8] < 0 && small_array[i + 1 + k][j - 7] >= 0); k++) {
                        if (((i > 4 && i - k >= 0 && j - 8 >= 0 && j + 1 < 80) && (small_array[i - k][j - 8] < 0 || small_array[i - k][j + 1] < 0)) || ((i < 37 && i + k < 40 && j - 8 >= 0 && j + 1 < 80) && (small_array[i + k][j - 8] < 0 || small_array[i + k][j + 1] < 0)))
                            condition++;
                    }
                    if (condition >= 5)
                        counter[2] = 5;
                    condition = 0;
                    if (counter[2] == 8) {
                        if (j - 3 > 0 && i + 1 < 40) {
                            x = j - 3;
                            y = i + 1;
                            Sprinkler s = new Sprinkler(t, x * 100, y * 100, 180, 180);
                            sprinklerList.add(s);
                        }
                        x = 0;
                        y = 0;
                        counter[2] = 0;
                    }
                }
            }
        }

        for (i = 0; i < 80; i++) {
            for (j = 0; j < 40; j++) {
                if (small_array[j][i] == -9) {
                    if (i - 1 >= 0 && i - 2 >= 0 && small_array[j][i - 1] == 0 && small_array[j][i - 2] == 0)
                        counter[1] += 1;
                    else
                        counter[1] = 0;

                    if (i < 77 && small_array[j][i + 1] == 0 && small_array[j][i + 2] == 0)
                        counter[3] += 1;
                    else
                        counter[3] = 0;
                }
                if (counter[1] == 8) {
                    for (k = 0; (j - 8 >= 0 && i - 1 - k >= 0 && small_array[j - 8][i - 1 - k] < 0 && small_array[j - 7][i - 1 - k] >= 0); k++) {
                        if (((j - 8 >= 0 && i - k >= 0 && j + 8 < 40) && (small_array[j - 8][i - k] < 0 || small_array[j + 8][i - k] < 0)) || ((j - 8 >= 0 && i + k < 80 && j + 8 < 40) && (small_array[j - 8][i + k] < 0 || small_array[j + 8][i + k] < 0)))
                            condition++;
                    }
                    if (condition >= 5)
                        counter[1] = 5;
                    condition = 0;
                    if (counter[1] == 8) {
                        if (j - 3 >= 0) {
                            x = i;
                            y = j - 3;
                            Sprinkler s = new Sprinkler(t, x * 100 - 1, y * 100, 180, 90);
                            sprinklerList.add(s);
                        }
                        x = 0;
                        y = 0;
                        counter[1] = 0;
                    }
                }
                if (counter[3] == 8) {
                    for (k = 0; (small_array[j - 8][i + 1 + k] < 0 && small_array[j - 7][i + 1 + k] >= 0 && j - 8 >= 0 && i + 1 + k < 80); k++) {
                        if (((j - 8 >= 0 && i - k >= 0 && j + 8 < 40) && (small_array[j - 8][i - k] < 0 || small_array[j + 8][i - k] < 0)) || ((j - 8 >= 0 && i + k < 80 && j + 8 < 40) && (small_array[j - 8][i + k] < 0 || small_array[j + 8][i + k] < 0)))
                            condition++;
                    }
                    if (condition >= 5)
                        counter[3] = 4;
                    condition = 0;
                    if (counter[3] == 8) {
                        if (i + 1 < 80 && j - 3 > 0) {
                            x = i + 1;
                            y = j - 3;
                            Sprinkler s = new Sprinkler(t, x * 100, y * 100, 180, 270);
                            sprinklerList.add(s);
                        }
                        x = 0;
                        y = 0;
                        counter[3] = 0;
                    }
                }
            }
        }
    }

    private void fill_interior(int[][] array) {
        for (int i = X_SIZE_START; i < X_SIZE_END; i += 100) //horizontally
        {
            for (int j = Y_SIZE_START; j < Y_SIZE_END; j += 50) //vertically
            {
                double test = check_condition(array, i, j);
                if (test >= 0.5) {
                    Sprinkler s = new Sprinkler(array, i - X_SIZE_START, j - Y_SIZE_START, 360, 0);
                    sprinklerList.add(s);
                }
            }
        }
    }

    private double check_condition(int[][] array, int center_x, int center_y) {
        double checked_fields = 0.0;
        double unwatered_fields = 0.0;
        double factor = 0.0;

        for (int i = 0; i < 2 * 200; i++) {
            x = center_x - 200 + i;
            for (int j = 0; j < 2 * 200; j++) {
                y = center_y - 200 + j;
                if ((i - 200) * (i - 200) + (j - 200) * (j - 200) <= 200 * 200 + TOLERANCE) {
                    if (array[y][x] == 0) {
                        unwatered_fields += 1.5;
                        checked_fields++;
                    } else if (array[y][x] > 0) {
                        checked_fields++;
                    } else if (array[y][x] < 0) {
                        checked_fields++;
                    }
                    factor = unwatered_fields / checked_fields;
                }
            }
        }
        return factor;
    }

    private void fileReader(File f) throws FileNotFoundException {
        Scanner sc = new Scanner(f);
        for (int i = 0; i < 40; i++) {
            String temp = sc.next();
            for (int j = 0; j < 80; j++) {
                if (temp.charAt(j) == '*') {
                    entryArray[i][j] = 1;
                } else {
                    entryArray[i][j] = 0;
                }
            }
        }
    }

    private void fill(int[][] t) {
        int c, k, l;
        for (int i = 1; i < 39; i++) {
            for (int j = 1; j < 79; j++) {
                if (entryArray[i][j] == 0) {
                    if (entryArray[i - 1][j] == 1 && entryArray[i][j - 1] == 1) {
                        c = 1;
                        for (k = 1; k <= 4; k++)
                            if (entryArray[i + k][j] == 1 || entryArray[i][j + k] == 1)
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * j + 1, 100 * i + 1, 90, 270);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i - 1][j] == 1 && entryArray[i][j + 1] == 1) {
                        c = 1;
                        for (k = 1; k <= 4; k++)
                            for (l = 1; l <= 9; l++)
                                if (j - l >= 0 && (entryArray[i + k][j] == 1 || entryArray[i][j - l] == 1))
                                    c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * (j + 1) - 1, 100 * i + 1, 90, 180);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i + 1][j] == 1 && entryArray[i][j - 1] == 1) {
                        c = 1;
                        for (k = 1; k <= 4; k++)
                            for (l = 1; l <= 9; l++)
                                if (i - l >= 0 && (entryArray[i - l][j] == 1 || entryArray[i][j + k] == 1))
                                    c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * j + 1, 100 * (i + 1) - 1, 90, 0);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i + 1][j] == 1 && entryArray[i][j + 1] == 1) {
                        c = 1;
                        for (k = 1; k <= 9; k++)
                            if ((i - k >= 0 && j - k >= 0) && (entryArray[i - k][j] == 1 || entryArray[i][j - k] == 1))
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * (j + 1) - 1, 100 * (i + 1) - 1, 90, 90);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i + 1][j + 1] == 1 && entryArray[i + 1][j] == 0 && entryArray[i][j + 1] == 0) {
                        c = 1;
                        for (k = 1; k <= 7; k++)
                            if (entryArray[i + k][j] == 1 || entryArray[i][j + k] == 1)
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * (j + 1) - 1, 100 * (i + 1) - 1, 270, 270);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i + 1][j - 1] == 1 && entryArray[i + 1][j] == 0 && entryArray[i][j - 1] == 0) {
                        c = 1;
                        for (k = 1; k <= 7; k++)
                            if (j - k >= 0 && (entryArray[i + k][j] == 1 || entryArray[i][j - k] == 1))
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * j + 1, 100 * (i + 1) - 1, 270, 180);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i - 1][j + 1] == 1 && entryArray[i][j + 1] == 0 && entryArray[i - 1][j] == 0) {
                        c = 1;
                        for (k = 1; k <= 7; k++)
                            if (i - k >= 0 && (entryArray[i - k][j] == 1 || entryArray[i][j + k] == 1))
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * (j + 1) - 1, 100 * i + 1, 270, 0);
                            sprinklerList.add(s);
                        }
                    }
                    if (entryArray[i - 1][j - 1] == 1 && entryArray[i][j - 1] == 0 && entryArray[i - 1][j] == 0) {
                        c = 1;
                        for (k = 1; k <= 7; k++)
                            if ((i - k >= 0 && j - k >= 0) && (entryArray[i - k][j] == 1 || entryArray[i][j - k] == 1))
                                c = 0;
                        if (c == 1) {
                            Sprinkler s = new Sprinkler(t, 100 * j + 1, 100 * i + 1, 270, 0);
                            sprinklerList.add(s);
                        }
                    }
                }
            }
        }
    }
}
