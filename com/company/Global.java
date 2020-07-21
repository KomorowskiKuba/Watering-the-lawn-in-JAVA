package com.company;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Global {
    protected static final int X_SIZE_START = 500;
    protected static final int Y_SIZE_START = 500;
    protected static final int X_SIZE_END = 8500;
    protected static final int Y_SIZE_END = 4500;
    protected static final int X_REAL_SIZE = 9000;
    protected static final int Y_REAL_SIZE = 5000;
    public static int amount_of_sprinklers = 0;
    protected static boolean reflections_enabled = true;
    static List<Sprinkler> sprinklerList = new ArrayList<>();
    public int TOLERANCE = 7;
    protected List<Image> img_list = new ArrayList<>();
}
