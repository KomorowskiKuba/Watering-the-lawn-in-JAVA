package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static com.company.Global.*;

public class Main extends Application {

    public static Controller controller;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static void runAlgorithm(String fileName, int numberOfSeries, int seriesLength, String jpgName, String out_name1, boolean waterReflection) throws Exception {
        int[][] t = new int[Y_REAL_SIZE][X_REAL_SIZE];
        Graphics map_graphic = new Graphics();
        Utils out = new Utils();

        for (int i = 0; i < Y_REAL_SIZE; i++) {
            for (int j = 0; j < X_REAL_SIZE; j++) {
                t[i][j] = 0;
            }
        }

        File f = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            f = new File(System.getProperty("user.dir") + "\\" + fileName);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e1) {
            System.out.println("BRAK PLIKU");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad pliku!");
            alert.setHeaderText("Blad pliku!");
            alert.setContentText("Podany plik nie mogl zostac wczytany!");
            alert.showAndWait();
        }

        int[][] small_array = new int[40][81];

        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 81; y++) {
                int c = 0;
                c = br.read();
                int k, l;

                if (c == '*') {
                    small_array[x][y] = -9;
                    for (k = 100 * x + X_SIZE_START; k < 100 * (x + 1) + X_SIZE_START; k++) {
                        for (l = 100 * y + Y_SIZE_START; l < 100 * (y + 1) + Y_SIZE_START; l++) {
                            t[k][l] = -999;
                        }
                    }
                } else if (c == '#') {
                    small_array[x][y] = 0;
                    for (k = 100 * x + X_SIZE_START; k < 100 * (x + 1) + X_SIZE_START; k++) {
                        for (l = 100 * y + Y_SIZE_START; l < 100 * (y + 1) + Y_SIZE_START; l++) {
                            t[k][l] = 0;
                        }
                    }
                } else if (c == '\n') {
                }
            }
        }

        boolean saveEnabled = true;
        if(jpgName.length() == 0)
            saveEnabled = false;

        reflections_enabled = waterReflection;
        Algorithm a = new Algorithm(small_array, t, f);

        map_graphic.takeValues(t);
        map_graphic.Generate_jpg(t, "1", false);
        for (int i = 2; i < numberOfSeries; i++) {
            map_graphic.addWateringIteration(t);
            map_graphic.Generate_jpg(t, Integer.toString(i), false);
        }
        map_graphic.addWateringIteration(t);
        map_graphic.Generate_jpg(t, jpgName, saveEnabled);

        if(out_name1.length() > 0)
            out.writePositionsToFile(out_name1 + ".txt", sprinklerList, amount_of_sprinklers);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("window.fxml").toURI().toURL());
        primaryStage.setTitle("Trawnik");
        primaryStage.getIcons().add(new Image("Ikonka.png"));
        Scene MainScene = new Scene(root, 1125, 450 );
        primaryStage.setResizable(false);
        MainScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(MainScene);
        primaryStage.show();
    }
}