package segura.taylor.bl.gestor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import segura.taylor.bl.entidades.*;

public class Gestor {

    //Variables
    private ArrayList<Album> albunes;
    private ArrayList<Artista> artistas;
    private ArrayList<Cancion> canciones;
    private ArrayList<Compositor> compositores;
    private ArrayList<Genero> generos;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ArrayList<Pais> paises;
    private ArrayList<Usuario> usuarios;

    //Constructor
    public Gestor(){
        this.albunes = new ArrayList<>();
        this.artistas = new ArrayList<>();
        this.canciones = new ArrayList<>();
        this.compositores = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.listasReproduccion = new ArrayList<>();
        this.paises = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    //Metodos


    //*******General**********
    public boolean existeAdmin(){
        if(usuarios.size() == 0){
            return false;
        }

        return (usuarios.get(0).esAdmin());
    }
    public Usuario iniciarSesion(String pCorreo, String pContrasenna){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getCorreo().equals(pCorreo)){
                if(objUsuario.getContrasenna().equals(pContrasenna)){
                    return objUsuario;
                }
            }
        }

        return null;
    }

    public int cantidadAlbunes() {return albunes.size();}
    public int cantidadArtistas() {return artistas.size();}
    public int cantidadCanciones() {return canciones.size();}
    public int cantidadCompositores() {return compositores.size();}
    public int cantidadGeneros() {return generos.size();}
    public int cantidadListasReproduccion() {return listasReproduccion.size();}
    public int cantidadPaises() {return paises.size();}
    public int cantidadUsuarios(){
        return usuarios.size();
    }


    //*******Manejo de usuarios*******
    //Admin
    public boolean crearUsuarioAdmin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaCreacion){
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaCreacion);
        usuarios.add(admin);
        return true;
    }
    //Cliente
    public boolean crearUsuarioCliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String pais, Biblioteca biblioteca){
        //Si no hay admin no se puede registrar usuarios.
        if(usuarios.size() == 0) return false;

        if(biblioteca == null){
            biblioteca = new Biblioteca();
        }

        Cliente nuevoCliente = new Cliente(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, pais, biblioteca);

        //Evitar repeticion de datos.
        if(buscarUsuarioPorInfo(correo) == null){
            usuarios.add(nuevoCliente);
            return true;
        }

        return false;
    }
    public boolean modificarUsuario(String pCorreo, String pNombreUsuario, String pImagenPerfil, String pContrasenna, String pNombre, String pApellidos){
        Usuario usuarioModifica = buscarUsuarioPorInfo(pCorreo);

        if(usuarioModifica != null){
            return usuarioModifica.modificar(pNombreUsuario, pImagenPerfil, pContrasenna, pNombre, pApellidos);
        }

        return false;
    }
    public boolean eliminarUsuario(Usuario usuario) {
        if(existeUsuario(usuario)){
            usuarios.remove(usuario);
            return true;
        }
        return false;
    }
    public ArrayList<Usuario> listarUsuarios(){
        return usuarios;
    }

    public boolean existeUsuario(Usuario pUsuario){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.equals(pUsuario)){
                return true;
            }
        }

        return false;
    }
    public Usuario buscarUsuarioPorId(int pId){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getId() == pId){
                return objUsuario;
            }
        }
        return null;
    }
    public Usuario buscarUsuarioPorInfo(String dato){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getCorreo().equals(dato) || objUsuario.getNombre().equals(dato)){
                return objUsuario;
            }
        }
        return null;
    }

    public int obtenerIndiceUsuario(Usuario pUsuario){
        int indice = 0;
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.equals(pUsuario)){
                return indice;
            }
            indice++;
        }
        return -1;
    }


    //*******Manejo de albunes********
    public boolean crearAlbum(String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String fechaLanzamiento, String imagen, ArrayList<Artista> artistas, Compositor compositor){
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }
        if(artistas == null){
            artistas = new ArrayList<Artista>();
        }

        Album nuevoAlbum = new Album(nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas, compositor);

        //Evitar repeticion de datos.
        if(!existeAlbum(nuevoAlbum)){
            albunes.add(nuevoAlbum);
            return true;
        }

        return false;
    }
    public boolean modificarAlbum(int pId, String pNombre, String pImagen, Compositor pCompositor){
        Album albumModifica = buscarAlbumPorId(pId);

        if(albumModifica != null){
            return albumModifica.modificar(pNombre, pImagen, pCompositor);
        }

        return false;
    }
    public boolean eliminarAlbum(Album pAlbum){
        if(existeAlbum(pAlbum)){
            albunes.remove(pAlbum);
            return true;
        }
        return false;
    }
    public ArrayList<Album> listarAlbunes(){
        return albunes;
    }

    //Para agregar o eliminar canciones y artistas
    public boolean agregarCancionEnAlbum(int pIdAlbum, Cancion pCancion){
        Album albumModifica = buscarAlbumPorId(pIdAlbum);

        if(albumModifica != null){
            return albumModifica.agregarCancion(pCancion);
        }
        return false;
    }
    public boolean removerCancionDeAlbum(int pIdAlbum, int pIdCancion){
        Album albumModifica = buscarAlbumPorId(pIdAlbum);

        if(albumModifica != null){
            Cancion cancionEncontrada = albumModifica.buscarCancion(pIdCancion);
            return albumModifica.removerCancion(cancionEncontrada);
        }
        return false;
    }
    public boolean agregarArtistaEnAlbum(int pIdAlbum, Artista pArtista){
        Album albumModifica = buscarAlbumPorId(pIdAlbum);

        if(albumModifica != null){
            return albumModifica.agregarArtista(pArtista);
        }
        return false;
    }
    public boolean removerArtistaDeAlbum(int pIdAlbum, String pDatoArtista){
        Album albumModifica = buscarAlbumPorId(pIdAlbum);

        if(albumModifica != null){
            Artista artistaEncontrado = albumModifica.buscarArtista(pDatoArtista);
            return albumModifica.removerArtista(artistaEncontrado);
        }
        return false;
    }

    public Album buscarAlbumPorId(int pId){
        for (Album objAlbum: albunes) {
            if(objAlbum.getId() == pId){
                return objAlbum;
            }
        }
        return null;
    }
    public Album buscarAlbumPorNombre(String pNombre){
        for (Album objAlbum: albunes) {
            if(objAlbum.getNombre().equals(pNombre)){
                return objAlbum;
            }
        }
        return null;
    }
    public int obtenerIndiceAlbum(Album pAlbum){
        int indice = 0;
        for (Album objAlbum: albunes) {
            if(objAlbum.equals(pAlbum)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeAlbum(Album pAlbum){
        for (Album objAlbum: albunes) {
            if(objAlbum.equals(pAlbum)){
                return true;
            }
        }
        return false;
    }


    //*********Manejo de Listas de Reproduccion***************
    public boolean crearListaReproduccion(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, int idCreador, String imagen){
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, fechaCreacion, canciones, idCreador, 0.0, imagen);

        //Evitar repeticion de datos.
        if(!existeListaReproduccion(nuevaLista)){
            listasReproduccion.add(nuevaLista);
            return true;
        }

        return false;
    }
    public boolean modificarListaReproduccion(int pIdLista, String pNombre, String pImagen){
        ListaReproduccion listaModifica = buscarListaReproduccionPorId(pIdLista);

        if(listaModifica != null){
            return listaModifica.modificar(pNombre, pImagen);
        }
        return false;
    }
    public boolean eliminarListaReproduccion(ListaReproduccion pListaReproduccion){
        if(existeListaReproduccion(pListaReproduccion)){
            listasReproduccion.remove(pListaReproduccion);
            return true;
        }
        return false;
    }
    public ArrayList<ListaReproduccion> listarListasReproduccion(){
        return listasReproduccion;
    }

    //Para agregar o eliminar canciones
    public boolean agregarCancionALista(int pIdLista, Cancion pCancion) {
        ListaReproduccion listaModifica = buscarListaReproduccionPorId(pIdLista);

        if(listaModifica != null){
            return listaModifica.agregarCancion(pCancion);
        }
        return false;
    }
    public boolean removerCancionDeLista(int pIdLista, int pIdCancion) {
        ListaReproduccion listaModifica = buscarListaReproduccionPorId(pIdLista);

        if(listaModifica != null){
            Cancion cancionEncontrada = listaModifica.buscarCancion(pIdCancion);
            return listaModifica.removerCancion(cancionEncontrada);
        }
        return false;
    }

    public ListaReproduccion buscarListaReproduccionPorId(int pIdLista){
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.getId() == pIdLista){
                return objListaReproduccion;
            }
        }
        return null;
    }
    public ListaReproduccion buscarListaReproduccionNombre(String dato){
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.getNombre().equals(dato)){
                return objListaReproduccion;
            }
        }
        return null;
    }

    public int obtenerIndiceListaReproduccion(ListaReproduccion pListaReproduccion){
        int indice = 0;
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.equals(pListaReproduccion)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeListaReproduccion(ListaReproduccion pListaReproduccion){
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.equals(pListaReproduccion)){
                return true;
            }
        }
        return false;
    }


    //*****************Manejo de artistas*******************
    public boolean crearArtista(String nombre, String apellidos, String nombreArtistico, String fechaNacimiento, String fechaDefuncion, String paisNacimiento, Genero genero, int edad, String descripcion){
        Artista nuevoArtista = new Artista(nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);

        //Evitar repeticion de datos.
        if(!existeArtista(nuevoArtista)){
            artistas.add(nuevoArtista);
            return true;
        }

        return false;
    }
    public boolean modificarArtista(int pIdArtista, String pNombre, String pApellidos, String pNomArtistico, String pFechaDefuncion){
        Artista artistaModifica = buscarArtistaPorId(pIdArtista);

        if(artistaModifica != null){
            return artistaModifica.modificar(pNombre, pApellidos, pNomArtistico, pFechaDefuncion);
        }
        return false;
    }
    public boolean eliminarArtista(Artista pArtista){
        if(existeArtista(pArtista)){
            artistas.remove(pArtista);
            return true;
        }
        return false;
    }
    public ArrayList<Artista> listarArtistas(){
        return artistas;
    }

    public Artista buscarArtistaPorId(int pId){
        for (Artista objArtista: artistas) {
            if(objArtista.getId() == pId){
                return objArtista;
            }
        }
        return null;
    }
    public int obtenerIndiceArtista(Artista pArtista){
        int indice = 0;
        for (Artista objArtista: artistas) {
            if(objArtista.equals(pArtista)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeArtista(Artista pArtista){
        for (Artista objArtista: artistas) {
            if(objArtista.equals(pArtista)){
                return true;
            }
        }
        return false;
    }


    //*******************Manejo de canciones******************
    public Cancion crearCancion(String nombre, String recurso, String nombreAlbum, Genero genero, Artista artista, Compositor compositor, String fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio){
        if(calificaciones == null){
            calificaciones = new ArrayList<Calificacion>();
        }

        Cancion nuevaCancion = new Cancion(nombre, recurso, nombreAlbum, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
        return nuevaCancion;
    }
    public boolean modificarCancion(int pIdCancion, String pNombreAlbum, double pPrecio){
        Cancion cancionModifica = buscarCancionPorId(pIdCancion);

        if(cancionModifica != null){
            return cancionModifica.modificar(pNombreAlbum, pPrecio);
        }
        return false;
    }
    public boolean eliminarCancion(Cancion pCancion){
        if(existeCancion(pCancion)){
            canciones.remove(pCancion);
            return true;
        }
        return false;
    }
    public ArrayList<Cancion> listarCanciones(){
        return canciones;
    }

    //Canciones de biblioteca general.
    public boolean guardarCancion(Cancion pCancion){
        //TODO condicion para no repetir canciones.
        canciones.add(pCancion);
        return true;
    }
    public Cancion buscarCancionPorId(int pIdCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId() == pIdCancion){
                return objCancion;
            }
        }
        return null;
    }
    public int obtenerIndiceCancion(Cancion pCancion){
        int indice = 0;
        for (Cancion objCancion: canciones) {
            if(objCancion.equals(pCancion)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeCancion(Cancion pCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.equals(pCancion)){
                return true;
            }
        }
        return false;
    }

    //Canciones de cliente
    public boolean agregarCancionABibliotecaUsuario(int pIdCliente, Cancion pCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().agregarCancion(pCancion);
        }
        return false;
    }
    public ArrayList<Cancion> listarCancionesDeBibliotecaUsuario(int pIdCliente){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().getCanciones();
        }
        return null;
    }
    public Cancion buscarCancionEnBibliotecaUsuario(int pIdCliente, int pIdCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().buscarCancion(pIdCancion);
        }
        return null;
    }
    public boolean removerCancionDeBibliotecaUsuario(int pIdCliente, Cancion pCancion){
        Cliente clienteModifica = (Cliente) buscarUsuarioPorId(pIdCliente);

        if(clienteModifica != null){
            return clienteModifica.getBiblioteca().removerCancion(pCancion);
        }
        return false;
    }

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
    public boolean crearCompositor(String nombre, String apellidos, String paisNacimiento, String fechaNacimiento, int edad){
        Compositor nuevoCompositor = new Compositor(nombre, apellidos, paisNacimiento, fechaNacimiento, edad);

        //Evitar repeticion de datos.
        if(!existeCompositor(nuevoCompositor)){
            compositores.add(nuevoCompositor);
            return true;
        }

        return false;
    }
    public boolean modificarCompositor(int pIdCompositor, String pNombre, String pApellidos){
        Compositor compositorModifica = buscarCompositorPorId(pIdCompositor);

        if(compositorModifica != null){
            return compositorModifica.modificar(pNombre, pApellidos);
        }

        return false;
    }
    public boolean eliminarCompositor(Compositor pCompositor){
        if(existeCompositor(pCompositor)){
            compositores.remove(pCompositor);
            return true;
        }
        return false;
    }
    public ArrayList<Compositor> listarCompositores(){
        return compositores;
    }

    public Compositor buscarCompositorPorId(int idCompositor){
        for (Compositor objCompositor: compositores) {
            if(objCompositor.getId() == idCompositor){
                return objCompositor;
            }
        }
        return null;
    }
    public int obtenerIndiceCompositor(Compositor pCompositor){
        int indice = 0;
        for (Compositor objCompositor: compositores) {
            if(objCompositor.equals(pCompositor)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeCompositor(Compositor pCompositor){
        for (Compositor objCompositor: compositores) {
            if(objCompositor.equals(pCompositor)){
                return true;
            }
        }
        return false;
    }


    //******************Manejo de Generos******************
    public boolean crearGenero(String nombre, String descripcion){
        Genero nuevoGenero = new Genero(nombre, descripcion);

        //Evitar repeticion de datos.
        if(!existeGenero(nuevoGenero)){
            generos.add(nuevoGenero);
            return true;
        }

        return false;
    }
    public boolean modificarGenero(int pIdGenero, String pNombre, String pDesc){
        Genero generoModifica = buscarGeneroPorId(pIdGenero);

        if(generoModifica != null){
            return generoModifica.modificar(pNombre, pDesc);
        }

        return false;
    }
    public boolean eliminarGenero(Genero pGenero){
        if(existeGenero(pGenero)){
            generos.remove(pGenero);
            return true;
        }

        return false;
    }
    public ArrayList<Genero> listarGeneros(){
        return generos;
    }

    public Genero buscarGeneroPorId(int pIdGenero){
        for (Genero objGenero: generos) {
            if(objGenero.getId() == pIdGenero){
                return objGenero;
            }
        }
        return null;
    }
    public int obtenerIndiceGenero(Genero pGenero){
        int indice = 0;
        for (Genero objGenero: generos) {
            if(objGenero.equals(pGenero)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeGenero(Genero pGenero){
        for (Genero objGenero: generos) {
            if(objGenero.equals(pGenero)){
                return true;
            }
        }
        return false;
    }


    //*****************Manejo de Paises********************
    public boolean crearPais(String nombrePais, String descripcion){
        Pais nuevoPais = new Pais(nombrePais, descripcion);

        //Evitar repeticion de datos.
        if(!existePais(nuevoPais)){
            paises.add(nuevoPais);
            return true;
        }

        return false;
    }
    public boolean modificarPais(int pIdPais, String pNombre, String pDescripcion){
        Pais paisModifica = buscarPaisPorId(pIdPais);

        if(paisModifica != null){
            return paisModifica.modificar(pNombre, pDescripcion);
        }

        return false;
    }
    public boolean eliminarPais(Pais pPais){
        if(existePais(pPais)){
            paises.remove(pPais);
            return true;
        }

        return false;
    }
    public ArrayList<Pais> listarPaises(){
        return paises;
    }

    public Pais buscarPaisPorId(int pIdPais){
        for (Pais objPais: paises) {
            if(objPais.getId() == pIdPais){
                return objPais;
            }
        }
        return null;
    }
    public int obtenerIndicePais(Pais pPais){
        int indice = 0;
        for (Pais objPais: paises) {
            if(objPais.equals(pPais)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existePais(Pais pPais){
        for (Pais objPais: paises) {
            if(objPais.equals(pPais)){
                return true;
            }
        }
        return false;
    }
}
