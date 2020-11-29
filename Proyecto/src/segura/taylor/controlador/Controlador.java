package segura.taylor.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import segura.taylor.bl.gestor.Gestor;

import segura.taylor.ui.*;
import segura.taylor.ui.dialogos.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Controlador {
    //Referencia estatica
    public static Controlador instancia;

    //BL
    private Gestor gestor = new Gestor();

    //UI
    private Stage window;

    public Controlador() {
        instancia = this;
    }

    //UTILES
    private String obtenerFechaActual() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = formato.format(LocalDate.now());
        return fecha;
    }
    private LocalDate fechaDesdeString(String pFechaNacimiento) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dFechaNacimiento = null;
        dFechaNacimiento = LocalDate.parse(pFechaNacimiento, formato);

        return dFechaNacimiento;
    }
    private int calcularEdad(String pFechaNacimiento) {
        //Crear fecha a partir del string
        LocalDate fechaNacimiento = fechaDesdeString(pFechaNacimiento);
        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaNacimiento, fechaActual);
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

    private void cambiarVentana(String titulo, Scene escena) {
        Controlador.instancia.window.setTitle(titulo);
        Controlador.instancia.window.setScene(escena);
        Controlador.instancia.window.centerOnScreen();
        Controlador.instancia.window.show();
    }

    public void menuIniciarSesion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/ventanas/VentanaLogin.fxml"));
            Controlador.instancia.cambiarVentana("Inicio de sesion", new Scene(root, 420, 320));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuRegistroCliente() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../ui/ventanas/VentanaRegistroCliente.fxml"));
            Controlador.instancia.cambiarVentana("Registro de usuario", new Scene(root, 580, 440));
            Controlador.instancia.window.centerOnScreen();
            Controlador.instancia.window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
