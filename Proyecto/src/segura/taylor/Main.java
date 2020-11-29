package segura.taylor;

import javafx.application.Application;
import javafx.stage.Stage;
import segura.taylor.controlador.Controlador;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controlador controlador = new Controlador();
        controlador.iniciarPrograma(primaryStage);
    }
}
