package segura.taylor.bl.logica;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.gestor.Gestor;
import segura.taylor.bl.ui.UI;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controlador {
    //Constantes
    private final Gestor gestor = new Gestor();
    private final UI ui = new UI();
    private final boolean incluirDatosDePrueba = true; //Usar para pruebas

    //Variables
    private Usuario usuarioIngresado;   //Referencia al usuario que está usando la aplicacion


    //Metodos
    public void iniciarPrograma() {
        //Se usa solo para pruebas
        if (incluirDatosDePrueba) {
            llenarDatosDePrueba();
        }

        int opcion1 = 0;

        do {
            if (gestor.existeAdmin()) {
                opcion1 = mostrarMenuInicioSesion();
                procesarOpcion1(opcion1);
            } else {
                ui.imprimirLinea("**No se ha detectado ningun usuario admin**");
                registrarUsuario(true);
            }
        } while (opcion1 != 3);
    }


    //****Utiles****
    private String generarIdUsuario(String pFecha, String pCorreo) {
        return "user_" + pFecha + pCorreo;
    }

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


    //****Menus Generales****
    private int mostrarMenuInicioSesion() {
        ui.imprimirLinea("Bienvenido");
        ui.imprimirLinea("1. Iniciar sesion");
        ui.imprimirLinea("2. Registrar usuario");
        ui.imprimirLinea("3. Salir");
        ui.imprimir("Su opcion: ");

        return ui.leerEntero();
    }

    private void procesarOpcion1(int opcion1) {
        switch (opcion1) {
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

    private void ingresarAlPrograma() {
        boolean inicioSesion = false;

        inicioSesion = iniciarSesion();

        if (!inicioSesion) {
            ui.imprimir("No se pudo iniciar sesión, intentelo nuevamente.");
            return;
        }

        //Logica del programa.
        int opcion2 = 0;

        do {
            if (usuarioIngresado.esAdmin()) {
                opcion2 = mostrarMenuAdmin();
                procesarOpcionAdmin(opcion2);
            } else {
                opcion2 = mostrarMenuCliente();
                procesarOpcionCliente(opcion2);
            }
        } while (usuarioIngresado != null);
    }


    //****Menus Admin****
    private int mostrarMenuAdmin() {
        ui.imprimirLinea("Bienvenido Admin");
        ui.imprimirLinea("1. Buscar usuario");
        ui.imprimirLinea("2. Listar usuarios");
        ui.imprimirLinea("3. Modificar usuario");

        ui.imprimirLinea("\n4. Crear genero");
        ui.imprimirLinea("5. Buscar genero");
        ui.imprimirLinea("6. Listar generos");
        ui.imprimirLinea("7. Modificar genero");

        ui.imprimirLinea("\n8. Crear compositor");
        ui.imprimirLinea("9. Buscar compositor");
        ui.imprimirLinea("10. Listar compositores");
        ui.imprimirLinea("11. Modificar compositor");

        ui.imprimirLinea("\n12. Crear artista");
        ui.imprimirLinea("13. Buscar artista");
        ui.imprimirLinea("14. Listar artistas");
        ui.imprimirLinea("15. Modificar artista");

        ui.imprimirLinea("\n16. Crear cancion");
        ui.imprimirLinea("17. Buscar cancion");
        ui.imprimirLinea("18. Listar canciones");
        ui.imprimirLinea("19. Modificar canciones");

        ui.imprimirLinea("\n20. Crear lista de reproduccion");
        ui.imprimirLinea("21. Buscar lista de reproduccion");
        ui.imprimirLinea("22. Listar listas de reproduccion");
        ui.imprimirLinea("23. Modificar lista de reproduccion");

        ui.imprimirLinea("\n24. Crear album");
        ui.imprimirLinea("25. Buscar album");
        ui.imprimirLinea("26. Listar albunes");
        ui.imprimirLinea("27. Modificar album");

        ui.imprimirLinea("\n28. Registrar pais");
        ui.imprimirLinea("29. Buscar pais");
        ui.imprimirLinea("30. Listar paises");
        ui.imprimirLinea("31. Modificar pais");

        ui.imprimirLinea("32. Cerrar sesion");
        ui.imprimir("Su opcion: ");
        return ui.leerEntero();
    }
    private void procesarOpcionAdmin(int opcion) {
        switch (opcion) {
            //Usuarios
            case 1:
                buscarUsuario();
                break;
            case 2:
                listarUsuarios();
                break;
            case 3:
                modificarUsuario();
                break;
            //Generos
            case 4:
                registrarGenero();
                break;
            case 5:
                buscarGenero();
                break;
            case 6:
                listarGeneros();
                break;
            case 7:
                modificarGenero();
                break;
            //Compositores
            case 8:
                registrarCompositor();
                break;
            case 9:
                buscarCompositor();
                break;
            case 10:
                listarCompositores();
                break;
            case 11:
                modificarCompositor();
                break;
            //Artistas
            case 12:
                registrarArtista();
                break;
            case 13:
                buscarArtista();
                break;
            case 14:
                listarArtistas();
                break;
            case 15:
                modificarArtista();
                break;
            //Canciones
            case 16:
                registrarCancion();
                break;
            case 17:
                buscarCancion();
                break;
            case 18:
                listarCanciones();
                break;
            case 19:
                modificarCancion();
                break;
            //Listas de reproduccion
            case 20:
                registrarListaReproduccion();
                break;
            case 21:
                buscarListaReproduccion();
                break;
            case 22:
                listarListasDeReproduccion();
                break;
            case 23:
                modificarListaReproduccion();
                break;
            //Albunes
            case 24:
                registrarAlbum();
                break;
            case 25:
                buscarAlbum();
                break;
            case 26:
                listarAlbunes();
                break;
            case 27:
                modificarAlbum();
                break;
            //Paises
            case 28:
                registrarPais();
                break;
            case 29:
                buscarPais();
                break;
            case 30:
                listarPaises();
                break;
            case 31:
                modificarPais();
                break;
            case 32:
                ui.imprimirLinea("Sesion cerrada");
                cerrarSesion();
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
        }
    }


    //****Menus Cliente****
    private int mostrarMenuCliente() {
        return 0;
    }
    private void procesarOpcionCliente(int opcion) {

    }


    //****Control de sesiones****
    private boolean iniciarSesion() {
        ui.imprimirLinea("\n\nInicio de sesion");
        ui.imprimir("Correo: ");
        String correo = ui.leerLinea();

        ui.imprimir("Contraseña: ");
        String contrasenna = ui.leerLinea();

        usuarioIngresado = gestor.iniciarSesion(correo, contrasenna);
        return usuarioIngresado != null;
    }

    private void cerrarSesion() {
        usuarioIngresado = null;
    }


    //Usuarios
    private void registrarUsuario(boolean registrandoAdmin) {
        ui.imprimirLinea("\nBienvenido al registro de usuarios");

        if (registrandoAdmin) {
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

            if (resultado) {
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
            if (edad < 18) {
                ui.imprimirLinea("Lo sentimos, los usuarios deben tener al menos 18 años para poder registrarse en la aplicacion");
                return;
            }

            ui.imprimir("Ingrese el nombre del pais donde vive: ");
            String pais = ui.leerLinea();

            //Generacion temporal de id
            String id = generarIdUsuario(fechaNacimiento, correo);

            boolean resultado = gestor.crearUsuarioCliente(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais, null);

            if (resultado) {
                ui.imprimirLinea("Usuario registrado correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar el registro del usuario :(");
            }
        }
    }

    private void listarUsuarios() {
        ArrayList<Usuario> usuarios = gestor.listarUsuarios();
        for (Usuario objUsuario : usuarios) {
            ui.imprimirLinea(objUsuario.toString());
        }
    }

    private void modificarUsuario() {

    }

    private void buscarUsuario() {

    }

    private void eliminarUsuario() {

    }

    private void guardarCancionEnBiblioteca() {

    }


    //Generos
    private void registrarGenero() {

    }

    private void listarGeneros() {
        ArrayList<Genero> generos = gestor.listarGeneros();
        for (Genero objGenero : generos) {
            ui.imprimirLinea(objGenero.toString());
        }
    }

    private void modificarGenero() {

    }

    private void buscarGenero() {

    }

    private void eliminarGenero() {

    }


    //Compositores
    private void registrarCompositor() {

    }

    private void listarCompositores() {
        ArrayList<Compositor> compositores = gestor.listarCompositores();
        for (Compositor objCompositor : compositores) {
            ui.imprimirLinea(objCompositor.toString());
        }
    }

    private void modificarCompositor() {

    }

    private void buscarCompositor() {

    }

    private void eliminarCompositor() {

    }


    //Artistas
    private void registrarArtista() {

    }

    private void listarArtistas() {
        ArrayList<Artista> artistas = gestor.listarArtistas();
        for (Artista objArtista : artistas) {
            ui.imprimirLinea(objArtista.toString());
        }
    }

    private void modificarArtista() {

    }

    private void buscarArtista() {

    }

    private void eliminarArtista() {

    }


    //Albunes
    private void registrarAlbum() {

    }

    private void listarAlbunes() {
        ArrayList<Album> albunes = gestor.listarAlbunes();
        for (Album objAlbum : albunes) {
            ui.imprimirLinea(objAlbum.toString());
        }
    }

    private void modificarAlbum() {

    }

    private void buscarAlbum() {

    }

    private void incluirCancionEnAlbum() {

    }

    private void removerCancionDeAlbum() {

    }

    private void eliminarAlbum() {

    }


    //Canciones
    private void registrarCancion() {

    }

    private void listarCanciones() {
        ArrayList<Cancion> canciones = gestor.listarCanciones();
        for (Cancion objCancion : canciones) {
            ui.imprimirLinea(objCancion.toString());
        }
    }

    private void modificarCancion() {

    }

    private void buscarCancion() {

    }

    private void eliminarCancion() {

    }


    //Listas de reproduccion
    private void registrarListaReproduccion() {

    }

    private void listarListasDeReproduccion() {
        ArrayList<ListaReproduccion> listasReproduccion = gestor.listarListasReproduccion();
        for (ListaReproduccion objListaReproduccion : listasReproduccion) {
            ui.imprimirLinea(objListaReproduccion.toString());
        }
    }

    private void modificarListaReproduccion() {

    }

    private void buscarListaReproduccion() {

    }

    private void eliminarListaReproduccion() {

    }

    private void incluirCancionEnListaReproduccion() {

    }

    private void removerCancionDeListaReproduccion() {

    }


    //Paises
    private void registrarPais() {

    }

    private void listarPaises() {
        ArrayList<Pais> paises = gestor.listarPaises();
        for (Pais objPais : paises) {
            ui.imprimirLinea(objPais.toString());
        }
    }

    private void modificarPais() {

    }

    private void buscarPais() {

    }

    private void eliminarPais() {

    }

    //Para pruebas
    private void llenarDatosDePrueba() {
        //Admin
        gestor.crearUsuarioAdmin("u001", "admin@admin.com", "admin1234", "Taylor", "Segura Vindas", "foto.png", "Administrador", "18/10/2020");

        //Usuarios
        gestor.crearUsuarioCliente("u002", "pepito@gmail.com", "pepe0101", "Pepe", "Ramirez Diaz", "lafotoperfil.png", "PepePoderoso", "01/02/1990", 30, "Mexico", null);
        gestor.crearUsuarioCliente("u003", "rodrigo@gmail.com", "rod1288", "Rodrigo", "Perez Figueres", "foto12.png", "Rodri7888", "01/02/1992", 28, "Costa Rica", null);
        gestor.crearUsuarioCliente("u004", "Yery@gmail.com", "yeryVindas1221", "Yery", "Vindas Arias", "lalala.png", "Chapito", "01/02/1988", 32, "Costa Rica", null);

        //Generos
        gestor.crearGenero("gen00001", "Cumbia", "Todo loco");
        gestor.crearGenero("gen00002", "Rock", "Buenisimo");
        gestor.crearGenero("gen00003", "Metal", "Tambien buenisimo");
        gestor.crearGenero("gen00004", "Regueton", "Meh");
        gestor.crearGenero("gen00005", "Salsa", "Bien movidito");
        gestor.crearGenero("gen00006", "Electronica", "ASsdjjkduurfffrr");

        //Artistas
        gestor.crearArtista("arti0001", "Artista1", "Inventado 1", "ElPrimeroInventado", "24/06/1977", "", "Estados Unidos", gestor.buscarGenero("gen00001"), 40, "El mejor de la cumbia");
        gestor.crearArtista("arti0002", "Artista2", "Inventado 2", "ElSegundoInventado", "01/12/1990", "01/12/1990", "Rusia", gestor.buscarGenero("gen00002"), 40, "Rockero hasta los huesos");
        gestor.crearArtista("arti0003", "Artista3", "Inventado 3", "ElTercerInventado", "22/02/1978", "", "Alemania", gestor.buscarGenero("gen00003"), 40, "Lalala letra de metal");
        gestor.crearArtista("arti0004", "Artista4", "Inventado 4", "ElCuartoInventado", "05/07/1996", "", "Costa Rica", gestor.buscarGenero("gen00004"), 40, "No se");
        gestor.crearArtista("arti0005", "Artista5", "Inventado 5", "ElQuintoInventado", "12/09/1988", "12/09/1988", "España", gestor.buscarGenero("gen00005"), 40, "El mejor de la salsa");
        gestor.crearArtista("arti0006", "Artista6", "Inventado 6", "ElSextoInventado", "13/01/1980", "", "Colombia", gestor.buscarGenero("gen00006"), 40, "tuititiriritititi");

        //Compositores
        gestor.crearCompositor("comp0001", "Compositor1", "Inventado 1", "Costa Rica", "24/06/1977", 47);
        gestor.crearCompositor("comp0002", "Compositor2", "Inventado 2", "Alemania", "24/06/1977", 47);
        gestor.crearCompositor("comp0003", "Compositor3", "Inventado 3", "EstadosUnidos", "24/06/1977", 47);
        gestor.crearCompositor("comp0004", "Compositor4", "Inventado 4", "Mexico", "24/06/1977", 47);
        gestor.crearCompositor("comp0005", "Compositor5", "Inventado 5", "Rusia", "24/06/1977", 47);
        gestor.crearCompositor("comp0006", "Compositor6", "Inventado 6", "España", "24/06/1977", 47);

        //Canciones
        gestor.crearCancion("can000001", "La cancion que escribi ayer", "cancionDeAyer.mp3", "Musica de la buena", gestor.buscarGenero("Cumbia"), gestor.buscarArtista("Artista1"), gestor.buscarCompositor("Compositor1"), "21/12/2008", null, 5000.00);
        gestor.crearCancion("can000002", "Una buena", "recurso.mp3", "Musica de la regular", gestor.buscarGenero("Rock"), gestor.buscarArtista("Artista2"), gestor.buscarCompositor("Compositor2"), "21/12/2008", null, 8000.00);
        gestor.crearCancion("can000003", "El pollito loco", "nombreArchivo.mp3", "Musica de la regular", gestor.buscarGenero("Rock"), gestor.buscarArtista("Artista2"), gestor.buscarCompositor("Compositor2"), "21/12/2008", null, 2000.00);
        gestor.crearCancion("can000004", "Tigresa canta asi", "descarga(1).mp3", "Musica de la mala", gestor.buscarGenero("Regueton"), gestor.buscarArtista("Artista3"), gestor.buscarCompositor("Compositor2"), "21/12/2008", null, 500.00);
        gestor.crearCancion("can000005", "Ya no se que escribir", "nombreRandom.mp3", "Musica bonita", gestor.buscarGenero("Metal"), gestor.buscarArtista("Artista4"), gestor.buscarCompositor("Compositor3"), "21/12/2008", null, 8000.00);
        gestor.crearCancion("can000006", "Pura melodia sin letra", "melodia.mp3", "Musica musica", gestor.buscarGenero("Salsa"), gestor.buscarArtista("Artista1"), gestor.buscarCompositor("Compositor4"), "21/12/2008", null, 10000.00);
        gestor.crearCancion("can000007", "Mas musica!", "musiquita.mp3", "Musica de la buena", gestor.buscarGenero("Electronica"), gestor.buscarArtista("Artista2"), gestor.buscarCompositor("Compositor1"), "21/12/2008", null, 1200.00);

        //Paises
        gestor.crearPais("pais0001", "Costa Rica", "Pura Vida");
        gestor.crearPais("pais0002", "Alemania", "Lo que sea");
        gestor.crearPais("pais0003", "Estados Unidos", "Otro lo que sea");
        gestor.crearPais("pais0004", "Mexico", "Tututro");
        gestor.crearPais("pais0005", "Rusia", "Rusos!!");
        gestor.crearPais("pais0006", "España", "Pura Vida");

        ArrayList<Cancion> listaCanciones = new ArrayList<Cancion>();
        listaCanciones.add(gestor.buscarCancion("can000001"));
        listaCanciones.add(gestor.buscarCancion("can000002"));
        listaCanciones.add(gestor.buscarCancion("can000003"));

        //Listas de reproduccion
        gestor.crearListaReproduccion("lRep00001", "Musica de la buena", "12/12/2020", listaCanciones, "u002", "foto.png");
        gestor.crearListaReproduccion("lRep00002", "Musica de la regular", "20/06/2020", null, "u002", "foto.png");
        gestor.crearListaReproduccion("lRep00003", "Musica de la mala", "15/02/2020", null, "u004", "foto.png");
        gestor.crearListaReproduccion("lRep00004", "Musica musica", "12/03/2020", listaCanciones, "u003", "foto.png");
        gestor.crearListaReproduccion("lRep00005", "Musica bonita", "14/05/2020", null, "u003", "foto.png");

        //Albunes
        ArrayList<Artista> artistasAlbum1 = new ArrayList<>();
        artistasAlbum1.add(gestor.buscarArtista("arti0001"));
        artistasAlbum1.add(gestor.buscarArtista("arti0004"));

        ArrayList<Artista> artistasAlbum2 = new ArrayList<>();
        artistasAlbum1.add(gestor.buscarArtista("arti0003"));

        gestor.crearAlbum("album000001", "Los 80s", "01/01/2020", listaCanciones, "01/01/1980", "foto.png", artistasAlbum1, gestor.buscarCompositor("comp0001"));
        gestor.crearAlbum("album000002", "Los 90s", "13/06/2020", null, "02/06/1990", "foto.png", artistasAlbum2, gestor.buscarCompositor("comp0002"));
        gestor.crearAlbum("album000003", "Buena Musica moderna", "22/02/2020", null, "01/01/2000", "foto.png", null, gestor.buscarCompositor("comp0003"));
    }
}
