package segura.taylor.controlador.interfaz.artista;

import javafx.scene.control.ComboBox;
import segura.taylor.bl.entidades.Artista;
import segura.taylor.controlador.ControladorGeneral;

public class ControladorAgregarArtistaALista {
    public ComboBox txtArtista;

    public void initialize() {
        actualizarComboBoxCanciones();
    }

    private void actualizarComboBoxCanciones() {
        txtArtista.getItems().clear();

        for (Artista artista : ControladorGeneral.instancia.getGestor().listarArtistas()) {
            txtArtista.getItems().add(artista.toComboBoxItem());
        }
    }
}
