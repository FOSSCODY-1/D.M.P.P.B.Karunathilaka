package playaid;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Piyumal
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer playaid;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            
            if(filePath != null){
                Media media = new Media(filePath);
                playaid = new MediaPlayer(media);
                mediaView.setMediaPlayer(playaid);
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty height = mediaView.fitHeightProperty();
                
                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                    
                    slider.setValue(playaid.getVolume()*100);
                    slider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        playaid.setVolume(slider.getValue()/100);
                    }
                });   
                
                playaid.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        seekSlider.setValue(newValue.toSeconds());
                    }
                });
                
                seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        playaid.seek(Duration.seconds(seekSlider.getValue()));
                    }
                });
                
                playaid.play();
            }
    }
    
    @FXML
    private void playVideo(ActionEvent event){
        playaid.play();
        playaid.setRate(1);
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        playaid.pause();
    }
    
    @FXML
    private void stopVideo(ActionEvent event){
        playaid.stop();
    }
    
    @FXML
    private void fastforwardVideo(ActionEvent event){
        playaid.setRate(2);
    }
    
    @FXML
    private void rewindVideo(ActionEvent event){
        playaid.setRate(-2);
    }
    
    @FXML
    private void exitVideo(ActionEvent event){
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
