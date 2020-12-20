package segura.taylor.controlador.interfaz.tienda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import segura.taylor.bl.entidades.Cancion;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.controlador.ControladorGeneral;

import java.text.DecimalFormat;
import java.util.Optional;

public class ControladorInfoListaReproduccion {
    public static int idListaSeleccionada;

    public TableView tblCanciones;
    public ImageView imgFondo;
    public Label lblNombre;
    public Label lblDescripcion;
    public Label lblFechaCreacion;
    public Label lblCalificacionPromedio;

    public Button btnGuardarLista;

    public void initialize() {
        inicializarTablaCanciones();
        actualizarInfoListaReproduccion();

        if(ControladorGeneral.instancia.getGestor().usuarioIngresadoEsAdmin()) {
            btnGuardarLista.setDisable(true);
        } else {
            try {   //Desactiva el botón de guardar lista si esta ya ha sido agregada a la biblioteca del usuario actual
                boolean listaGuardada = ControladorGeneral.instancia.getGestor().buscarListaReproduccionEnBibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado(), idListaSeleccionada).isPresent();
                btnGuardarLista.setDisable(listaGuardada);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inicializarTablaCanciones() {
        //Nombre
        TableColumn<Cancion, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Genero
        TableColumn<Cancion, String> columnaGenero = new TableColumn("Genero");
        columnaGenero.setMinWidth(100);
        columnaGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        //Duración
        TableColumn<Cancion, String> columnaDuracion = new TableColumn("Duración");
        columnaDuracion.setMinWidth(100);
        columnaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        //Fecha lanzamiento
        TableColumn<Cancion, String> columnaFechaLanzamiento = new TableColumn("Fecha lanzamiento");
        columnaFechaLanzamiento.setMinWidth(100);
        columnaFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));


        tblCanciones.getColumns().addAll(columnaNombre, columnaGenero, columnaDuracion, columnaFechaLanzamiento);
    }
    private void actualizarInfoListaReproduccion() {
        Optional<ListaReproduccion> listaReproduccionEncontrada;
        ListaReproduccion listaReproduccionSeleccionada;

        try {
            listaReproduccionEncontrada = ControladorGeneral.instancia.getGestor().buscarListaReproduccionPorId(idListaSeleccionada);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if(listaReproduccionEncontrada.isPresent()) {
            listaReproduccionSeleccionada = listaReproduccionEncontrada.get();
        } else {
            System.out.println("No se encontró la lista :(");
            return;
        }

        //Actualizar datos
        if(!listaReproduccionSeleccionada.getImagen().equals("")) {
            imgFondo.setImage(new Image(listaReproduccionSeleccionada.getImagen()));
        }

        lblNombre.setText(listaReproduccionSeleccionada.getNombre());
        lblDescripcion.setText(listaReproduccionSeleccionada.getDescripcion());
        lblCalificacionPromedio.setText("Calificacion promedio: " + new DecimalFormat("#.##").format(listaReproduccionSeleccionada.calcularCalificacion()));
        lblFechaCreacion.setText("Creada: " + listaReproduccionSeleccionada.getFechaCreacion().toString());
        ObservableList<Cancion> canciones = FXCollections.observableArrayList();

        for (Cancion cancion : listaReproduccionSeleccionada.getCanciones()) {  //Obtener canciones
            canciones.add(cancion);
        }

        tblCanciones.setItems(canciones);
    }

    public void reproducirLista() {
        ControladorGeneral.instancia.reproducirLista(idListaSeleccionada);
    }

    public void guardarLista() {
        try {
            boolean resultado = ControladorGeneral.instancia.getGestor().agregarListaReproduccionABibliotecaUsuario(ControladorGeneral.instancia.getIdUsuarioIngresado(), idListaSeleccionada);
            if(resultado) {
                ControladorGeneral.refVentanaPrincipalCliente.actualizarListasReproduccionUsuario();
                btnGuardarLista.setDisable(true);   //Desactiva el botón una vez se guarda la lista
            }
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
