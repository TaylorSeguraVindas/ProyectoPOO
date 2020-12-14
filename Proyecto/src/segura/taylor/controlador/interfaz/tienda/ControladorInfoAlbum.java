package segura.taylor.controlador.interfaz.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import segura.taylor.bl.entidades.Album;
import segura.taylor.bl.entidades.Cancion;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;

import java.util.Optional;

public class ControladorInfoAlbum {
    public static int idAlbumSeleccionado;

    public TableView tblCanciones;
    public ImageView imgFondo;
    public Label lblNombre;
    public Label lblFechaLanzamiento;
    public Label lblFechaCreacion;

    public void initialize() {
        inicializarTablaCanciones();
        actualizarInfoAlbum();
    }

    public void inicializarTablaCanciones() {
        //Nombre
        TableColumn<Cancion, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Genero
        TableColumn<Cancion, String> columnaGenero = new TableColumn("Genero");
        columnaGenero.setMinWidth(100);
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        //Duración
        TableColumn<Cancion, String> columnaDuracion = new TableColumn("Duración");
        columnaDuracion.setMinWidth(100);
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        //Fecha lanzamiento
        TableColumn<Cancion, String> columnaFechaLanzamiento = new TableColumn("Fecha lanzamiento");
        columnaFechaLanzamiento.setMinWidth(100);
        columnaFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));


        tblCanciones.getColumns().addAll(columnaNombre, columnaGenero, columnaDuracion, columnaFechaLanzamiento);
    }
    private void actualizarInfoAlbum() {
        Optional<Album> albumEncontrado;
        Album albumSeleccionado;

        try {
            albumEncontrado = ControladorGeneral.instancia.getGestor().buscarAlbumPorId(idAlbumSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if(albumEncontrado.isPresent()) {
            albumSeleccionado = albumEncontrado.get();
        } else {
            System.out.println("No se encontró el album :(");
            return;
        }

        //Actualizar datos
        if(!albumSeleccionado.getImagen().equals("")) {
            imgFondo.setImage(new Image(albumSeleccionado.getImagen()));
        }

        lblNombre.setText(albumSeleccionado.getNombre());
        lblFechaLanzamiento.setText("Lanzamiento: " + albumSeleccionado.getFechaLanzamiento().toString());
        lblFechaCreacion.setText("Creado: " + albumSeleccionado.getFechaCreacion().toString());
        ObservableList<Cancion> canciones = FXCollections.observableArrayList();

        for (Cancion cancion : albumSeleccionado.getCanciones()) {  //Obtener canciones
            canciones.add(cancion);
        }

        tblCanciones.setItems(canciones);
    }

    public void reproducirAlbum() {
        //TODO reproducir album
    }

    public void guardarEnLista() {
        //TODO Agregar a lista de reproduccion
    }

    public void volver() {
        if(ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin()) {
            ControladorGeneral.instancia.refVentanaPrincipalAdmin.mostrarTienda();
        } else {
            //TODO lo mismo pero para el cliente
        }
    }
}
