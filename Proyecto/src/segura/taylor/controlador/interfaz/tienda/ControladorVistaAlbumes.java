package segura.taylor.controlador.interfaz.tienda;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import segura.taylor.bl.entidades.Album;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;

import java.util.List;

public class ControladorVistaAlbumes {
    public ScrollPane contenedorFlowPane;
    public FlowPane flowPaneContenido;

    public void initialize() {
        mostrarListasReproduccion();
        flowPaneContenido.prefWidthProperty().bind(contenedorFlowPane.widthProperty()); //Expandir
    }

    private void mostrarListasReproduccion() {
        flowPaneContenido.getChildren().clear();    //Limpiar contenido

        List<Album> albumes = ControladorGeneral.instancia.getGestor().listarAlbunes();

        for (Album album : albumes) {
            crearCartaListaReproduccion(album.getId(), album.getImagen(), album.getNombre());
        }
    }

    private void crearCartaListaReproduccion(int idAlbum, String imagen, String nombre) {
        try {
            VBox nuevaCarta = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/tienda/ElementoAlbum.fxml"));

            //Referencias a campos
            ImageView imagenFondo = (ImageView) nuevaCarta.lookup("#imgFondo");
            Label txtNombre = (Label) nuevaCarta.lookup("#txtNombre");

            //Actualizar campos
            if(!imagen.equals("")) {
                imagenFondo.setImage(new Image(imagen));
            }

            txtNombre.setText(nombre);
            nuevaCarta.setOnMouseClicked( e -> {
                mostrarDetalleAlbum(idAlbum);
            });

            flowPaneContenido.getChildren().add(nuevaCarta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalleAlbum(int idAlbum) {
        ControladorInfoAlbum.idAlbumSeleccionado = idAlbum;

        if(ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin()) {
            ControladorGeneral.refVentanaPrincipalAdmin.mostrarInfoAlbum();
        } else {
            //TODO Hacer lo mismo pero para cliente
        }
    }

    public void buscar() {
        //Actualizar lista
    }
}
