package com.happynicetime.blackyellow;

import java.io.File;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Slider;

public class PrimaryController {
    @FXML
    public void initialize(){
        slider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                adjustColor();
            }
        });
    }
    private static WritableImage imageOut;
    private static Image imageIn;
    @FXML
    private void browse() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File showOpenDialog = fileChooser.showOpenDialog(App.stage);
        imageIn = new Image(showOpenDialog.toURI().toURL().toString());
        adjustColor();
    }
    @FXML
    private ImageView imageView;
    @FXML
    private void save() throws IOException {
           FileChooser fileChooser = new FileChooser();
           fileChooser.setTitle("save as");
           fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png"));
           File selectedFile = fileChooser.showSaveDialog(App.stage);
           ImageIO.write(SwingFXUtils.fromFXImage(imageOut, null), "PNG", selectedFile);
    }
    @FXML
    private Slider slider;

    private void adjustColor() {
        double weight1 = slider.getValue();
        double weight2 = 100d - weight1;
        PixelReader pixelReader = imageIn.getPixelReader();
        double pixelsWidth = imageIn.getWidth();
        double pixelsHeight = imageIn.getHeight();
        imageOut = new WritableImage((int)pixelsWidth, (int)pixelsHeight);
        PixelWriter pixelWriter = imageOut.getPixelWriter();
        for(int x = 0;x < pixelsWidth;x++){
            for(int y = 0;y < pixelsHeight;y++){
                //average the color and make yellow
                Color color = pixelReader.getColor(x, y);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                double avg = (red + green + blue) / 3d;
                double newRed = ((weight1 * avg) + (weight2 * red)) / 100d;
                double newGreen = ((weight1 * avg) + (weight2 * green)) / 100d;
                double newBlue =  0d + (weight2 * blue) / 100d;
                Color color2 = new Color(newRed,newGreen,newBlue,1f);
                pixelWriter.setColor(x, y, color2);
            }
        }
        imageView.setImage(imageOut);
    }
}
