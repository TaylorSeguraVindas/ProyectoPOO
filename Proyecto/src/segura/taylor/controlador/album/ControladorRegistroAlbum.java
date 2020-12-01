package segura.taylor.controlador.album;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Artista;
import segura.taylor.bl.entidades.Compositor;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.compositor.ControladorRegistroCompositor;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;

public class ControladorRegistroAlbum {
    public static int idAlbumSeleccionado;
    public static Stage ventana;
    public static boolean modificando;

    public TextField txtNombre;
    public DatePicker txtFechaLanzamiento;
    public ComboBox txtCompositor;

    public Button btnRegistrarModificar;
    public Button btnCrearCompositor;
    public Button btnSeleccionarImagen;

    public Label lblTitulo;

    public void initialize() {
        if(ControladorRegistroAlbum.modificando) {
            lblTitulo.setText("Modificar Album");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> { modificarAlbum(); });
            btnCrearCompositor.setDisable(true);
        } else {
            lblTitulo.setText("Registrar Album");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> { registrarAlbum(); });
        }
    }

    public void seleccionarImagen() {
        //TODO Funcionalidad para agregar una cancion
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Prueba", "Aquí se mostraría un dropdown");
    }
    public void crearCompositor() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroCompositor.ventana = ventanaRegistroArtista;
            ControladorRegistroCompositor.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroCompositor.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //TODO Actualizar valores de los dropdown
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void registrarAlbum() {
        String nombre = txtNombre.getText();
        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaLanzamiento = txtFechaLanzamiento.getValue();
        String imagen = "";
        Compositor compositor = null;
        Artista artista = null;

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearAlbum(nombre, fechaCreacion, fechaLanzamiento, imagen, compositor, artista);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Registro exitoso", "Album registrado correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar el Album");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modificarAlbum() {
        String nombre = txtNombre.getText();
        String imagen = "";

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().modificarAlbum(idAlbumSeleccionado, nombre, imagen);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Modificacion exitosa", "Album modificado correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo modificar el Album");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrar() {
        ventana.close();
    }
}
