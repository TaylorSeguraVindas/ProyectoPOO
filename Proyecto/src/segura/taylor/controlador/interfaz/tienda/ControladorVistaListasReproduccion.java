package segura.taylor.controlador.interfaz.tienda;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;

import java.util.List;

public class ControladorVistaListasReproduccion {
    public ScrollPane contenedorFlowPane;
    public FlowPane flowPaneContenido;

    public void initialize() {
        mostrarListasReproduccion();
        flowPaneContenido.prefWidthProperty().bind(contenedorFlowPane.widthProperty()); //Expandir
    }

    private void mostrarListasReproduccion() {
        flowPaneContenido.getChildren().clear();    //Limpiar contenido

        List<ListaReproduccion> listasReproduccion = ControladorGeneral.instancia.getGestor().listarListasReproduccion();

        for (ListaReproduccion listaReproduccion : listasReproduccion) {
            crearCartaListaReproduccion(listaReproduccion.getId(), listaReproduccion.getImagen(), listaReproduccion.getNombre(), listaReproduccion.getDescripcion());
        }
    }

    private void crearCartaListaReproduccion(int idLista, String imagen, String nombre, String descripcion) {
        try {
            VBox nuevaCarta = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/tienda/ElementoListaReproduccion.fxml"));

            //Referencias a campos
            ImageView imagenFondo = (ImageView) nuevaCarta.lookup("#imgFondo");
            Label txtNombre = (Label) nuevaCarta.lookup("#txtNombre");
            Label txtDescripcion = (Label) nuevaCarta.lookup("#txtDescripcion");

            //Actualizar campos
            if(!imagen.equals("")) {
                imagenFondo.setImage(new Image(imagen));
            }

            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);

            nuevaCarta.setOnMouseClicked( e -> {
                mostrarDetalleListaReproduccion(idLista);
            });

            flowPaneContenido.getChildren().add(nuevaCarta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarDetalleListaReproduccion(int idLista) {
        ControladorInfoListaReproduccion.idListaSeleccionada = idLista;

        if(ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin()) {
            ControladorGeneral.refVentanaPrincipalAdmin.mostrarInfoListaReproduccion();
        } else {
            //TODO Hacer lo mismo pero para cliente
        }
    }

    public void buscar() {
        //Actualizar lista
    }
}
