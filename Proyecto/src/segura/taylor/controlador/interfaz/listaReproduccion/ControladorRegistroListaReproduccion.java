package segura.taylor.controlador.interfaz.listaReproduccion;

import javafx.scene.control.*;
import javafx.stage.Stage;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.interfaz.compositor.ControladorRegistroCompositor;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;

public class ControladorRegistroListaReproduccion {
    public static int idListaReproduccionSeleccionada;
    public static Stage ventana;
    public static boolean modificando;

    public TextField txtNombre;
    public TextArea txtDescripcion;

    public Button btnRegistrarModificar;
    public Button btnSeleccionarImagen;

    public Label lblTitulo;

    public void initialize() {
        if(ControladorRegistroListaReproduccion.modificando) {
            lblTitulo.setText("Modificar ListaReproduccion");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> { modificarListaReproduccion(); });
        } else {
            lblTitulo.setText("Registrar ListaReproduccion");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> { registrarListaReproduccion(); });
        }
    }

    public void registrarListaReproduccion() {
        String nombre = txtNombre.getText();
        LocalDate fechaCreacion = LocalDate.now();
        String imagen = "";
        String descripcion = txtDescripcion.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearListaReproduccion(nombre, fechaCreacion, imagen, descripcion);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Registro exitoso", "Lista de Reproduccion registrada correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar la Lista de Reproduccion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void seleccionarImagen() {
        //TODO Funcionalidad para agregar una cancion
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Prueba", "Aquí seleccionaría una imagen");
    }
    public void modificarListaReproduccion() {
        String nombre = txtNombre.getText();
        String imagen = "";
        String descripcion = txtDescripcion.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().modificarListaReproduccion(idListaReproduccionSeleccionada, nombre, imagen, descripcion);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Modificacion exitosa", "Lista de Reproduccion modificada correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo modificar la Lista de Reproduccion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrar() {
        ventana.close();
    }
}
