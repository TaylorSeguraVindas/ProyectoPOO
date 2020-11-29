package segura.taylor.ui.dialogos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertDialog {
    public void mostrar(String titulo, String mensaje) {
        Stage window = new Stage();

        VBox contenedorPrincipal = new VBox();
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
