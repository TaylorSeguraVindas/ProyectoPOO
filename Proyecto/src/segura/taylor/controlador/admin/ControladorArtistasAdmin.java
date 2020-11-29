package segura.taylor.controlador.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import segura.taylor.bl.entidades.Artista;
import segura.taylor.controlador.ControladorGeneral;

import java.util.List;

public class ControladorArtistasAdmin {
    public TableView tblArtistas;
    public VBox ventanaPrincipal;

    public void initialize() {
        inicializarTabla();
        mostrarDatos();
    }

    public void inicializarTabla() {
        //Nombre
        TableColumn<Artista, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Apellidos
        TableColumn<Artista, String> columnaApellidos = new TableColumn("Apellidos");
        columnaApellidos.setMinWidth(100);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        //NombreArtistico
        TableColumn<Artista, String> columnaNombreArtistico = new TableColumn("Nombre Artistico");
        columnaNombreArtistico.setMinWidth(100);
        columnaNombreArtistico.setCellValueFactory(new PropertyValueFactory<>("nombreArtistico"));

        //FechaNacimiento
        TableColumn<Artista, String> columnaFechaNacimiento = new TableColumn("Fecha Nacimiento");
        columnaFechaNacimiento.setMinWidth(100);
        columnaFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        //FechaDefuncion
        TableColumn<Artista, String> columnaFechaDefuncion = new TableColumn("Fecha Defuncion");
        columnaFechaDefuncion.setMinWidth(100);
        columnaFechaDefuncion.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Edad
        TableColumn<Artista, String> columnaEdad = new TableColumn("Edad");
        columnaEdad.setMinWidth(100);
        columnaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        //Genero
        TableColumn<Artista, String> columnaGenero = new TableColumn("Genero");
        columnaGenero.setMinWidth(100);
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        //Pais
        TableColumn<Artista, String> columnaPais = new TableColumn("Pais");
        columnaPais.setMinWidth(100);
        columnaPais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        //Descripcion
        TableColumn<Artista, String> columnaDescripcion = new TableColumn("Descripcion");
        columnaDescripcion.setMinWidth(100);
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tblArtistas.getColumns().addAll(columnaNombre, columnaApellidos, columnaNombreArtistico, columnaFechaNacimiento,columnaFechaDefuncion, columnaEdad, columnaGenero, columnaPais, columnaDescripcion);
    }
    private void mostrarDatos() {
        tblArtistas.setItems(obtenerArtistas());
    }

    public ObservableList<Artista> obtenerArtistas() {
        List<Artista> artistas = ControladorGeneral.instancia.getGestor().listarArtistas();

        ObservableList<Artista> artistasFinal = FXCollections.observableArrayList();

        for(Artista artista : artistas) {
            artistasFinal.addAll(artista);
        }

        return artistasFinal;
    }
}
