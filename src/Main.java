import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // load the GUI
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("Shark");
        Scene scene=new Scene(root);

        // disable window's resize-ability
        primaryStage.setResizable(false);

        // show the GUI
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // launch the application (GUI)
    public static void main(String[] args) {
        launch(args);
    }
}
