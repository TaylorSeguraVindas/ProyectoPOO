package segura.taylor.controlador.interfaz.cancion;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.controlador.interfaz.album.ControladorRegistroAlbum;
import segura.taylor.controlador.interfaz.artista.ControladorRegistroArtista;
import segura.taylor.controlador.interfaz.compositor.ControladorRegistroCompositor;
import segura.taylor.controlador.interfaz.genero.ControladorRegistroGenero;
import segura.taylor.ui.dialogos.AlertDialog;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

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
            txtPrecio.setVisible(true);
            paraTienda = true;
        } else {
            txtPrecio.setVisible(false);
            txtPrecio.setText("0.00");
            paraTienda = false;
        }

        actualizarComboBoxArtistas();
        actualizarComboBoxCompositores();
        actualizarComboBoxGeneros();
    }

    public void seleccionarRecurso() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una cancion");

        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.wav", "*.ogg");
        //fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File("C:/dev/"));
        File selectedFile = fileChooser.showOpenDialog(ventana);

        if(selectedFile != null) {  //Actualizar campos
            txtRecurso.setText(selectedFile.toURI().toString());

            Media media = new Media(txtRecurso.getText());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    txtDuracion.setText(String.valueOf(media.getDuration().toMinutes()));

                    // display media's metadata
                    for (Map.Entry<String, Object> entry : media.getMetadata().entrySet()){
                        System.out.println(entry.getKey() + ": " + entry.getValue());

                        if(entry.getKey().equals("artist")) {
                            //TODO Hacer algo con el artista
                        }
                        if(entry.getKey().equals("album")) {
                            //TODO Hacer algo con el album
                        }
                        if(entry.getKey().equals("genre")) {
                            //TODO hacer algo con el genero
                        }
                        if(entry.getKey().equals("title")) {
                            txtNombre.setText(entry.getValue().toString());
                        }
                    }
                }
            });
        }
    }
    public void crearArtista() {
        try {
            Stage ventanaRegistroArtista = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroArtista.initModality(Modality.APPLICATION_MODAL);

            //Referencias para el controlador
            ControladorRegistroArtista.ventana = ventanaRegistroArtista;
            ControladorRegistroArtista.modificando = false;

            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/VentanaRegistroArtista.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //Actualizar valores de los dropdown
            actualizarComboBoxArtistas();
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

            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/VentanaRegistroCompositor.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //Actualizar valores de los dropdown
            actualizarComboBoxCompositores();
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

            VBox root = FXMLLoader.load(getClass().getResource("../../../ui/ventanas/VentanaRegistroGenero.fxml"));
            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroArtista.setScene(escena);
            ventanaRegistroArtista.setTitle("Registro de artista");
            ventanaRegistroArtista.setResizable(false);
            ventanaRegistroArtista.showAndWait();

            //Actualizar valores de los dropdown
            actualizarComboBoxGeneros();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarCancion() {
        String nombre = txtNombre.getText();
        String recurso = txtRecurso.getText();
        double duracion = Double.parseDouble(txtDuracion.getText());

        //Combo boxes
        String[] itemGenero = txtGenero.getValue().toString().split("-");
        int genero = Integer.parseInt(itemGenero[0]);

        String[] itemArtista = txtArtista.getValue().toString().split("-");
        int artista = Integer.parseInt(itemArtista[0]);

        String[] itemCompositor = txtCompositor.getValue().toString().split("-");
        int compositor = Integer.parseInt(itemCompositor[0]);

        LocalDate fechaLanzamiento = txtFechaLanzamiento.getValue();
        double precio = Double.parseDouble(txtPrecio.getText());

        try {
            int idCancionRegistrada = ControladorGeneral.instancia.getGestor().crearCancion(nombre, recurso, duracion, genero, artista, compositor, fechaLanzamiento, precio);
            if (idCancionRegistrada != -1) {
                //Si la lista fue creada por un usuario corriente automáticamente se agrega a su biblioteca
                if(!ControladorGeneral.instancia.usuarioIngresadoEsAdmin()) {
                    ControladorGeneral.instancia.getGestor().agregarCancionABibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado(), idCancionRegistrada);
                }

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
            String recurso = txtRecurso.getText();
            double duracion = Double.parseDouble(txtDuracion.getText());

            //Combo boxes
            String[] itemGenero = txtGenero.getValue().toString().split("-");
            int genero = Integer.parseInt(itemGenero[0]);

            String[] itemArtista = txtArtista.getValue().toString().split("-");
            int artista = Integer.parseInt(itemArtista[0]);

            String[] itemCompositor = txtCompositor.getValue().toString().split("-");
            int compositor = Integer.parseInt(itemCompositor[0]);

            LocalDate fechaLanzamiento = txtFechaLanzamiento.getValue();
            double precio = Double.parseDouble(txtPrecio.getText());

            boolean resultado = ControladorGeneral.instancia.getGestor().modificarCancion(idCancionSeleccionada, nombre, recurso, duracion, genero, artista, compositor, fechaLanzamiento, precio);

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

    private void actualizarComboBoxArtistas() {
        txtArtista.getItems().clear();

        for (Artista artista : ControladorGeneral.instancia.getGestor().listarArtistas()) {
            txtArtista.getItems().add(artista.toComboBoxItem());
        }
    }

    private void actualizarComboBoxCompositores() {
        txtCompositor.getItems().clear();

        for (Compositor compositor : ControladorGeneral.instancia.getGestor().listarCompositores()) {
            txtCompositor.getItems().add(compositor.toComboBoxItem());
        }
    }

    private void actualizarComboBoxGeneros() {
        txtGenero.getItems().clear();

        for (Genero genero : ControladorGeneral.instancia.getGestor().listarGeneros()) {
            txtGenero.getItems().add(genero.toComboBoxItem());
        }
    }
}