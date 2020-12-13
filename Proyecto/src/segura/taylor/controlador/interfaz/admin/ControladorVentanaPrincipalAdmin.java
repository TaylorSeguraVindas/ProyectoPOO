package segura.taylor.controlador.interfaz.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import segura.taylor.controlador.ControladorGeneral;

public class ControladorVentanaPrincipalAdmin {
    public VBox contenedorPrincipal;

    private void limpiarPantalla() {
        contenedorPrincipal.getChildren().clear();
    }

    //MUSICA
    public void onPausaReproducirPressed() {
        ControladorGeneral.instancia.alternarEstadoCancion();
    }

    //MENUS
    public void mostrarInicio() {
        limpiarPantalla();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/InicioAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarTienda() {
        limpiarPantalla();
    }
    public void mostrarUsuarios() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/UsuariosAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarAlbunes() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/AlbunesAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarArtistas() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/ArtistasAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarCompositores() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/CompositoresAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarGeneros() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/GenerosAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarCanciones() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/CancionesAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarListasReproduccion() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/ListasReproduccionAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarPaises() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/admin/PaisesAdmin.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
