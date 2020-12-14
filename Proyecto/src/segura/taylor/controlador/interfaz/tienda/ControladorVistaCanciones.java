package segura.taylor.controlador.interfaz.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import segura.taylor.bl.entidades.Cancion;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.controlador.ControladorGeneral;

import java.util.List;
import java.util.Locale;

public class ControladorVistaCanciones {
    public TableView tblCanciones;
    public VBox ventanaPrincipal;

    public TextField txtBusqueda;

    public void initialize() {
        inicializarTabla();
        mostrarDatos(false);
    }

    public void inicializarTabla() {
        //Nombre
        TableColumn<Cancion, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Duración
        TableColumn<Cancion, String> columnaDuracion = new TableColumn("Duración");
        columnaDuracion.setMinWidth(100);
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        //Fecha lanzamiento
        TableColumn<Cancion, String> columnaFechaLanzamiento = new TableColumn("Fecha lanzamiento");
        columnaFechaLanzamiento.setMinWidth(100);
        columnaFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));

        //Artista
        TableColumn<Cancion, String> columnaArtista = new TableColumn("Artista");
        columnaArtista.setMinWidth(100);
        columnaArtista.setCellValueFactory(new PropertyValueFactory<>("nombreArtista"));

        //Compositor
        TableColumn<Cancion, String> columnaCompositor = new TableColumn("Compositor");
        columnaCompositor.setMinWidth(100);
        columnaCompositor.setCellValueFactory(new PropertyValueFactory<>("nombreCompositor"));

        //Precio
        TableColumn<Cancion, String> columnaPrecio = new TableColumn("Precio");
        columnaPrecio.setMinWidth(100);
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tblCanciones.getColumns().addAll(columnaNombre, columnaDuracion, columnaFechaLanzamiento, columnaArtista, columnaCompositor, columnaPrecio);

    }
    private void mostrarDatos(boolean usandoFiltro) {
        tblCanciones.getItems().clear();
        tblCanciones.setItems(obtenerCanciones(usandoFiltro));
    }

    public ObservableList<Cancion> obtenerCanciones(boolean usandoFiltro) {
        List<Cancion> Canciones = ControladorGeneral.instancia.getGestor().listarCanciones();

        ObservableList<Cancion> CancionesFinal = FXCollections.observableArrayList();

        for(Cancion cancion : Canciones) {
            if(cancion.getTipoCancion().equals(TipoCancion.PARA_TIENDA)) {  //Muestra solo canciones que sean para la tienda
                if(usandoFiltro) {
                    if(cancionCoincideConBusqueda(cancion)) {
                        CancionesFinal.add(cancion);
                    }
                } else {
                    CancionesFinal.add(cancion);
                }
            }
        }

        return CancionesFinal;
    }

    private boolean cancionCoincideConBusqueda(Cancion cancion) {
        String textoBusqueda = txtBusqueda.getText().trim().toUpperCase(Locale.ROOT);

        //NOMBRE
        String nombreCancion = cancion.getNombre().trim().toUpperCase(Locale.ROOT);
        if(nombreCancion.equals(textoBusqueda) || nombreCancion.contains(textoBusqueda)) {
            return true;
        }

        //GENERO
        String generoCancion = cancion.getGenero().getNombre().trim().toUpperCase(Locale.ROOT);
        if(generoCancion.equals(textoBusqueda) || generoCancion.contains(textoBusqueda)) {
            return true;
        }

        //ARTISTA
        String artistaCancion = cancion.getArtista().getNombreArtistico().trim().toUpperCase(Locale.ROOT);
        if(artistaCancion.equals(textoBusqueda) || artistaCancion.contains(textoBusqueda)) {
            return true;
        }

        //COMPOSITOR
        String compositorCancion = cancion.getCompositor().getNombre().trim().toUpperCase(Locale.ROOT);
        if(compositorCancion.equals(textoBusqueda) || compositorCancion.contains(textoBusqueda)) {
            return true;
        }

        return false;
    }

    public void abrirFiltros() {
        //Mostrar filtros
    }

    public void abrirInfoDetallada() {
        //Abrir info detallada de la cancion
    }

    public void buscar() {
        //Actualizar lista
        mostrarDatos(true);
    }
}
