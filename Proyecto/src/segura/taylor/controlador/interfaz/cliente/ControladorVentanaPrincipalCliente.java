package segura.taylor.controlador.interfaz.cliente;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Admin;
import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.interfaz.usuarios.ControladorRegistroCliente;
import segura.taylor.ui.dialogos.AlertDialog;

import java.util.List;

public class ControladorVentanaPrincipalCliente {
    public VBox contenedorPrincipal;
    public ListView listListaReproduccion;

    private void limpiarPantalla() {
        contenedorPrincipal.getChildren().clear();
    }

    public void initialize() {
        ControladorGeneral.instancia.refVentanaPrincipalCliente = this;
        listListaReproduccion.setOnMouseClicked(new EventHandler<MouseEvent>() {    //Evento doble click
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    System.out.println(ControladorGeneral.instancia.getGestor().obtenerIdListaReproduccion(listListaReproduccion.getSelectionModel().getSelectedItem().toString()));
                }
            }
        });
    }

    //GENERAL LISTAS REPRODUCCION
    public void actualizarListasReproduccionUsuario() {
        listListaReproduccion.getItems().clear();   //Limpiar

        List<ListaReproduccion> listas = ControladorGeneral.instancia.getGestor().getBibliotecaUsuarioIngresado().getListasDeReproduccion();

        for (ListaReproduccion lista : listas) {
            listListaReproduccion.getItems().add(lista.getNombre());
        }
    }

    public void crearListaReproduccion() {
        //Agregar a biblioteca y mostrar en el menu

    }
    public void removerListaReproduccion() {
        //remover de biblioteca y actualizar el menu
    }

    //MUSICA
    public void onPausaReproducirPressed() {
        ControladorGeneral.instancia.alternarEstadoCancion();
    }
    public void onSiguienteCancionPressed() {
        ControladorGeneral.instancia.siguienteCancion();
    }
    public void onCancionAnteriorPressed() {
        ControladorGeneral.instancia.cancionAnterior();
    }

    //MENUS
    public void mostrarTienda() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/tienda/VentanaTienda.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarInfoListaReproduccion() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/tienda/VentanaInfoListaReproduccion.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mostrarInfoAlbum() {
        limpiarPantalla();
        try {
            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/tienda/VentanaInfoAlbum.fxml"));
            contenedorPrincipal.getChildren().add(root);
            //Expandir
            root.prefWidthProperty().bind(contenedorPrincipal.widthProperty());
            root.prefHeightProperty().bind(contenedorPrincipal.heightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarBiblioteca() {
        //Mostrar canciones en biblioteca
    }

    public void modificarUsuario() {
        //Ventana para modificar usuario
        if(ControladorGeneral.instancia.usuarioIngresadoEsAdmin()) return;

        try {
            //Referencias para el controlador
            Cliente usuarioIngresado = (Cliente) ControladorGeneral.instancia.getUsuarioIngresado();

            if (usuarioIngresado == null) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo verificar el usuario ingresado");
                return;
            }


            Stage ventanaRegistroAdmin = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroAdmin.initModality(Modality.APPLICATION_MODAL);

            ControladorRegistroCliente.ventana = ventanaRegistroAdmin;
            ControladorRegistroCliente.idCliente = usuarioIngresado.getId();
            ControladorRegistroCliente.modificando = true;

            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/VentanaRegistroCliente.fxml"));

            //Referencia a los campos
            TextField txtCorreo = (TextField) root.lookup("#txtCorreo");
            PasswordField txtContrasenna = (PasswordField) root.lookup("#txtContrasenna");
            TextField txtNombre = (TextField) root.lookup("#txtNombre");
            TextField txtApellidos = (TextField) root.lookup("#txtApellidos");
            TextField txtNombreUsuario = (TextField) root.lookup("#txtNombreUsuario");
            ImageView imagenPerfil = (ImageView) root.lookup("#imagenPerfil");
            DatePicker txtFechaNacimiento = (DatePicker) root.lookup("#txtFechaNacimiento");
            ComboBox txtPais = (ComboBox) root.lookup("#txtPais");

            //Actualizar campos
            txtCorreo.setText(usuarioIngresado.getCorreo());
            txtContrasenna.setText(usuarioIngresado.getContrasenna());
            txtNombre.setText(usuarioIngresado.getNombre());
            txtApellidos.setText(usuarioIngresado.getApellidos());
            txtNombreUsuario.setText(usuarioIngresado.getNombreUsuario());
            txtFechaNacimiento.setValue(usuarioIngresado.getFechaNacimiento());
            txtPais.setValue(usuarioIngresado.getPais().toComboBoxItem());

            ControladorRegistroCliente.urlImagenPerfil = "";

            if(!usuarioIngresado.getImagenPerfil().equals("")) {
                ControladorRegistroCliente.urlImagenPerfil = usuarioIngresado.getImagenPerfil();
                try {
                    imagenPerfil.setImage(new Image(usuarioIngresado.getImagenPerfil()));
                } catch (Exception e) {
                    System.out.println("Imagen invalida");
                }
            }

            //Desactivar campos inmodificables
            txtContrasenna.setDisable(true);
            txtFechaNacimiento.setDisable(true);
            txtPais.setDisable(true);

            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroAdmin.setScene(escena);
            ventanaRegistroAdmin.setTitle("Modificación de cliente");
            ventanaRegistroAdmin.setResizable(false);
            ventanaRegistroAdmin.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
