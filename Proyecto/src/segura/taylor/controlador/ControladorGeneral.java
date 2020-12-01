package segura.taylor.controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import segura.taylor.bl.gestor.Gestor;

import segura.taylor.ui.dialogos.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ControladorGeneral {
    //Referencia estatica
    public static ControladorGeneral instancia;

    //BL
    private Gestor gestor = new Gestor();

    //UI
    private Stage window;

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
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
}
