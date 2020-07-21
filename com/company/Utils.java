package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Utils {
    public void writePositionsToFile(String name, List<Sprinkler> sList, int n) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(name);
        file.println("Position x:  Position y:  Angle:  Rotation angle:");
        for (int i = 0; i < n; i++) {
            file.println("   " + (sList.get(i)).getX_position() + "          " + (sList.get(i)).getY_position() + "        " + (sList.get(i)).getAngle() + "          " + (sList.get(i)).getRotation_angle());
        }
        file.close();
    }
}
