package segura.taylor.controlador.interfaz.listaReproduccion;

import javafx.scene.control.ComboBox;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;

public class ControladorSeleccionarRepoCanciones {
    public ComboBox txtLista;

    public void initialize() {
        actualizarComboBoxCanciones();
    }

    private void actualizarComboBoxCanciones() {
        try {
            txtLista.getItems().clear();

            for (ListaReproduccion lista : ControladorGeneral.instancia.getGestor().listarListasReproduccionDeBibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado())) {
                txtLista.getItems().add(lista.toComboBoxItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
