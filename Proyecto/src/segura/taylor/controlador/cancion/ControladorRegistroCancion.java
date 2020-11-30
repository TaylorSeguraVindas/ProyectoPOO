package segura.taylor.controlador.cancion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.artista.ControladorRegistroArtista;
import segura.taylor.controlador.compositor.ControladorRegistroCompositor;
import segura.taylor.controlador.genero.ControladorRegistroGenero;
import segura.taylor.ui.dialogos.AlertDialog;

import java.time.LocalDate;

public class ControladorRegistroCancion {
    public static int idCancionSeleccionada;
    public static Stage ventana;
    public static boolean modificando;

    public TextField txtRecurso;
    public TextField txtNombre;
    public TextField txtDuracion;
    public DatePicker txtFechaLanzamiento;

    public ComboBox txtArtista;
    public ComboBox txtCompositor;
    public ComboBox txtGenero;

    public ComboBox txtAlbum;
    public Button btnCrearAlbum;
    public TextField txtPrecio;

    public Button btnRegistrarModificar;
    public Label lblTitulo;

    private boolean paraTienda = false;

    public void initialize() {
        if(ControladorRegistroCancion.modificando) {
            lblTitulo.setText("Modificar Cancion");
            btnRegistrarModificar.setText("Modificar");
            btnRegistrarModificar.setOnAction(e -> { modificarCancion(); });
        } else {
            lblTitulo.setText("Registrar Cancion");
            btnRegistrarModificar.setText("Registrar");
            btnRegistrarModificar.setOnAction(e -> { registrarCancion(); });
        }

        //Hay campos que solo son editables por el admin o creadores de contenido
        if(ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin() || ControladorGeneral.instancia.getGestor().usuarioIngresadoEsCreador()) {
            txtAlbum.setVisible(true);
            btnCrearAlbum.setVisible(true);
            txtPrecio.setVisible(true);
            paraTienda = true;
        } else {
            txtAlbum.setVisible(false);
            btnCrearAlbum.setVisible(false);
            txtPrecio.setVisible(false);
            txtPrecio.setText("0.00");
            paraTienda = false;
        }
    }

    public void seleccionarRecurso() {
        //TODO logica para seleccionar un archivo
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Prueba", "Aquí seleccionaría el recurso");
    }
    public void crearArtista() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroArtista.ventana = ventanaRegistroArtista;
            ControladorRegistroArtista.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroArtista.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //TODO Actualizar valores de los dropdown
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void crearCompositor() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroCompositor.ventana = ventanaRegistroArtista;
            ControladorRegistroCompositor.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroCompositor.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //TODO Actualizar valores de los dropdown
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void crearGenero() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroGenero.ventana = ventanaRegistroArtista;
            ControladorRegistroGenero.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroGenero.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //TODO Actualizar valores de los dropdown
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void crearAlbum() {
        //TODO Funcionalidad para crear un album
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.mostrar("Prueba", "Aquí crearía el album");
    }

    public void registrarCancion() {
        TipoCancion tipoCancion = (paraTienda) ? TipoCancion.PARA_TIENDA : TipoCancion.PARA_USUARIO;
        int idCreador = ControladorGeneral.instancia.getGestor().getIdUsuarioIngresado();
        String nombre = txtNombre.getText();
        String recurso = txtRecurso.getText();
        double duracion = Double.parseDouble(txtDuracion.getText());
        Album album = null;
        Genero genero = null;
        Artista artista = null;
        Compositor compositor = null;
        LocalDate fechaLanzamiento = txtFechaLanzamiento.getValue();
        double precio = Double.parseDouble(txtPrecio.getText());

        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().crearCancion(tipoCancion, idCreador, nombre, recurso, duracion, album, genero, artista, compositor, fechaLanzamiento, precio);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Registro exitoso", "Cancion registrada correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo registrar la Cancion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modificarCancion() {
        try {
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());

            boolean resultado = ControladorGeneral.instancia.getGestor().modificarCancion(idCancionSeleccionada, nombre, precio);
            if (resultado) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Modificacion exitosa", "Cancion modificado correctamente");
                ventana.close();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No se pudo modificar el Cancion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cerrar() {
        ventana.close();
    }
}