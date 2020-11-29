package segura.taylor.controlador.admin;

import segura.taylor.ui.dialogos.AlertDialog;

public class ControladorInicioAdmin {
    public void ImprimirAlgo() {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Algo", "Algo x2");
    }
}
