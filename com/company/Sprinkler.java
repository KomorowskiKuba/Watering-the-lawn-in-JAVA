package com.company;

public class Sprinkler extends Global {

    private final int x_position;
    private final int y_position;
    private final int rotation_angle;
    private final int angle;
    private final int[] x_reflection = new int[100];
    private final int[] y_reflection = new int[100];
    private int radius;
    private int increment;
    private int x, y;
    private int amount_of_reflections = 0;
    private int left_distance = 0;
    private int right_distance = 0;
    private int up_distance = 0;
    private int down_distance = 0;
    private int x_loop_start = 0;
    private int x_loop_end = 0;
    private int y_loop_start = 0;
    private int y_loop_end = 0;

    Sprinkler(int[][] tab, int x, int y, int angle, int rotation_angle) {
        this.x_position = x + X_SIZE_START;
        this.y_position = y + Y_SIZE_START;
        this.angle = angle;
        this.rotation_angle = rotation_angle;

        if (angle == 90) {
            draw90(tab);
            if (reflections_enabled == true) {
                reflect90(tab);
            }
        } else if (angle == 180) {
            draw180(tab);
            if (reflections_enabled == true) {
                reflect180(tab);
            }
        } else if (angle == 270) {
            draw270(tab);
            if (reflections_enabled == true) {
                reflect270(tab);
            }
        } else if (angle == 360) {
            draw360(tab);
            if (reflections_enabled == true) {
                if(tab[y][x] >= 0)
                    reflect360(tab);
            }
        }
        amount_of_sprinklers++;
    }

    private void draw90(int[][] tab) {
        radius = 500;
        increment = 4;

        x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
        x_loop_end = x_position + radius + TOLERANCE;
        y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
        y_loop_end = y_position + radius + TOLERANCE;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        if (rotation_angle == 0 || rotation_angle == 270) {
            for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
            {
                if (tab[y_position][i] < 0) {
                    x_loop_end = i;
                    break;
                }
            }
        }

        if (rotation_angle == 90 || rotation_angle == 180) {
            for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
            {
                if (tab[y_position][i] < 0) {
                    x_loop_start = i;
                    break;
                }
            }
        }

        if (rotation_angle == 180 || rotation_angle == 270) {
            for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
            {
                if (tab[i][x_position] < 0) {
                    y_loop_end = i;
                    break;
                }
            }
        }
        if (rotation_angle == 0 || rotation_angle == 90) {
            for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
            {
                if (tab[i][x_position] < 0) {
                    y_loop_start = i;
                    break;
                }
            }
        }

        for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
            x = y_position - i;
            for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                y = x_position - j;

                if ((rotation_angle >= 0 && rotation_angle <= 90) || (rotation_angle > 180 && rotation_angle <= 270)) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && y <= a1 * x && y >= a2 * x) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;


                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                } else if (rotation_angle > 90 && rotation_angle <= 180) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && y >= a1 * x && y >= a2 * x) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                } else if (rotation_angle > 270 && rotation_angle < 360) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && y <= a1 * x && y <= a2 * x) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                }
            }
        }
    }

    private void draw180(int[][] tab) {
        radius = 400;
        increment = 3;

        x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
        x_loop_end = x_position + radius + TOLERANCE;
        y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
        y_loop_end = y_position + radius + TOLERANCE;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle == 0 || rotation_angle == 180 || rotation_angle == 270) {
            for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
            {
                if (tab[y_position][i] < 0) {
                    x_loop_end = i;
                    break;
                }
            }
        }

        if (rotation_angle == 0 || rotation_angle == 90 || rotation_angle == 180) {
            for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
            {
                if (tab[y_position][i] < 0) {
                    x_loop_start = i;
                    break;
                }
            }
        }

        if (rotation_angle == 180 || rotation_angle == 90 || rotation_angle == 270) {
            for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
            {
                if (tab[i][x_position] < 0) {
                    y_loop_end = i;
                    break;
                }
            }
        }
        if (rotation_angle == 0 || rotation_angle == 90 || rotation_angle == 270) {
            for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
            {
                if (tab[i][x_position] < 0) {
                    y_loop_start = i;
                    break;
                }
            }
        }

        for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
            x = y_position - i;
            for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                y = x_position - j;

                if ((rotation_angle <= 90 && rotation_angle >= 0) || (rotation_angle > 90 && rotation_angle <= 180)) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a2 * x)) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                } else if ((rotation_angle >= 270 && rotation_angle < 360) || (rotation_angle > 180 && rotation_angle < 270)) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && (y <= a2 * x)) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                }
            }
        }
    }

    private void draw270(int[][] tab) {
        radius = 300;
        increment = 2;

        x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
        x_loop_end = x_position + radius + TOLERANCE;
        y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
        y_loop_end = y_position + radius + TOLERANCE;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
        {
            if (tab[y_position][i] < 0) {
                x_loop_end = i;
                break;
            }
        }

        for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
        {
            if (tab[y_position][i] < 0) {
                x_loop_start = i;
                break;
            }
        }

        for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
        {
            if (tab[i][x_position] < 0) {
                y_loop_end = i;
                break;
            }
        }
        for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
        {
            if (tab[i][x_position] < 0) {
                y_loop_start = i;
                break;
            }
        }

        for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
            x = y_position - i;
            for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                y = x_position - j;

                if ((rotation_angle >= 0 && rotation_angle <= 90) || (rotation_angle > 180 && rotation_angle <= 270)) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a1 * x || y <= a2 * x)) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                } else if (rotation_angle > 90 && rotation_angle <= 180) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && (y <= a1 * x || y <= a2 * x)) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                } else if (rotation_angle > 270 && rotation_angle < 360) {
                    if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a1 * x || y >= a2 * x)) {
                        if (tab[i][j] >= 0)
                            tab[i][j] += increment;

                        if ((tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                                (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                            x_reflection[amount_of_reflections] = j;
                            y_reflection[amount_of_reflections++] = i;
                        }
                    }
                }
            }
        }
    }

    private void draw360(int[][] tab) {
        radius = 200;
        increment = 1;

        x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
        x_loop_end = x_position + radius + TOLERANCE;
        y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
        y_loop_end = y_position + radius + TOLERANCE;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
        {
            if (tab[y_position][i] < 0) {
                x_loop_end = i;
                break;
            }
        }

        for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
        {
            if (tab[y_position][i] < 0) {
                x_loop_start = i;
                break;
            }
        }

        for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
        {
            if (tab[i][x_position] < 0) {
                y_loop_end = i;
                break;
            }
        }
        for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
        {
            if (tab[i][x_position] < 0) {
                y_loop_start = i;
                break;
            }
        }

        for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
            x = y_position - i;
            for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                y = x_position - j;
                if (x * x + y * y <= radius * radius + TOLERANCE) {
                    if (tab[i][j] >= 0)
                        tab[i][j] += increment;

                    if ((tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                            (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] >= 0) ||
                            (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                            (tab[i][j] >= 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||

                            (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] >= 0) ||
                            (tab[i][j] < 0 && tab[i][j + 1] < 0 && tab[i + 1][j] >= 0 && tab[i + 1][j + 1] < 0) ||
                            (tab[i][j] < 0 && tab[i][j + 1] >= 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0) ||
                            (tab[i][j] >= 0 && tab[i][j + 1] < 0 && tab[i + 1][j] < 0 && tab[i + 1][j + 1] < 0)) {
                        x_reflection[amount_of_reflections] = j;
                        y_reflection[amount_of_reflections++] = i;
                    }
                }
            }
        }
    }

    private void reflect90(int[][] tab) {

        radius = 500;
        increment = 4;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        if (rotation_angle == 0 || rotation_angle == 270) {
            for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
            {
                if (tab[y_position][i] < 0) {
                    x_reflection[amount_of_reflections] = i;
                    y_reflection[amount_of_reflections++] = y_position;
                    break;
                }
            }
        }

        if (rotation_angle == 90 || rotation_angle == 180) {
            for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
            {
                if (tab[y_position][i] < 0) {
                    x_reflection[amount_of_reflections] = i;
                    y_reflection[amount_of_reflections++] = y_position;
                    break;
                }
            }
        }

        if (rotation_angle == 180 || rotation_angle == 270) {
            for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
            {
                if (tab[i][x_position] < 0) {
                    x_reflection[amount_of_reflections] = x_position;
                    y_reflection[amount_of_reflections++] = i;
                    break;
                }
            }
        }
        if (rotation_angle == 0 || rotation_angle == 90) {
            for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
            {
                if (tab[i][x_position] < 0) {
                    x_reflection[amount_of_reflections] = x_position;
                    y_reflection[amount_of_reflections++] = i;
                    break;
                }
            }
        }

        for (int p = 0; p < amount_of_reflections; p++) {
            x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
            x_loop_end = x_position + radius + TOLERANCE;
            y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
            y_loop_end = y_position + radius + TOLERANCE;

            left_distance = 0;
            right_distance = 0;
            up_distance = 0;
            down_distance = 0;

            if (x_reflection[p] > x_position) {
                left_distance = 2 * Math.abs(x_position - x_reflection[p]);
                x_loop_start = x_reflection[p];
            } else if (x_reflection[p] < x_position) {
                right_distance = 2 * -Math.abs(x_position - x_reflection[p]);
                x_loop_end = x_reflection[p];
            }
            if (y_reflection[p] > y_position) {
                down_distance = 2 * Math.abs(y_position - y_reflection[p]);
                y_loop_start = y_reflection[p];
            } else if (y_reflection[p] < y_position) {
                up_distance = 2 * -Math.abs(y_position - y_reflection[p]);
                y_loop_end = y_reflection[p];
            }

            int x_multi = 0, y_multi = 0;
            if (down_distance != 0 || up_distance != 0)
                y_multi = 1;
            if (right_distance != 0 || left_distance != 0)
                x_multi = 1;

            for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
                x = y_position - i;
                for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                    y = x_position - j;

                    if ((rotation_angle >= 0 && rotation_angle <= 90) || (rotation_angle > 180 && rotation_angle <= 270)) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && y <= a1 * x && y >= a2 * x/* && tab[i][j] < -100*/) {// wazna zmiana!!!!
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    } else if (rotation_angle > 90 && rotation_angle <= 180) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && y >= a1 * x && y >= a2 * x) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    } else if (rotation_angle > 270 && rotation_angle < 360) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && y <= a1 * x && y <= a2 * x) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    }
                }
            }
        }
    }

    private void reflect180(int[][] tab) {
        radius = 400;
        increment = 3;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle == 0 || rotation_angle == 180 || rotation_angle == 270) {
            for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
            {
                if (tab[y_position][i] < 0) {
                    x_reflection[amount_of_reflections] = i;
                    y_reflection[amount_of_reflections++] = y_position;
                    break;
                }
            }
        }

        if (rotation_angle == 0 || rotation_angle == 90 || rotation_angle == 180) {
            for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
            {
                if (tab[y_position][i] < 0) {
                    x_reflection[amount_of_reflections] = i;
                    y_reflection[amount_of_reflections++] = y_position;
                    break;
                }
            }
        }

        if (rotation_angle == 180 || rotation_angle == 90 || rotation_angle == 270) {
            for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
            {
                if (tab[i][x_position] < 0) {
                    x_reflection[amount_of_reflections] = x_position;
                    y_reflection[amount_of_reflections++] = i;
                    break;
                }
            }
        }
        if (rotation_angle == 0 || rotation_angle == 90 || rotation_angle == 270) {
            for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
            {
                if (tab[i][x_position] < 0) {
                    x_reflection[amount_of_reflections] = x_position;
                    y_reflection[amount_of_reflections++] = i;
                    break;
                }
            }
        }

        for (int p = 0; p < amount_of_reflections; p++) {
            x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
            x_loop_end = x_position + radius + TOLERANCE;
            y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
            y_loop_end = y_position + radius + TOLERANCE;

            left_distance = 0;
            right_distance = 0;
            up_distance = 0;
            down_distance = 0;

            if (x_reflection[p] > x_position) {
                left_distance = 2 * Math.abs(x_position - x_reflection[p]);
                x_loop_start = x_reflection[p];
            } else if (x_reflection[p] < x_position) {
                right_distance = 2 * -Math.abs(x_position - x_reflection[p]);
                x_loop_end = x_reflection[p];
            }
            if (y_reflection[p] > y_position) {
                down_distance = 2 * Math.abs(y_position - y_reflection[p]);
                y_loop_start = y_reflection[p];
            } else if (y_reflection[p] < y_position) {
                up_distance = 2 * -Math.abs(y_position - y_reflection[p]);
                y_loop_end = y_reflection[p];
            }

            int x_multi = 0, y_multi = 0;
            if (down_distance != 0 || up_distance != 0)
                y_multi = 1;
            if (right_distance != 0 || left_distance != 0)
                x_multi = 1;


            for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
                x = y_position - i;
                for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                    y = x_position - j;

                    if ((rotation_angle <= 90 && rotation_angle >= 0) || (rotation_angle > 90 && rotation_angle <= 180)) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a2 * x)) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    } else if ((rotation_angle >= 270 && rotation_angle < 360) || (rotation_angle > 180 && rotation_angle < 270)) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && (y <= a2 * x)) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    }
                }
            }
        }
    }

    private void reflect270(int[][] tab) {

        radius = 300;
        increment = 2;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
        {
            if (tab[y_position][i] < 0) {
                x_reflection[amount_of_reflections] = i;
                y_reflection[amount_of_reflections++] = y_position;
                break;
            }
        }

        for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
        {
            if (tab[y_position][i] < 0) {
                x_reflection[amount_of_reflections] = i;
                y_reflection[amount_of_reflections++] = y_position;
                break;
            }
        }

        for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
        {
            if (tab[i][x_position] < 0) {
                x_reflection[amount_of_reflections] = x_position;
                y_reflection[amount_of_reflections++] = i;
                break;
            }
        }
        for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
        {
            if (tab[i][x_position] < 0) {
                x_reflection[amount_of_reflections] = x_position;
                y_reflection[amount_of_reflections++] = i;
                break;
            }
        }

        for (int p = 0; p < amount_of_reflections; p++) {

            x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
            x_loop_end = x_position + radius + TOLERANCE;
            y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
            y_loop_end = y_position + radius + TOLERANCE;

            left_distance = 0;
            right_distance = 0;
            up_distance = 0;
            down_distance = 0;

            if (x_reflection[p] > x_position) {
                left_distance = 2 * Math.abs(x_position - x_reflection[p]);
                x_loop_start = x_reflection[p];
            } else if (x_reflection[p] < x_position) {
                right_distance = 2 * -Math.abs(x_position - x_reflection[p]);
                x_loop_end = x_reflection[p];
            }
            if (y_reflection[p] > y_position) {
                down_distance = 2 * Math.abs(y_position - y_reflection[p]);
                y_loop_start = y_reflection[p];
            } else if (y_reflection[p] < y_position) {
                up_distance = 2 * -Math.abs(y_position - y_reflection[p]);
                y_loop_end = y_reflection[p];
            }


            int x_multi = 0, y_multi = 0;
            if (down_distance != 0 || up_distance != 0)
                y_multi = 1;
            if (right_distance != 0 || left_distance != 0)
                x_multi = 1;

            for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
                x = y_position - i;
                for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                    y = x_position - j;

                    if ((rotation_angle >= 0 && rotation_angle <= 90) || (rotation_angle > 180 && rotation_angle <= 270)) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a1 * x || y <= a2 * x)) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    } else if (rotation_angle > 90 && rotation_angle <= 180) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && (y <= a1 * x || y <= a2 * x)) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    } else if (rotation_angle > 270 && rotation_angle < 360) {
                        if ((x * x + y * y <= radius * radius + TOLERANCE) && (y >= a1 * x || y >= a2 * x)) {
                            if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                            } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                                tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            } else {
                                tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                            }
                        }
                    }
                }
            }
        }
    }

    private void reflect360(int[][] tab) {
        radius = 200;
        increment = 1;

        double a1 = Math.tan((double) rotation_angle * Math.PI / 180), a2 = -1 / a1;

        if (rotation_angle > 90 && rotation_angle <= 180) {
            a1 = -Math.tan(((180 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        } else if (rotation_angle > 180 && rotation_angle <= 270) {
            a1 = -Math.tan(((90 - rotation_angle) * Math.PI) / 180);
            a2 = -1 / a1;
            if (a1 == 0.0)
                a2 = 1000000000;
        }

        for (int i = x_position; (i <= x_position + radius + TOLERANCE) && (i < X_SIZE_END); i++) // prawo
        {
            if (tab[y_position][i] < 0) {
                x_reflection[amount_of_reflections] = i;
                y_reflection[amount_of_reflections++] = y_position;
                break;
            }
        }

        for (int i = x_position; (i >= x_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // lewo
        {
            if (tab[y_position][i] < 0) {
                x_reflection[amount_of_reflections] = i;
                y_reflection[amount_of_reflections++] = y_position;
                break;
            }
        }

        for (int i = y_position; (i <= y_position + radius + TOLERANCE) && (i < Y_SIZE_END); i++) // dol
        {
            if (tab[i][x_position] < 0) {
                x_reflection[amount_of_reflections] = x_position;
                y_reflection[amount_of_reflections++] = i;
                break;
            }
        }
        for (int i = y_position; (i >= y_position - radius - TOLERANCE) && (i > Y_SIZE_START); i--) // gora
        {
            if (tab[i][x_position] < 0) {
                x_reflection[amount_of_reflections] = x_position;
                y_reflection[amount_of_reflections++] = i;
                break;
            }
        }

        for (int p = 0; p < amount_of_reflections; p++) {
            x_loop_start = (x_position - radius - TOLERANCE >= X_SIZE_START) ? x_position - radius - TOLERANCE : X_SIZE_START;
            x_loop_end = x_position + radius + TOLERANCE;
            y_loop_start = (y_position - radius - TOLERANCE >= Y_SIZE_START) ? y_position - radius - TOLERANCE : Y_SIZE_START;
            y_loop_end = y_position + radius + TOLERANCE;

            left_distance = 0;
            right_distance = 0;
            up_distance = 0;
            down_distance = 0;

            if (x_reflection[p] > x_position) {
                left_distance = 2 * Math.abs(x_position - x_reflection[p]);
                x_loop_start = x_reflection[p];
            } else if (x_reflection[p] < x_position) {
                right_distance = 2 * -Math.abs(x_position - x_reflection[p]);
                x_loop_end = x_reflection[p];
            }
            if (y_reflection[p] > y_position) {
                down_distance = 2 * Math.abs(y_position - y_reflection[p]);
                y_loop_start = y_reflection[p];
            } else if (y_reflection[p] < y_position) {
                up_distance = 2 * -Math.abs(y_position - y_reflection[p]);
                y_loop_end = y_reflection[p];
            }

            int x_multi = 0, y_multi = 0;
            if (down_distance != 0 || up_distance != 0)
                y_multi = 1;
            if (right_distance != 0 || left_distance != 0)
                x_multi = 1;

            for (int i = y_loop_start; (i <= y_loop_end) && (i < Y_SIZE_END); i++) {
                x = y_position - i;
                for (int j = x_loop_start; (j <= x_loop_end) && (j < X_SIZE_END); j++) {
                    y = x_position - j;
                    if (x * x + y * y <= radius * radius + TOLERANCE) {
                        if (j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance) < 0) {
                            tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j] += increment;
                        } else if (i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance) < 0) {
                            tab[i][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                        } else {
                            tab[i + y_multi * (-(y_position - i) * -2 + up_distance + down_distance)][j + x_multi * (-(x_position - j) * -2 + right_distance + left_distance)] += increment;
                        }
                    }
                }
            }
        }
    }


    public int getX_position() {
        return x_position;
    }

    public int getY_position() {
        return y_position;
    }

    public int getAngle() {
        return angle;
    }

    public int getRotation_angle() {
        return rotation_angle;
    }
}
