package segura.taylor.controlador.interfaz.usuarios;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;

public class ControladorRegistroCliente {
    Stage window;

    public TextField txtCorreo;
    public TextField txtContrasenna;
    public TextField txtNombre;
    public TextField txtApellidos;
    public TextField txtNombreUsuario;
    public DatePicker txtFechaNacimiento;
    public ComboBox txtPais;

    public void initialize() {
        actualizarComboBoxPaises();
    }

    public void registrarUsuario() {
        String correo = txtCorreo.getText();
        String contrasenna = txtContrasenna.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String imagenPerfil = "";
        String nombreUsuario = txtNombreUsuario.getText();
        LocalDate fechaNacimiento = txtFechaNacimiento.getValue();
        int edad = ControladorGeneral.instancia.calcularEdad(fechaNacimiento);

        //Combo boxes
        String[] itemPais = txtPais.getValue().toString().split("-");
        int pais = Integer.parseInt(itemPais[0]);

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearUsuarioCliente(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais);
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

    private void actualizarComboBoxPaises() {
        txtPais.getItems().clear();

        for (Pais pais : ControladorGeneral.instancia.getGestor().listarPaises()) {
            txtPais.getItems().add(pais.toComboBoxItem());
        }
    }
}