package segura.taylor.ui.dialogos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.controlador.ControladorGeneral;

import javax.xml.soap.Text;
import java.util.concurrent.ThreadLocalRandom;

public class VentanaVerificarCorreo {
    private boolean resultado = false;
    private String codigoGenerado = "";

    public boolean mostrar() {
        generarCodigo();

        try {
            Stage window = new Stage();
            //This locks previous window interacivity until this one is closed.
            window.initModality(Modality.APPLICATION_MODAL);

            VBox root = FXMLLoader.load(getClass().getResource("../ventanas/VentanaVerificarCorreo.fxml"));

            TextField txtCodigo = (TextField) root.lookup("#txtCodigo");

            Button btnVerificar = (Button) root.lookup("#btnVerificar");
            btnVerificar.setOnAction(e -> {
                String codigoIngresado = txtCodigo.getText();

                if(codigoGenerado.equals(codigoIngresado)) {
                    //Continuar y actualizar usuario
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Éxito", "Correo verificado correctamente");

                    resultado = true;
                    window.close();
                } else {
                    //Mostrar mensaje de error
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Error", "El código ingresado es incorrecto");
                }
            });

            Button btnVolver = (Button) root.lookup("#btnVolver");
            btnVolver.setOnAction(e -> { window.close(); });  //Volver al inicio de sesión

            Scene escena = new Scene(root, 380, 280);

            window.setScene(escena);
            window.setTitle("Verificar correo");
            window.setResizable(false);
            window.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    private void generarCodigo() {
        int numRandom;

        for (int i = 0; i < 6; i++) {
            numRandom = ThreadLocalRandom.current().nextInt(1, 10);
            codigoGenerado += numRandom;
        }

        System.out.println("Hola " + ControladorGeneral.instancia.getUsuarioIngresado().getNombreUsuario() + " el codigo que debe ingresar es: " + codigoGenerado);
    }
}
