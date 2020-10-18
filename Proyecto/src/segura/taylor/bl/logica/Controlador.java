package segura.taylor.bl.logica;

import segura.taylor.bl.entidades.Usuario;
import segura.taylor.bl.gestor.Gestor;
import segura.taylor.bl.ui.UI;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controlador {
    //Variables
    private Gestor gestor = new Gestor();
    private UI ui = new UI();

    private Usuario usuarioIngresado;   //Referencia al usuario que está usando la aplicacion

    //Metodos
    public void iniciarPrograma(){
        int opcion1 = 0;

        do {
            if(gestor.existeAdmin()){
                ArrayList<Usuario> usuarios = gestor.listarUsuarios();
                for (Usuario objUsuario: usuarios) {
                    ui.imprimirLinea(objUsuario.toString());
                }

                opcion1 = mostrarMenuInicioSesion();
                procesarOpcion1(opcion1);
            } else {
                ui.imprimirLinea("**No se ha detectado ningun usuario admin**");
                registrarUsuario(true);
            }
        }while (opcion1 != 3);
    }


    //****Utiles****
    private String generarIdUsuario(String pFecha, String pCorreo){
        return "user_" + pFecha + pCorreo;
    }
    private String obtenerFechaActual(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = formato.format(LocalDate.now());
        return fecha;
    }
    private LocalDate fechaDesdeString(String pFechaNacimiento){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dFechaNacimiento = null;
        dFechaNacimiento = LocalDate.parse(pFechaNacimiento, formato);

        return dFechaNacimiento;
    }
    private int calcularEdad(String pFechaNacimiento){
        //Crear fecha a partir del string
        LocalDate fechaNacimiento = fechaDesdeString(pFechaNacimiento);
        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaNacimiento, fechaActual);
        return periodo.getYears();
    }


    //****General****
    private int mostrarMenuInicioSesion(){
        ui.imprimirLinea("Bienvenido");
        ui.imprimirLinea("1. Iniciar sesion");
        ui.imprimirLinea("2. Registrar usuario");
        ui.imprimirLinea("3. Salir");
        ui.imprimir("Su opcion: ");

        return ui.leerEntero();
    }
    private void procesarOpcion1(int opcion1){
        switch (opcion1){
            case 1:
                ingresarAlPrograma();
                break;
            case 2:
                registrarUsuario(false);
                break;
            case 3:
                ui.imprimirLinea("Adios");
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
        }
    }


    //****Manejo de usuarios****
    private void registrarUsuario(boolean registrandoAdmin){
        ui.imprimirLinea("\nBienvenido al registro de usuarios");

        if(registrandoAdmin){
            String fechaRegistro = obtenerFechaActual();

            ui.imprimir("Ingrese su correo: ");
            String correo = ui.leerLinea();
            ui.imprimir("Ingrese su contraseña: ");
            String contrasenna = ui.leerLinea();

            ////TODO VALIDAR CONTRASEÑA

            ui.imprimir("Ingrese su nombre: ");
            String nombre = ui.leerLinea();
            ui.imprimir("Ingrese sus apellidos: ");
            String apellidos = ui.leerLinea();
            ui.imprimir("Directorio de su foto de perfil o avatar: ");
            String imagenPerfil = ui.leerLinea();
            ui.imprimir("Ingrese su nombre de usuario: ");
            String nombreUsuario = ui.leerLinea();

            //Generacion temporal de id
            String id = generarIdUsuario(fechaRegistro, correo);

            boolean resultado = gestor.crearUsuarioAdmin(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaRegistro);

            if(resultado){
                ui.imprimirLinea("Usuario registrado correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar el registro del usuario :(");
            }
        } else {
            //String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String idPais, Biblioteca biblioteca
            ui.imprimir("Ingrese su correo: ");
            String correo = ui.leerLinea();
            ui.imprimir("Ingrese su contraseña: ");
            String contrasenna = ui.leerLinea();

            ////TODO VALIDAR CONTRASEÑA

            ui.imprimir("Ingrese su nombre: ");
            String nombre = ui.leerLinea();
            ui.imprimir("Ingrese sus apellidos: ");
            String apellidos = ui.leerLinea();
            ui.imprimir("Directorio de su foto de perfil o avatar: ");
            String imagenPerfil = ui.leerLinea();
            ui.imprimir("Ingrese su nombre de usuario: ");
            String nombreUsuario = ui.leerLinea();

            ui.imprimir("Ingrese su fecha de nacimiento(dd/MM/yyyy): ");
            String fechaNacimiento = ui.leerLinea();
            int edad = calcularEdad(fechaNacimiento);
            if(edad < 18){
                ui.imprimirLinea("Lo sentimos, los usuarios deben tener al menos 18 años para poder registrarse en la aplicacion");
                return;
            }

            ui.imprimir("Ingrese el nombre del pais donde vive: ");
            String pais = ui.leerLinea();

            //Generacion temporal de id
            String id = generarIdUsuario(fechaNacimiento, correo);

            boolean resultado = gestor.crearUsuarioCliente(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais, null);

            if(resultado){
                ui.imprimirLinea("Usuario registrado correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar el registro del usuario :(");
            }
        }
    }

    private void ingresarAlPrograma(){
        boolean inicioSesion = false;

        do{
            inicioSesion = iniciarSesion();
            ui.imprimirLinea("Inicio sesion: " + inicioSesion);

            if(!inicioSesion){
                ui.imprimir("No se pudo iniciar sesión, intentelo nuevamente.");
            }
        } while(inicioSesion == false);


        //Logica del programa.
        int opcion2 = 0;

        do{
            if (usuarioIngresado.esAdmin()) {
                opcion2 = mostrarMenuAdmin();
                procesarOpcionAdmin(opcion2);
            } else {
                opcion2 = mostrarMenuCliente();
                procesarOpcionCliente(opcion2);
            }
        }while (usuarioIngresado != null);
    }

    private int mostrarMenuAdmin(){
        return 0;
    }
    private void procesarOpcionAdmin(int opcion){

    }

    private int mostrarMenuCliente(){
        return 0;
    }
    private void procesarOpcionCliente(int opcion){

    }


    //****Manejo de sesiones****
    private boolean iniciarSesion(){
        ui.imprimirLinea("\n\nInicio de sesion");
        ui.imprimir("Correo: ");
        String correo = ui.leerLinea();

        ui.imprimir("Contraseña: ");
        String contrasenna = ui.leerLinea();

        usuarioIngresado = gestor.iniciarSesion(correo, contrasenna);

        ui.imprimirLinea("usuario: " + usuarioIngresado.toString());
        return usuarioIngresado != null;
    }
    private void cerrarSesion(){
        usuarioIngresado = null;
    }
}
