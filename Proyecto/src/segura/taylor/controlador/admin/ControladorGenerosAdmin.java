package segura.taylor.controlador.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Genero;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.genero.ControladorRegistroGenero;
import segura.taylor.ui.dialogos.AlertDialog;
import segura.taylor.ui.dialogos.YesNoDialog;

import java.util.List;

public class ControladorGenerosAdmin {
    public TableView tblGeneros;
    public VBox ventanaPrincipal;

    public void initialize() {
        inicializarTabla();
        mostrarDatos();
    }

    public void inicializarTabla() {
        //Nombre
        TableColumn<Genero, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Descripcion
        TableColumn<Genero, String> columnaDescripcion = new TableColumn("Descripción");
        columnaDescripcion.setMinWidth(100);
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tblGeneros.getColumns().addAll(columnaNombre, columnaDescripcion);

    }
    private void mostrarDatos() {
        tblGeneros.getItems().clear();
        tblGeneros.setItems(obtenerGeneros());
    }

    public ObservableList<Genero> obtenerGeneros() {
        List<Genero> generos = ControladorGeneral.instancia.getGestor().listarGeneros();

        ObservableList<Genero> generosFinal = FXCollections.observableArrayList();

        for(Genero genero : generos) {
            generosFinal.addAll(genero);
        }

        return generosFinal;
    }

    public void agregarGenero() {
        try {
            Stage ventanaRegistroGenero = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroGenero.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroGenero.ventana = ventanaRegistroGenero;
            ControladorRegistroGenero.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroGenero.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroGenero.setScene(escena);
            ventanaRegistroGenero.setTitle("Registro de Genero");
            ventanaRegistroGenero.setResizable(false);
            ventanaRegistroGenero.showAndWait();

            mostrarDatos(); //Actualizar tabla
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarGenero() {
        try {
            //Referencias para el controlador
            Genero generoSeleccionado = (Genero) tblGeneros.getSelectionModel().getSelectedItem();

            if (generoSeleccionado == null) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No hay ningún Genero seleccionado");
                return;
            }

            Stage ventanaRegistroGenero = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroGenero.initModality(Modality.APPLICATION_MODAL);

            ControladorRegistroGenero.ventana = ventanaRegistroGenero;
            ControladorRegistroGenero.idGeneroSeleccionado = generoSeleccionado.getId();
            ControladorRegistroGenero.modificando = true;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroGenero.fxml"));

            //Referencia a los campos
            TextField txtNombre = (TextField) root.lookup("#txtNombre");
            TextArea txtDescripcion = (TextArea) root.lookup("#txtDescripcion");

            //Actualizar campos
            txtNombre.setText(generoSeleccionado.getNombre());
            txtDescripcion.setText(generoSeleccionado.getDescripcion());

            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroGenero.setScene(escena);
            ventanaRegistroGenero.setTitle("Modificacion de Genero");
            ventanaRegistroGenero.setResizable(false);
            ventanaRegistroGenero.showAndWait();

            mostrarDatos(); //Actualizar tabla
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarGenero() {
        YesNoDialog yesNoDialog = new YesNoDialog();
        boolean resultado = yesNoDialog.mostrar("Aviso", "Realmente quiere eliminar al Genero seleccionado?");

        if (resultado) {
            Genero GeneroSeleccionado = (Genero) tblGeneros.getSelectionModel().getSelectedItem();

            if (GeneroSeleccionado == null) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No hay ningún Genero seleccionado");
                return;
            }

            int idGenero = GeneroSeleccionado.getId();
            try {
                resultado = ControladorGeneral.instancia.getGestor().eliminarGenero(idGenero);
                if (resultado) {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Exito", "Genero eliminado correctamente");
                    mostrarDatos();
                } else {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Error", "No se pudo eliminar el Genero");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
