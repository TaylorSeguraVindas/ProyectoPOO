package segura.taylor.bl.gestor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.bl.persistencia.*;

import javax.swing.text.html.Option;

public class Gestor {
    //Variables
    public static Usuario usuarioIngresado;

    private ArtistaDAO artistaDAO;
    private CancionDAO cancionDAO;
    private CompositorDAO compostorDAO;
    private GeneroDAO generoDAO;
    private PaisDAO paisDAO;
    private RepositorioCancionesDAO repoCancionesDAO;
    private UsuarioDAO usuarioDAO;

    //Constructor
    public Gestor(){
        artistaDAO = new ArtistaDAO();
        cancionDAO = new CancionDAO();
        compostorDAO = new CompositorDAO();
        generoDAO = new GeneroDAO();
        paisDAO = new PaisDAO();
        repoCancionesDAO = new RepositorioCancionesDAO();
        usuarioDAO = new UsuarioDAO();
    }

    //Metodos

    //*******General**********
    public boolean existeAdmin(){
        if(usuarioDAO.findAll().size() == 0){
            return false;
        }

        return (usuarioDAO.findAll().get(0).esAdmin());
    }
    public boolean iniciarSesion(String pCorreo, String pContrasenna){
        for (Usuario objUsuario: usuarioDAO.findAll()) {
            if(objUsuario.getCorreo().equals(pCorreo)){
                if(objUsuario.getContrasenna().equals(pContrasenna)){
                    usuarioIngresado = objUsuario;
                    return true;
                }
            }
        }

        return false;
    }

    //*******Manejo de usuarios*******
    //Admin
    public boolean crearUsuarioAdmin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaCreacion) throws Exception {
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaCreacion);

        return usuarioDAO.save(admin);
    }
    //Cliente
    public boolean crearUsuarioCliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaNacimiento, int edad, int idPais, Biblioteca biblioteca) throws Exception {
        //Si no hay admin no se puede registrar usuarios.
        //if(usuarioDAO.findAll().size() == 0) return false;

        if(biblioteca == null){
            biblioteca = new Biblioteca();
        }
        //Pais pais = buscarPaisPorId(idPais).get();
        Cliente nuevoCliente = new Cliente(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, null, biblioteca);

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
        return Collections.unmodifiableList(usuarioDAO.findAll());
    }

    public Usuario buscarUsuarioPorId(int pId){
        return usuarioDAO.findByID(pId).get();
    }
    public Usuario buscarUsuarioPorCorreo(String pCorreo){
        return usuarioDAO.findByEmail(pCorreo).get();
    }

    //*******Manejo de albunes********
    public boolean crearAlbum(String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String fechaLanzamiento, String imagen, ArrayList<Artista> artistas, Compositor compositor) throws Exception {
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }
        if(artistas == null){
            artistas = new ArrayList<Artista>();
        }

        Album nuevoAlbum = new Album(nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas, compositor);

        return repoCancionesDAO.save(nuevoAlbum);
    }
    public boolean modificarAlbum(int pId, String pNombre, String pImagen, Compositor pCompositor) throws Exception {
        Optional<RepositorioCanciones> repoEncontrado = repoCancionesDAO.findByID(pId);

        if(repoEncontrado.isPresent()){
            Album albumModifica = (Album) repoEncontrado.get();
            albumModifica.modificar(pNombre, pImagen, pCompositor);
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
    public boolean crearListaReproduccion(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, Usuario creador, String imagen) throws Exception {
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, fechaCreacion, canciones, creador, 0.0, imagen);
        return repoCancionesDAO.save(nuevaLista);
    }
    public boolean modificarListaReproduccion(int pIdLista, String pNombre, String pImagen) throws Exception {
        Optional<RepositorioCanciones> listaEncontrada = repoCancionesDAO.findByID(pIdLista);

        if(listaEncontrada.isPresent()) {
            ListaReproduccion listaModifica = (ListaReproduccion) listaEncontrada.get();
            listaModifica.modificar(pNombre, pImagen);
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
    public boolean crearArtista(String nombre, String apellidos, String nombreArtistico, String fechaNacimiento, String fechaDefuncion, String paisNacimiento, Genero genero, int edad, String descripcion) throws Exception {
        Artista nuevoArtista = new Artista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
        return artistaDAO.save(nuevoArtista);
    }
    public boolean modificarArtista(int pIdArtista, String pNombre, String pApellidos, String pNomArtistico, String pFechaDefuncion) throws Exception {
        Optional<Artista> artistaEncontrado = artistaDAO.findByID(pIdArtista);

        if(artistaEncontrado.isPresent()) {
            Artista artistaModifica = artistaEncontrado.get();
            artistaModifica.modificar(pNombre, pApellidos, pNomArtistico, pFechaDefuncion);
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
    public boolean crearCancion(TipoCancion tipoCancion, Usuario creador, String nombre, String recurso, String nombreAlbum, Genero genero, Artista artista, Compositor compositor, String fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio) throws Exception {
        if(calificaciones == null){
            calificaciones = new ArrayList<>();
        }

        Cancion nuevaCancion = new Cancion(tipoCancion, creador, nombre, recurso, nombreAlbum, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
        return cancionDAO.save(nuevaCancion);
    }
    public boolean modificarCancion(int pIdCancion, String pNombreAlbum, double pPrecio) throws Exception {
        Optional<Cancion> cancionEncontrada = cancionDAO.findByID(pIdCancion);

        if(cancionEncontrada.isPresent()) {
            Cancion cancionModifica = cancionEncontrada.get();
            cancionModifica.modificar(pNombreAlbum, pPrecio);
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

    //TODO completar esto
    public boolean agregarCancionAFavoritosUsuario(int pIdCliente, Cancion pCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().agregarAFavoritos(pCancion.getId());
        }
        return false;
    }
    public int buscarCancionEnFavoritos(int pIdCliente, int pIdCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().buscarEnFavoritos(pIdCancion);
        }
        return -1;
    }
    public boolean removerCancionDeFavoritosUsuario(int pIdCliente, int pIdCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().removerDeFavoritos(pIdCancion);
        }
        return false;
    }


    //**************Manejo de compositores********************
    public boolean crearCompositor(String nombre, String apellidos, String paisNacimiento, String fechaNacimiento, int edad) throws Exception {
        Compositor nuevoCompositor = new Compositor(nombre, apellidos, paisNacimiento, fechaNacimiento, edad);
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
        return Collections.unmodifiableList(compostorDAO.findAll());
    }

    public Optional<Compositor> buscarCompositorPorId(int idCompositor){
        return compostorDAO.findByID(idCompositor);
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
        return Collections.unmodifiableList(generoDAO.findAll());
    }

    public Optional<Genero> buscarGeneroPorId(int pIdGenero){
        return generoDAO.findByID(pIdGenero);
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
        return Collections.unmodifiableList(paisDAO.findAll());
    }

    public Optional<Pais> buscarPaisPorId(int pIdPais){
        return paisDAO.findByID(pIdPais);
    }
}
