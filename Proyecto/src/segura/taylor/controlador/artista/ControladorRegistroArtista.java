package segura.taylor.controlador.artista;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import segura.taylor.bl.entidades.Genero;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;
import java.util.Optional;

public class ControladorRegistroArtista {
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreArtistico;
    public ComboBox txtGenero;
    public ComboBox txtPais;
    public DatePicker txtFechaNacimiento;
    public DatePicker txtFechaDefuncion;
    public TextArea txtDescripcion;

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
                limpiarCampos();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar el artista");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
