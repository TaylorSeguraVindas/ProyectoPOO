package segura.taylor.controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.ListaReproduccion;
import segura.taylor.bl.entidades.RepositorioCanciones;
import segura.taylor.bl.gestor.Gestor;

import segura.taylor.controlador.interfaz.admin.ControladorVentanaPrincipalAdmin;
import segura.taylor.controlador.interfaz.cliente.ControladorVentanaPrincipalCliente;
import segura.taylor.ui.dialogos.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ControladorGeneral {
    //Referencia estatica
    public static ControladorGeneral instancia;
    public static ControladorVentanaPrincipalAdmin refVentanaPrincipalAdmin;
    public static ControladorVentanaPrincipalCliente refVentanaPrincipalCliente;

    //BL
    private Gestor gestor = new Gestor();

    //UI
    private Stage window;

    //Reproductor
    private int idCancionActual = 0;
    private RepositorioCanciones repoCancionesActual;   //La lista que siempre va a estar sonando
    private MediaPlayer mediaPlayer;
    private double volumen;
    private boolean pausado = true;

    //Propiedades
    public Gestor getGestor() {
        return gestor;
    }

    public ControladorGeneral() {
        instancia = this;
    }

    //UTILES
    public String obtenerFechaActual() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = formato.format(LocalDate.now());
        return fecha;
    }
    public LocalDate fechaDesdeString(String pFechaNacimiento) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nuevaFecha = null;
        nuevaFecha = LocalDate.parse(pFechaNacimiento, formato);

        return nuevaFecha;
    }
    public int calcularEdad(LocalDate pFechaNacimiento) {
        //Crear fecha a partir del string
        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(pFechaNacimiento, fechaActual);
        return periodo.getYears();
    }

    //LOGICA
    public void iniciarPrograma(Stage primaryStage) {
        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();    //Stops the base event.
            CloseProgram();
        });
        window.setTitle("NotSpotify");

        try {
            menuIniciarSesion();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void CloseProgram() {
        boolean result = new YesNoDialog().mostrar("Confirmar cierre", "Realmente quiere salir?");

        if(result) {
            System.out.println("Cerrando programa");
            window.close();
        }
    }

    private void cambiarVentana(String titulo, Scene escena, boolean maximizar) {
        ControladorGeneral.instancia.window.setTitle(titulo);
        ControladorGeneral.instancia.window.setScene(escena);
        ControladorGeneral.instancia.window.centerOnScreen();
        ControladorGeneral.instancia.window.setMaximized(maximizar);
        ControladorGeneral.instancia.window.show();
    }

    public void menuIniciarSesion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/ventanas/VentanaLogin.fxml"));
            ControladorGeneral.instancia.cambiarVentana("Inicio de sesion", new Scene(root, 420, 320), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void menuRegistroCliente() {
        try {
            Parent root;

            if(gestor.existeAdmin()) {
                root = FXMLLoader.load(getClass().getResource("../ui/ventanas/VentanaRegistroCliente.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("../ui/ventanas/VentanaRegistroAdmin.fxml"));
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Aviso", "No se ha detectado un usuario admin, se va a crear uno");
            }

            ControladorGeneral.instancia.cambiarVentana("Registro de usuario", new Scene(root, 580, 440), false);
            ControladorGeneral.instancia.window.centerOnScreen();
            ControladorGeneral.instancia.window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void menuPrincipal(boolean admin) {
        try {
            Parent root;

            if(admin) {
                root = FXMLLoader.load(getClass().getResource("../ui/ventanas/admin/VentanaPrincipalAdmin.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("../ui/ventanas/cliente/VentanaPrincipalCliente.fxml"));
            }
            ControladorGeneral.instancia.cambiarVentana("Inicio de sesion", new Scene(root, 420, 320), true);
            window.setMinWidth(1100);
            window.setMinHeight(620);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //MUSICA
    public void reproducirLista(int idLista) {
        try {
            Optional<ListaReproduccion> listaEncontrada = gestor.buscarListaReproduccionPorId(idLista);

            if(listaEncontrada.isPresent()) {
                repoCancionesActual = listaEncontrada.get();
                cargarCancion(repoCancionesActual.getCanciones().get(0).getRecurso());    //Reproduce la primer cancion
                reproducirCancion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarCancionDeLista(int posicion) {
        if(repoCancionesActual == null) return;

        this.idCancionActual = repoCancionesActual.getCanciones().get(posicion).getId();
        cargarCancion(repoCancionesActual.getCanciones().get(posicion).getRecurso());
        reproducirCancion();
    }

    public void cargarCancion(String pRecurso) {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(pRecurso);
        mediaPlayer = new MediaPlayer(media);
    }

    public void siguienteCancion() {
        if(repoCancionesActual == null) return;

        int siguienteCancion = repoCancionesActual.obtenerIndiceCancion(idCancionActual) + 1;

        if(siguienteCancion < repoCancionesActual.getCanciones().size()) {  //Reproduce la siguiente cancion
            cargarCancionDeLista(siguienteCancion);
            reproducirCancion();
        } else {    //Reproduce la ultima cancion
            cargarCancionDeLista(repoCancionesActual.getCanciones().size() - 1);
            reproducirCancion();
        }
    }

    public void cancionAnterior() {
        if(repoCancionesActual == null) return;

        int cancionAnterior = repoCancionesActual.obtenerIndiceCancion(idCancionActual) - 1;

        if(cancionAnterior > 0) {  //Reproduce la siguiente cancion
            cargarCancionDeLista(cancionAnterior);
            reproducirCancion();
        } else {    //Reproduce la ultima cancion
            cargarCancionDeLista(0);
            reproducirCancion();
        }
    }

    public void detenerCancion() {
        mediaPlayer.stop();
    }

    public void alternarEstadoCancion() {
        if (mediaPlayer != null) {
            if(pausado) {
                reproducirCancion();
            } else {
                pausarCancion();
            }
        }
    }

    public void pausarCancion() {
        mediaPlayer.pause();
        pausado = true;
    }

    public void reproducirCancion() {
        mediaPlayer.play();
        pausado = false;
    }
}
