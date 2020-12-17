package segura.taylor.controlador.interfaz.tienda;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import segura.taylor.bl.entidades.Calificacion;
import segura.taylor.bl.entidades.Cancion;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;

import java.util.Optional;

public class ControladorInfoCancion {
    public static int idCancionSeleccionada;

    public Label lblNombre;
    public Label lblDuracion;
    public Label lblFechaLanzamiento;
    public Label lblGenero;
    public Label lblArtista;
    public Label lblCompositor;
    public Label lblCalificacion;
    public Label lblMiCalificacion;
    public ComboBox txtMiCalificacion;
    public Label lblPrecio;

    public Button btnComprar;

    public void initialize() {
        Optional<Cancion> cancionSeleccionada = ControladorGeneral.instancia.getGestor().buscarCancionPorId(idCancionSeleccionada);

        if(cancionSeleccionada.isPresent()) {
            Cancion cancion = cancionSeleccionada.get();

            lblNombre.setText(cancion.getNombre());
            lblDuracion.setText("Duracion: " + cancion.getDuracion());
            lblFechaLanzamiento.setText("Fecha de lanzamiento: " + cancion.getFechaLanzamiento().toString());
            lblGenero.setText("Genero: " + cancion.getNombreGenero());
            lblArtista.setText("Artista: " + cancion.getNombreArtista());
            lblCompositor.setText("Compositor: " + cancion.getNombreCompositor());

            lblCalificacion.setText("Calificación promedio: " + cancion.getCalificacionPromedio());
            lblPrecio.setText("Precio: " + cancion.getPrecio());

            txtMiCalificacion.getItems().addAll("-Sin calificar-", "1 estrella", "2 estrellas", "3 estrellas", "4 estrellas", "5 estrellas");

            try {
                if(ControladorGeneral.instancia.getGestor().buscarCancionEnBibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado(), idCancionSeleccionada).isPresent()) {
                    //La canción ha sido comprada por el usuario.
                    lblMiCalificacion.setVisible(true);
                    txtMiCalificacion.setVisible(true);

                    btnComprar.setDisable(true);
                    btnComprar.setText("Comprada");
                } else {
                    //La canción NO ha sido comprada por el usuario.
                    lblMiCalificacion.setVisible(false);
                    txtMiCalificacion.setVisible(false);

                    btnComprar.setDisable(false);
                    btnComprar.setText("Comprar");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void comprarCancion() {
        //TODO ventana para introducir tarjeta y todo eso
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Éxito", "Canción comprada correctamente");

        //Copiar a biblioteca
        try {
            ControladorGeneral.instancia.getGestor().agregarCancionABibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado(), idCancionSeleccionada);

            //Actualizar botón y calificaciones
            //La canción ha sido comprada por el usuario.
            lblMiCalificacion.setVisible(true);
            txtMiCalificacion.setVisible(true);

            btnComprar.setDisable(true);
            btnComprar.setText("Comprada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void volver() {
        if(ControladorGeneral.instancia.usuarioIngresadoEsAdmin()) {
            ControladorGeneral.instancia.refVentanaPrincipalAdmin.mostrarTienda();
        } else {
            ControladorGeneral.instancia.refVentanaPrincipalCliente.mostrarTienda();
        }
    }
}
