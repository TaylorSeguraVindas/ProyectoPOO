package segura.taylor.bl.gestor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.*;

import segura.taylor.PropertiesHandler;
import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.bl.persistencia.*;

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

    //Constructor
    public Gestor(){
        try {
            propertiesHandler.loadProperties();
            //ABRIR DB
            String driver = propertiesHandler.getDriver();
            try {
                Class.forName(driver).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("LOADED DRIVER ---> " + driver);
            String url = propertiesHandler.getCnxStr();
            this.connection = DriverManager.getConnection(url, propertiesHandler.getUser(), propertiesHandler.getPwd());
            System.out.println("CONNECTED TO ---> "+ url);

            artistaDAO = new ArtistaDAO(this.connection);
            cancionDAO = new CancionDAO(this.connection);
            compostorDAO = new CompositorDAO(this.connection);
            generoDAO = new GeneroDAO(this.connection);
            paisDAO = new PaisDAO(this.connection);
            repoCancionesDAO = new RepositorioCancionesDAO(this.connection);
            usuarioDAO = new UsuarioDAO(this.connection);
        } catch (Exception e) {
            System.out.println("CANT CONNECT TO DATABASE");
            e.printStackTrace();
        }
    }

    //Metodos
    public boolean usuarioIngresadoEsAdmin() {
        return usuarioIngresado.esAdmin();
    }
    public boolean usuarioIngresadoEsCreador() {
        return usuarioIngresado.esCreador();
    }
    public int getIdUsuarioIngresado() {
        return usuarioIngresado.getId();
    }

    //*******General**********
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
    //Admin
    public boolean crearUsuarioAdmin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaCreacion) throws Exception {
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaCreacion);

        return usuarioDAO.save(admin);
    }
    //Cliente
    public boolean crearUsuarioCliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaNacimiento, int edad, int idPais) throws Exception {
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
    public boolean modificarUsuario(int id, String pNombreUsuario, String pImagenPerfil, String pContrasenna, String pNombre, String pApellidos) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(id);

        if(usuarioEncontrado.isPresent()){
            Usuario usuarioModifica = usuarioEncontrado.get();
            usuarioModifica.modificar(pNombreUsuario, pImagenPerfil, pContrasenna, pNombre, pApellidos);
            return usuarioDAO.update(usuarioModifica);
        }

        return false;
    }
    public boolean eliminarUsuario(int idUsuario) throws Exception {
        return usuarioDAO.delete(idUsuario);
    }
    public List<Usuario> listarUsuarios(){
        try {
            return Collections.unmodifiableList(usuarioDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Usuario buscarUsuarioPorId(int pId){
        return usuarioDAO.findByID(pId).get();
    }
    public Usuario buscarUsuarioPorCorreo(String pCorreo){
        return usuarioDAO.findByEmail(pCorreo).get();
    }

    //*******Manejo de albunes********
    public boolean crearAlbum(String nombre, LocalDate fechaCreacion, LocalDate fechaLanzamiento, String imagen) throws Exception {
        ArrayList<Cancion> canciones = new ArrayList<Cancion>();
        ArrayList<Artista> artistas = new ArrayList<Artista>();

        Album nuevoAlbum = new Album(nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas);
        return (repoCancionesDAO.save(nuevoAlbum)) != -1;
    }
    public boolean modificarAlbum(int pId, String pNombre, String pImagen) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pId);

        if(repoEncontrado.isPresent()){
            Album albumModifica = (Album) repoEncontrado.get();
            albumModifica.modificar(pNombre, pImagen);
            return repoCancionesDAO.update(albumModifica);
        }

        return false;
    }
    public boolean eliminarAlbum(int idAlbum) throws Exception {
        return repoCancionesDAO.delete(idAlbum);
    }
    public List<Album> listarAlbunes(){
        return Collections.unmodifiableList(repoCancionesDAO.findAlbunes());
    }

    //Para agregar o eliminar canciones y artistas
    public boolean agregarCancionEnAlbum(int pIdAlbum, int idCancion) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            Optional<Cancion> nuevaCancion = cancionDAO.findByID(idCancion);

            //Busca cancion
            if(nuevaCancion.isPresent())
            {
                Album albumModifica = (Album) repoEncontrado.get();
                albumModifica.agregarCancion(nuevaCancion.get());
                return repoCancionesDAO.update(albumModifica);
            }
        }

        return false;
    }
    public boolean removerCancionDeAlbum(int pIdAlbum, int pIdCancion) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            Album albumModifica = (Album) repoEncontrado.get();
            albumModifica.removerCancion(pIdCancion);
            return repoCancionesDAO.update(albumModifica);
        }

        return false;
    }
    public boolean agregarArtistaEnAlbum(int pIdAlbum, int pIdArtista) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdAlbum);
        Optional<Artista> artistaEncontrado = artistaDAO.findByID(pIdArtista);

        //Busca album
        if(repoEncontrado.isPresent()){
            Album albumModifica = (Album) repoEncontrado.get();

            if(artistaEncontrado.isPresent()) {
                albumModifica.agregarArtista(artistaEncontrado.get());
                return repoCancionesDAO.update(albumModifica);
            }
        }

        return false;
    }
    public boolean removerArtistaDeAlbum(int pIdAlbum, int pIdArtista) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdAlbum);

        //Busca album
        if(repoEncontrado.isPresent()){
            Album albumModifica = (Album) repoEncontrado.get();
            Optional<Artista> artistaEncontrado = albumModifica.buscarArtista(pIdArtista);  //Referencia al artista dentro del album

            if(artistaEncontrado.isPresent()) {
                albumModifica.removerArtista(artistaEncontrado.get());
                return repoCancionesDAO.update(albumModifica);
            }
        }

        return false;
    }

    public Optional<Album> buscarAlbumPorId(int pId){
        return Optional.of((Album) repoCancionesDAO.findByID(pId).get());
    }


    //*********Manejo de Listas de Reproduccion***************
    public boolean crearListaReproduccion(String nombre, LocalDate fechaCreacion, String imagen, String descripcion) throws Exception {
        ArrayList<Cancion> canciones = new ArrayList<>();   //Las listas de reproducción SIEMPRE se crea sin canciones por defecto

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, fechaCreacion, canciones, 0.0, imagen, descripcion);
        return (repoCancionesDAO.save(nuevaLista)) != -1;
    }
    public boolean modificarListaReproduccion(int pIdLista, String pNombre, String pImagen, String pDescripcion) throws Exception {
        Optional<RepositorioCanciones> listaEncontrada = repoCancionesDAO.findByID(pIdLista);

        if(listaEncontrada.isPresent()) {
            ListaReproduccion listaModifica = (ListaReproduccion) listaEncontrada.get();
            listaModifica.modificar(pNombre, pImagen, pDescripcion);
            return repoCancionesDAO.update(listaModifica);
        }

        return false;
    }
    public boolean eliminarListaReproduccion(int pIdLista) throws Exception {
        return repoCancionesDAO.delete(pIdLista);
    }
    public List<ListaReproduccion> listarListasReproduccion(){
        return Collections.unmodifiableList(repoCancionesDAO.findListasReproduccion());
    }

    //Para agregar o eliminar canciones
    public boolean agregarCancionALista(int pIdLista, int pIdCancion) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdLista);

        //Busca lista de reproduccion
        if(repoEncontrado.isPresent()){
            Optional<Cancion> nuevaCancion = cancionDAO.findByID(pIdCancion);

            //Busca cancion
            if(nuevaCancion.isPresent())
            {
                ListaReproduccion listaModifica = (ListaReproduccion) repoEncontrado.get();
                listaModifica.agregarCancion(nuevaCancion.get());
                return repoCancionesDAO.update(listaModifica);
            }
        }

        return false;
    }
    public boolean removerCancionDeLista(int pIdLista, int pIdCancion) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pIdLista);

        //Busca lista de reproduccion
        if(repoEncontrado.isPresent()){
            ListaReproduccion listaModifica = (ListaReproduccion) repoEncontrado.get();
            listaModifica.removerCancion(pIdCancion);
            return repoCancionesDAO.update(listaModifica);
        }

        return false;
    }

    public Optional<ListaReproduccion> buscarListaReproduccionPorId(int pIdLista){
        return Optional.of((ListaReproduccion) repoCancionesDAO.findByID(pIdLista).get());
    }


    //*****************Manejo de artistas*******************
    public boolean crearArtista(String nombre, String apellidos, String nombreArtistico, LocalDate fechaNacimiento, LocalDate fechaDefuncion, int idPaisNacimiento, int idGenero, int edad, String descripcion) throws Exception {
        Pais paisNacimiento = buscarPaisPorId(idPaisNacimiento).get();
        Genero genero = buscarGeneroPorId(idGenero).get();

        Artista nuevoArtista = new Artista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
        return artistaDAO.save(nuevoArtista);
    }
    public boolean modificarArtista(int pIdArtista, String pNombre, String pApellidos, String pNomArtistico, LocalDate pFechaDefuncion, String descripcion) throws Exception {
        Optional<Artista> artistaEncontrado = artistaDAO.findByID(pIdArtista);

        if(artistaEncontrado.isPresent()) {
            Artista artistaModifica = artistaEncontrado.get();
            artistaModifica.modificar(pNombre, pApellidos, pNomArtistico, pFechaDefuncion, descripcion);
            return artistaDAO.update(artistaModifica);
        }

        return false;
    }
    public boolean eliminarArtista(int pIdArtista) throws Exception {
        return artistaDAO.delete(pIdArtista);
    }
    public List<Artista> listarArtistas(){
        return Collections.unmodifiableList(artistaDAO.findAll());
    }

    public Optional<Artista> buscarArtistaPorId(int pId){
        return artistaDAO.findByID(pId);
    }


    //*******************Manejo de canciones******************
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
    public boolean modificarCancion(int pIdCancion, String pNombre, double pPrecio) throws Exception {
        Optional<Cancion> cancionEncontrada = cancionDAO.findByID(pIdCancion);

        if(cancionEncontrada.isPresent()) {
            Cancion cancionModifica = cancionEncontrada.get();
            cancionModifica.modificar(pNombre, pPrecio);
            return cancionDAO.update(cancionModifica);
        }
        return false;
    }
    public boolean eliminarCancion(int pIdCancion) throws Exception {
        return cancionDAO.delete(pIdCancion);
    }
    public List<Cancion> listarCanciones(){
        return Collections.unmodifiableList(cancionDAO.findAll());
    }
    public Optional<Cancion> buscarCancionPorId(int pIdCancion){
        return cancionDAO.findByID(pIdCancion);
    }


    //Canciones de cliente
    public boolean agregarCancionABibliotecaUsuario(int pIdCliente, int pIdCancion) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()){  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();

            Optional<RepositorioCanciones> bibliotecaEncontrada = repoCancionesDAO.findByID(idBiblioteca);
            if(bibliotecaEncontrada.isPresent()) {  //Luego verifica si la biblioteca de ese usuario existe
                Optional<Cancion> cancionEncontrada = cancionDAO.findByID(pIdCancion);

                if(cancionEncontrada.isPresent()) {     //Luego busca la cancion a ver si existe
                    Biblioteca bibliotecaModifica = (Biblioteca) bibliotecaEncontrada.get();
                    bibliotecaModifica.agregarCancion(cancionEncontrada.get());
                    return repoCancionesDAO.update(bibliotecaModifica);
                }
            }
        }
        return false;
    }
    public List<Cancion> listarCancionesDeBibliotecaUsuario(int pIdCliente){
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return Collections.unmodifiableList(repoEncontrado.get().getCanciones());
            }
        }

        return null;
    }
    public Optional<Cancion> buscarCancionEnBibliotecaUsuario(int pIdCliente, int pIdCancion){
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                return repoEncontrado.get().buscarCancion(pIdCancion);
            }
        }

        return Optional.empty();
    }
    public boolean removerCancionDeBibliotecaUsuario(int pIdCliente, int pIdCancion) throws Exception {
        Optional<Usuario> usuarioEncontrado = usuarioDAO.findByID(pIdCliente);

        if(usuarioEncontrado.isPresent()) {  //Primero verifica que el usuario exista
            Cliente clienteModifica = (Cliente) usuarioEncontrado.get();
            int idBiblioteca = clienteModifica.getBiblioteca().getId();
            Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(idBiblioteca);

            if(repoEncontrado.isPresent()) {    //Luego verifica que la biblioteca exista
                Biblioteca bibliotecaModifica = (Biblioteca) repoEncontrado.get();
                bibliotecaModifica.removerCancion(pIdCancion);
                return repoCancionesDAO.update(bibliotecaModifica);
            }
        }

        return false;
    }


    //**************Manejo de compositores********************
    public boolean crearCompositor(String nombre, String apellidos, int idPaisNacimiento, int idGenero, LocalDate fechaNacimiento, int edad) throws Exception {
        Pais paisNacimiento = buscarPaisPorId(idPaisNacimiento).get();
        Genero genero = buscarGeneroPorId(idGenero).get();

        Compositor nuevoCompositor = new Compositor(nombre, apellidos, paisNacimiento, genero, fechaNacimiento, edad);
        return compostorDAO.save(nuevoCompositor);
    }
    public boolean modificarCompositor(int pIdCompositor, String pNombre, String pApellidos) throws Exception {
        Optional<Compositor> compositorEncontrado = compostorDAO.findByID(pIdCompositor);

        if(compositorEncontrado.isPresent()) {
            Compositor compositorModifica = compositorEncontrado.get();
            compositorModifica.modificar(pNombre, pApellidos);
            return compostorDAO.update(compositorModifica);
        }

        return false;
    }
    public boolean eliminarCompositor(int pIdCompositor) throws Exception {
        return compostorDAO.delete(pIdCompositor);
    }
    public List<Compositor> listarCompositores(){
        try {
            return Collections.unmodifiableList(compostorDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Optional<Compositor> buscarCompositorPorId(int idCompositor){
        try {
            return compostorDAO.findByID(idCompositor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //******************Manejo de Generos******************
    public boolean crearGenero(String nombre, String descripcion) throws Exception {
        Genero nuevoGenero = new Genero(nombre, descripcion);
        return generoDAO.save(nuevoGenero);
    }
    public boolean modificarGenero(int pIdGenero, String pNombre, String pDesc) throws Exception {
        Optional<Genero> generoEncontrado = generoDAO.findByID(pIdGenero);

        if(generoEncontrado.isPresent()) {
            Genero generoModifica = generoEncontrado.get();
            generoModifica.modificar(pNombre, pDesc);
            return generoDAO.update(generoModifica);
        }

        return false;
    }
    public boolean eliminarGenero(int pIdGenero) throws Exception {
        return generoDAO.delete(pIdGenero);
    }
    public List<Genero> listarGeneros(){
        try {
            return Collections.unmodifiableList(generoDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Optional<Genero> buscarGeneroPorId(int pIdGenero){
        try {
            return generoDAO.findByID(pIdGenero);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    //*****************Manejo de Paises********************
    public boolean crearPais(String nombrePais, String descripcion) throws Exception {
        Pais nuevoPais = new Pais(nombrePais, descripcion);
        return paisDAO.save(nuevoPais);
    }
    public boolean modificarPais(int pIdPais, String pNombre, String pDescripcion) throws Exception {
        Optional<Pais> paisEncontrado = paisDAO.findByID(pIdPais);

        if(paisEncontrado.isPresent()) {
            Pais paisModifica = paisEncontrado.get();
            paisModifica.modificar(pNombre, pDescripcion);
            return paisDAO.update(paisModifica);
        }

        return false;
    }
    public boolean eliminarPais(int pIdPais) throws Exception {
        return paisDAO.delete(pIdPais);
    }
    public List<Pais> listarPaises(){
        try {
            return Collections.unmodifiableList(paisDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public Optional<Pais> buscarPaisPorId(int pIdPais){
        try {
            return paisDAO.findByID(pIdPais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
