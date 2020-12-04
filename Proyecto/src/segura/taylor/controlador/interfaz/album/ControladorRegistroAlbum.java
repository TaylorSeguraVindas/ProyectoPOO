package segura.taylor.controlador.interfaz.album;

import javafx.scene.control.*;
import javafx.stage.Stage;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.interfaz.compositor.ControladorRegistroCompositor;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;

public class ControladorRegistroAlbum {
    public static int idAlbumSeleccionado;
    public static Stage ventana;
    public static boolean modificando;

    public TextField txtNombre;
    public DatePicker txtFechaLanzamiento;

    public Button btnRegistrarModificar;
    public Button btnSeleccionarImagen;

    public Label lblTitulo;

    public void initialize() {
        if(ControladorRegistroAlbum.modificando) {
            lblTitulo.setText("Modificar Album");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> { modificarAlbum(); });
        } else {
            lblTitulo.setText("Registrar Album");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> { registrarAlbum(); });
        }
    }

    public void registrarAlbum() {
        String nombre = txtNombre.getText();
        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaLanzamiento = txtFechaLanzamiento.getValue();
        String imagen = "";

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearAlbum(nombre, fechaCreacion, fechaLanzamiento, imagen);
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
    public void seleccionarImagen() {
        //TODO Funcionalidad para agregar una cancion
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Prueba", "Aquí se mostraría un dropdown");
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
