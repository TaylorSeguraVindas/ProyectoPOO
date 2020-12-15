package segura.taylor.controlador.interfaz.tienda;

import javafx.scene.control.CheckBox;

public class ControladorFiltroCanciones {
    public CheckBox chbFiltroNombre;
    public CheckBox chbFiltroArtista;
    public CheckBox chbFiltroGenero;

    public void initialize() {
        chbFiltroNombre.setSelected(ControladorVistaCanciones.filtrandoPorNombre);
        chbFiltroArtista.setSelected(ControladorVistaCanciones.filtrandoPorArtista);
        chbFiltroGenero.setSelected(ControladorVistaCanciones.filtrandoPorGenero);
    }

    public void cambioFiltroNombre() {
        ControladorVistaCanciones.filtrandoPorNombre = chbFiltroNombre.isSelected();
    }

    public void cambioFiltroArtista() {
        ControladorVistaCanciones.filtrandoPorArtista = chbFiltroArtista.isSelected();
    }

    public void cambioFiltroGenero() {
        ControladorVistaCanciones.filtrandoPorGenero = chbFiltroGenero.isSelected();
    }
}
