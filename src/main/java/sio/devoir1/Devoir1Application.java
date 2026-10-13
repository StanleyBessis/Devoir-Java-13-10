package sio.devoir1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Devoir1Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Devoir1Application.class.getResource("devoir1-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),800,600);
        scene.getStylesheets().add(
                getClass()
                        .getResource("/CSS/StylesDevoir.css")
                        .toExternalForm()
        );
        stage.setTitle("Gestion tournois");
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }
}
