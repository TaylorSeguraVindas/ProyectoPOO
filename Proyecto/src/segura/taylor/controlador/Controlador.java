package segura.taylor.controlador;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.gestor.Gestor;
import segura.taylor.ui.UI;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*TODO
-Enviar correo con OTP
-Verificar correo
-Verificar contraseña
-Permisos para editar y eliminar listas de reproduccion
-Agregar albunes dentro de listas de reproduccion
*/

public class Controlador {
    //Constantes
    private final Gestor gestor = new Gestor();
    private final UI ui = new UI();
    private final boolean incluirDatosDePrueba = true; //Usar para pruebas, si está en true se van a llenar las listas con unos datos de prueba

    //Variables
    private Usuario usuarioIngresado;   //Referencia al usuario que está usando la aplicacion

    //Metodos
    public void iniciarPrograma() {
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
    private void esperarTecla(){
        ui.imprimirLinea("\nPresione Enter para continuar...");
        ui.leerLinea();
    }

    //****Menus Generales****
    private int mostrarMenuInicioSesion() {
        ui.imprimirLinea("\n\n\tBienvenido");
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
        ui.imprimirLinea("\n\n\tBienvenido Admin");
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

        ui.imprimirLinea("\nOPCIONES PARA ELIMINAR");
        ui.imprimirLinea("32. Eliminar usuario");
        ui.imprimirLinea("33. Eliminar genero");
        ui.imprimirLinea("34. Eliminar compositor");
        ui.imprimirLinea("35. Eliminar artista");
        ui.imprimirLinea("36. Eliminar cancion");
        ui.imprimirLinea("37. Eliminar lista de reproduccion");
        ui.imprimirLinea("38. Eliminar album");
        ui.imprimirLinea("39. Eliminar pais");

        ui.imprimirLinea("\n\n40. Cerrar sesion");
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
                modificarUsuario(true);
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
            //Eliminacion
            case 32:
                eliminarUsuario();
                break;
            case 33:
                eliminarGenero();
                break;
            case 34:
                eliminarCompositor();
                break;
            case 35:
                eliminarArtista();
                break;
            case 36:
                eliminarCancion();
                break;
            case 37:
                eliminarListaReproduccion();
                break;
            case 38:
                eliminarAlbum();
                break;
            case 39:
                eliminarPais();
                break;
            case 40:
                ui.imprimirLinea("Sesion cerrada");
                cerrarSesion();
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
        }
    }


    //****Menus Cliente****
    private int mostrarMenuCliente() {
        ui.imprimirLinea("\n\n\tBienvenido");
        ui.imprimirLinea("1. Ver mis canciones");
        ui.imprimirLinea("2. Buscar cancion en biblioteca");
        ui.imprimirLinea("3. Explorar canciones");
        ui.imprimirLinea("4. Subir cancion");
        ui.imprimirLinea("5. Comprar cancion");
        ui.imprimirLinea("6. Crear lista de reproduccion");
        ui.imprimirLinea("7. Explorar listas de reproduccion");
        ui.imprimirLinea("8. Modificar lista de reproduccion");
        ui.imprimirLinea("9. Explorar albunes");
        ui.imprimirLinea("10. Modificar perfil");

        ui.imprimirLinea("\nOPCIONES PARA ELIMINAR");
        ui.imprimirLinea("11. Eliminar cancion");
        ui.imprimirLinea("12. Eliminar lista de reproduccion");
        ui.imprimirLinea("13. Eliminar album");

        ui.imprimirLinea("\n14. Cerrar sesion");
        ui.imprimir("Su opcion: ");
        int opcion = ui.leerEntero();
        return opcion;
    }
    private void procesarOpcionCliente(int opcion) {
        switch (opcion){
            case 1:
                listarCancionesEnBiblioteca();
                break;
            case 2:
                buscarCancionEnBiblioteca();
                break;
            case 3:
                listarCanciones();
                break;
            case 4:
                registrarCancion();
                break;
            case 5:
                comprarCancion();
                break;
            case 6:
                registrarListaReproduccion();
                break;
            case 7:
                listarListasDeReproduccion();
                break;
            case 8:
                modificarListaReproduccion();
                break;
            case 9:
                listarAlbunes();
                break;
            case 10:
                modificarUsuario(false);
                break;
            case 11:
                eliminarCancionDeBiblioteca();
                break;
            case 12:
                eliminarListaReproduccion();
                break;
            case 13:
                eliminarAlbum();
                break;
            case 14:
                ui.imprimirLinea("Sesion cerrada");
                cerrarSesion();
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
        }
    }


    //****Control de sesiones****
    private boolean iniciarSesion() {
        ui.imprimirLinea("\n\n\tInicio de sesion");
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


    //Usuarios ++
    private void registrarUsuario(boolean registrandoAdmin) {
        ui.imprimirLinea("\n\n\tRegistro de usuarios");

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

            boolean resultado = gestor.crearUsuarioAdmin(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaRegistro);

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

            boolean resultado = gestor.crearUsuarioCliente(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais, null);

            if (resultado) {
                ui.imprimirLinea("Usuario registrado correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar el registro del usuario :(");
            }
        }
    }
    private void listarUsuarios() {
        ui.imprimirLinea("\n\n\tLista de usuarios");
        ArrayList<Usuario> usuarios = gestor.listarUsuarios();
        for (Usuario objUsuario : usuarios) {
            ui.imprimirLinea(objUsuario.toString());
        }

        esperarTecla();
    }
    private void modificarUsuario(boolean desdeAdmin) {
        ui.imprimirLinea("\n\n\tModificar usuario");

        String correo;

        if(desdeAdmin){
            ui.imprimir("correo del usuario por modificar: ");
            correo = ui.leerLinea();
        } else {
            correo = usuarioIngresado.getCorreo();
        }

        ui.imprimir("Nombre de usuario: ");
        String nombreUsuario = ui.leerLinea();
        ui.imprimir("Imagen de perfil: ");
        String imagenPerfil = ui.leerLinea();
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Apellidos: ");
        String apellidos = ui.leerLinea();

        //La contraseña NO se modifica desde aqui.
        gestor.modificarUsuario(correo, nombreUsuario, imagenPerfil, "", nombre, apellidos);

        esperarTecla();
    }
    private void buscarUsuario() {
        ui.imprimirLinea("\n\n\tBuscar de usuarios");
        ui.imprimir("Ingrese el correo o nombre del usuario que desea buscar: ");
        String dato = ui.leerLinea();
        Usuario usuarioEncontrado = gestor.buscarUsuarioPorInfo(dato);

        if(usuarioEncontrado != null){
            ui.imprimirLinea("Se encontró: " + usuarioEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarUsuario() {
        ui.imprimirLinea("\n\n\tEliminar usuario");
        ui.imprimir("Ingrese el id del usuario que desea eliminar: ");
        int id = ui.leerEntero();
        Usuario usuarioEncontrado = gestor.buscarUsuarioPorId(id);

        if(usuarioEncontrado != null){
            if(usuarioEncontrado.getId() == id){
                gestor.eliminarUsuario(usuarioEncontrado);
                ui.imprimirLinea("Usuario eliminado");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void comprarCancion(){
        ui.imprimirLinea("\n\n\tCompra de canciones");
        ui.imprimir("Ingrese el id cancion que desea comprar");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada = gestor.buscarCancionPorId(idCancion);
        if(cancionEncontrada != null){
            ui.imprimirLinea("Se encontró: " + cancionEncontrada.toString());
            ui.imprimirLinea("Tiene un precio de: " + cancionEncontrada.getPrecio());

            ui.imprimirLinea("Comprar?");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();

            if(opcion != 1){
                return;
            }

            ui.imprimirLinea("AQUI SE SIMULA LA COMPRA");
            ui.imprimirLinea("Cancion comprada! :D");
            gestor.agregarCancionABibliotecaUsuario(usuarioIngresado.getId(), cancionEncontrada);
        } else {
            ui.imprimirLinea("No se encontró la cancion :(");
        }

        esperarTecla();
    }
    private void listarCancionesEnBiblioteca(){
        ui.imprimirLinea("\n\n\tLista de canciones");
        ArrayList<Cancion> canciones = gestor.listarCancionesDeBibliotecaUsuario(usuarioIngresado.getId());
        for (Cancion objCancion : canciones) {
            ui.imprimirLinea(objCancion.toString());
        }

        esperarTecla();
    }
    private void buscarCancionEnBiblioteca(){
        ui.imprimirLinea("\n\n\tBuscar cancion en biblioteca");
        ui.imprimir("Ingrese el ID o nombre de la cancion que desea buscar: ");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada = gestor.buscarCancionEnBibliotecaUsuario(usuarioIngresado.getId(), idCancion);
        if(cancionEncontrada != null){
            ui.imprimirLinea("Encontrado: " + cancionEncontrada.toString());
        } else {
            ui.imprimirLinea("No hay resultados.");
        }

        esperarTecla();
    }
    private void eliminarCancionDeBiblioteca() {
        ui.imprimirLinea("\n\n\tEliminar cancion de biblioteca");
        ui.imprimir("Ingrese el ID de la cancion que desea eliminar: ");
        int id = ui.leerEntero();

        Cancion cancionEncontrada = gestor.buscarCancionEnBibliotecaUsuario(usuarioIngresado.getId(), id);
        if(cancionEncontrada != null){
            if(cancionEncontrada.getId() == id){
                gestor.removerCancionDeBibliotecaUsuario(usuarioIngresado.getId(), cancionEncontrada);
                ui.imprimirLinea("Cancion eliminada correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados.");
        }

        esperarTecla();
    }


    //Generos ++
    private void registrarGenero() {
        ui.imprimirLinea("\n\n\tRegistro de generos");
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        boolean resultado = gestor.crearGenero(nombre, descripcion);

        if(resultado){
            ui.imprimirLinea("Genero registrado correctamente! :D");
        } else {
            ui.imprimirLinea("Hubo un problema al intentar el registro del genero");
        }
    }
    private void listarGeneros() {
        ui.imprimirLinea("\n\n\tLista de generos");
        ArrayList<Genero> generos = gestor.listarGeneros();
        for (Genero objGenero : generos) {
            ui.imprimirLinea(objGenero.toString());
        }

        esperarTecla();
    }
    private void modificarGenero() {
        ui.imprimirLinea("\n\n\tModificar genero");
        ui.imprimir("Ingrese el id del genero que desea modificar: ");
        int id = ui.leerEntero();
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        gestor.modificarGenero(id, nombre, descripcion);

        esperarTecla();
    }
    private void buscarGenero() {
        ui.imprimirLinea("\n\n\tBuscar genero");

        ui.imprimir("Ingrese el id del genero que desea buscar: ");
        int idGenero = ui.leerEntero();

        Genero generoEncontrado = gestor.buscarGeneroPorId(idGenero);

        if(generoEncontrado != null){
            ui.imprimirLinea("Encontrado: " + generoEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarGenero() {
        ui.imprimirLinea("\n\n\tEliminar genero");

        ui.imprimir("Ingrese el id del genero que desea eliminar: ");
        int id = ui.leerEntero();

        Genero generoEncontrado = gestor.buscarGeneroPorId(id);
        if(generoEncontrado != null){
            if(generoEncontrado.getId() == id){
                gestor.eliminarGenero(generoEncontrado);
                ui.imprimirLinea("Genero eliminado");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Compositores ++
    private void registrarCompositor() {
        ui.imprimirLinea("\n\n\tRegistro de compositor");
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Apellidos: ");
        String apellidos = ui.leerLinea();
        ui.imprimir("Pais de nacimiento: ");
        String pais = ui.leerLinea();
        ui.imprimir("Fecha de nacimiento(dd/MM/yyyy): ");
        String fechaNacimiento = ui.leerLinea();
        int edad = calcularEdad(fechaNacimiento);
        String id = "comp0000" + nombre + fechaNacimiento;

        boolean resultado = gestor.crearCompositor(nombre, apellidos, pais, fechaNacimiento, edad);

        if(resultado){
            ui.imprimirLinea("Compositor registrado correctamente! :D");
        } else {
            ui.imprimirLinea("Hubo un problema al intentar el registro del compositor :(");
        }
    }
    private void listarCompositores() {
        ui.imprimirLinea("\n\n\tLista de compositores");
        ArrayList<Compositor> compositores = gestor.listarCompositores();
        for (Compositor objCompositor : compositores) {
            ui.imprimirLinea(objCompositor.toString());
        }

        esperarTecla();
    }
    private void modificarCompositor() {
        ui.imprimirLinea("\n\n\tModificar compositor");
        ui.imprimir("Ingrese el id del compositor que desea modificar: ");
        int id = ui.leerEntero();
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Apellidos: ");
        String apellidos = ui.leerLinea();

        gestor.modificarCompositor(id, nombre, apellidos);

        esperarTecla();
    }
    private void buscarCompositor() {
        ui.imprimirLinea("\n\n\tBuscar compositor");
        ui.imprimir("Ingrese el id o nombre del compositor que desea buscar: ");
        int idCompositor = ui.leerEntero();

        Compositor compositorEncontrado = gestor.buscarCompositorPorId(idCompositor);

        if(compositorEncontrado != null){
            ui.imprimirLinea("Encontrado: " + compositorEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarCompositor() {
        ui.imprimirLinea("\n\n\tEliminar compositor");
        ui.imprimir("Ingrese el id del compositor que desea eliminar: ");
        int id = ui.leerEntero();

        Compositor compositorEncontrado = gestor.buscarCompositorPorId(id);

        if(compositorEncontrado != null){
            if(compositorEncontrado.getId() == id){
                gestor.eliminarCompositor(compositorEncontrado);
                ui.imprimirLinea("Compositor eliminado");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Artistas ++
    private void registrarArtista() {
        ui.imprimirLinea("\n\n\tRegistro de artista");
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Apellidos: ");
        String apellidos = ui.leerLinea();
        ui.imprimir("Nombre artistico: ");
        String nombreArtistico = ui.leerLinea();
        ui.imprimir("Fecha de nacimiento(dd/MM/yyyy): ");
        String fechaNacimiento = ui.leerLinea();
        ui.imprimir("Fecha de defuncion(dd/MM/yyyy): ");
        String fechaDefuncion = ui.leerLinea();
        ui.imprimir("Pais de nacimiento: ");
        String paisNacimiento = ui.leerLinea();

        //Genero
        ui.imprimir("ID del genero: ");
        int idGenero = ui.leerEntero();
        Genero genero = gestor.buscarGeneroPorId(idGenero);
/*
        if(genero == null){
            ui.imprimirLinea("****No se ha encontrado un genero con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();
            if(opcion == 1){
                genero = registrarGenero();
            }
        }
*/
        int edad = calcularEdad(fechaNacimiento);
        String id = "arti0000" + nombre + fechaNacimiento;

        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        boolean resultado = gestor.crearArtista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
        if(resultado){
            ui.imprimirLinea("Artista registrado correctamente! :D");
        } else {
            ui.imprimir("Hubo un problema al intentar el registro del artista :(");
        }
    }
    private void listarArtistas() {
        ui.imprimirLinea("\n\n\tLista de artistas");
        ArrayList<Artista> artistas = gestor.listarArtistas();
        for (Artista objArtista : artistas) {
            ui.imprimirLinea(objArtista.toString());
        }

        esperarTecla();
    }
    private void modificarArtista() {
        ui.imprimirLinea("\n\n\tModificar artista");
        ui.imprimir("Ingrese el id del artista que desea modificar: ");
        int id = ui.leerEntero();

        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Apellidos: ");
        String apellidos = ui.leerLinea();
        ui.imprimir("Nombre artistico: ");
        String nombreArtistico = ui.leerLinea();
        ui.imprimir("Fecha defuncion(dd/MM/yyyy): ");
        String fechaDefuncion = ui.leerLinea();

        gestor.modificarArtista(id, nombre, apellidos, nombreArtistico, fechaDefuncion);

        esperarTecla();
    }
    private void buscarArtista() {
        ui.imprimirLinea("\n\n\tBuscar artista");
        ui.imprimir("Ingrese el id o nombre del artista que desea buscar: ");
        int dato = ui.leerEntero();

        Artista artistaEncontrado = gestor.buscarArtistaPorId(dato);
        if(artistaEncontrado != null){
            ui.imprimirLinea("Encontrado: " + artistaEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarArtista() {
        ui.imprimirLinea("\n\n\tEliminar artista");
        ui.imprimir("Ingrese el id del artista que desea eliminar: ");
        int id = ui.leerEntero();

        Artista artistaEncontrado = gestor.buscarArtistaPorId(id);
        if(artistaEncontrado != null){
            if(artistaEncontrado.getId() == id){
                gestor.eliminarArtista(artistaEncontrado);
                ui.imprimirLinea("Artista eliminado correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Canciones ++
    private void registrarCancion() {
        ui.imprimirLinea("\n\n\tRegistro de cancion");

        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Directorio de la cancion: ");
        String recurso = ui.leerLinea();
        ui.imprimir("Nombre del album: ");
        String nombreAlbum = ui.leerLinea();
        ui.imprimir("Fecha de lanzamiento(dd/MM/yyyy): ");
        String fechaLanzamiento = ui.leerLinea();

        ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();
        String id = "Cancion0000" + nombre + obtenerFechaActual();

        //Genero
        ui.imprimir("ID del genero: ");
        int idGenero = ui.leerEntero();
        Genero genero = gestor.buscarGeneroPorId(idGenero);
/*
        if(genero == null){
            ui.imprimirLinea("****No se ha encontrado un genero con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();
            if(opcion == 1){
                genero = registrarGenero();
            }
        }
*/
        //Artista
        ui.imprimir("ID del artista: ");
        int idArtista = ui.leerEntero();
        Artista artista = gestor.buscarArtistaPorId(idArtista);
/*
        if(artista == null){
            ui.imprimirLinea("****No se ha encontrado un artista con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();
            if(opcion == 1){
                artista = registrarArtista();
            }
        }
*/
        //Compositor
        ui.imprimir("ID del compositor: ");
        int idCompositor = ui.leerEntero();
        Compositor compositor = gestor.buscarCompositorPorId(idCompositor);
/*
        if(compositor == null){
            ui.imprimirLinea("****No se ha encontrado un compositor con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();
            if(opcion == 1){
                compositor = registrarCompositor();
            }
        }
*/
        //Precio
        double precio = 0.0f;
        if(usuarioIngresado.esAdmin()){
            ui.imprimir("Precio: ");
            precio = ui.leerDouble();
        }

        Cancion cancionCreada = gestor.crearCancion(nombre, recurso, nombreAlbum, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);

        //Si es admin la guarda en la biblioteca general, si es cliente la guarda en SU biblioteca
        if(usuarioIngresado.esAdmin()){
            gestor.guardarCancion(cancionCreada);
        } else {
            gestor.agregarCancionABibliotecaUsuario(usuarioIngresado.getId(), cancionCreada);
        }
    }
    private void listarCanciones() {
        ui.imprimirLinea("\n\n\tLista de canciones");
        ArrayList<Cancion> canciones = gestor.listarCanciones();
        for (Cancion objCancion : canciones) {
            ui.imprimirLinea(objCancion.toString());
        }

        esperarTecla();
    }
    private void modificarCancion() {
        ui.imprimirLinea("\n\n\tModificar cancion");
        ui.imprimir("Ingrese el ID de la cancion que desea modificar: ");
        int id = ui.leerEntero();

        ui.imprimir("Nombre del album: ");
        String album = ui.leerLinea();
        ui.imprimir("Precio: ");
        double precio = ui.leerDouble();

        gestor.modificarCancion(id, album, precio);

        esperarTecla();
    }
    private void buscarCancion() {
        ui.imprimirLinea("\n\n\tBuscar cancion");
        ui.imprimir("Ingrese el id o nombre de la cancion que desea buscar: ");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada = gestor.buscarCancionPorId(idCancion);
        if(cancionEncontrada != null){
            ui.imprimirLinea("Encontrado: " + cancionEncontrada.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarCancion() {
        ui.imprimirLinea("\n\n\tEliminar cancion");
        ui.imprimir("Ingrese el id de la cancion que desea eliminar: ");
        int id = ui.leerEntero();

        Cancion cancionEncontrada = gestor.buscarCancionPorId(id);
        if(cancionEncontrada != null){
            if(cancionEncontrada.getId() == id){
                gestor.eliminarCancion(cancionEncontrada);
                ui.imprimirLinea("Cancion eliminada correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Albunes ++
    private void registrarAlbum() {
        ui.imprimirLinea("\n\n\tRegistro de album");
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();

        String fechaCreacion = obtenerFechaActual();
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        ArrayList<Artista> artistas = new ArrayList<Artista>();

        ui.imprimir("Fecha de lanzamiento(dd/MM/yyyy): ");
        String fechaLanzamiento = ui.leerLinea();
        ui.imprimir("Directorio de la imagen para la portada: ");
        String imagen = ui.leerLinea();

        //Compositor
        ui.imprimir("ID del compositor: ");
        int idCompositor = ui.leerEntero();
        Compositor compositor = gestor.buscarCompositorPorId(idCompositor);
/*
        if(compositor == null){
            ui.imprimirLinea("****No se ha encontrado un compositor con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion = ui.leerEntero();
            if(opcion == 1){
                compositor = registrarCompositor();
            }
        }
*/
        String id = "album000" + nombre + fechaCreacion;
        boolean resultado = gestor.crearAlbum(nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas, compositor);

        if(resultado){
            ui.imprimirLinea("Album registrado correctamente! :D");
        } else {
            ui.imprimirLinea("Ha ocurrido un problema al intentar el registro del album :(");
        }
    }
    private void listarAlbunes() {
        ui.imprimirLinea("\n\n\tLista de albunes");
        ArrayList<Album> albunes = gestor.listarAlbunes();
        for (Album objAlbum : albunes) {
            ui.imprimirLinea(objAlbum.toString());
        }

        esperarTecla();
    }
    private void modificarAlbum() {
        ui.imprimirLinea("\n\n\tModificar album");

        ui.imprimirLinea("Que desea realizar?");
        ui.imprimirLinea("1. Agregar canciones en el album");
        ui.imprimirLinea("2. Remover canciones del album");
        ui.imprimirLinea("3. Agregar Artistas en el album");
        ui.imprimirLinea("4. Remover canciones del album");
        ui.imprimirLinea("5. Modificar datos del album");
        ui.imprimir("Su opcion: ");
        int opcion = ui.leerEntero();
        switch (opcion){
            case 1:
                incluirCancionEnAlbum();
                return;
            case 2:
                removerCancionDeAlbum();
                return;
            case 3:
                incluirArtistaEnAlbum();
                return;
            case 4:
                removerArtistaDeAlbum();
                return;
            case 5:
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
                return;
        }

        ui.imprimir("Ingrese el id del album que desea modificar: ");
        int id = ui.leerEntero();
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Directorio de la imagen para la portada: ");
        String imagen = ui.leerLinea();

        //Compositor
        ui.imprimir("ID del compositor: ");
        int idCompositor = ui.leerEntero();
        Compositor compositor = gestor.buscarCompositorPorId(idCompositor);
/*
        if(compositor == null){
            ui.imprimirLinea("****No se ha encontrado un compositor con ese ID, desea crear uno?****");
            ui.imprimirLinea("1. Si");
            ui.imprimirLinea("2. No");
            ui.imprimir("Su opcion: ");
            int opcion1 = ui.leerEntero();
            if(opcion1 == 1){
                compositor = registrarCompositor();
            }
        }
*/
        gestor.modificarAlbum(id, nombre, imagen, compositor);

        esperarTecla();
    }
    private void buscarAlbum() {
        ui.imprimirLinea("\n\n\tBuscar album");
        ui.imprimir("Ingrese el id del album que desea buscar: ");
        int idAlbum = ui.leerEntero();

        Album albumEncontrado = gestor.buscarAlbumPorId(idAlbum);
        if(albumEncontrado != null){
            ui.imprimirLinea("Encontrado: " + albumEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void incluirCancionEnAlbum() {
        //Primero pide id o nombre de la cancion
        //Luego busca el album
        ui.imprimirLinea("\n\n\tIncluir cancion en album");

        ui.imprimir("Ingrese el ID de la cancion que desea ingresar: ");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada;

        //El admin puede agregar cualquier cancion existente
        //El usuario normal solo puede agregar las que tenga es su biblioteca
        if(usuarioIngresado.esAdmin()){
            cancionEncontrada = gestor.buscarCancionPorId(idCancion);
        } else {
            cancionEncontrada = gestor.buscarCancionEnBibliotecaUsuario(usuarioIngresado.getId(), idCancion);
        }

        if(cancionEncontrada != null){
            ui.imprimir("Ingrese el ID o nombre del album en el que desea incluir la cancion: ");
            int idAlbum = ui.leerEntero();

            boolean resultado = gestor.agregarCancionEnAlbum(idAlbum, cancionEncontrada);

            if(resultado){
                ui.imprimirLinea("Cancion agregada correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al agregar la cancion :(");
            }
        } else {
            ui.imprimirLinea("No se encontró ninguna canción :(");
        }

        esperarTecla();
    }
    private void removerCancionDeAlbum() {
        //Primero busca album
        //Luego pide id o nombre de la cancion
        ui.imprimirLinea("\n\n\tRemover cancion de album");
        ui.imprimir("Ingrese el ID o nombre del album en el que desea remover la cancion: ");
        int idAlbum = ui.leerEntero();

        Album albumEncontrado = gestor.buscarAlbumPorId(idAlbum);

        if(albumEncontrado != null){
            ui.imprimir("Ingrese el ID de la cancion que desea remover: ");
            int idCancion = ui.leerEntero();

            boolean resultado = gestor.removerCancionDeAlbum(idAlbum, idCancion);
            if(resultado){
                ui.imprimirLinea("Cancion removida correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar remover la cancion del album :(");
            }
        } else {
            ui.imprimirLinea("No se encontró ningún album :(");
        }

        esperarTecla();
    }
    private void incluirArtistaEnAlbum() {
        //Primero pide id o nombre de la cancion
        //Luego busca el album
        ui.imprimirLinea("\n\n\tIncluir cancion en album");

        ui.imprimir("Ingrese el ID o nombre de la cancion que desea ingresar: ");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada;

        //El admin puede agregar cualquier cancion existente
        //El usuario normal solo puede agregar las que tenga es su biblioteca
        if(usuarioIngresado.esAdmin()){
            cancionEncontrada = gestor.buscarCancionPorId(idCancion);
        } else {
            cancionEncontrada = gestor.buscarCancionEnBibliotecaUsuario(usuarioIngresado.getId(), idCancion);
        }

        if(cancionEncontrada != null){
            ui.imprimir("Ingrese el ID o nombre del album en el que desea incluir la cancion: ");
            int idAlbum = ui.leerEntero();

            boolean resultado = gestor.agregarCancionEnAlbum(idAlbum, cancionEncontrada);

            if(resultado){
                ui.imprimirLinea("Cancion agregada correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al agregar la cancion :(");
            }
        } else {
            ui.imprimirLinea("No se encontró ninguna canción :(");
        }

        esperarTecla();
    }
    private void removerArtistaDeAlbum() {
        //Primero busca album
        //Luego pide id o nombre de la cancion
        ui.imprimirLinea("\n\n\tRemover cancion de album");
        ui.imprimir("Ingrese el ID o nombre del album en el que desea remover la cancion: ");
        int idAlbum = ui.leerEntero();

        Album albumEncontrado = gestor.buscarAlbumPorId(idAlbum);

        if(albumEncontrado != null){
            ui.imprimir("Ingrese el ID o nombre de la cancion que desea remover: ");
            int idCancion = ui.leerEntero();

            boolean resultado = gestor.removerCancionDeAlbum(idAlbum, idCancion);
            if(resultado){
                ui.imprimirLinea("Cancion removida correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar remover la cancion del album :(");
            }
        } else {
            ui.imprimirLinea("No se encontró ningún album :(");
        }

        esperarTecla();
    }
    private void eliminarAlbum() {
        ui.imprimirLinea("\n\n\tEliminar album");
        ui.imprimir("Ingrese el id del album que desea eliminar: ");
        int id = ui.leerEntero();

        Album albumEncontrado = gestor.buscarAlbumPorId(id);
        if(albumEncontrado != null){
            if(albumEncontrado.getId() == id){
                gestor.eliminarAlbum(albumEncontrado);
                ui.imprimirLinea("Album eliminado correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Listas de reproduccion ++
    private void registrarListaReproduccion() {
        ui.imprimirLinea("\n\n\tRegistro de lista de reproduccion");

        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Directorio de la imagen que se va a usar en la portada: ");
        String imagen = ui.leerLinea();

        String fechaCreacion = obtenerFechaActual();
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        int idCreador = usuarioIngresado.getId();
        String id = "ListRep000" + nombre + fechaCreacion;

        boolean resultado = gestor.crearListaReproduccion(id, nombre, fechaCreacion, canciones, idCreador, imagen);

        if(resultado){
            ui.imprimirLinea("Lista de reproduccion registrada correctamente! :D");
        } else {
            ui.imprimirLinea("Hubo un problema al intentar el registro de la lista de reproduccion :(");
        }
    }
    private void listarListasDeReproduccion() {
        ui.imprimirLinea("\n\n\tLista de listas de reproduccion");
        ArrayList<ListaReproduccion> listasReproduccion = gestor.listarListasReproduccion();
        for (ListaReproduccion objListaReproduccion : listasReproduccion) {
            ui.imprimirLinea(objListaReproduccion.toString());
        }

        esperarTecla();
    }
    private void modificarListaReproduccion() {
        ui.imprimirLinea("\n\n\tModificar lista de reproduccion");

        ui.imprimirLinea("Que desea realizar?");
        ui.imprimirLinea("1. Agregar canciones en la lista");
        ui.imprimirLinea("2. Remover canciones de la lista");
        ui.imprimirLinea("3. Modificar datos de la lista");
        ui.imprimir("Su opcion: ");
        int opcion = ui.leerEntero();
        switch (opcion){
            case 1:
                incluirCancionEnListaReproduccion();
                return;
            case 2:
                removerCancionDeListaReproduccion();
                return;
            case 3:
                break;
            default:
                ui.imprimirLinea("Opcion invalida");
                return;
        }

        ui.imprimir("Ingrese el id de la lista de reproduccion que desea modificar: ");
        int id = ui.leerEntero();
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Directorio de la imagen que se va a usar en la portada: ");
        String imagen = ui.leerLinea();

        gestor.modificarListaReproduccion(id, nombre, imagen);

        esperarTecla();
    }
    private void buscarListaReproduccion() {
        ui.imprimirLinea("\n\n\tBuscar lista de reproduccion");
        ui.imprimir("Ingrese el id o nombre de la lista de reproduccion que desea buscar: ");
        int idLista = ui.leerEntero();

        ListaReproduccion listaEncontrada = gestor.buscarListaReproduccionPorId(idLista);
        if(listaEncontrada != null){
            ui.imprimirLinea("Encontrado: " + listaEncontrada.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void incluirCancionEnListaReproduccion() {
        //Primero pide id o nombre de la cancion
        //Luego busca lista de reproduccion
        ui.imprimirLinea("\n\n\tIncluir cancion en lista de reproduccion");

        ui.imprimir("Ingrese el ID o nombre de la cancion que desea ingresar: ");
        int idCancion = ui.leerEntero();

        Cancion cancionEncontrada;

        //El admin puede agregar cualquier cancion existente
        //El usuario normal solo puede agregar las que tenga es su biblioteca
        if(usuarioIngresado.esAdmin()){
            cancionEncontrada = gestor.buscarCancionPorId(idCancion);
        } else {
            cancionEncontrada = gestor.buscarCancionEnBibliotecaUsuario(usuarioIngresado.getId(), idCancion);
        }

        if(cancionEncontrada != null){
            ui.imprimir("Ingrese el ID o nombre de la lista de reproduccion en la que desea incluir la cancion: ");
            int idLista = ui.leerEntero();

            boolean resultado = gestor.agregarCancionALista(idLista, cancionEncontrada);

            if(resultado){
                ui.imprimirLinea("Cancion agregada correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al agregar la cancion :(");
            }
        } else {
            ui.imprimirLinea("No se encontró la canción :(");
        }

        esperarTecla();
    }
    private void removerCancionDeListaReproduccion() {
        //Primero busca lista de reproduccion
        //Luego pide id o nombre de la cancion
        ui.imprimirLinea("\n\n\tRemover cancion de lista de reproduccion");
        ui.imprimir("Ingrese el ID o nombre de la lista de reproduccion en la que desea remover la cancion: ");
        int idLista = ui.leerEntero();

        ListaReproduccion listaEncontrada = gestor.buscarListaReproduccionPorId(idLista);

        if(listaEncontrada != null){
            ui.imprimir("Ingrese el ID o nombre de la cancion que desea remover: ");
            int idCancion = ui.leerEntero();

            boolean resultado = gestor.removerCancionDeLista(idLista, idCancion);
            if(resultado){
                ui.imprimirLinea("Cancion removida correctamente! :D");
            } else {
                ui.imprimirLinea("Hubo un problema al intentar remover la cancion de la lista de reproduccion :(");
            }
        } else {
            ui.imprimirLinea("No se encontró ninguna lista de reproduccion :(");
        }

        esperarTecla();
    }
    private void eliminarListaReproduccion() {
        ui.imprimirLinea("\n\n\tEliminar lista de reproduccion");
        ui.imprimir("Ingrese el id de la lista de reproduccion que desea eliminar: ");
        int id = ui.leerEntero();

        ListaReproduccion listaEncontrada = gestor.buscarListaReproduccionPorId(id);
        if(listaEncontrada != null){
            if(listaEncontrada.getId() == id){
                gestor.eliminarListaReproduccion(listaEncontrada);
                ui.imprimirLinea("Lista de reproduccion eliminada correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }


    //Paises ++
    private void registrarPais() {
        ui.imprimirLinea("\n\n\tRegistro de pais");
        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        String id = "Pais000" + nombre + obtenerFechaActual();

        boolean resultado = gestor.crearPais(nombre, descripcion);

        if(resultado){
            ui.imprimirLinea("Pais registrado correctamente! :D");
        } else {
            ui.imprimirLinea("Hubo un problema al intentar el registro del pais");
        }
    }
    private void listarPaises() {
        ui.imprimirLinea("\n\n\tLista de paises");
        ArrayList<Pais> paises = gestor.listarPaises();
        for (Pais objPais : paises) {
            ui.imprimirLinea(objPais.toString());
        }

        esperarTecla();
    }
    private void modificarPais() {
        ui.imprimirLinea("\n\n\tModificar pais");
        ui.imprimir("Ingrese el ID del pais que desea modificar: ");
        int id = ui.leerEntero();

        ui.imprimir("Nombre: ");
        String nombre = ui.leerLinea();
        ui.imprimir("Descripcion: ");
        String descripcion = ui.leerLinea();

        gestor.modificarPais(id, nombre, descripcion);

        esperarTecla();
    }
    private void buscarPais() {
        ui.imprimirLinea("\n\n\tBuscar pais");
        ui.imprimir("Ingrese el ID o nombre del pais que desea buscar: ");
        int idPais = ui.leerEntero();

        Pais paisEncontrado = gestor.buscarPaisPorId(idPais);
        if(paisEncontrado != null){
            ui.imprimirLinea("Encontrado: " + paisEncontrado.toString());
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
    private void eliminarPais() {
        ui.imprimirLinea("\n\n\tEliminar pais");
        ui.imprimir("Ingrese el ID del pais que desea eliminar: ");
        int id = ui.leerEntero();

        Pais paisEncontrado = gestor.buscarPaisPorId(id);
        if(paisEncontrado != null){
            if(paisEncontrado.getId() == id){
                gestor.eliminarPais(paisEncontrado);
                ui.imprimirLinea("Pais eliminado correctamente");
            }
        } else {
            ui.imprimirLinea("No hay resultados");
        }

        esperarTecla();
    }
}
