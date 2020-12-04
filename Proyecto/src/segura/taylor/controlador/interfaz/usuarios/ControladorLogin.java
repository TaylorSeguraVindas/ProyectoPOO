package segura.taylor.controlador.interfaz.usuarios;
import javafx.scene.control.TextField;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

public class ControladorLogin {
    private boolean ingresadoCorrectamente = false;

    public TextField txtCorreo;
    public TextField txtContrasenna;

    public boolean mostrar() {
        return ingresadoCorrectamente;
    }

    public void iniciarSesion() {
        boolean resultado = ControladorGeneral.instancia.getGestor().iniciarSesion(txtCorreo.getText(), txtContrasenna.getText());
        if(!resultado) {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.mostrar("No se pudo iniciar sesion", "El correo o la contraseña son incorrectos");
        } else {
            boolean usuarioEsAdmin = ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin();
            System.out.println("Ingresando como admin: " + usuarioEsAdmin);
            ControladorGeneral.instancia.menuPrincipal(usuarioEsAdmin);
        }
    }
    public void registrarUsuario() {
        ControladorGeneral.instancia.menuRegistroCliente();
    }
}