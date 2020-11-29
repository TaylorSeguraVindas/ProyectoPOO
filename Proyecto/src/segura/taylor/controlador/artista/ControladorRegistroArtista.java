package segura.taylor.controlador.artista;

import javafx.scene.control.*;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Genero;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;
import java.util.Optional;

public class ControladorRegistroArtista {
    public static int idArtistaSeleccionado;
    public static Stage ventana;
    public static boolean modificando;

    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreArtistico;
    public ComboBox txtGenero;
    public ComboBox txtPais;
    public DatePicker txtFechaNacimiento;
    public DatePicker txtFechaDefuncion;
    public TextArea txtDescripcion;

    public Button btnRegistrarModificar;
    public Label lblTitulo;

    public void initialize() {
        if(ControladorRegistroArtista.modificando) {
            lblTitulo.setText("Modificar artista");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> { modificarArtista(); });
        } else {
            lblTitulo.setText("Registrar artista");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> { registrarArtista(); });
        }
    }

    public void registrarArtista() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String nombreArtistico = txtNombreArtistico.getText();
        LocalDate fechaNacimiento = txtFechaNacimiento.getValue();
        LocalDate fechaDefuncion = txtFechaDefuncion.getValue();
        Pais paisNacimiento = null;
        Genero genero = null;
        int edad = ControladorGeneral.instancia.calcularEdad(fechaNacimiento);
        String descripcion = txtDescripcion.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearArtista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Registro exitoso", "Artista registrado correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar el artista");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modificarArtista() {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String nombreArtistico = txtNombreArtistico.getText();
        LocalDate fechaDefuncion = txtFechaDefuncion.getValue();
        String descripcion = txtDescripcion.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().modificarArtista(idArtistaSeleccionado, nombre, apellidos, nombreArtistico, fechaDefuncion, descripcion);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Modificacion exitosa", "Artista modificado correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo modificar el artista");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrar() {
        ventana.close();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtNombreArtistico.setText("");
        txtGenero.setValue("");
        txtPais.setValue("");
        txtFechaNacimiento.setValue(null);
        txtFechaDefuncion.setValue(null);
        txtDescripcion.setText("");
    }
}
