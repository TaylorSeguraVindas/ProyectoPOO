package segura.taylor.controlador.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ControladorVentanaPrincipalAdmin {
    public VBox contenedorPrincipal;

    private void limpiarPantalla() {
        contenedorPrincipal.getChildren().clear();
    }

    public void mostrarInicio() {
        limpiarPantalla();
        System.out.println("Hey hey 1");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/admin/InicioAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            System.out.println("Hey hey 2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void mostrarTienda() {
        limpiarPantalla();
    }

    public void mostrarAlbunes() {
        limpiarPantalla();
    }

    public void mostrarArtistas() {
        limpiarPantalla();
    }

    public void mostrarCompositores() {
        limpiarPantalla();
    }

    public void mostrarCanciones() {
        limpiarPantalla();
    }

    public void mostrarListasReproduccion() {
        limpiarPantalla();
    }
}
