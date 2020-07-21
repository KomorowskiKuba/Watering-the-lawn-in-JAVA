package com.company;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Controller {

    private static final List<ImageView> imageViewList = new ArrayList<ImageView>();
    private int oldAmountOfSeries = 0;
    private int oldDuration = 0;
    @FXML
    private ImageView gif;

    @FXML
    private TextField dataFile;

    @FXML
    private TextField numberOfSeries;

    @FXML
    private TextField seriesLength;

    @FXML
    private TextField imageFile;

    @FXML
    private TextField coordinatesFile;

    @FXML
    private CheckBox waterReflection;

    @FXML
    private Button button;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    public static void ImageViewListAdd(ImageView imageView) {
        imageViewList.add(imageView);
    }

    public void handleButtonClick() throws Exception {    //obsługa kliknięcia przycisku

        if (imageViewList.isEmpty() == false) {
            imageViewList.clear();
            System.gc();
        }

        try {
            if (Integer.parseInt(numberOfSeries.getText()) > 0) {
                int error1 = Integer.parseInt(numberOfSeries.getText());
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Blad ilosci serii podlan!");
                alert1.setHeaderText("Blad ilosci serii podlan!");
                alert1.setContentText("Podana ilosc serii jest zbyt mala!");
                numberOfSeries.setText("5");
                alert1.showAndWait();
                throw new Exception();
            }
        } catch (NumberFormatException e1) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Blad ilosci serii podlan!");
            alert2.setHeaderText("Blad ilosci serii podlan!");
            alert2.setContentText("Podana ilosc serii podlan nie jest liczba!");
            alert2.showAndWait();
        }

        try {
            if (Integer.parseInt(seriesLength.getText()) > 0) {
                int error2 = Integer.parseInt(seriesLength.getText());
            } else {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Blad dlugosci serii podlan!");
                alert3.setHeaderText("Blad dlugosci serii podlan!");
                alert3.setContentText("Podana dlugosc serii jest zbyt mala!");
                alert3.showAndWait();
                seriesLength.setText("1000");
                throw new Exception();
            }
        } catch (NumberFormatException e2) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Blad dlugosci serii podlan!");
            alert4.setHeaderText("Blad dlugosci serii podlan!");
            alert4.setContentText("Podana dlugosci serii podlan nie jest liczba!");
            alert4.showAndWait();
        }

        try {
            Main.runAlgorithm(dataFile.getText(), Integer.parseInt(numberOfSeries.getText()), Integer.parseInt(seriesLength.getText()), imageFile.getText(), coordinatesFile.getText(), waterReflection.isSelected());
            oldAmountOfSeries = Integer.parseInt(numberOfSeries.getText());
            oldDuration = Integer.parseInt(seriesLength.getText());
            show(Integer.parseInt(numberOfSeries.getText()), Integer.parseInt(seriesLength.getText()));
        } catch (OutOfMemoryError e_mem) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Koniec pamieci!");
            alert4.setHeaderText("Koniec pamieci!");
            alert4.setContentText("Program zostanie zamkniety!");
            alert4.showAndWait();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

    public void initialize() {  //funkcja wykonywana przed uruchomieniem fxml
        dataFile.setText("dane1.txt");
        numberOfSeries.setText("5");
        seriesLength.setText("1000");
        imageFile.setText("Obraz");
        coordinatesFile.setText("Pozycje_zraszaczy");
        waterReflection.setSelected(false);
    }

    public void show(int amountofseries, int duration) {
        Image[] images = new Image[amountofseries];
        for (int i = 1; i <= amountofseries; i++) {
            images[i - 1] = (imageViewList.get(i - 1)).getImage();
        }
        Timeline timeLine = new Timeline();
        timeLine.pause();
        Collection<KeyFrame> frames = timeLine.getKeyFrames();
        Duration frameGap = Duration.millis(duration);
        Duration frameTime = Duration.millis(duration);
        for (Image img : images) {
            frameTime = frameTime.add(frameGap);
            frames.add(new KeyFrame(frameTime, e -> {
                gif.setImage(img);
            }));
        }
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        button.setOnAction(e -> {
            timeLine.stop();
            try {
                handleButtonClick();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}