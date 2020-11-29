package segura.taylor.controlador.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Artista;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;
import segura.taylor.ui.dialogos.YesNoDialog;

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

    public void agregarArtista() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);
            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroArtista.fxml"));

            Scene escena = new Scene(root, 580, 440);

            Button btnCerrar = (Button) root.lookup("#btnCerrar");
            btnCerrar.setOnAction(e -> { ventanaRegistroArtista.close(); });

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            mostrarDatos(); //Actualizar tabla
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarArtista() {

    }

    public void eliminarArtista() {
        YesNoDialog yesNoDialog = new YesNoDialog();
        boolean resultado = yesNoDialog.mostrar("Aviso", "Realmente quiere eliminar al artista seleccionado?");

        if (resultado) {
            Artista artistaSeleccionado = (Artista) tblArtistas.getSelectionModel().getSelectedItem();
            int idArtista = artistaSeleccionado.getId();
            try {
                resultado = ControladorGeneral.instancia.getGestor().eliminarArtista(idArtista);
                if (resultado) {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Exito", "Artista eliminado correctamente");
                    mostrarDatos();
                } else {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Error", "No se pudo eliminar el artista");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
