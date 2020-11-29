package segura.taylor.controlador;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import segura.taylor.bl.gestor.Gestor;

public class ControladorLogin {
    private boolean ingresadoCorrectamente = false;

    public boolean mostrar() {
        return ingresadoCorrectamente;
    }

    public void iniciarSesion() {
        System.out.println("Inicio sesion");
    }
    public void registrarUsuario() {
        Controlador.instancia.menuRegistroCliente();
    }
}
