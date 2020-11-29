package segura.taylor.ui.dialogos;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDialog {
    public void mostrar(String titulo, String mensaje) {
        Stage window = new Stage();
        //This locks previous window interacivity until this one is closed.
        window.initModality(Modality.APPLICATION_MODAL);

        VBox contenedorPrincipal = new VBox();
        contenedorPrincipal.setSpacing(10);
        contenedorPrincipal.setAlignment(Pos.CENTER);

        Label lblMensaje = new Label(mensaje);
        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setOnAction(e -> { window.close(); });

        contenedorPrincipal.getChildren().addAll(lblMensaje, btnAceptar);
        Scene escena = new Scene(contenedorPrincipal, 240, 120);

        window.setScene(escena);
        window.setTitle(titulo);
        window.setResizable(false);
        window.showAndWait();
    }
}
