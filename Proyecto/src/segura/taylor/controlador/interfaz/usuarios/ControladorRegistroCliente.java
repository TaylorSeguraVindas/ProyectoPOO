package segura.taylor.controlador.interfaz.usuarios;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.io.File;
import java.time.LocalDate;

public class ControladorRegistroCliente {
    public static Stage ventana;
    public static int idCliente;
    public static boolean modificando;
    public static String urlImagenPerfil;

    Stage window;

    private String recursoImagenPerfil = "";

    public TextField txtCorreo;
    public PasswordField txtContrasenna;
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreUsuario;
    public DatePicker txtFechaNacimiento;
    public ComboBox txtPais;

    public Button btnSeleccionarImagen;
    public ImageView imagenPerfil;

    public Label lblTitulo;
    public Button btnRegistrarModificar;

    public void initialize() {
        actualizarComboBoxPaises();
        if(modificando) {
            lblTitulo.setText("Modificar usuario");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> modificarUsuario());

            recursoImagenPerfil = urlImagenPerfil;
        } else {
            lblTitulo.setText("Registrar usuario");
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
        LocalDate fechaNacimiento = txtFechaNacimiento.getValue();
        int edad = ControladorGeneral.instancia.calcularEdad(fechaNacimiento);

        //Combo boxes
        String[] itemPais = txtPais.getValue().toString().split("-");
        int pais = Integer.parseInt(itemPais[0]);

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearUsuarioCliente(correo, contrasenna, nombre, apellidos, recursoImagenPerfil, nombreUsuario, fechaNacimiento, edad, pais);
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

    private void modificarUsuario() {
        String correo = txtCorreo.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String nombreUsuario = txtNombreUsuario.getText();

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().modificarUsuario(idCliente, correo, nombreUsuario, recursoImagenPerfil, nombre, apellidos);
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

    private void actualizarComboBoxPaises() {
        txtPais.getItems().clear();

        for (Pais pais : ControladorGeneral.instancia.getGestor().listarPaises()) {
            txtPais.getItems().add(pais.toComboBoxItem());
        }
    }

}
