package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TicTacToe extends Application{

    /**
     * Creates a GridPane by loading the TicTacToe.fxml file.
     * Creates a scene with that GridPane.
     * Puts that scene on the primaryStage.
     * Displays the Stage.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane gridPane = FXMLLoader.load(getClass().getResource("TicTacToe.fxml"));
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Calls the static method Launch from the superclass Application
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);

    }
}
