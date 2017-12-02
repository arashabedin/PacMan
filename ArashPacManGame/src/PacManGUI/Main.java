package PacManGUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by Arash Abedin on 9-03-2017.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{




        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacman.fxml"));
        //loader.load(getClass().getResource("PacManGUI.fxml".ope));
        Parent root = loader.load();
        primaryStage.setTitle("ArashPacMan");

        Controller controller = (Controller) loader.getController();

        Scene scene = new Scene(root, 560,760);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            controller.keyPressed(event.getCode());
            }

        });

        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("PacManGUI/stylesheet.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
