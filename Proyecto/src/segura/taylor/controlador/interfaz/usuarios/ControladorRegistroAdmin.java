package segura.taylor.controlador.interfaz.usuarios;

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
    Stage window;

    private String recursoImagenPerfil = "";

    public TextField txtCorreo;
    public TextField txtContrasenna;
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreUsuario;

    public ImageView imagenPerfil;

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
                volverAInicioDeSesion();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar el usuario");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void volverAInicioDeSesion() {
        ControladorGeneral.instancia.menuIniciarSesion();
    }
}
