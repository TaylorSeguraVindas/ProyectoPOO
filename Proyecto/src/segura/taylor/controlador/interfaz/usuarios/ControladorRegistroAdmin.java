package segura.taylor.controlador.interfaz.usuarios;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.io.File;
import java.time.LocalDate;

public class ControladorRegistroAdmin {
    public static Stage ventana;
    public static int idAdmin;
    public static boolean modificando;
    public static String urlImagenPerfil;

    Stage window;

    private String recursoImagenPerfil = "";

    public Label lblTitulo;

    public TextField txtCorreo;
    public PasswordField txtContrasenna;
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreUsuario;

    public ImageView imagenPerfil;

    public Button btnRegistrarModificar;

    public void initialize() {
        if(modificando) {
            lblTitulo.setText("Modificar admin");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> modificarUsuario());

            recursoImagenPerfil = urlImagenPerfil;
        } else {
            lblTitulo.setText("Registrar admin");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> registrarUsuario());
        }
    }

    public void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagen de perfil");
        File selectedFile = fileChooser.showOpenDialog(window);

        if(selectedFile != null) {
            recursoImagenPerfil = selectedFile.toURI().toString();
            imagenPerfil.setImage(new Image(recursoImagenPerfil));
        }
    }

    public void registrarUsuario() {
        String correo = txtCorreo.getText();
        String contrasenna = txtContrasenna.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String nombreUsuario = txtNombreUsuario.getText();
        LocalDate fechaCreacion = LocalDate.now();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearUsuarioAdmin(correo, contrasenna, nombre, apellidos, recursoImagenPerfil, nombreUsuario, fechaCreacion);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Registro exitoso", "Usuario registrado correctamente");
                volver();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar el usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modificarUsuario() {
        String correo = txtCorreo.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String nombreUsuario = txtNombreUsuario.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().modificarUsuario(idAdmin, correo, nombreUsuario, recursoImagenPerfil, nombre, apellidos);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Modificación exitosa", "Usuario modificado correctamente");
                volver();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo modificar el usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void volver() {
        if(modificando) {
            ventana.close();
        } else {
            ControladorGeneral.instancia.menuIniciarSesion();
        }
    }
}
