package segura.taylor.bl.gestor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import segura.taylor.PropertiesHandler;
import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.dao.*;

/**
 * La clase gestor se encarga de realizar la conexión entre el controlador y los DAOs
 *
 * @author Taylor Segura Vindas
 * @version 1.0
 */
public class Gestor {
    //Variables
    private Usuario usuarioIngresado;

    private Connection connection;
    private PropertiesHandler propertiesHandler = new PropertiesHandler();

    private ArtistaDAO artistaDAO;
    private CancionDAO cancionDAO;
    private CompositorDAO compostorDAO;
    private GeneroDAO generoDAO;
    private PaisDAO paisDAO;
    private RepositorioCancionesDAO repoCancionesDAO;
    private UsuarioDAO usuarioDAO;

    //Intermedias
    private CancionesAlbumDAO cancionesAlbumDAO;
    private ArtistasAlbumDAO artistasAlbumDAO;
    private CancionesListaReproduccionDAO cancionesListaReproduccionDAO;
    private CancionesBibliotecaDAO cancionesBibliotecaDAO;
    private ListasReproduccionBibliotecaDAO listasBibliotecaDAO;

    /**
     * Método constructor
     * Inicializa la conexión con la base de datos y los DAOs que posteriormente serán usados
     */
    public Gestor(){
        try {
            propertiesHandler.loadProperties();
            //ABRIR DB
            String driver = propertiesHandler.getDriver();
            try {
                Class.forName(driver).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            System.out.println("LOADED DRIVER ---> " + driver);
            String url = propertiesHandler.getCnxStr();
            this.connection = DriverManager.getConnection(url, propertiesHandler.getUser(), propertiesHandler.getPwd());
            System.out.println("CONNECTED TO ---> "+ url);

            this.artistaDAO = new ArtistaDAO(this.connection);
            this.cancionDAO = new CancionDAO(this.connection);
            this.compostorDAO = new CompositorDAO(this.connection);
            this.generoDAO = new GeneroDAO(this.connection);
            this.paisDAO = new PaisDAO(this.connection);
            this.repoCancionesDAO = new RepositorioCancionesDAO(this.connection);
            this.usuarioDAO = new UsuarioDAO(this.connection);

            this.cancionesAlbumDAO = new CancionesAlbumDAO(this.connection);
            this.artistasAlbumDAO = new ArtistasAlbumDAO(this.connection);
            this.cancionesListaReproduccionDAO = new CancionesListaReproduccionDAO(this.connection);
            this.cancionesBibliotecaDAO = new CancionesBibliotecaDAO(this.connection);
            this.listasBibliotecaDAO = new ListasReproduccionBibliotecaDAO(this.connection);
        } catch (Exception e) {
            System.out.println("CANT CONNECT TO DATABASE");
            e.printStackTrace();
        }
    }

    //Metodos

    public void reiniciar() {
        usuarioIngresado = null;
    }

    /**
     * Verifica si el usuario que usa la aplicación es administrador
     * @return true si es administrador, false si no lo es
     */
    public boolean usuarioIngresadoEsAdmin() {
        return usuarioIngresado.esAdmin();
    }
    /**
     * Verifica si el usuario que usa la aplicación es creador
     * @return true si es creador, false si no lo es
     */
    public boolean usuarioIngresadoEsCreador() {
        return usuarioIngresado.esCreador();
    }

    /**
     * Método usado para conocer el usuario ingresado.
     * @return
     */
    public Usuario getUsuarioIngresado() {
        return usuarioIngresado;
    }

    /**
     * Metodo usado para conocer el id del usuario que está usando la aplicación
     * @return id del usuario que está usando la aplicación
     */
    public int getIdUsuarioIngresado() {
        return usuarioIngresado.getId();
    }

    /**
     * Método usado para obtener la biblioteca del cliente ingresado.
     * @return instancia de la clase Biblioteca que corresponde a la que le pertenece al cliente ingresado
     */
    public Biblioteca getBibliotecaUsuarioIngresado() {
        Cliente clienteIngresado = (Cliente) usuarioIngresado;
        return clienteIngresado.getBiblioteca();
    }

    //*******General**********
    /**
     * Método usado para verificar la existencia del usuario administrador
     * @return true si existe un admin, false si no
     */
    public boolean existeAdmin(){
        try {
            if(usuarioDAO.findAll().size() == 0){
                return false;
            }

            return (usuarioDAO.findAll().get(0).esAdmin());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método usado para iniciar sesión. Busca el usuario que coincide con los atributos enviados como parámetros
     * @param pCorreo el correo del usuario
     * @param pContrasenna la contraseña del usuario
     * @return true si se encuentra alguna coincidencia, false si no.
     */
    public boolean iniciarSesion(String pCorreo, String pContrasenna){
        try {
            for (Usuario objUsuario: usuarioDAO.findAll()) {
                if(objUsuario.getCorreo().equals(pCorreo)){
                    if(objUsuario.getContrasenna().equals(pContrasenna)){
                        usuarioIngresado = objUsuario;
                        System.out.println("Usuario: " + objUsuario.getTipoUsuario());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //*******Manejo de usuarios*******
    /**
     * Método usado para el registro del usuario admin
     * @param correo String que define el correo del usuario
     * @param contrasenna String que define la contraseña del usuario
     * @param nombre String que define el nombre del usuario
     * @param apellidos String que define los apellidos del usuario
     * @param imagenPerfil String que define la ruta de la imagen de perfil del usuario
     * @param nombreUsuario String que define el nombre de usuario
     * @param fechaCreacion LocalDate que define la fecha de creación del usuario
     * @return true si se logra crear, false si ocurre algún error
     */
    public boolean crearUsuarioAdmin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaCreacion) {
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaCreacion);

        return usuarioDAO.save(admin);
    }

    /**
     * Metodo usado para registrar clientes
     * @param correo String que define el nombre del usuario
     * @param contrasenna String que define la contraseña del usuario
     * @param nombre String que define el nombre del usuario
     * @param apellidos String que define los apellidos del usuario
     * @param imagenPerfil String que define la ruta de la imagen de perfil
     * @param nombreUsuario String que define el nombre de usuario
     * @param fechaNacimiento LocalDate que define la fecha de nacimiento
     * @param edad int que define la edad
     * @param idPais int que define el id del pais en el que vive el usurio
     * @return true si el registro es éxitoso, false si ocurre algún error
     */
    public boolean crearUsuarioCliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaNacimiento, int edad, int idPais) {
        //Si no hay admin no se puede registrar usuarios.
        //if(usuarioDAO.findAll().size() == 0) return false;
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNombre("Biblioteca " + nombreUsuario);
        biblioteca.setFechaCreacion(LocalDate.now());

        Pais pais = buscarPaisPorId(idPais).get();

        System.out.println(biblioteca);

        int idBibliotecaGuardada = repoCancionesDAO.save(biblioteca);
        biblioteca.setId(idBibliotecaGuardada);

        //Pais pais = buscarPaisPorId(idPais).get();
        Cliente nuevoCliente = new Cliente(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais, biblioteca);
        nuevoCliente.setBiblioteca(biblioteca);

        return usuarioDAO.save(nuevoCliente);
    }

    /**
     * Método usado para modificar un usuario
     * @param id int que define el id del usuario que se va a modificar
     * @param pNombreUsuario String que define el nuevo nombre de usuario
     * @param pImagenPerfil String que define la nueva ruta de la imagen de perfil del usuario
     * @param pNombre String que define el nuevo nombre del usuario
     * @param pApellidos String que define los nuevos apellidos del usuario
     * @return true si la modificación es existosa, false si ocurre algún error
     * @throws Exception si no se puede realizar la conexión con la DB o si el usuario no existe
     */
    public boolean modificarUsuario(int id, String correo, String pNombreUsuario, String pImagenPerfil, String pNombre, String pApellidos) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(id);

        if(usuarioEncontrado.isPresent()){

            Usuario usuarioModifica = usuarioEncontrado.get();

            usuarioModifica.setCorreo(correo);
            usuarioModifica.setNombreUsuario(pNombreUsuario);
            usuarioModifica.setImagenPerfil(pImagenPerfil);
            usuarioModifica.setNombre(pNombre);
            usuarioModifica.setApellidos(pApellidos);

            if(usuarioIngresado.getId() == usuarioModifica.getId()) {
                usuarioIngresado = usuarioModifica; //Actualizar info del usuario ingresado.
            }

            return usuarioDAO.update(usuarioModifica);
        }

        return false;
    }

    /**
     * Método usado para modificar la contraseña de un usuario
     * @param idUsuario int que define el id del usuario que se va a modificar su contraseña
     * @param nuevaContrasenna String que define la nueva contraseña
     * @return true si la modificacion es exitosa, false si ocurre algun error
     */
    public boolean modificarContrasennaUsuario(int idUsuario, String nuevaContrasenna) {
        //TODO Modificar contraseña con correo y todo eso
        return true;
    }

    /**
     * Método usado para eliminar un usuario
     * @param idUsuario int que define el id del usuario que se va a eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede realizar la conexion con la DB o si el usuario no existe
     */
    public boolean eliminarUsuario(int idUsuario) throws Exception {
        return usuarioDAO.delete(idUsuario);
    }

    /**
     * Método usado para obtener una lista con todos los usuarios almacenados
     * @return lista con todos los usuarios almacenados
     * @see List
     * @see Usuario
     */
    public List<Usuario> listarUsuarios(){
        try {
            return Collections.unmodifiableList(usuarioDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un usuario usando como filtro su id
     * @param pId int que define el id del usuario que se desea buscar
     * @return instancia de la clase Usuario si se encuentra alguno, null si no hay coincidencias
     */
    public Optional<Usuario> buscarUsuarioPorId(int pId){
        try {
            return usuarioDAO.findByID(pId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
    /**
     * Método usado para buscar un usuario por correo
     * @param pCorreo String que define el correo del usuario que se desea buscar
     * @return instancia de la clase Usuario si se encuentra alguno, null si no hay coincidencias
     */
    public Optional<Usuario> buscarUsuarioPorCorreo(String pCorreo){
        try {
            return usuarioDAO.findByEmail(pCorreo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //*******Manejo de albunes********
    /**
     * Método usado para crear un album
     * @param nombre String que define el nombre del album
     * @param fechaCreacion LocalDate que define la fecha de creacion del album
     * @param fechaLanzamiento LocalDate que define la fecha de lanzamiento del album
     * @param imagen String que define la imagen del album
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean crearAlbum(String nombre, LocalDate fechaCreacion, LocalDate fechaLanzamiento, String imagen) {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        ArrayList<Artista> artistas = new ArrayList<Artista>();

        Album nuevoAlbum = new Album(nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas);
        return (repoCancionesDAO.save(nuevoAlbum)) != -1;
    }

    /**
     * Método usado para modificar un album
     * @param pId int que define el id del album que se desea modificar
     * @param pNombre String que define el nuevo nombre del album
     * @param pImagen String que define la nueva imagen del album
     * @return true si la modificacion es exitosa, false si ocurre algún error
     * @throws Exception si no se puede hacer la conexion con la DB o si el album no existe
     */
    public boolean modificarAlbum(int pId, String pNombre, String pImagen) throws Exception {
        Optional<Album> albumEncontrado = repoCancionesDAO.findAlbumById(pId);

        if(albumEncontrado.isPresent()){
            Album albumModifica = albumEncontrado.get();
            albumModifica.setNombre(pNombre);
            albumModifica.setImagen(pImagen);

            return repoCancionesDAO.update(albumModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar un album
     * @param idAlbum int que define el id del album que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algún error
     * @throws Exception si no se puede hacer la conexion con la DB o si el album no existe
     */
    public boolean eliminarAlbum(int idAlbum) throws Exception {
        return repoCancionesDAO.delete(idAlbum);
    }

    /**
     * Método usado para obtener una lista con todos los albunes almacenados
     * @return una lista con todos los albunes almacenados
     * @see List
     * @see Album
     */
    public List<Album> listarAlbunes(){
        try {
            return Collections.unmodifiableList(repoCancionesDAO.findAlbunes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un album usando como filtro su id
     * @param pId int que define el id del album que se desea encontrar
     * @return objeto de tipo Optiona que contiene una instancia de Album si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public Optional<Album> buscarAlbumPorId(int pId) throws SQLException {
        return repoCancionesDAO.findAlbumById(pId);
    }

    /**
     * Método usado para agregar una canción a un album
     * @param pIdAlbum int que define el id del album que se va a modificar
     * @param pIdCancion int que define el id de la canción que se desea incluir
     * @return true si la agregación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el album o la cancion no existe
     */
    public boolean agregarCancionEnAlbum(int pIdAlbum, int pIdCancion) throws Exception {
        Optional<Album> repoEncontrado = repoCancionesDAO.findAlbumById(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            Optional<Cancion> nuevaCancion = cancionDAO.findByID(pIdCancion);

            //Busca cancion
            if(nuevaCancion.isPresent())
            {
                return cancionesAlbumDAO.save(pIdAlbum, pIdCancion); //Modifica tabla intermedia
            }
        }

        return false;
    }

    /**
     * Método usado para remover una canción de un album
     * @param pIdAlbum int que define el id del album que se va a modificar
     * @param pIdCancion int que define el id de la canción que se desea remover
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el album o la cancion no existe
     */
    public boolean removerCancionDeAlbum(int pIdAlbum, int pIdCancion) throws Exception {
        Optional<Album> repoEncontrado = repoCancionesDAO.findAlbumById(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            return cancionesAlbumDAO.delete(pIdAlbum, pIdCancion); //Modifica tabla intermedia
        }

        return false;
    }

    /**
     * Método usado para agregar un artista a un album
     * @param pIdAlbum int que define el id del album que se va a modificar
     * @param pIdArtista int que define el id del artista que se desea incluir
     * @return true si la agregación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el album o artista no existe
     */
    public boolean agregarArtistaEnAlbum(int pIdAlbum, int pIdArtista) throws Exception {
        Optional<Album> repoEncontrado = repoCancionesDAO.findAlbumById(pIdAlbum);
        Optional<Artista> artistaEncontrado = artistaDAO.findByID(pIdArtista);

        //Busca album
        if(repoEncontrado.isPresent()){
            if(artistaEncontrado.isPresent()) {
                //albumModifica.agregarArtista(artistaEncontrado.get());
                return artistasAlbumDAO.save(pIdAlbum, pIdArtista);
            }
        }

        return false;
    }

    /**
     * Método usado para remover un artista de un albm
     * @param pIdAlbum int que define el id del album que se va a modificar
     * @param pIdArtista int que define el id del artista que se desea remover
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el album o artista no existe
     */
    public boolean removerArtistaDeAlbum(int pIdAlbum, int pIdArtista) throws Exception {
        Optional<Album> repoEncontrado = repoCancionesDAO.findAlbumById(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            return artistasAlbumDAO.delete(pIdAlbum, pIdArtista);
        }

        return false;
    }


    //*********Manejo de Listas de Reproduccion***************
    public int obtenerIdListaReproduccion(String nombre) {
        try {
            return repoCancionesDAO.getIdLista(nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Método usado para crear una lista de reproducción
     * @param nombre String que define el nombre de la lista
     * @param fechaCreacion LocalDate que define la fecha de creacion de la lista
     * @param imagen String que define la imagen de la lista
     * @param descripcion String que define la descripción de la lista
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si la lista de reproduccion ya existe
     */
    public int crearListaReproduccion(String nombre, LocalDate fechaCreacion, String imagen, String descripcion) throws Exception {
        ArrayList<Cancion> canciones = new ArrayList<>();   //Las listas de reproducción SIEMPRE se crea sin canciones por defecto

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, fechaCreacion, canciones, 0.0, imagen, descripcion);
        return repoCancionesDAO.save(nuevaLista);
    }

    /**
     * Método usado para modificar una lista de reproduccion
     * @param pIdLista int que define el id de la lista que se va a modificar
     * @param pNombre String que define el nuevo nombre de la lista
     * @param pImagen String que define la nueva ruta de la imagen de la lista
     * @param pDescripcion String que define la nueva descripcio de la lista
     * @return true si la modificación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si la lista de reproduccion no existe
     */
    public boolean modificarListaReproduccion(int pIdLista, String pNombre, String pImagen, String pDescripcion) throws Exception {
        Optional<ListaReproduccion> listaEncontrada = repoCancionesDAO.findListaReproduccionById(pIdLista);

        if(listaEncontrada.isPresent()) {
            ListaReproduccion listaModifica = listaEncontrada.get();
            listaModifica.setNombre(pNombre);
            listaModifica.setImagen(pImagen);
            listaModifica.setDescripcion(pDescripcion);

            return repoCancionesDAO.update(listaModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar una lista de reproduccion
     * @param pIdLista int que define el id de la lista de reproduccion que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si nose puede conectar con la DB o si la lista de reproduccion no existe
     */
    public boolean eliminarListaReproduccion(int pIdLista) throws Exception {
        return repoCancionesDAO.delete(pIdLista);
    }

    /**
     * Método para obtener la lista de todas las listas de reproduccion almacenadas
     * @return lista con las listas de reproduccion almacenadas
     * @see List
     * @see ListaReproduccion
     */
    public List<ListaReproduccion> listarListasReproduccion(){
        try {
            return Collections.unmodifiableList(repoCancionesDAO.findListasReproduccion());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para agregar una cancion a una lista de reproduccion
     * @param pIdLista int que define el id de la lista que se va a modificar
     * @param pIdCancion int que define el id de la cancion que se va a incluir
     * @return true si la agregación es exitosa, false si ocurre algún error
     * @throws Exception si no se logra conectar con la DB o si la lista de reproduccion o cancion no existe
     */
    public boolean agregarCancionALista(int pIdLista, int pIdCancion) throws Exception {
        Optional<ListaReproduccion> repoEncontrado = repoCancionesDAO.findListaReproduccionById(pIdLista);

        //Busca lista de reproduccion
        if(repoEncontrado.isPresent()){
            Optional<Cancion> nuevaCancion = cancionDAO.findByID(pIdCancion);

            //Busca cancion
            if(nuevaCancion.isPresent())
            {
                return cancionesListaReproduccionDAO.save(pIdLista, pIdCancion);
            }
        }

        return false;
    }

    /**
     * Método usado para remover una cancion de una lista de reproduccion
     * @param pIdLista int que define el id de la lista que se va a modificar
     * @param pIdCancion int que define el id de la cancion que se desea remover
     * @return true si la eliminacion es exitosa, false si ocurre algún error
     * @throws Exception si no se logra conectar con la DB o si la lista de reproduccion o cancion no existe
     */
    public boolean removerCancionDeLista(int pIdLista, int pIdCancion) throws Exception {
        Optional<ListaReproduccion> repoEncontrado = repoCancionesDAO.findListaReproduccionById(pIdLista);

        //Busca lista de reproduccion
        if(repoEncontrado.isPresent()){
           return cancionesListaReproduccionDAO.delete(pIdLista, pIdCancion);
        }

        return false;
    }

    /**
     * Método usado para buscar una lista de reproduccion usando como filtro su id
     * @param pIdLista int que define el id de la lista que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de ListaReproduccion si se encuentra una coincidencia
     * @see Optional
     * @see ListaReproduccion
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public Optional<ListaReproduccion> buscarListaReproduccionPorId(int pIdLista) throws SQLException {
        return repoCancionesDAO.findListaReproduccionById(pIdLista);
    }


    //*****************Manejo de artistas*******************
    /**
     * Método usado para crear un artista
     * @param nombre String que define el nombre del artista
     * @param apellidos String que define los apellidos del artista
     * @param nombreArtistico String que define el nombre artistico del artista
     * @param fechaNacimiento LocalDate que define la fecha de nacimiento del artista
     * @param fechaDefuncion LocalDate que define la fecha de defuncion del artista
     * @param idPaisNacimiento int que define el id del pais donde vive el artista
     * @param idGenero int que define el id del genero del artista
     * @param edad int que define la edad del artista
     * @param descripcion String que define la descripcion del artista
     * @return true si el registro es exitoso, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el artista ya existe
     */
    public boolean crearArtista(String nombre, String apellidos, String nombreArtistico, LocalDate fechaNacimiento, LocalDate fechaDefuncion, int idPaisNacimiento, int idGenero, int edad, String descripcion) throws Exception {
        Pais paisNacimiento = buscarPaisPorId(idPaisNacimiento).get();
        Genero genero = buscarGeneroPorId(idGenero).get();

        Artista nuevoArtista = new Artista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
        return artistaDAO.save(nuevoArtista);
    }

    /**
     * Método usado para modificar un artista
     * @param pIdArtista int que define el id del artista que se desea modificar
     * @param pNombre String que define el nuevo nombre del artista
     * @param pApellidos String que define los nuevos apellidos del artista
     * @param pNomArtistico String que define el nuevo nombre artistico del artista
     * @param pFechaDefuncion LocalDate que define la nueva fecha de defunción del artista
     * @param pDescripcion String que define la nueva descripcion del artista
     * @return true si la modificacion es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB o si el artista no existe
     */
    public boolean modificarArtista(int pIdArtista, String pNombre, String pApellidos, String pNomArtistico, LocalDate pFechaDefuncion, String pDescripcion) throws Exception {
        Optional<Artista> artistaEncontrado = artistaDAO.findByID(pIdArtista);

        if(artistaEncontrado.isPresent()) {
            Artista artistaModifica = artistaEncontrado.get();
            artistaModifica.setNombre(pNombre);
            artistaModifica.setApellidos(pApellidos);
            artistaModifica.setNombreArtistico(pNomArtistico);
            artistaModifica.setFechaDefuncion(pFechaDefuncion);
            artistaModifica.setDescripcion(pDescripcion);

            return artistaDAO.update(artistaModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar un artista
     * @param pIdArtista int que define el id del artista que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el artista no existe
     */
    public boolean eliminarArtista(int pIdArtista) throws Exception {
        return artistaDAO.delete(pIdArtista);
    }

    /**
     * Método usado para obtener una lista con todos los artistas almacenados
     * @return una lista con todos los artistas almacenados
     */
    public List<Artista> listarArtistas(){
        try {
            return Collections.unmodifiableList(artistaDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un artista usando como filtro su id
     * @param pId int que define el id del artista que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de un artista si se encuentra una coincidencia
     * @see Optional
     * @see Artista
     */
    public Optional<Artista> buscarArtistaPorId(int pId){
        try {
            return artistaDAO.findByID(pId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //*******************Manejo de canciones******************

    /**
     * Método usado para crear una cancion
     * @param nombre String que define el nombre de la cancion
     * @param recurso String que define la ruta de la cancion
     * @param duracion double que define la duracion de la cancion
     * @param idGenero int que define el id del genero de la cancion
     * @param idArtista int que define el id del artista de la cancion
     * @param idCompositor int que define el id del compositor de la cancion
     * @param fechaLanzamiento LocalDate que define la fecha de lanzamiento de la cancion
     * @param precio double que define el precio de la cancion
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si la cancion ya existe
     */
    public boolean crearCancion(String nombre, String recurso, double duracion, int idGenero, int idArtista, int idCompositor, LocalDate fechaLanzamiento, double precio) throws Exception {
        ArrayList<Calificacion> calificaciones = new ArrayList<>();

        TipoCancion tipoCancion;
        if(usuarioIngresadoEsAdmin() || usuarioIngresadoEsCreador()) {
            tipoCancion = TipoCancion.PARA_TIENDA;
        } else {
            tipoCancion = TipoCancion.PARA_USUARIO;
        }

        Genero genero = buscarGeneroPorId(idGenero).get();
        Artista artista = buscarArtistaPorId(idArtista).get();
        Compositor compositor = buscarCompositorPorId(idCompositor).get();

        Cancion nuevaCancion = new Cancion(tipoCancion, nombre, recurso, duracion, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
        return cancionDAO.save(nuevaCancion);
    }

    public boolean modificarCancion(int pIdCancion, String nombre, String recurso, double duracion, int idGenero, int idArtista, int idCompositor, LocalDate fechaLanzamiento, double precio) throws Exception {
        Optional<Cancion> cancionEncontrada = cancionDAO.findByID(pIdCancion);

        if(cancionEncontrada.isPresent()) {
            Cancion cancionModifica = cancionEncontrada.get();
            cancionModifica.setNombre(nombre);
            cancionModifica.setRecurso(recurso);
            cancionModifica.setDuracion(duracion);
            cancionModifica.setPrecio(precio);
            cancionModifica.setFechaLanzamiento(fechaLanzamiento);
            cancionModifica.setGenero(buscarGeneroPorId(idGenero).get());
            cancionModifica.setArtista(buscarArtistaPorId(idArtista).get());
            cancionModifica.setCompositor(buscarCompositorPorId(idCompositor).get());

            return cancionDAO.update(cancionModifica);
        }
        return false;
    }

    /**
     * Método usado para eliminar una cancion
     * @param pIdCancion int que define el id de la cancion que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si la cancion no existe
     */
    public boolean eliminarCancion(int pIdCancion) throws Exception {
        return cancionDAO.delete(pIdCancion);
    }

    /**
     * Método usado para obtener una lista con todas las canciones almacenadas
     * @return una lista con todas las canciones almacenadas
     * @see List
     * @see Cancion
     */
    public List<Cancion> listarCanciones(){
        try {
            return Collections.unmodifiableList(cancionDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar una cancion usando como filtro su id
     * @param pIdCancion int que define el id de la cancion que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Cancion si se encuentra una coincidencia
     * @see Optional
     * @see Cancion
     */
    public Optional<Cancion> buscarCancionPorId(int pIdCancion){
        try {
            return cancionDAO.findByID(pIdCancion);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //Biblioteca de cliente

    //CANCIONES
    /**
     * Método usado para agregar una canción a la biblioteca de un usuario
     * @param pIdCliente int que define el id del cliente dueño de la biblioteca
     * @param pIdCancion int que define el id de la cancion que se desea incluir
     * @return true si la agregacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si la biblioteca o cancion no existe
     */
    public boolean agregarCancionABibliotecaUsuario(int pIdCliente, int pIdCancion) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()){  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();

            Optional<Biblioteca> bibliotecaEncontrada = repoCancionesDAO.findBibliotecaByID(idBiblioteca);
            if(bibliotecaEncontrada.isPresent()) {  //Luego verifica si la biblioteca de ese usuario existe
                Optional<Cancion> cancionEncontrada = cancionDAO.findByID(pIdCancion);

                if(cancionEncontrada.isPresent()) {     //Luego busca la cancion a ver si existe
                    Biblioteca bibliotecaModifica = bibliotecaEncontrada.get();
                    boolean resultado = bibliotecaModifica.agregarCancion(cancionEncontrada.get());

                    if(resultado) { //Puedo guardar esta cancion?
                        return cancionesBibliotecaDAO.save(bibliotecaModifica.getId(), cancionEncontrada.get().getId());
                    }
                }
            }
        }
        return false;
    }

    /**
     * Método usado para obtener una lista con las canciones almacenadas en la biblioteca de un usuario
     * @param pIdCliente int que define el id del que se desea conocer sus canciones
     * @return lista con las canciones almacenadas en la biblioteca del usuario
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public List<Cancion> listarCancionesDeBibliotecaUsuario(int pIdCliente) throws SQLException {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return Collections.unmodifiableList(repoEncontrado.get().getCanciones());
            }
        }

        return null;
    }

    /**
     * Método usado para buscar una cancion dentro de la biblioteca de un usuario usando como filtro su id
     * @param pIdCliente int que define el id del cliente dueño de la biblioteca
     * @param pIdCancion int que define el id de la cancion que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de una Cancion si se encuentra una coincidencia
     * @see Optional
     * @see Cancion
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public Optional<Cancion> buscarCancionEnBibliotecaUsuario(int pIdCliente, int pIdCancion) throws SQLException {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return repoEncontrado.get().buscarCancion(pIdCancion);
            }
        }

        return Optional.empty();
    }

    /**
     * Método usado para remover una canción de la biblioteca de un usuario
     * @param pIdCliente int que define el id del cliente dueño de la biblioteca
     * @param pIdCancion int que define el id de la cancion que se desea remover
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la Db o si la biblioteca o cancion no existe
     */
    public boolean removerCancionDeBibliotecaUsuario(int pIdCliente, int pIdCancion) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                Biblioteca bibliotecaModifica = repoEncontrado.get();
                boolean resultado = bibliotecaModifica.removerCancion(pIdCancion);

                if(resultado) { //Puedo remover esta cancion?
                    return cancionesBibliotecaDAO.delete(idBiblioteca, pIdCancion);
                }
            }
        }

        return false;
    }

    //LISTAS REPRODUCCION
    //TODO javadoc para esto
    public boolean agregarListaReproduccionABibliotecaUsuario(int pIdCliente, int pIdLista) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()){  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();

            Optional<Biblioteca> bibliotecaEncontrada = repoCancionesDAO.findBibliotecaByID(idBiblioteca);
            if(bibliotecaEncontrada.isPresent()) {  //Luego verifica si la biblioteca de ese usuario existe
                Optional<ListaReproduccion> listaEncontrada = repoCancionesDAO.findListaReproduccionById(pIdLista);

                if(listaEncontrada.isPresent()) {     //Luego busca la lista a ver si existe
                    Biblioteca bibliotecaModifica = bibliotecaEncontrada.get();
                    boolean resultado = bibliotecaModifica.agregarListaReproduccion(listaEncontrada.get());

                    if(resultado) { //Puedo guardar esta lista?
                        return listasBibliotecaDAO.save(bibliotecaModifica.getId(), listaEncontrada.get().getId());
                    }
                }
            }
        }
        return false;
    }

    public List<ListaReproduccion> listarListasReproduccionDeBibliotecaUsuario(int pIdCliente) throws SQLException {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return Collections.unmodifiableList(repoEncontrado.get().getListasDeReproduccion());
            }
        }

        return null;
    }

    public Optional<ListaReproduccion> buscarListaReproduccionEnBibliotecaUsuario(int pIdCliente, int pIdLista) throws SQLException {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return repoEncontrado.get().buscarListaReproduccion(pIdLista);
            }
        }

        return Optional.empty();
    }

    public boolean removerListaReproduccionDeBibliotecaUsuario(int pIdCliente, int pIdLista) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<Biblioteca> repoEncontrado = repoCancionesDAO.findBibliotecaByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                Biblioteca bibliotecaModifica = repoEncontrado.get();
                boolean resultado = bibliotecaModifica.removerListaReproduccion(pIdLista);

                if(resultado) { //Puedo remover esta lista?
                    return listasBibliotecaDAO.delete(idBiblioteca, pIdLista);
                }
            }
        }

        return false;
    }

    //**************Manejo de compositores********************

    /**
     * Método usado para crear un compositor
     * @param nombre String que define el nombre del compositor
     * @param apellidos String que define los apellidos del compositor
     * @param idPaisNacimiento int que define el id del pais de nacimiento del compositor
     * @param idGenero int que define el id del genero del compositor
     * @param fechaNacimiento LocalDate que define la fecha de nacimineto del compositor
     * @param edad int que define la edad del compositor
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el compositor ya existe
     */
    public boolean crearCompositor(String nombre, String apellidos, int idPaisNacimiento, int idGenero, LocalDate fechaNacimiento, int edad) throws Exception {
        System.out.println("idGenero: " + idGenero);
        System.out.println("idPais: " + idPaisNacimiento);

        Pais paisNacimiento = buscarPaisPorId(idPaisNacimiento).get();
        Genero genero = buscarGeneroPorId(idGenero).get();

        Compositor nuevoCompositor = new Compositor(nombre, apellidos, paisNacimiento, genero, fechaNacimiento, edad);
        return compostorDAO.save(nuevoCompositor);
    }

    /**
     * Método usado para modificar un compositor
     * @param pIdCompositor int que define el id del compositor que se desea modificar
     * @param pNombre String que define el nuevo nombre del compositor
     * @param pApellidos String que define los nuevos apellidos del compositor
     * @return true si la modificacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o el compositor no existe
     */
    public boolean modificarCompositor(int pIdCompositor, String pNombre, String pApellidos) throws Exception {
        Optional<Compositor> compositorEncontrado = compostorDAO.findByID(pIdCompositor);

        if(compositorEncontrado.isPresent()) {
            Compositor compositorModifica = compositorEncontrado.get();
            compositorModifica.setNombre(pNombre);
            compositorModifica.setApellidos(pApellidos);

            return compostorDAO.update(compositorModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar un compositor
     * @param pIdCompositor int que define el id del compositor que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la Db o si el compositor no existe
     */
    public boolean eliminarCompositor(int pIdCompositor) throws Exception {
        return compostorDAO.delete(pIdCompositor);
    }

    /**
     * Método usado para obtener una lista de todos los compositores almacenados
     * @return una lista con todos los compositores almacenados
     */
    public List<Compositor> listarCompositores(){
        try {
            return Collections.unmodifiableList(compostorDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un compositor usando como filtro su id
     * @param idCompositor int que define el id del compositor que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Compositor si se encuentra una coincidencia
     * @see Optional
     * @see Compositor
     */
    public Optional<Compositor> buscarCompositorPorId(int idCompositor){
        try {
            return compostorDAO.findByID(idCompositor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //******************Manejo de Generos******************

    /**
     * Método usado para crear un genero
     * @param nombre String que define el nombre del genero
     * @param descripcion String que define la descripcion del genero
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el genero ya existe
     */
    public boolean crearGenero(String nombre, String descripcion) throws Exception {
        Genero nuevoGenero = new Genero(nombre, descripcion);
        return generoDAO.save(nuevoGenero);
    }

    /**
     * Método usaodo para modificar un genero
     * @param pIdGenero int que define el id del genero que se desea modificar
     * @param pNombre String que define el nuevo nombre del genero
     * @param pDesc String que define la nueva descripcion del genero
     * @return true si la modificacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el genero no existe
     */
    public boolean modificarGenero(int pIdGenero, String pNombre, String pDesc) throws Exception {
        Optional<Genero> generoEncontrado = generoDAO.findByID(pIdGenero);

        if(generoEncontrado.isPresent()) {
            Genero generoModifica = generoEncontrado.get();
            generoModifica.setNombre(pNombre);
            generoModifica.setDescripcion(pDesc);

            return generoDAO.update(generoModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar un genero
     * @param pIdGenero int que define el id del genero que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el genero no existe
     */
    public boolean eliminarGenero(int pIdGenero) throws Exception {
        return generoDAO.delete(pIdGenero);
    }

    /**
     * Método usado para obtener una lista con todos los generos almacenados
     * @return una lista con todos los generos almacenados
     */
    public List<Genero> listarGeneros(){
        try {
            return Collections.unmodifiableList(generoDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un genero usando como filtro su id
     * @param pIdGenero int que define el id del genero que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Genero si se encuentra una coincidencia
     */
    public Optional<Genero> buscarGeneroPorId(int pIdGenero){
        try {
            return generoDAO.findByID(pIdGenero);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //*****************Manejo de Paises********************

    /**
     * Método usado para crear un pais
     * @param nombrePais String que define el nombre del pais
     * @param descripcion String que define la descripcion del pais
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la Db o si el pais ya existe
     */
    public boolean crearPais(String nombrePais, String descripcion) throws Exception {
        Pais nuevoPais = new Pais(nombrePais, descripcion);
        return paisDAO.save(nuevoPais);
    }

    /**
     * Método usado para modificar un pais
     * @param pIdPais int que define el id del pais que se desea modificar
     * @param pNombre String que define el nuevo nombre del pais
     * @param pDescripcion String que define la nueva descripcion del pais
     * @return true si el registro es exitoso, false si ocurre algun error
     * @throws Exception si no se puede conectar con la Db o si el pais no existe
     */
    public boolean modificarPais(int pIdPais, String pNombre, String pDescripcion) throws Exception {
        Optional<Pais> paisEncontrado = paisDAO.findByID(pIdPais);

        if(paisEncontrado.isPresent()) {
            Pais paisModifica = paisEncontrado.get();
            paisModifica.setNombre(pNombre);
            paisModifica.setDescripcion(pDescripcion);

            return paisDAO.update(paisModifica);
        }

        return false;
    }

    /**
     * Método usado para eliminar un pais
     * @param pIdPais int que define el id del pais que se desea eliminar
     * @return true si la eliminacion es exitosa, false si ocurre algun error
     * @throws Exception si no se puede conectar con la DB o si el pais no existe
     */
    public boolean eliminarPais(int pIdPais) throws Exception {
        return paisDAO.delete(pIdPais);
    }

    /**
     * Método usado para obtener una lista con todos los paises almacenados
     * @return una lista con todos los paises almacenados
     */
    public List<Pais> listarPaises(){
        try {
            return Collections.unmodifiableList(paisDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Método usado para buscar un pais usando como filtro su id
     * @param pIdPais int que define el id del pais que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Pais si se encuentra una coincidencia
     * @see Optional
     * @see Pais
     */
    public Optional<Pais> buscarPaisPorId(int pIdPais){
        try {
            return paisDAO.findByID(pIdPais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
